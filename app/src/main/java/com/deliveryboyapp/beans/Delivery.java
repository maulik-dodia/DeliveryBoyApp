package com.deliveryboyapp.beans;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.Gson;

public class Delivery {

    private String id;
    private String imageUrl;
    private Location location;
    private String description;

    public Delivery(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Delivery fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Delivery.class);
    }

    public static DiffUtil.ItemCallback<Delivery> DIFF_CALLBACK = new DiffUtil.ItemCallback<Delivery>() {

        @Override
        public boolean areItemsTheSame(@NonNull Delivery oldItem, @NonNull Delivery newItem) {
            return oldItem.id.equalsIgnoreCase(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Delivery oldItem, @NonNull Delivery newItem) {
            return oldItem.equals(newItem);
        }
    };
}