package com.example.labrinth_game;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView gamesListView;
    private List<Game> gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gamesListView = findViewById(R.id.gamesList);
       TextView notAvailableView = findViewById(R.id.notAvailable);

        gamesList = GameManager.getGamesList(this);

        Set<Integer> unavailableSensors = checkSensorAvailability(gamesList);
        if (unavailableSensors.size() > 0) {
            gamesListView.setAdapter(new GamesListAdapter(getAvailableGames(gamesList,
                    unavailableSensors), this));
            String notAvailableText = getResources().getString(R.string.not_available_intro);
            for (int sensorRes : unavailableSensors) {
                notAvailableText += getResources().getString(sensorRes).toLowerCase() + ", ";
            }
            notAvailableText = notAvailableText.substring(0, notAvailableText.length() - 2);
            notAvailableView.setText(notAvailableText);
        } else {
            gamesListView.setAdapter(new GamesListAdapter(gamesList, this));
            notAvailableView.setVisibility(View.GONE);
        }

        gamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (view.getId()) {
                    case R.string.ball:
                        intent = new Intent(MainActivity.this,
                                GameBallActivity.class);
                        break;
                    default:
                        return;
                }
                GameStatus.updateStatus(MainActivity.this, view.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (Game game : gamesList) {
            game.loadStatus(this);
        }
        ((BaseAdapter)gamesListView.getAdapter()).notifyDataSetChanged();
    }

    private Set<Integer> checkSensorAvailability(List<Game> gamesList) {
        Set<Integer> sensorsNeeded = new HashSet<>();
        for (Game game: gamesList) {
            for (int sensor: game.getSensorsResources()){
                if (!sensorsNeeded.contains(sensor)) {
                    sensorsNeeded.add(sensor);
                }
            }
        }
        Set<Integer> unavailableSensors = new HashSet<>();
        SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (mSensorManager != null) {
            for (int sensor: sensorsNeeded) {
                    if (mSensorManager.getDefaultSensor(GameManager.getSensorConstant(sensor)) == null) {
                        unavailableSensors.add(sensor);
                    }
            }
        }
        return unavailableSensors;
    }

    private List<Game> getAvailableGames(List<Game> gamesList, Set<Integer> unavailableSensors) {
        List<Game> availableGames = new ArrayList<>(gamesList);
        for (Game game : gamesList) {
            for (int sensor: unavailableSensors) {
                if (game.getSensorsResources().contains(sensor)) {
                    availableGames.remove(game);
                    break;
                }
            }
        }
        return availableGames;
    }
}
