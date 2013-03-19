package org.runio.garmin.activity;

public class GarminQuery {

    private GarminFilters filters;
    private String sortOrder;
    private String sortField;
    private String activityStart;
    private String activitiesPerPage;
    private String explore;
    private String ignoreUntitled;
    private String ignoreNonGPS;

    public GarminFilters getFilters() {
        return filters;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public String getActivityStart() {
        return activityStart;
    }

    public String getActivitiesPerPage() {
        return activitiesPerPage;
    }

    public String getExplore() {
        return explore;
    }

    public String getIgnoreUntitled() {
        return ignoreUntitled;
    }

    public String getIgnoreNonGPS() {
        return ignoreNonGPS;
    }
}
