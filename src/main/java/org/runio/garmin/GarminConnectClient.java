package org.runio.garmin;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.gson.annotations.SerializedName;
import org.runio.garmin.activity.GarminBoundingBox;
import org.runio.garmin.activity.GarminActivityDetails;
import org.runio.garmin.activity.GarminActivitySummary;
import org.runio.garmin.activity.GarminQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GarminConnectClient {

    private static final Logger log = LoggerFactory.getLogger(GarminConnectClient.class);

    @Autowired
    private RestTemplate garminRestTemplate;

    public List<GarminActivitySummary> retrieveAllActivitySummaries() {
        log.info("Retrieving summaries for Garmin activities...");

        SearchServiceResponse response = garminRestTemplate.getForObject("https://connect.garmin.com/proxy/activity-search-service-1.2/json/activities", SearchServiceResponse.class);
        // TODO: Retrieve multiple pages instead of just the first.

        List<GarminActivitySummary> summaries = new LinkedList<GarminActivitySummary>();

        for (ActivityWrapper wrapper : response.results.activities) {
            summaries.add(wrapper.activity);
        }

        log.info("Found {} Garmin activity summaries.", summaries.size());
        return summaries;
    }

    public GarminActivityDetails retrieveActivityDetails(long activityId) {
        log.info("Retrieving activity details for activity {}", activityId);
        ActivityDetailsResponse activityDetailsResponse = garminRestTemplate.getForObject("https://connect.garmin.com/proxy/activity-service-1.3/json/activityDetails/" + activityId, ActivityDetailsResponse.class);
        return activityDetailsResponse.garminActivityDetails;
    }

    private static class SearchServiceResponse {
        private ActivitySearchServiceResult results;
    }

    private static class ActivitySearchServiceResult {
        private List<ActivityWrapper> activities;
        private long totalFound;
        private long currentPage; // Can be used to retrieve multiple pages
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