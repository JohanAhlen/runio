package com.johanahlen.runsync.garmin.activity;

import org.joda.time.LocalDateTime;

public class GarminActivity {

    private long activityId;
    private String activityName;
    private String activityDescription;
    private String locationName;
    private Boolean isTitled;
    private Boolean isElevationCorrected;
    private Boolean isBarometricCapable;
    private Boolean isSwimAlgorithmCapable;
    private Boolean isActivityEdited;
    private Boolean favorite;
    private Boolean ispr;
    private long userId;
    private String username;
    private String displayname;
    private LocalDateTime uploadDate;
    private UploadSource uploadApplication;
    private UploadSource device;
    private String deviceId;
    private String deviceImageUrl;
    private Boolean isDeviceReleased;
    private String externalId;
    private Field privacy;
    private Long numTrackpoints;
    private ActivityType activityType;
    private Field eventType;
    private TimeZone activityTimeZone;
    private String localizedSpeedLabel;
    private String localizedPaceLabel;
    private ActivitySummary activitySummary;
    //private ? totalLaps;
    private Boolean garminSwimAlgorithm;
    private LocalDateTime updatedDate;
    private String updatedDateFormatted;
    private String[] userRoles;

    private ActivityDetails activityDetails;

    public long getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public String getLocationName() {
        return locationName;
    }

    public Boolean getTitled() {
        return isTitled;
    }

    public Boolean getElevationCorrected() {
        return isElevationCorrected;
    }

    public Boolean getBarometricCapable() {
        return isBarometricCapable;
    }

    public Boolean getSwimAlgorithmCapable() {
        return isSwimAlgorithmCapable;
    }

    public Boolean getActivityEdited() {
        return isActivityEdited;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public Boolean getIspr() {
        return ispr;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayname() {
        return displayname;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public UploadSource getUploadApplication() {
        return uploadApplication;
    }

    public UploadSource getDevice() {
        return device;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceImageUrl() {
        return deviceImageUrl;
    }

    public Boolean getDeviceReleased() {
        return isDeviceReleased;
    }

    public String getExternalId() {
        return externalId;
    }

    public Field getPrivacy() {
        return privacy;
    }

    public Long getNumTrackpoints() {
        return numTrackpoints;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public Field getEventType() {
        return eventType;
    }

    public TimeZone getActivityTimeZone() {
        return activityTimeZone;
    }

    public String getLocalizedSpeedLabel() {
        return localizedSpeedLabel;
    }

    public String getLocalizedPaceLabel() {
        return localizedPaceLabel;
    }

    public ActivitySummary getActivitySummary() {
        return activitySummary;
    }

    public Boolean getGarminSwimAlgorithm() {
        return garminSwimAlgorithm;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public String getUpdatedDateFormatted() {
        return updatedDateFormatted;
    }

    public String[] getUserRoles() {
        return userRoles;
    }

    public ActivityDetails getActivityDetails() {
        return activityDetails;
    }
}
