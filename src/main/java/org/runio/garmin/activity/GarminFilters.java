package org.runio.garmin.activity;

import com.google.gson.annotations.SerializedName;

public class GarminFilters {

    private String userId;

    @SerializedName("userId_oper")
    private String userIdOper;

    public String getUserId() {
        return userId;
    }

    public String getUserIdOper() {
        return userIdOper;
    }
}
