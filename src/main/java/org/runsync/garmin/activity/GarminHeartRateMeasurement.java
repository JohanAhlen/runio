package org.runsync.garmin.activity;

public class GarminHeartRateMeasurement extends GarminSummaryMeasurement {

    private GarminSummaryMeasurement percentMax;
    private GarminSummaryMeasurement zones;

    public GarminSummaryMeasurement getPercentMax() {
        return percentMax;
    }

    public GarminSummaryMeasurement getZones() {
        return zones;
    }
}
