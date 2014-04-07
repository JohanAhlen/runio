package org.runio.runkeeper.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import org.joda.time.LocalDateTime;
import org.runio.garmin.activity.*;

public class RunKeeperActivity {

    public static final String TYPE_RUNNING = "Running";
    public static final String TYPE_CYCLING = "Cycling";
    public static final String TYPE_MOUNTAIN_BIKING = "Mountain Biking";
    public static final String TYPE_WALKING = "Walking";
    public static final String TYPE_DOWNHILL_SKIING = "Downhill Skiing";
    public static final String TYPE_CROSS_COUNTRY_SKIING = "Cross-Country Skiing";
    public static final String TYPE_SNOWBOARDING = "Snowboarding";
    public static final String TYPE_SKATING = "Skating";
    public static final String TYPE_SWIMMING = "Swimming";
    public static final String TYPE_WHEELCHAIR = "Wheelchair";
    public static final String TYPE_ROWING = "Rowing";
    public static final String TYPE_ELLIPTICAL = "Elliptical";
    public static final String TYPE_OTHER = "Other";

    public static final String EQUIPMENT_NONE = "None";
    public static final String EQUIPMENT_TREADMILL = "Treadmill";
    public static final String EQUIPMENT_STATIONARY_BIKE = "Stationary Bike";
    public static final String EQUIPMENT_ELLIPTICAL = "Elliptical";
    public static final String EQUIPMENT_ROW_MACHINE = "Row Machine";

    private static final String ENTRY_MDOE_API = "API";
    private static final String ENTRY_MDOE_WEB = "Web";

    @SerializedName("activity")
    private String activityPublicUrl;
    @SerializedName("average_heart_rate")
    private Integer averageHeartRateInBpm;
    @SerializedName("calories")
    private List<RunKeeperCalorie> caloricBurnMeasurements;
    @SerializedName("comments")
    private String commentsUri;
    @SerializedName("distance")
    private List<RunKeeperDistance> distanceMeasurements;
    @SerializedName("entry_mode")
    private String entryMode;
    private String equipment;
    @SerializedName("heart_rate")
    private List<RunKeeperHeartRate> heartRates;
    @SerializedName("images")
    private List<RunKeeperImage> images;
    @SerializedName("is_live")
    private Boolean live;
    @SerializedName("nearest_background_activity")
    private String nearestBackgroundActivityUri;
    @SerializedName("nearest_diabetes")
    private String nearestDiabetesUri;
    @SerializedName("nearest_general_measurement")
    private String nearestGeneralMeasurementUri;
    @SerializedName("nearest_nutrition")
    private String nearestNutritionUri;
    @SerializedName("nearest_sleep")
    private String nearestSleepMeasurementUri;
    @SerializedName("nearest_strength_training_activity")
    private String nearestStengthTrainingActivityUri;
    @SerializedName("nearest_teammate_background_activities")
    private List<String> nearestTeammateBackgroundActivityUris;
    @SerializedName("nearest_teammate_diabetes")
    private List<String> nearestTeammateDiabetetesUris;
    @SerializedName("nearest_teammate_fitness_activities")
    private List<String> nearestTeammateFitnessActivityUris;
    @SerializedName("nearest_teammate_general_measurements")
    private List<String> nearestTeammateGeneralMeasurementUris;
    @SerializedName("nearest_teammate_nutrition")
    private List<String> nearestTeammateNutritionUris;
    @SerializedName("nearest_teammate_sleep")
    private List<String> nearestTeammateSleepUris;
    @SerializedName("nearest_teammate_strength_training_activities")
    private List<String> nearestTeammateStrengthTrainingActivityUris;
    @SerializedName("nearest_teammate_weight")
    private List<String> nearestTeammateWeightUris;
    @SerializedName("nearest_weight")
    private String nearestWeightUri;
    @SerializedName("next")
    private String nextActivityUri;
    private String notes;
    private List<RunKeeperPathPoint> path;
    @SerializedName("has_path")
    private Boolean pathAvailable;
    @SerializedName("post_to_facebook")
    private Boolean postToFacebook;
    @SerializedName("post_to_twitter")
    private Boolean postToTwitter;
    @SerializedName("previous")
    private String previousActivityUri;
    @SerializedName("start_time")
    private LocalDateTime startTime;
    private String source;
    @SerializedName("total_calories")
    private Double totalCalories;
    @SerializedName("climb")
    private Double totalClimbInM;
    @SerializedName("total_distance")
    private Double totalDistanceInM;
    @SerializedName("duration")
    private Double totalDurationInSeconds;
    private String type;
    @SerializedName("secondary_type")
    private String typeOther;
    private String uri;
    @SerializedName("userID")
    private Integer userId;

    RunKeeperActivity() {
    }

    public static class Builder {

        private RunKeeperActivity runKeeperActivity;

        public Builder() {
            runKeeperActivity = new RunKeeperActivity();
        }

        private static void verifyUom(String fieldId, String expectedUom, String actualUom) {
            if (!expectedUom.equals(actualUom)) {
                throw new IllegalArgumentException(String.format("Expected %s unit to be '%s', got '%s' instead.", fieldId, expectedUom, actualUom));
            }
        }

        private static List<RunKeeperHeartRate> generateHeartRates(GarminActivityDetails details) {
            int heartRateIndex = findMetricsIndexForKey("directHeartRate", details.getMeasurements());
            int secondsElapsedIndex = findMetricsIndexForKey("sumElapsedDuration", details.getMeasurements());
            List<RunKeeperHeartRate> result = new ArrayList<RunKeeperHeartRate>(details.getMetrics().length);
            for (GarminMetrics metric : details.getMetrics()) {
                if ((int) metric.getMetrics()[heartRateIndex] < 190)
                    result.add(new RunKeeperHeartRate(metric.getMetrics()[secondsElapsedIndex], (int) metric.getMetrics()[heartRateIndex]));
            }

            return result;
        }

        private static List<RunKeeperPathPoint> generatePaths(GarminActivityDetails details) {
            int secondsElapsedIndex = findMetricsIndexForKey("sumElapsedDuration", details.getMeasurements());
            int latitudeIndex = findMetricsIndexForKey("directLatitude", details.getMeasurements());
            int longitudeIndex = findMetricsIndexForKey("directLongitude", details.getMeasurements());
            int altitudeIndex = findMetricsIndexForKey("directElevation", details.getMeasurements());

            List<RunKeeperPathPoint> result = new ArrayList<RunKeeperPathPoint>(details.getMetrics().length);
            for (GarminMetrics metric : details.getMetrics()) {
                if (metric.getMetrics()[latitudeIndex] == 0.0 || metric.getMetrics()[longitudeIndex] == 0.0) {
                    continue;
                }
                result.add(new RunKeeperPathPoint(
                        (int) metric.getMetrics()[secondsElapsedIndex],
                        metric.getMetrics()[latitudeIndex],
                        metric.getMetrics()[longitudeIndex],
                        metric.getMetrics()[altitudeIndex]));
            }

            result.get(0).markAsStartPoint();
            result.get(result.size() - 1).markAsEndPoint();
            return result;
        }

        private static boolean hasMetricsForPath(GarminDetailsMeasurement[] measurements) {
            for (GarminDetailsMeasurement measurement : measurements) {
                if ("directLatitude".equals(measurement.getKey())) {
                    return true;
                }
            }
            return false;
        }

        private static int findMetricsIndexForKey(String key, GarminDetailsMeasurement[] measurements) {
            for (GarminDetailsMeasurement measurement : measurements) {
                if (key.equals(measurement.getKey())) {
                    return measurement.getMetricsIndex();
                }
            }
            throw new IllegalArgumentException("Could not find key '" + key + "' in measurements.");
        }

        public RunKeeperActivity build() {
            return runKeeperActivity;
        }

        public Builder fromGarminActivity(GarminActivitySummary summary, GarminActivityDetails details) {
            GarminMeasurementSummaries measurementSummaries = summary.getActivitySummary();

            boolean heartRateRecorded = measurementSummaries.getWeightedMeanHeartRate() != null;

            verifyUom("SumDistance", "kilometer", measurementSummaries.getSumDistance().getUom());
            verifyUom("SumElapsedDuration", "second", measurementSummaries.getSumElapsedDuration().getUom());
            if (heartRateRecorded) {
                verifyUom("WeightedMeanHeartRate", "bpm", measurementSummaries.getWeightedMeanHeartRate().getUom());
            }
            verifyUom("SumEnergy", "kilocalorie", measurementSummaries.getSumEnergy().getUom());

            Builder builder = fromGarminActivityType(summary.getActivityType())
                    .withStartTime(LocalDateTime.parse(measurementSummaries.getBeginTimestamp().getValue(), GarminActivitySummary.GARMIN_TIME_FORMAT))
                    .withTotalDistanceInKm(Double.parseDouble(measurementSummaries.getSumDistance().getValue()))
                    .withTotalDurationInSeconds(Double.parseDouble(measurementSummaries.getSumElapsedDuration().getValue()))
                    .withTotalCalories(Double.parseDouble(measurementSummaries.getSumEnergy().getValue()))
                    .withNotes(summary.getActivityDescription())
                    .withPostToFacebook(false)
                    .withPostToTwitter(false);
            if (heartRateRecorded) {
                builder.withAverageHeartRateInBpm((int) Double.parseDouble(measurementSummaries.getWeightedMeanHeartRate().getValue()))
                        .withHeartRates(generateHeartRates(details));
            }
            if (hasMetricsForPath(details.getMeasurements())) {
                builder.withPath(generatePaths(details));
            }
            return builder;
        }

        public Builder fromGarminActivityType(GarminActivityType garminType) {
            switch (garminType.getKey()) {
                case GarminActivityType.TYPE_KEY_CROSS_COUNTRY_SKIING :
                    runKeeperActivity.type = RunKeeperActivity.TYPE_CROSS_COUNTRY_SKIING;
                    break;
                case GarminActivityType.TYPE_KEY_INDOOR_CYCLING :
                    runKeeperActivity.type = RunKeeperActivity.TYPE_CYCLING;
                    break;
                default : runKeeperActivity.type = RunKeeperActivity.TYPE_RUNNING;
            }
            return this;
        }

        public Builder withType(String type) {
            runKeeperActivity.type = type;
            return this;
        }

        public Builder withStartTime(LocalDateTime startTime) {
            runKeeperActivity.startTime = startTime;
            return this;
        }

        public Builder withTotalDistanceInKm(double totalDistanceInKm) {
            runKeeperActivity.totalDistanceInM = totalDistanceInKm;
            return this;
        }

        public Builder withTotalDurationInSeconds(double totalDurationInSeconds) {
            runKeeperActivity.totalDurationInSeconds = totalDurationInSeconds;
            return this;
        }

        public Builder withAverageHeartRateInBpm(int averageHeartRateInBpm) {
            runKeeperActivity.averageHeartRateInBpm = averageHeartRateInBpm;
            return this;
        }

        public Builder withHeartRates(List<RunKeeperHeartRate> heartRates) {
            runKeeperActivity.heartRates = Collections.unmodifiableList(heartRates);
            return this;
        }

        public Builder withTotalCalories(double totalCalories) {
            runKeeperActivity.totalCalories = totalCalories;
            return this;
        }

        public Builder withNotes(String notes) {
            runKeeperActivity.notes = notes;
            return this;
        }

        public Builder withPath(List<RunKeeperPathPoint> path) {
            runKeeperActivity.path = Collections.unmodifiableList(path);
            return this;
        }

        public Builder withPostToFacebook(boolean postToFacebook) {
            runKeeperActivity.postToFacebook = postToFacebook;
            return this;
        }

        public Builder withPostToTwitter(boolean postToTwitter) {
            runKeeperActivity.postToTwitter = postToTwitter;
            return this;
        }
    }

    public String getActivityPublicUrl() {
        return activityPublicUrl;
    }

    public Integer getAverageHeartRateInBpm() {
        return averageHeartRateInBpm;
    }

    public List<RunKeeperCalorie> getCaloricBurnMeasurements() {
        return caloricBurnMeasurements;
    }

    public String getCommentsUri() {
        return commentsUri;
    }

    public List<RunKeeperDistance> getDistanceMeasurements() {
        return distanceMeasurements;
    }

    public String getEntryMode() {
        return entryMode;
    }

    public String getEquipment() {
        return equipment;
    }

    public List<RunKeeperHeartRate> getHeartRates() {
        return heartRates;
    }

    public List<RunKeeperImage> getImages() {
        return images;
    }

    public Boolean getLive() {
        return live;
    }

    public String getNearestBackgroundActivityUri() {
        return nearestBackgroundActivityUri;
    }

    public String getNearestDiabetesUri() {
        return nearestDiabetesUri;
    }

    public String getNearestGeneralMeasurementUri() {
        return nearestGeneralMeasurementUri;
    }

    public String getNearestNutritionUri() {
        return nearestNutritionUri;
    }

    public String getNearestSleepMeasurementUri() {
        return nearestSleepMeasurementUri;
    }

    public String getNearestStengthTrainingActivityUri() {
        return nearestStengthTrainingActivityUri;
    }

    public List<String> getNearestTeammateBackgroundActivityUris() {
        return nearestTeammateBackgroundActivityUris;
    }

    public List<String> getNearestTeammateDiabetetesUris() {
        return nearestTeammateDiabetetesUris;
    }

    public List<String> getNearestTeammateFitnessActivityUris() {
        return nearestTeammateFitnessActivityUris;
    }

    public List<String> getNearestTeammateGeneralMeasurementUris() {
        return nearestTeammateGeneralMeasurementUris;
    }

    public List<String> getNearestTeammateNutritionUris() {
        return nearestTeammateNutritionUris;
    }

    public List<String> getNearestTeammateSleepUris() {
        return nearestTeammateSleepUris;
    }

    public List<String> getNearestTeammateStrengthTrainingActivityUris() {
        return nearestTeammateStrengthTrainingActivityUris;
    }

    public List<String> getNearestTeammateWeightUris() {
        return nearestTeammateWeightUris;
    }

    public String getNearestWeightUri() {
        return nearestWeightUri;
    }

    public String getNextActivityUri() {
        return nextActivityUri;
    }

    public String getNotes() {
        return notes;
    }

    public List<RunKeeperPathPoint> getPath() {
        return path;
    }

    public Boolean getPathAvailable() {
        return pathAvailable;
    }

    public Boolean getPostToFacebook() {
        return postToFacebook;
    }

    public Boolean getPostToTwitter() {
        return postToTwitter;
    }

    public String getPreviousActivityUri() {
        return previousActivityUri;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getSource() {
        return source;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public Double getTotalClimbInM() {
        return totalClimbInM;
    }

    public Double getTotalDistanceInM() {
        return totalDistanceInM;
    }

    public Double getTotalDurationInSeconds() {
        return totalDurationInSeconds;
    }

    public String getType() {
        return type;
    }

    public String getTypeOther() {
        return typeOther;
    }

    public String getUri() {
        return uri;
    }

    public Integer getUserId() {
        return userId;
    }
}