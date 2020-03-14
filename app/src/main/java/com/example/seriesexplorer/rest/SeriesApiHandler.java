package com.example.seriesexplorer.rest;

import android.os.AsyncTask;

import com.example.seriesexplorer.model.Series;
import com.example.seriesexplorer.model.SeriesDetails;
import com.example.seriesexplorer.model.SeriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SeriesApiHandler  {

    @GET("tv/popular")
    Call<SeriesResponse> getPopularSeries(@Query("api_key") String apiKey);

    @GET("tv/top_rated")
    Call<SeriesResponse> getTopRatedSeries(@Query("api_key") String apiKey);

    @GET("tv/latest")
    Call<SeriesResponse> getLatestSeries(@Query("api_key") String apiKey);

    @GET("tv/{tv_id}")
    Call<SeriesDetails> getSeriesDetails(@Path("tv_id") String tv_id, @Query("api_key") String apiKey);





}
