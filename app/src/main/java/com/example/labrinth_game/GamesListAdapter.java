package com.example.labrinth_game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class GamesListAdapter extends BaseAdapter{

    private List<Game> gamesList;

    private Context context;

    GamesListAdapter(List<Game> gamesList, Context context) {
        this.gamesList = gamesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return gamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return gamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.activity_menu, parent, false);
            } else {
                return null;
            }
        }

        Game game = gamesList.get(position);

        convertView.setId(game.getId());

        TextView name = convertView.findViewById(R.id.name);
        name.setText(game.getName());

        return convertView;
    }
}
