package org.runio.runkeeper.activity;

import com.google.gson.annotations.SerializedName;

public class RunKeeperCalorie {
    @SerializedName("timestamp")
    private double secondsSinceStartOfActivity;
    private double calories;

    public double getSecondsSinceStartOfActivity() {
        return secondsSinceStartOfActivity;
    }

    public double getCalories() {
        return calories;
    }
}