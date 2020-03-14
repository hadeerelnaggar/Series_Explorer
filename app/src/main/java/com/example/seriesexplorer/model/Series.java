package com.example.seriesexplorer.model;

import com.google.gson.annotations.SerializedName;

public class Series {
    @SerializedName("id")
    private String id;
    @SerializedName("original_name")
    private String name;
    @SerializedName("overview")
    String description;
    @SerializedName("poster_path")
    String imageURL;
    @SerializedName("vote_average")
    double rating;

    public Series(String id, String name, String description, String imageURL, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.rating = rating;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
