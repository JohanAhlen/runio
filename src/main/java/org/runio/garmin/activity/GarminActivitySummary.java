package org.runio.garmin.activity;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class GarminActivitySummary {

    public static final DateTimeFormatter GARMIN_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZoneUTC();

    private long activityId;

    private String activityName;
    private String activityDescription;

    private GarminMeasurementSummaries activitySummary;
    private GarminTimeZone activityTimeZone;
    private GarminActivityType activityType;
    private GarminUploadSource device;
    private String deviceId;
    private String deviceImageUrl;
    private String displayname;
    private GarminField eventType;
    private String externalId;
    private boolean favorite;
    private boolean garminSwimAlgorithm;
    private boolean isActivityEdited;
    private boolean isBarometricCapable;
    private boolean isDeviceReleased;
    private boolean isElevationCorrected;
    private boolean ispr;
    private boolean isSwimAlgorithmCapable;
    private boolean isTitled;
    private String locationName;
    private String localizedSpeedLabel;
    private String localizedPaceLabel;
    private long numTrackpoints;
    private GarminField privacy;
    private GarminUploadSource uploadApplication;
    private LocalDateTime uploadDate;
    private long userId;
    private String username;
    private String[] userRoles;
    private LocalDateTime updatedDate;
    private String updatedDateFormatted;

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

    public boolean getTitled() {
        return isTitled;
    }

    public boolean getElevationCorrected() {
        return isElevationCorrected;
    }

    public boolean getBarometricCapable() {
        return isBarometricCapable;
    }

    public boolean getSwimAlgorithmCapable() {
        return isSwimAlgorithmCapable;
    }

    public boolean getActivityEdited() {
        return isActivityEdited;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public boolean getIspr() {
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

    public GarminUploadSource getUploadApplication() {
        return uploadApplication;
    }

    public GarminUploadSource getDevice() {
        return device;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceImageUrl() {
        return deviceImageUrl;
    }

    public boolean getDeviceReleased() {
        return isDeviceReleased;
    }

    public String getExternalId() {
        return externalId;
    }

    public GarminField getPrivacy() {
        return privacy;
    }

    public long getNumTrackpoints() {
        return numTrackpoints;
    }

    public GarminActivityType getActivityType() {
        return activityType;
    }

    public GarminField getEventType() {
        return eventType;
    }

    public GarminTimeZone getActivityTimeZone() {
        return activityTimeZone;
    }

    public String getLocalizedSpeedLabel() {
        return localizedSpeedLabel;
    }

    public String getLocalizedPaceLabel() {
        return localizedPaceLabel;
    }

    public GarminMeasurementSummaries getActivitySummary() {
        return activitySummary;
    }

    public boolean getGarminSwimAlgorithm() {
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

}
