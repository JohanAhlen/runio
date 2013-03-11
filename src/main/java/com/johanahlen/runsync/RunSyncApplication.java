package com.johanahlen.runsync;

import java.util.LinkedList;
import java.util.List;
import com.johanahlen.runsync.context.ApplicationConfig;
import com.johanahlen.runsync.garmin.GarminConnectService;
import com.johanahlen.runsync.garmin.activity.GarminActivity;
import com.johanahlen.runsync.runkeeper.RunKeeperApiService;
import com.johanahlen.runsync.runkeeper.RunKeeperRestTemplate;
import com.johanahlen.runsync.runkeeper.activity.RunKeeperActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RunSyncApplication {

    @Autowired
    private GarminConnectService garminConnectService;

    @Autowired
    private RunKeeperApiService runKeeperApiService;

    @Autowired
    RunKeeperRestTemplate restTemplate;

    private void run() {

        // used to clean up if sync fails...
//        try {
//            String activityId = "1234";
//            restTemplate.executeDelete("https://api.runkeeper.com/fitnessActivities/" + activityId);
//            System.out.println(restTemplate.executeGet("https://api.runkeeper.com/fitnessActivities"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.exit(0);


        List<GarminActivity> garminActivities = garminConnectService.retrieveActivitiesAfter(0L);
        List<RunKeeperActivity> runKeeperActivities = new LinkedList<RunKeeperActivity>();

        for(GarminActivity garminActivity : garminActivities) {
            runKeeperActivities.add(RunKeeperActivity.fromGarminActivity(garminActivity));
        }
        for (RunKeeperActivity runKeeperActivity : runKeeperActivities) {
            runKeeperApiService.postActivity(runKeeperActivity);
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        appContext.start();
        appContext.registerShutdownHook();
        RunSyncApplication application = appContext.getBean(RunSyncApplication.class);
        application.run();
    }
}
