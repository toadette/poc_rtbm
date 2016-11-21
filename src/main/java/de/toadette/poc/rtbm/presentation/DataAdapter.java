package de.toadette.poc.rtbm.presentation;

import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.toadette.poc.rtbm.R;


class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<String> items;
    private MainActivity parent;

    DataAdapter(List<String> items, MainActivity parent) {
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
        String text = String.format(parent.getResources().getString(R.string.description_item),
                items.get(i));
        viewHolder.textView2.setText(text);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private ImageButton action;
        private ImageButton action2;

        ViewHolder(View view) {
            super(view);

            textView1 = (TextView) view.findViewById(R.id.tv_1);
            textView2 = (TextView) view.findViewById(R.id.tv_2);
            action = (ImageButton) view.findViewById(R.id.openMap);
            action2 = (ImageButton) view.findViewById(R.id.triggerCall);
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(parent, MapActivity.class);
                    Location location = new Location("");
                    location.setLatitude(-118.408745);
                    location.setLongitude(33.941998);
                    intent.putExtra("location", location);
                    intent.putExtra("name", textView1.getText());
                    intent.putExtra("description", textView2.getText());
                    parent.startActivity(intent);
                }
            });
            action2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parent.sendNotification(textView1.getText().toString());
                }
            });
        }
    }

}