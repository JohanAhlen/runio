package org.runsync;

import java.util.LinkedList;
import java.util.List;
import org.runsync.context.ApplicationConfig;
import org.runsync.garmin.GarminConnectClient;
import org.runsync.garmin.activity.GarminActivityDetails;
import org.runsync.garmin.activity.GarminActivitySummary;
import org.runsync.runkeeper.RunKeeperApiClient;
import org.runsync.runkeeper.activity.RunKeeperActivity;
import org.runsync.runkeeper.activity.RunKeeperActivitySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RunSyncApplication {

    @Autowired
    private GarminConnectClient garminConnectClient;

    @Autowired
    private RunKeeperApiClient runKeeperApiClient;

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
        List<RunKeeperActivitySummary> summaries = runKeeperApiClient.retrieveAllActivitySummaries();
        System.out.println(summaries.size());
        //garminConnectClient.retrieveAllActivitySummaries();
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
