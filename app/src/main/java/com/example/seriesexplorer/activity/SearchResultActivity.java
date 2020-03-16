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

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.example.seriesexplorer.R;
import com.example.seriesexplorer.RoomDatabase.AppDataBase;
import com.example.seriesexplorer.adapter.SeriesRecyclerViewAdapter;
import com.example.seriesexplorer.model.Series;
import com.example.seriesexplorer.model.SeriesResponse;
import com.example.seriesexplorer.rest.SeriesApiHandler;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "07ad75bcb5f5916bf449961df56037a6";
    Series series;
    @BindView(R.id.searchresultlist)
    RecyclerView resultlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        handleIntent(getIntent());
        ButterKnife.bind(this);
        resultlist.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        ButterKnife.bind(this);
        resultlist.setLayoutManager(new LinearLayoutManager(this));
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            connectAndGetApi(query);
        }
    }
    private void connectAndGetApi(String query){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        SeriesApiHandler seriesApiService = retrofit.create(SeriesApiHandler.class);
        Call<SeriesResponse> call = seriesApiService.searchSeries(API_KEY,query);
        call.enqueue(new Callback<SeriesResponse>() {
            @Override
            public void onResponse(Call<SeriesResponse> call, Response<SeriesResponse> response) {
                List<Series> serieslist=response.body().getResults();
                resultlist.setAdapter(new SeriesRecyclerViewAdapter(serieslist,R.layout.seriesrecyclerviewitem,getApplicationContext()));
            }
            @Override
            public void onFailure(Call<SeriesResponse> call, Throwable t) {

            }
        });
    }
}
