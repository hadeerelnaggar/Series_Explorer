package com.example.seriesexplorer.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.seriesexplorer.R;
import com.example.seriesexplorer.adapter.SeriesRecyclerViewAdapter;
import com.example.seriesexplorer.model.Series;
import com.example.seriesexplorer.model.SeriesDetails;
import com.example.seriesexplorer.model.SeriesResponse;
import com.example.seriesexplorer.rest.SeriesApiHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.example.seriesexplorer.activity.MainActivity.BASE_URL;

public class seriesdetailsactivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "07ad75bcb5f5916bf449961df56037a6";
    Series series;
    @BindView(R.id.pagelayout)
    LinearLayout pagelayout;
    @BindView(R.id.series_name)
    TextView series_name;
    @BindView(R.id.series_description)
    TextView Series_description;
    @BindView(R.id.go)
    Button homepage;
    @BindView(R.id.addtowishlist)
    Button addtowishlist;
    String homepageurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seriesdetailsactivity);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra("seriesId");
        String name = getIntent().getStringExtra("seriesname");
        String description = getIntent().getStringExtra("seriesdesription");
        double rating = getIntent().getDoubleExtra("rating", 0.0);
        String imageUrl = getIntent().getStringExtra("imageurl");
        series = new Series(id, name, description, imageUrl, rating);
        connectAndGetApi();
        series_name.setText(series.getName());
        Series_description.setText(series.getDescription());
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW , Uri.parse(homepageurl));
                startActivity( browse );
            }
        });
        addtowishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void connectAndGetApi(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        SeriesApiHandler seriesApiService = retrofit.create(SeriesApiHandler.class);
        System.out.println(series.getId());
        Call<SeriesDetails> call = seriesApiService.getSeriesDetails(series.getId(),API_KEY);
        call.enqueue(new Callback<SeriesDetails>() {
            @Override
            public void onResponse(Call<SeriesDetails> call, Response<SeriesDetails> response) {
                if(response.isSuccessful()){
                SeriesDetails seriesDetails = response.body();
                  homepageurl=seriesDetails.getHomepage();
                }
            }
            @Override
            public void onFailure(Call<SeriesDetails> call, Throwable t) {

            }
        });
    }
}
