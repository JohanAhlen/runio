package org.runsync.runkeeper;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.runsync.runkeeper.activity.RunKeeperActivity;
import org.runsync.runkeeper.activity.RunKeeperActivitySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RunKeeperApiClient {

    @Autowired
    private RestTemplate runKeeperRestTemplate;

    public void postActivity(RunKeeperActivity activity) {
        runKeeperRestTemplate.postForLocation("https://api.runkeeper.com/fitnessActivities", activity);
    }

    public List<RunKeeperActivitySummary> retrieveAllActivitySummaries() {
        ActivitiesGetResponse response = runKeeperRestTemplate.getForObject("https://api.runkeeper.com/fitnessActivities", ActivitiesGetResponse.class);

        List<RunKeeperActivitySummary> summaries = new LinkedList<RunKeeperActivitySummary>();
        summaries.addAll(response.items);
        while (StringUtils.isNotEmpty(response.next)) {
            response = runKeeperRestTemplate.getForObject("https://api.runkeeper.com" + response.next, ActivitiesGetResponse.class);
            summaries.addAll(response.items);
        }
        return summaries;
    }

    public void deleteActivity(long activityId) {
        runKeeperRestTemplate.delete("https://api.runkeeper.com/fitnessActivities/" + activityId);
    }

    private static class ActivitiesGetResponse {
        private List<RunKeeperActivitySummary> items;
        private long size;
        private String next;
    }

}
