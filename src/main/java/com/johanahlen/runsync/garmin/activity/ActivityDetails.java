package com.johanahlen.runsync.garmin.activity;

public class ActivityDetails {
    private Long activityId;
    private Boolean isDetailsAvailable;
    private Long measurementCount;
    private Long metricsCount;
    private DetailsMeasurement[] measurements;
    private Metrics[] metrics;

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

    public DetailsMeasurement[] getMeasurements() {
        return measurements;
    }

    public Metrics[] getMetrics() {
        return metrics;
    }
}
