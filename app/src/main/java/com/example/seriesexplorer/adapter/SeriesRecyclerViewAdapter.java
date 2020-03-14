package com.example.seriesexplorer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seriesexplorer.R;
import com.example.seriesexplorer.activity.seriesdetailsactivity;
import com.example.seriesexplorer.model.Series;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeriesRecyclerViewAdapter extends RecyclerView.Adapter<SeriesHolder> {
    List<Series> series;
    private int rowLayout;
    Context context;
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";

    public SeriesRecyclerViewAdapter(List<Series> series, int rowLayout, Context context) {
        this.series = series;
        this.rowLayout=rowLayout;
        this.context=context;
    }

    @NonNull
    @Override
    public SeriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SeriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesHolder holder, int position) {
        holder.seriesDescription.setMaxLines(2);
        holder.seriesname.setText(series.get(position).getName());
        holder.seriesDescription.setText(series.get(position).getDescription());
        String image_url = IMAGE_URL_BASE_PATH + series.get(position).getImageURL();
        String rate="rating:"+series.get(position).getRating();
        holder.rating.setText(rate);
        Picasso.get().load(image_url).into(holder.seriesImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,seriesdetailsactivity.class);
                intent.putExtra("seriesId",series.get(position).getId());
                intent.putExtra("seriesname",series.get(position).getName());
                intent.putExtra("seriesdesription",series.get(position).getDescription());
                intent.putExtra("rating",series.get(position).getRating());
                intent.putExtra("imageurl",series.get(position).getImageURL());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

}
class SeriesHolder extends RecyclerView.ViewHolder{
    ImageView seriesImage;
    TextView seriesname;
    TextView seriesDescription;
    TextView rating;

    public SeriesHolder(@NonNull View itemView) {
        super(itemView);
        seriesname=itemView.findViewById(R.id.series_name);
        seriesImage=itemView.findViewById(R.id.series_image);
        seriesDescription=itemView.findViewById(R.id.series_description);
        rating=itemView.findViewById(R.id.rate);
    }

}
