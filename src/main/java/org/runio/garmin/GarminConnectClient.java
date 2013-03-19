package org.runio.garmin;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import com.google.gson.annotations.SerializedName;
import org.runio.garmin.activity.GarminBoundingBox;
import org.runio.garmin.activity.GarminActivityDetails;
import org.runio.garmin.activity.GarminActivitySummary;
import org.runio.garmin.activity.GarminQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GarminConnectClient {

    private static final String signInUrl = "https://connect.garmin.com/signin?login=login&login:loginUsernameField=%s&login:password=%s&login:signInButton=Sign%%20In&javax.faces.ViewState=j_id2";

    @Autowired
    private RestTemplate garminRestTemplate;

    @Value("${garmin.password}")
    private String password;

    @Value("${garmin.username}")
    private String username;

    private AtomicBoolean signedIn;

    public GarminConnectClient() {
        signedIn = new AtomicBoolean(false);
    }

    private synchronized void signIn() {
        if (!signedIn.get()) {
            garminRestTemplate.postForEntity(String.format(signInUrl, username, password), null, String.class);
            garminRestTemplate.postForEntity(String.format(signInUrl, username, password), null, String.class);
            signedIn.set(true);
        }
    }

    public List<GarminActivitySummary> retrieveAllActivitySummaries() {
        if (!signedIn.get()) {
            signIn();
        }
        SearchServiceResponse response = garminRestTemplate.getForObject("https://connect.garmin.com/proxy/activity-search-service-1.2/json/activities", SearchServiceResponse.class);

        List<GarminActivitySummary> summaries = new LinkedList<GarminActivitySummary>();

        for (ActivityWrapper wrapper : response.results.activities) {
            summaries.add(wrapper.activity);
        }
        // TODO: Retrieve multiple pages instead of just first.

        return summaries;
    }

    public GarminActivityDetails retrieveActivityDetails(long activityId) {
        if (!signedIn.get()) {
            signIn();
        }
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