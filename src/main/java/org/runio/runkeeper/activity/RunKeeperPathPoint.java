package org.runio.runkeeper.activity;

import com.google.gson.annotations.SerializedName;

public class RunKeeperPathPoint {

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

    public RunKeeperPathPoint(int secondsSinceStartOfActivity, double latitude, double longitude, double altitudeInMeters) {
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

    public double getSecondsSinceStartOfActivity() {
        return secondsSinceStartOfActivity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitudeInMeters() {
        return altitudeInMeters;
    }

    public String getType() {
        return type;
    }
}
