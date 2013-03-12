package org.runsync.garmin.activity;

public class GarminActivityDetails {

    private long activityId;
    private boolean isDetailsAvailable;
    private long measurementCount;
    private long metricsCount;
    private GarminDetailsMeasurement[] measurements;
    private GarminMetrics[] metrics;

    public long getActivityId() {
        return activityId;
    }

    public boolean getDetailsAvailable() {
        return isDetailsAvailable;
    }

    public long getMeasurementCount() {
        return measurementCount;
    }

    public long getMetricsCount() {
        return metricsCount;
    }

    public GarminDetailsMeasurement[] getMeasurements() {
        return measurements;
    }

    public GarminMetrics[] getMetrics() {
        return metrics;
    }
}
