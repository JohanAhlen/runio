package org.runsync.runkeeper.activity;

import com.google.gson.annotations.SerializedName;

public class RunKeeperPath {

    private static final String TYPE_START = "start";
    private static final String TYPE_END = "end";
    private static final String TYPE_GPS = "gps";

    @SerializedName("timestamp")
    private double secondsSinceStartOfActivity;
    private double latitude;
    private double longitude;
    @SerializedName("altitude")
    private double altitudeInMeters;
    private String type;

    public RunKeeperPath(int secondsSinceStartOfActivity, double latitude, double longitude, double altitudeInMeters) {
        this.secondsSinceStartOfActivity = secondsSinceStartOfActivity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitudeInMeters = altitudeInMeters;
        this.type = TYPE_GPS;
    }

    public void markAsStartPoint() {
        this.type = TYPE_START;
    }

    public void markAsEndPoint() {
        this.type = TYPE_END;
    }
}
