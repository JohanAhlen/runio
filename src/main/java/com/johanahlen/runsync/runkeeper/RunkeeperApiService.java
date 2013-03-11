package com.johanahlen.runsync.runkeeper;

import java.io.IOException;
import com.johanahlen.runsync.runkeeper.activity.RunKeeperActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunKeeperApiService {

    @Autowired
    RunKeeperRestTemplate restTemplate;

    public void postActivity(RunKeeperActivity activity) {
        try {
            restTemplate.executePost("https://api.runkeeper.com/fitnessActivities", "application/vnd.com.runkeeper.NewFitnessActivity+json", activity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
