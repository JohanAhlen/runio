package com.johanahlen.runsync;

import com.johanahlen.runsync.context.ApplicationConfig;
import com.johanahlen.runsync.garmin.GarminConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RunSyncApplication {

    @Autowired
    private GarminConnectService garminConnectService;

    private void run() {
        garminConnectService.retrieveAllActivities();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        appContext.start();
        appContext.registerShutdownHook();
        RunSyncApplication application = appContext.getBean(RunSyncApplication.class);
        application.run();

    }
}
