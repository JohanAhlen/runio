package org.runio.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.runio.context.ApplicationConfig;
import org.runio.garmin.GarminConnectClient;
import org.runio.garmin.activity.GarminActivityDetails;
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

    public void syncGarminToRunKeeper(long garminActivityId) {
        List<GarminActivitySummary> garminActivities = garminConnectClient.retrieveAllActivitySummaries();
        List<RunKeeperActivity> runKeeperActivities = new LinkedList<>();

        for(GarminActivitySummary garminActivitySummary : garminActivities) {
            if (garminActivitySummary.getActivityId() == garminActivityId) {
                GarminActivityDetails garminActivityDetails = garminConnectClient.retrieveActivityDetails(garminActivitySummary.getActivityId());
                runKeeperActivities.add(new RunKeeperActivity.Builder().fromGarminActivity(garminActivitySummary, garminActivityDetails).build());
            }
        }
        for (RunKeeperActivity runKeeperActivity : runKeeperActivities) {
            runKeeperApiClient.postActivity(runKeeperActivity);
        }
    }


    public void countShoeDistances(String... shoeIdentifiers) {
        // If you add shoe identifiers in the activity notes you can use this method to get stats for the shoes
        // countShoeDistances("Nike1", "Asics1");
        Map<String, Double> distancesInMeterPerShoe = new HashMap<>();
        for (String id : shoeIdentifiers) {
            distancesInMeterPerShoe.put(id, 0.0);
        }
        List<RunKeeperActivity> runKeeperActivities = runKeeperApiClient.retrieveAllReducedActivities();
        for (RunKeeperActivity runKeeperActivity : runKeeperActivities) {
            RunKeeperActivity completeActivity = runKeeperApiClient.retrieveCompleteActivity(runKeeperActivity);

            System.out.println(completeActivity.getStartTime().toString("EEE yyyy-MM-dd") + " " +completeActivity.getUri() + " " + Math.round(completeActivity.getTotalDistanceInM())/1000.0 + " km " + completeActivity.getNotes());
            for (String id : shoeIdentifiers) {
                if (completeActivity.getNotes().toLowerCase().contains(id.toLowerCase())) {
                    Double distance = distancesInMeterPerShoe.get(id) + completeActivity.getTotalDistanceInM();
                    distancesInMeterPerShoe.put(id, distance);
                }
            }
        }

        for (String id: shoeIdentifiers) {
            System.out.println(id + ": " + distancesInMeterPerShoe.get(id).toString() + " m.");
        }
    }

    public void addRunkeeperNotes() {
        // Run this one to update notes on the given activities

        HashMap<String, String> notesMap = new HashMap<String, String>();

        notesMap.put("1234", "Sunday run");
        notesMap.put("4567", "Interval training");

        for (Map.Entry<String, String> entry : notesMap.entrySet()) {
            runKeeperApiClient.updateNotes(entry.getKey(), entry.getValue());
        }
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        appContext.start();
        appContext.registerShutdownHook();
        RunSyncApplication application = appContext.getBean(RunSyncApplication.class);

        // Run the following with the garmin activity id to sync a garmin activity with runkeeper

        //long garminId = 0;
        //application.syncGarminToRunKeeper(garminId);
    }
}
