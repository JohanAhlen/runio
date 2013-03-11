package com.johanahlen.runsync.garmin.activity;

import com.google.gson.annotations.SerializedName;

public class ActivityDetailsResponse {

    @SerializedName("com.garmin.activity.details.json.ActivityDetails")
    private ActivityDetails activityDetails;

    public ActivityDetails getActivityDetails() {
        return activityDetails;
    }
}
