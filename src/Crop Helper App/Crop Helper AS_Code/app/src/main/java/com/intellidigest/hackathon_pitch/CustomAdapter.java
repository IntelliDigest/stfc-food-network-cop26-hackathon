package com.intellidigest.hackathon_pitch;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<RecyclerItem> localDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView crop, seedName, price;
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);

            crop = view.findViewById(R.id.crop_name);
            seedName = view.findViewById(R.id.seed_name);
            price = view.findViewById(R.id.price);
            image = view.findViewById(R.id.image);
        }
    }

    public CustomAdapter(List<RecyclerItem> localDataSet, Context context) {
        this.localDataSet = localDataSet;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.crop_suggestion, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final RecyclerItem recyclerItem = localDataSet.get(position);

        viewHolder.crop.setText(recyclerItem.getCrop());
        viewHolder.seedName.setText(recyclerItem.getSeedName());
        viewHolder.price.setText(recyclerItem.getPrice());
        viewHolder.image.setImageResource(recyclerItem.getImage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
