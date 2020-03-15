package com.example.seriesexplorer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.seriesexplorer.R;
import com.example.seriesexplorer.RoomDatabase.AppDataBase;
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
    AppDataBase mDb;
    @BindView(R.id.seriesrecyclerview)
    RecyclerView seriesList;
    boolean popular=true;
    boolean toprated=false;
    boolean favorites=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seriesList=null;
        setContentView(R.layout.activity_main);
        setTitle("Popular Series");
        ButterKnife.bind(this);
        seriesList.setLayoutManager(new LinearLayoutManager(this));
        if(!favorites)
         connectAndGetApi();
        mDb=AppDataBase.getInstance(getApplicationContext());
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.findseriesmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                popular=true;
                toprated=false;
                favorites=false;
                connectAndGetApi();
                setTitle("Popular Series");
                return true;
            case R.id.top_rated:
                popular=false;
                toprated=true;
                favorites=false;
                connectAndGetApi();
                setTitle("Top Ranked Series");
                return true;
            case R.id.favorites:
                popular=false;
                toprated=false;
                favorites=true;
                getfavorites();
                setTitle("My WishList");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public  void getfavorites(){
        LiveData<List<Series>>series=mDb.taskDao().loadAllTasks();
        series.observe(this, new Observer<List<Series>>()
        {
            @Override
            public void onChanged(List<Series> series1) {
                Log.d("From on CHANGED", "onChanged:  receiving database update from liveData");
                seriesList.setAdapter(new SeriesRecyclerViewAdapter(series1,R.layout.seriesrecyclerviewitem,getApplicationContext()));
            }
        });
    }
    public void connectAndGetApi(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            }
        SeriesApiHandler seriesApiService = retrofit.create(SeriesApiHandler.class);
        Call<SeriesResponse> call;
        if(popular)
            call = seriesApiService.getPopularSeries(API_KEY);
        else
            call = seriesApiService.getTopRatedSeries(API_KEY);
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
