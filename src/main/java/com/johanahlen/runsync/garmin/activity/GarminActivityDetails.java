package com.johanahlen.runsync.garmin.activity;

public class GarminActivityDetails {
    private Long activityId;
    private Boolean isDetailsAvailable;
    private Long measurementCount;
    private Long metricsCount;
    private GarminDetailsMeasurement[] measurements;
    private GarminMetrics[] metrics;

    public Long getActivityId() {
        return activityId;
    }

    public Boolean getDetailsAvailable() {
        return isDetailsAvailable;
    }

    public Long getMeasurementCount() {
        return measurementCount;
    }

    public Long getMetricsCount() {
        return metricsCount;
    }

    public GarminDetailsMeasurement[] getMeasurements() {
        return measurements;
    }

    public GarminMetrics[] getMetrics() {
        return metrics;
    }
}
