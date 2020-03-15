package com.example.seriesexplorer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "wishlistseries")
public class Series implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    @NonNull
    private String id;
    @SerializedName("original_name")
    private String name;
    @SerializedName("overview")
    String description;
    @SerializedName("poster_path")
    String imageURL;
    @SerializedName("vote_average")
    double rating;
    @SerializedName("homepage")
    String homepage;

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Ignore
    public Series(String id, String name, String description, String imageURL, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.rating = rating;
        this.homepage=null;
    }

    public Series(String id, String name, String description, String imageURL, double rating, String homepage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.rating = rating;
        this.homepage = homepage;
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
