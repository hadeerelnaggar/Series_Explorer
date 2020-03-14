package com.example.seriesexplorer.model;

import com.google.gson.annotations.SerializedName;

public class SeriesDetails extends Series {
    @SerializedName("homepage")
    String homepage;

    public SeriesDetails(String id, String name, String description, String imageURL, double rating, String homepage) {
        super(id, name, description, imageURL, rating);
        this.homepage=homepage;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
