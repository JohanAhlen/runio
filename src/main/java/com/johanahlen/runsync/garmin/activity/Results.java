package com.johanahlen.runsync.garmin.activity;

import java.util.List;

public class Results {

    private List<ActivityWrapper> activities;
    private Long totalFound;
    private Long currentPage;
    private Long totalPages;
    private BoundingBox boundingBox;
    private Query query;

    public List<ActivityWrapper> getActivities() {
        return activities;
    }

    public Long getTotalFound() {
        return totalFound;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public Query getQuery() {
        return query;
    }
}
