package com.johanahlen.runsync.garmin.activity;

import com.google.gson.annotations.SerializedName;

public class ActivitySummary {
    @SerializedName("SumSampleCountElevation")
    private SummaryMeasurement sumSampleCountElevation;
    @SerializedName("SumSampleCountSpeed")
    private SummaryMeasurement sumSampleCountSpeed;
    @SerializedName("SumSampleCountHeartRate")
    private SummaryMeasurement sumSampleCountHeartRate;
    @SerializedName("MaxElevation")
    private SummaryMeasurement maxElevation;
    @SerializedName("SumSampleCountMovingSpeed")
    private SummaryMeasurement sumSampleCountMovingSpeed;
    @SerializedName("EndLatitude")
    private SummaryMeasurement endLatitude;
    @SerializedName("EndLongitude")
    private SummaryMeasurement endLongitude;
    @SerializedName("WeightedMeanMovingSpeed")
    private SummaryMeasurement weightedMeanMovingSpeed;
    @SerializedName("MaxHeartRate")
    private HeartRateMeasurement maxHeartRate;
    @SerializedName("MinSpeed")
    private SummaryMeasurement minSpeed;
    @SerializedName("MinPace")
    private SummaryMeasurement minPace;
    @SerializedName("WeightedMeanHeartRate")
    private HeartRateMeasurement weightedMeanHeartRate;
    @SerializedName("MaxSpeed")
    private SummaryMeasurement maxSpeed;
    @SerializedName("MaxPace")
    private SummaryMeasurement maxPace;
    @SerializedName("SumEnergy")
    private SummaryMeasurement sumEnergy;
    @SerializedName("SumElapsedDuration")
    private SummaryMeasurement sumElapsedDuration;
    @SerializedName("BeginLatitude")
    private SummaryMeasurement beginLatitude;
    @SerializedName("SumMovingDuration")
    private SummaryMeasurement sumMovingDuration;
    @SerializedName("WeightedMeanSpeed")
    private SummaryMeasurement weightedMeanSpeed;
    @SerializedName("WeightedMeanPace")
    private SummaryMeasurement weightedMeanPace;
    @SerializedName("SumDuration")
    private SummaryMeasurement sumDuration;
    @SerializedName("SumDistance")
    private SummaryMeasurement sumDistance;
    @SerializedName("BeginLongitude")
    private SummaryMeasurement beginLongitude;
    @SerializedName("SumSampleCountTimestamp")
    private SummaryMeasurement sumSampleCountTimestamp;
    @SerializedName("MinHeartRate")
    private HeartRateMeasurement minHeartRate;
    @SerializedName("MinElevation")
    private SummaryMeasurement minElevation;
    @SerializedName("GainElevation")
    private SummaryMeasurement gainElevation;
    @SerializedName("SumSampleCountLongitude")
    private SummaryMeasurement sumSampleCountLongitude;
    @SerializedName("BeginTimestamp")
    private SummaryMeasurement beginTimestamp;
    @SerializedName("SumSampleCountLatitude")
    private SummaryMeasurement sumSampleCountLatitude;
    @SerializedName("EndTimestamp")
    private SummaryMeasurement endTimestamp;
    @SerializedName("LossElevation")
    private SummaryMeasurement lossElevation;

    public SummaryMeasurement getSumSampleCountElevation() {
        return sumSampleCountElevation;
    }

    public SummaryMeasurement getSumSampleCountSpeed() {
        return sumSampleCountSpeed;
    }

    public SummaryMeasurement getSumSampleCountHeartRate() {
        return sumSampleCountHeartRate;
    }

    public SummaryMeasurement getMaxElevation() {
        return maxElevation;
    }

    public SummaryMeasurement getSumSampleCountMovingSpeed() {
        return sumSampleCountMovingSpeed;
    }

    public SummaryMeasurement getEndLatitude() {
        return endLatitude;
    }

    public SummaryMeasurement getEndLongitude() {
        return endLongitude;
    }

    public SummaryMeasurement getWeightedMeanMovingSpeed() {
        return weightedMeanMovingSpeed;
    }

    public HeartRateMeasurement getMaxHeartRate() {
        return maxHeartRate;
    }

    public SummaryMeasurement getMinSpeed() {
        return minSpeed;
    }

    public SummaryMeasurement getMinPace() {
        return minPace;
    }

    public HeartRateMeasurement getWeightedMeanHeartRate() {
        return weightedMeanHeartRate;
    }

    public SummaryMeasurement getMaxSpeed() {
        return maxSpeed;
    }

    public SummaryMeasurement getMaxPace() {
        return maxPace;
    }

    public SummaryMeasurement getSumEnergy() {
        return sumEnergy;
    }

    public SummaryMeasurement getSumElapsedDuration() {
        return sumElapsedDuration;
    }

    public SummaryMeasurement getBeginLatitude() {
        return beginLatitude;
    }

    public SummaryMeasurement getSumMovingDuration() {
        return sumMovingDuration;
    }

    public SummaryMeasurement getWeightedMeanSpeed() {
        return weightedMeanSpeed;
    }

    public SummaryMeasurement getWeightedMeanPace() {
        return weightedMeanPace;
    }

    public SummaryMeasurement getSumDuration() {
        return sumDuration;
    }

    public SummaryMeasurement getSumDistance() {
        return sumDistance;
    }

    public SummaryMeasurement getBeginLongitude() {
        return beginLongitude;
    }

    public SummaryMeasurement getSumSampleCountTimestamp() {
        return sumSampleCountTimestamp;
    }

    public HeartRateMeasurement getMinHeartRate() {
        return minHeartRate;
    }

    public SummaryMeasurement getMinElevation() {
        return minElevation;
    }

    public SummaryMeasurement getGainElevation() {
        return gainElevation;
    }

    public SummaryMeasurement getSumSampleCountLongitude() {
        return sumSampleCountLongitude;
    }

    public SummaryMeasurement getBeginTimestamp() {
        return beginTimestamp;
    }

    public SummaryMeasurement getSumSampleCountLatitude() {
        return sumSampleCountLatitude;
    }

    public SummaryMeasurement getEndTimestamp() {
        return endTimestamp;
    }

    public SummaryMeasurement getLossElevation() {
        return lossElevation;
    }
}
