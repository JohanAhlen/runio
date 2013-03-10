package com.johanahlen.runsync.garmin.activity;

public class HeartRateMeasurement extends SummaryMeasurement {
    private SummaryMeasurement percentMax;
    private SummaryMeasurement zones;

    public SummaryMeasurement getPercentMax() {
        return percentMax;
    }

    public SummaryMeasurement getZones() {
        return zones;
    }
}
