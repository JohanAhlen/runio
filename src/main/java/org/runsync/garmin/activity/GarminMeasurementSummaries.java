package org.runsync.garmin.activity;

import com.google.gson.annotations.SerializedName;

public class GarminMeasurementSummaries {

    @SerializedName("SumSampleCountElevation")
    private GarminSummaryMeasurement sumSampleCountElevation;

    @SerializedName("SumSampleCountSpeed")
    private GarminSummaryMeasurement sumSampleCountSpeed;

    @SerializedName("SumSampleCountHeartRate")
    private GarminSummaryMeasurement sumSampleCountHeartRate;

    @SerializedName("MaxElevation")
    private GarminSummaryMeasurement maxElevation;

    @SerializedName("SumSampleCountMovingSpeed")
    private GarminSummaryMeasurement sumSampleCountMovingSpeed;

    @SerializedName("EndLatitude")
    private GarminSummaryMeasurement endLatitude;

    @SerializedName("EndLongitude")
    private GarminSummaryMeasurement endLongitude;

    @SerializedName("WeightedMeanMovingSpeed")
    private GarminSummaryMeasurement weightedMeanMovingSpeed;

    @SerializedName("MaxHeartRate")
    private GarminHeartRateMeasurement maxHeartRate;

    @SerializedName("MinSpeed")
    private GarminSummaryMeasurement minSpeed;

    @SerializedName("MinPace")
    private GarminSummaryMeasurement minPace;

    @SerializedName("WeightedMeanHeartRate")
    private GarminHeartRateMeasurement weightedMeanHeartRate;

    @SerializedName("MaxSpeed")
    private GarminSummaryMeasurement maxSpeed;

    @SerializedName("MaxPace")
    private GarminSummaryMeasurement maxPace;

    @SerializedName("SumEnergy")
    private GarminSummaryMeasurement sumEnergy;

    @SerializedName("SumElapsedDuration")
    private GarminSummaryMeasurement sumElapsedDuration;

    @SerializedName("BeginLatitude")
    private GarminSummaryMeasurement beginLatitude;

    @SerializedName("SumMovingDuration")
    private GarminSummaryMeasurement sumMovingDuration;

    @SerializedName("WeightedMeanSpeed")
    private GarminSummaryMeasurement weightedMeanSpeed;

    @SerializedName("WeightedMeanPace")
    private GarminSummaryMeasurement weightedMeanPace;

    @SerializedName("SumDuration")
    private GarminSummaryMeasurement sumDuration;

    @SerializedName("SumDistance")
    private GarminSummaryMeasurement sumDistance;

    @SerializedName("BeginLongitude")
    private GarminSummaryMeasurement beginLongitude;

    @SerializedName("SumSampleCountTimestamp")
    private GarminSummaryMeasurement sumSampleCountTimestamp;

    @SerializedName("MinHeartRate")
    private GarminHeartRateMeasurement minHeartRate;

    @SerializedName("MinElevation")
    private GarminSummaryMeasurement minElevation;

    @SerializedName("GainElevation")
    private GarminSummaryMeasurement gainElevation;

    @SerializedName("SumSampleCountLongitude")
    private GarminSummaryMeasurement sumSampleCountLongitude;

    @SerializedName("BeginTimestamp")
    private GarminSummaryMeasurement beginTimestamp;

    @SerializedName("SumSampleCountLatitude")
    private GarminSummaryMeasurement sumSampleCountLatitude;

    @SerializedName("EndTimestamp")
    private GarminSummaryMeasurement endTimestamp;

    @SerializedName("LossElevation")
    private GarminSummaryMeasurement lossElevation;

    public GarminSummaryMeasurement getSumSampleCountElevation() {
        return sumSampleCountElevation;
    }

    public GarminSummaryMeasurement getSumSampleCountSpeed() {
        return sumSampleCountSpeed;
    }

    public GarminSummaryMeasurement getSumSampleCountHeartRate() {
        return sumSampleCountHeartRate;
    }

    public GarminSummaryMeasurement getMaxElevation() {
        return maxElevation;
    }

    public GarminSummaryMeasurement getSumSampleCountMovingSpeed() {
        return sumSampleCountMovingSpeed;
    }

    public GarminSummaryMeasurement getEndLatitude() {
        return endLatitude;
    }

    public GarminSummaryMeasurement getEndLongitude() {
        return endLongitude;
    }

    public GarminSummaryMeasurement getWeightedMeanMovingSpeed() {
        return weightedMeanMovingSpeed;
    }

    public GarminHeartRateMeasurement getMaxHeartRate() {
        return maxHeartRate;
    }

    public GarminSummaryMeasurement getMinSpeed() {
        return minSpeed;
    }

    public GarminSummaryMeasurement getMinPace() {
        return minPace;
    }

    public GarminHeartRateMeasurement getWeightedMeanHeartRate() {
        return weightedMeanHeartRate;
    }

    public GarminSummaryMeasurement getMaxSpeed() {
        return maxSpeed;
    }

    public GarminSummaryMeasurement getMaxPace() {
        return maxPace;
    }

    public GarminSummaryMeasurement getSumEnergy() {
        return sumEnergy;
    }

    public GarminSummaryMeasurement getSumElapsedDuration() {
        return sumElapsedDuration;
    }

    public GarminSummaryMeasurement getBeginLatitude() {
        return beginLatitude;
    }

    public GarminSummaryMeasurement getSumMovingDuration() {
        return sumMovingDuration;
    }

    public GarminSummaryMeasurement getWeightedMeanSpeed() {
        return weightedMeanSpeed;
    }

    public GarminSummaryMeasurement getWeightedMeanPace() {
        return weightedMeanPace;
    }

    public GarminSummaryMeasurement getSumDuration() {
        return sumDuration;
    }

    public GarminSummaryMeasurement getSumDistance() {
        return sumDistance;
    }

    public GarminSummaryMeasurement getBeginLongitude() {
        return beginLongitude;
    }

    public GarminSummaryMeasurement getSumSampleCountTimestamp() {
        return sumSampleCountTimestamp;
    }

    public GarminHeartRateMeasurement getMinHeartRate() {
        return minHeartRate;
    }

    public GarminSummaryMeasurement getMinElevation() {
        return minElevation;
    }

    public GarminSummaryMeasurement getGainElevation() {
        return gainElevation;
    }

    public GarminSummaryMeasurement getSumSampleCountLongitude() {
        return sumSampleCountLongitude;
    }

    public GarminSummaryMeasurement getBeginTimestamp() {
        return beginTimestamp;
    }

    public GarminSummaryMeasurement getSumSampleCountLatitude() {
        return sumSampleCountLatitude;
    }

    public GarminSummaryMeasurement getEndTimestamp() {
        return endTimestamp;
    }

    public GarminSummaryMeasurement getLossElevation() {
        return lossElevation;
    }
}
