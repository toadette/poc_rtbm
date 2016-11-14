package de.toadette.poc.rtbm.presentation;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.toadette.poc.rtbm.R;


class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<String> items;
    private Activity parent;

    DataAdapter(ArrayList<String> items, Activity parent) {
        this.items = items;
        this.parent = parent;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.textView1.setText(items.get(i));
        viewHolder.textView2.setText(R.string.describtion);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private Button action;

        ViewHolder(View view) {
            super(view);

            textView1 = (TextView) view.findViewById(R.id.tv_1);
            textView2 = (TextView) view.findViewById(R.id.tv_2);
            action = (Button) view.findViewById(R.id.openMap);
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(parent, StartActivity.class);
                    Location location = new Location("");
                    location.setLatitude(-118.408745);
                    location.setLongitude(33.941998);
                    intent.putExtra("location", location);
                    parent.startActivity(intent);
                }
            });
        }
    }

}