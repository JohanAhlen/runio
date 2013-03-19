package org.runio.runkeeper;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.runio.runkeeper.activity.RunKeeperActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RunKeeperApiClient {

    @Autowired
    private RestTemplate runKeeperRestTemplate;

    public void postActivity(RunKeeperActivity activity) {

        runKeeperRestTemplate.postForLocation("https://api.runkeeper.com/fitnessActivities", activity);
    }

    public List<RunKeeperActivity> retrieveAllReducedActivities() {
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
        return runKeeperRestTemplate.getForObject("https://api.runkeeper.com" + reducedActivity.getUri(), RunKeeperActivity.class);
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
