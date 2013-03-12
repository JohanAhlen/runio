package org.runsync.runkeeper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.runsync.runkeeper.activity.RunKeeperActivity;
import org.runsync.runkeeper.activity.RunKeeperActivitySummary;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunKeeperApiClient {

    @Autowired
    RunKeeperRestTemplate restTemplate;

    public void postActivity(RunKeeperActivity activity) {
        try {
            restTemplate.executePost("https://api.runkeeper.com/fitnessActivities", "application/vnd.com.runkeeper.NewFitnessActivity+json", activity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RunKeeperActivitySummary> retrieveAllActivitySummaries() {
        try {
            ActivitiesGetResponse response = restTemplate.executeGet("https://api.runkeeper.com/fitnessActivities", ActivitiesGetResponse.class);
            List<RunKeeperActivitySummary> summaries = new LinkedList<RunKeeperActivitySummary>();
            summaries.addAll(response.items);
            while (StringUtils.isNotEmpty(response.next)) {
                System.out.println("calling... " + response.next);
                response = restTemplate.executeGet("https://api.runkeeper.com" + response.next, ActivitiesGetResponse.class);
                summaries.addAll(response.items);
            }
            return summaries;
        } catch (IOException e) {
            throw new RuntimeException("Failed retrieving summaries for RunKeeper activities, see nested exception.", e);
        }
    }

    public void deleteActivity(long activityId) {
        try {
            restTemplate.executeDelete("https://api.runkeeper.com/fitnessActivities/" + activityId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ActivitiesGetResponse {
        private List<RunKeeperActivitySummary> items;
        private long size;
        private String next;
    }

}
