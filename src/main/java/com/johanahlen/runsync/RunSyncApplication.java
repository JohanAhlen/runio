package com.johanahlen.runsync;

import java.util.LinkedList;
import java.util.List;
import com.johanahlen.runsync.context.ApplicationConfig;
import com.johanahlen.runsync.garmin.GarminConnectClient;
import com.johanahlen.runsync.garmin.activity.GarminActivityDetails;
import com.johanahlen.runsync.garmin.activity.GarminActivitySummary;
import com.johanahlen.runsync.runkeeper.RunKeeperApiClient;
import com.johanahlen.runsync.runkeeper.RunKeeperRestTemplate;
import com.johanahlen.runsync.runkeeper.activity.RunKeeperActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RunSyncApplication {

    @Autowired
    private GarminConnectClient garminConnectClient;

    @Autowired
    private RunKeeperApiClient runKeeperApiClient;

    @Autowired
    RunKeeperRestTemplate restTemplate;

    public void syncGarminToRunKeeper(long garminStartActivityId) {
        List<GarminActivitySummary> garminActivities = garminConnectClient.retrieveAllActivitySummaries();
        List<RunKeeperActivity> runKeeperActivities = new LinkedList<RunKeeperActivity>();

        for(GarminActivitySummary garminActivitySummary : garminActivities) {
            if (garminActivitySummary.getActivityId() > garminStartActivityId) {
                GarminActivityDetails garminActivityDetails = garminConnectClient.retrieveActivityDetails(garminActivitySummary.getActivityId());
                runKeeperActivities.add(RunKeeperActivity.fromGarminActivity(garminActivitySummary, garminActivityDetails));
            }

        }
        for (RunKeeperActivity runKeeperActivity : runKeeperActivities) {
            runKeeperApiClient.postActivity(runKeeperActivity);
        }
    }

    private void readAllActivitiesFromRunKeeper() {
        runKeeperApiClient.retrieveAllActivitySummaries();
        garminConnectClient.retrieveAllActivitySummaries();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        appContext.start();
        appContext.registerShutdownHook();
        RunSyncApplication application = appContext.getBean(RunSyncApplication.class);
        application.readAllActivitiesFromRunKeeper();
        //application.syncGarminToRunKeeper(0l);
    }
}
