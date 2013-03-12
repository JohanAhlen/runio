package org.runsync.garmin;

import java.util.LinkedList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import org.runsync.garmin.activity.GarminBoundingBox;
import org.runsync.garmin.activity.GarminActivityDetails;
import org.runsync.garmin.activity.GarminActivitySummary;
import org.runsync.garmin.activity.GarminQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GarminConnectClient {

    @Autowired
    private RestTemplate garminRestTemplate;

    public List<GarminActivitySummary> retrieveAllActivitySummaries() {
        SearchServiceResponse response = garminRestTemplate.getForObject("https://connect.garmin.com/proxy/activity-search-service-1.2/json/activities", SearchServiceResponse.class);

        List<GarminActivitySummary> summaries = new LinkedList<GarminActivitySummary>();

        for (ActivityWrapper wrapper : response.results.activities) {
            summaries.add(wrapper.activity);
        }

        // TODO: Retrieve multiple pages instead of just first.

        return summaries;
    }

    public GarminActivityDetails retrieveActivityDetails(long activityId) {
        ActivityDetailsResponse activityDetailsResponse = garminRestTemplate.getForObject("https://connect.garmin.com/proxy/activity-service-1.3/json/activityDetails/" + activityId, ActivityDetailsResponse.class);
        return activityDetailsResponse.garminActivityDetails;
    }

    private static class SearchServiceResponse {
        private Results results;
    }

    private static class Results {
        private List<ActivityWrapper> activities;
        private long totalFound;
        private long currentPage;
        private long totalPages;
        private GarminBoundingBox boundingBox;
        private GarminQuery query;
    }

    private static class ActivityWrapper {
        private GarminActivitySummary activity;
    }

    private class ActivityDetailsResponse {
        @SerializedName("com.garmin.activity.details.json.ActivityDetails")
        private GarminActivityDetails garminActivityDetails;
    }
}