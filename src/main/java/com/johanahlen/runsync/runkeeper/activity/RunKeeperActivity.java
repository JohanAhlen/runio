package com.johanahlen.runsync.runkeeper.activity;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
import com.johanahlen.runsync.garmin.activity.ActivityDetails;
import com.johanahlen.runsync.garmin.activity.ActivitySummary;
import com.johanahlen.runsync.garmin.activity.DetailsMeasurement;
import com.johanahlen.runsync.garmin.activity.GarminActivity;
import com.johanahlen.runsync.garmin.activity.Metrics;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class RunKeeperActivity {

    public static final String TYPE_RUNNING = "Running";

    private String type;

    @SerializedName("start_time")
    private String startTime; // Sat, 1 Jan 2011 00:00:00

    @SerializedName("total_distance")
    private Double totalDistanceInKm;

    @SerializedName("duration")
    private Double totalDurationInSeconds;

    @SerializedName("average_heart_rate")
    private Integer averageHeartRateInBpm;

    @SerializedName("heart_rate")
    private RunKeeperHeartRate[] heartRates;

    @SerializedName("total_calories")
    private Double totalCalories;

    private String notes;

    private RunKeeperPath[] path;

    @SerializedName("post_to_facebook")
    private Boolean postToFacebook;

    @SerializedName("post_to_twitter")
    private Boolean postToTwitter;

    public static RunKeeperActivity fromGarminActivity(GarminActivity garminActivity) {
        RunKeeperActivity result = new RunKeeperActivity();
        result.type = RunKeeperActivity.TYPE_RUNNING;

        DateTimeFormatter inputFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZoneUTC();
        ActivitySummary activitySummary = garminActivity.getActivitySummary();
        DateTimeFormatter outputFormat = DateTimeFormat.forPattern("EEE, d MMM yyyy HH:mm:ss").withZone(DateTimeZone.forID(activitySummary.getBeginTimestamp().getUom()));
        LocalDateTime beginTime = LocalDateTime.parse(activitySummary.getBeginTimestamp().getValue(), inputFormat);
        result.startTime = outputFormat.print(beginTime);

        verifyUom("SumDistance", "kilometer", activitySummary.getSumDistance().getUom());
        result.totalDistanceInKm = Double.parseDouble(activitySummary.getSumDistance().getValue());

        verifyUom("SumElapsedDuration", "second", activitySummary.getSumElapsedDuration().getUom());
        result.totalDurationInSeconds = Double.parseDouble(activitySummary.getSumElapsedDuration().getValue());

        verifyUom("WeightedMeanHeartRate", "bpm", activitySummary.getWeightedMeanHeartRate().getUom());
        result.averageHeartRateInBpm = (int) Double.parseDouble(activitySummary.getWeightedMeanHeartRate().getValue());

        result.heartRates = generateHeartRates(garminActivity.getActivityDetails());

        verifyUom("SumEnergy", "kilocalorie", activitySummary.getSumEnergy().getUom());
        result.totalCalories = Double.parseDouble(activitySummary.getSumEnergy().getValue());

        result.notes = garminActivity.getActivityDescription();

        result.path = generatePaths(garminActivity.getActivityDetails());

        result.postToFacebook = false;

        result.postToTwitter = false;

        return result;
    }

    private static int findMetricsIndexForKey(String key, DetailsMeasurement[] measurements) {
        for (DetailsMeasurement measurement : measurements) {
            if (key.equals(measurement.getKey())) {
                return measurement.getMetricsIndex();
            }
        }
        throw new IllegalArgumentException("Could not find key '" + key + "' in measurements.");
    }

    private static RunKeeperHeartRate[] generateHeartRates(ActivityDetails details) {
        int heartRateIndex = findMetricsIndexForKey("directHeartRate", details.getMeasurements());
        int secondsElapsedIndex = findMetricsIndexForKey("sumElapsedDuration", details.getMeasurements());
        RunKeeperHeartRate[] result = new RunKeeperHeartRate[details.getMetrics().length];
        Metrics[] metrics = details.getMetrics();
        for (int i = 0; i < result.length; i++) {
            result[i] = new RunKeeperHeartRate(metrics[i].getMetrics()[secondsElapsedIndex], metrics[i].getMetrics()[heartRateIndex].intValue());
        }
        return result;
    }

    private static RunKeeperPath[] generatePaths(ActivityDetails details) {
        int secondsElapsedIndex = findMetricsIndexForKey("sumElapsedDuration", details.getMeasurements());
        int latitudeIndex = findMetricsIndexForKey("directLatitude", details.getMeasurements());
        int longitudeIndex = findMetricsIndexForKey("directLongitude", details.getMeasurements());
        int altitudeIndex = findMetricsIndexForKey("directElevation", details.getMeasurements());

        Metrics[] metrics = details.getMetrics();
        ArrayList<RunKeeperPath> result = new ArrayList<RunKeeperPath>(details.getMetrics().length);
        for (int i = 0; i < metrics.length; i++) {
            if (metrics[i].getMetrics()[latitudeIndex].doubleValue() == 0.0 || metrics[i].getMetrics()[longitudeIndex].doubleValue() == 0.0) {
                continue;
            }
            result.add(new RunKeeperPath(
                    metrics[i].getMetrics()[secondsElapsedIndex].intValue(),
                    metrics[i].getMetrics()[latitudeIndex],
                    metrics[i].getMetrics()[longitudeIndex],
                    metrics[i].getMetrics()[altitudeIndex]));
        }
        result.get(0).markAsStartPoint();
        result.get(result.size() - 1).markAsEndPoint();
        return result.toArray(new RunKeeperPath[result.size()]);
    }

    private static void verifyUom(String fieldId, String expectedUom, String actualUom) {
        if (!expectedUom.equals(actualUom)) {
            throw new IllegalArgumentException("Expected " + fieldId + " unit to be '" + expectedUom + "', got '" + actualUom + "' instead.");
        }
    }

}
