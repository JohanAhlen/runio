package org.runio.runkeeper.activity;

import com.google.gson.annotations.SerializedName;

public class RunKeeperImage {

    @SerializedName("timestamp")
    private double secondsSinceStartOfActivity;

    private double latitude;

    private double longitude;

    private String uri;

    @SerializedName("thumbnail_uri")
    private String thumbnailUri;

    public double getSecondsSinceStartOfActivity() {
        return secondsSinceStartOfActivity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getUri() {
        return uri;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }
}
