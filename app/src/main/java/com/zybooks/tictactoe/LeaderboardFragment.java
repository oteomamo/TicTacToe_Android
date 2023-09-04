package com.zybooks.tictactoe;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**Connected to fragment_leaderboard.xml
* => used for displaying a leaderboard of players**/
public class LeaderboardFragment extends Fragment {
/*
    creates the fragment's user interface. First, it gets the XML layout file
    and initializes a LeaderboardRepository to handle data retrieval.
    Then, it queries the player data from the database,
    organizes it into a list of player records, and sets up a RecyclerView to
    display the leaderboard.
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        LeaderboardRepository leaderboardRepository = LeaderboardRepository.getInstance(requireContext());

        List<PlayerRecord> entries = new ArrayList<>();

        Cursor cursor = leaderboardRepository.getAllPlayers();

        while (cursor.moveToNext()) {
            String playerName = cursor.getString(
                    cursor.getColumnIndexOrThrow(LeaderboardContents.LeaderboardEntry.COLUMN_NAME_PLAYER_NAME));
            int gamesWon = cursor.getInt(
                    cursor.getColumnIndexOrThrow(LeaderboardContents.LeaderboardEntry.COLUMN_NAME_GAMES_WON));
            entries.add(new PlayerRecord(playerName, gamesWon));
        }

        cursor.close();

        RecyclerView recyclerView = rootView.findViewById(R.id.leaderboard_list);
        recyclerView.setAdapter(new LeaderboardAdapter(entries));

        return rootView;
    }



    /* manage the data for a RecyclerView that displays a leaderboard.
    it takes player records and stores it in a member variable mEntries

    */
    private class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardHolder> {

        private final List<PlayerRecord> mEntries;

        public LeaderboardAdapter(List<PlayerRecord> entries) {
            mEntries = entries;
        }

        @NonNull
        @Override
        public LeaderboardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LeaderboardHolder(layoutInflater, parent);
        }
        /*onBindViewHolder method is used to assign specific data from the mEntries list*/
        @Override
        public void onBindViewHolder(LeaderboardHolder holder, int position) {
            PlayerRecord entry = mEntries.get(position);
            holder.bind(entry);
        }

        @Override
        public int getItemCount() {
            return mEntries.size();
        }
    }

    private static class LeaderboardHolder extends RecyclerView.ViewHolder {

        private final TextView mNameTextView;
        private final TextView mScoreTextView;
        /*setting the text values of these views based on the PlayerRecord*/
        public LeaderboardHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_leaderboard, parent, false));
            mNameTextView = itemView.findViewById(R.id.player_name);
            mScoreTextView = itemView.findViewById(R.id.player_score);
        }

        public void bind(PlayerRecord entry) {
            mNameTextView.setText(entry.getPlayerName());
            mScoreTextView.setText(String.valueOf(entry.getGamesWon()));
        }
    }
}
