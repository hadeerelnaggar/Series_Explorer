package com.example.seriesexplorer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;

import com.example.seriesexplorer.R;
import com.example.seriesexplorer.adapter.SeriesRecyclerViewAdapter;
import com.example.seriesexplorer.model.Series;
import com.example.seriesexplorer.model.SeriesResponse;
import com.example.seriesexplorer.rest.SeriesApiHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "07ad75bcb5f5916bf449961df56037a6";
    @BindView(R.id.seriesrecyclerview)
    RecyclerView seriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        seriesList.setLayoutManager(new LinearLayoutManager(this));
        connectAndGetApi();
    }
    public void connectAndGetApi(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            }
        SeriesApiHandler seriesApiService = retrofit.create(SeriesApiHandler.class);
        Call<SeriesResponse> call = seriesApiService.getPopularSeries(API_KEY);
        call.enqueue(new Callback<SeriesResponse>() {
            @Override
            public void onResponse(Call<SeriesResponse> call, Response<SeriesResponse> response) {
                List<Series> series = response.body().getResults();
                seriesList.setAdapter(new SeriesRecyclerViewAdapter(series,R.layout.seriesrecyclerviewitem,getApplicationContext()));
                Log.d(TAG, "Number of movies received: " + series.size());
            }

            @Override
            public void onFailure(Call<SeriesResponse> call, Throwable t) {

            }
        });
    }

}
