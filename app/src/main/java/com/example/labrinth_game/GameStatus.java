package com.example.labrinth_game;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class GameStatus {

    static void updateStatus(Context context, int gameId) {
        SharedPreferences pref = context.getSharedPreferences(String.valueOf(gameId),0);
        if (pref.getInt(Game.STATUS, R.string.not_played) == R.string.not_played) {
            pref.edit().putInt(Game.STATUS, R.string.in_progress).apply();
        }
    }

    public static void updateScore(Context context, int gameId, float score, boolean scoreIsTime){
        SharedPreferences pref = context.getSharedPreferences(String.valueOf(gameId),0);
        if (pref.getInt(Game.STATUS, R.string.not_played) == R.string.in_progress) {
            pref.edit().putInt(Game.STATUS, R.string.done).apply();
        }
        float currentScore = pref.getFloat(Game.SCORE, 0);
        if (score > currentScore || (scoreIsTime && score != 0 && score < currentScore)) {
            pref.edit().putFloat(Game.SCORE, score).apply();
            Toast.makeText(context, context.getResources().getString(R.string.new_highscore),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
