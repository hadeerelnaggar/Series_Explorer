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
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.seriesexplorer.R;
import com.example.seriesexplorer.RoomDatabase.AppDataBase;
import com.example.seriesexplorer.model.Series;
import com.example.seriesexplorer.rest.SeriesApiHandler;
import com.squareup.picasso.Picasso;

public class seriesdetailsactivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private final static String API_KEY = "07ad75bcb5f5916bf449961df56037a6";
    Series series;
    @BindView(R.id.pagelayout)
    LinearLayout pagelayout;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.series_name)
    TextView series_name;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.series_description)
    TextView Series_description;
    @BindView(R.id.go)
    Button homepage;
    @BindView(R.id.addtowishlist)
    Button addtowishlist;
    AppDataBase mDb;
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seriesdetailsactivity);
        ButterKnife.bind(this);
        series=(Series)getIntent().getSerializableExtra("series");
        mDb=AppDataBase.getInstance(getApplicationContext());
        disableWishListButton();
        String image_url = IMAGE_URL_BASE_PATH + series.getImageURL();
        if(image_url!=null)
            Picasso.get().load(image_url).into(image);
        if(series.getHomepage()==null)
        {
             connectAndGetApi();
        }
        series_name.setText(series.getName());
        rating.setText(Double.toString(series.getRating())+"/10");
        if(series.getHomepage()==null){
            homepage.setClickable(false);
        }
        Series_description.setText(series.getDescription());
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW , Uri.parse(series.getHomepage()));
                startActivity( browse );
            }
        });
        addtowishlist.setOnClickListener(new View.OnClickListener() {
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mDb.taskDao().insertSeries(series);
                    disableWishListButton();
                }
            });
            @Override
            public void onClick(View v) {
                thread.start();
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
        Call<Series> call = seriesApiService.getSeriesDetails(series.getId(),API_KEY);
        call.enqueue(new Callback<Series>() {
            @Override
            public void onResponse(Call<Series> call, Response<Series> response) {
                if(response.isSuccessful()){
                    series = response.body();
                }
            }
            @Override
            public void onFailure(Call<Series> call, Throwable t) {

            }
        });
    }
    public void disableWishListButton(){
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                Series check=mDb.taskDao().getTaskById(series.getId());
                if(check==null){
                    addtowishlist.setClickable(true);
                    addtowishlist.setFocusable(true);
                }
                else{
                    addtowishlist.setClickable(false);
                    addtowishlist.setFocusable(false);
                    addtowishlist.setAlpha((float) 0.7);
                }
            }
        });
        thread.start();
    }
}
