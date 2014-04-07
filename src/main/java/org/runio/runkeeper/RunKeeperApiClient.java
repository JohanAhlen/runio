package org.runio.runkeeper;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.runio.runkeeper.activity.RunKeeperActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RunKeeperApiClient {

    private static final Logger log = LoggerFactory.getLogger(RunKeeperApiClient.class);


    @Autowired
    private RestTemplate runKeeperRestTemplate;

    public void postActivity(RunKeeperActivity activity) {
        log.info("Posting activity to RunKeeper.");
        runKeeperRestTemplate.postForLocation("https://api.runkeeper.com/fitnessActivities", activity);
    }

    public List<RunKeeperActivity> retrieveAllReducedActivities() {
        log.info("Retrieving all reduced activities from RunKeeper.");
        ActivitiesGetResponse response = runKeeperRestTemplate.getForObject("https://api.runkeeper.com/fitnessActivities", ActivitiesGetResponse.class);
        List<RunKeeperActivity> summaries = new LinkedList<RunKeeperActivity>();
        summaries.addAll(response.items);
        while (StringUtils.isNotEmpty(response.next)) {
            response = runKeeperRestTemplate.getForObject("https://api.runkeeper.com" + response.next, ActivitiesGetResponse.class);
            summaries.addAll(response.items);
        }
        return summaries;
    }

    public RunKeeperActivity retrieveCompleteActivity(RunKeeperActivity reducedActivity) {
        log.info("Retrieving complete activity from RunKeeper.");
        return runKeeperRestTemplate.getForObject("https://api.runkeeper.com" + reducedActivity.getUri(), RunKeeperActivity.class);
    }

    public void updateNotes(String activityId, String notes) {
        RunKeeperActivity activity = new RunKeeperActivity.Builder().withNotes(notes).build();
        runKeeperRestTemplate.put("https://api.runkeeper.com/fitnessActivities/" + activityId, activity);
    }

    public void deleteActivity(long activityId) {
        runKeeperRestTemplate.delete("https://api.runkeeper.com/fitnessActivities/" + activityId);
    }

    private static class ActivitiesGetResponse {
        private List<RunKeeperActivity> items;
        private long size;
        private String next;
    }

}
