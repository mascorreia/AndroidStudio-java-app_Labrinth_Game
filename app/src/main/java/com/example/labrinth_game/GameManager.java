package com.example.labrinth_game;

import android.content.Context;
import android.hardware.Sensor;

import java.util.ArrayList;
import java.util.List;

class GameManager {

    static List<Game> getGamesList(Context context) {
        List<Game> gamesList = new ArrayList<>();
        gamesList.add(new Game(context, R.string.ball, R.string.accelerometer));
        return gamesList;
    }

    static int getSensorConstant(int sensorRes) {
        switch (sensorRes) {
            case R.string.accelerometer:
                return Sensor.TYPE_ACCELEROMETER;
            default:
            return 0;
        }
    }
}
