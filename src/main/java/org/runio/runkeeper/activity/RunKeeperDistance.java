package org.runio.runkeeper.activity;

import com.google.gson.annotations.SerializedName;

public class RunKeeperDistance {
    @SerializedName("timestamp")
    private double secondsSinceStartOfActivity;

    @SerializedName("distance")
    private double distanceInKm;

    public double getSecondsSinceStartOfActivity() {
        return secondsSinceStartOfActivity;
    }

    public double getDistanceInKm() {
        return distanceInKm;
    }
}
