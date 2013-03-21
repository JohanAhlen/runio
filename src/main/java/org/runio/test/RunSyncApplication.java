package org.runio.test;

import java.util.List;
import org.runio.context.ApplicationConfig;
import org.runio.garmin.GarminConnectClient;
import org.runio.garmin.activity.GarminActivitySummary;
import org.runio.runkeeper.RunKeeperApiClient;
import org.runio.runkeeper.activity.RunKeeperActivity;
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
//        List<RunKeeperActivity> runKeeperActivities = new LinkedList<RunKeeperActivity>();
//
//        for(GarminActivitySummary garminActivitySummary : garminActivities) {
//            if (garminActivitySummary.getActivityId() >= garminStartActivityId) {
//                GarminActivityDetails garminActivityDetails = garminConnectClient.retrieveActivityDetails(garminActivitySummary.getActivityId());
//                runKeeperActivities.add(new RunKeeperActivity.Builder().fromGarminActivity(garminActivitySummary, garminActivityDetails).build());
//            }
//        }
//        for (RunKeeperActivity runKeeperActivity : runKeeperActivities) {
//            runKeeperApiClient.postActivity(runKeeperActivity);
//        }
    }

    private void readAllActivitiesFromRunKeeper() {
        List<RunKeeperActivity> reducedActivities = runKeeperApiClient.retrieveAllReducedActivities();
        RunKeeperActivity completeActivity = runKeeperApiClient.retrieveCompleteActivity(reducedActivities.get(0));
        System.out.println(reducedActivities.size());
        //garminConnectClient.retrieveAllReducedActivities();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        appContext.start();
        appContext.registerShutdownHook();
        RunSyncApplication application = appContext.getBean(RunSyncApplication.class);
        //application.readAllActivitiesFromRunKeeper();
        application.syncGarminToRunKeeper(0L);
    }
}
