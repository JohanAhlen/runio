package com.johanahlen.runsync.garmin;

import java.lang.reflect.Field;
import com.johanahlen.runsync.garmin.activity.Activity;
import com.johanahlen.runsync.garmin.activity.ActivityDetailsResponse;
import com.johanahlen.runsync.garmin.activity.ActivityWrapper;
import com.johanahlen.runsync.garmin.activity.SearchServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class GarminConnectService {

    @Autowired
    private RestTemplate garminRestTemplate;

    public void retrieveAllActivities() {
        SearchServiceResponse response = garminRestTemplate.getForObject("https://connect.garmin.com/proxy/activity-search-service-1.2/json/activities", SearchServiceResponse.class);
        for (ActivityWrapper wrapper : response.getResults().getActivities()) {
            ActivityDetailsResponse activityDetailsResponse = garminRestTemplate.getForObject("https://connect.garmin.com/proxy/activity-service-1.3/json/activityDetails/" + wrapper.getActivity().getActivityId(), ActivityDetailsResponse.class);
            Field activityDetailsField = ReflectionUtils.findField(Activity.class, "activityDetails");
            ReflectionUtils.makeAccessible(activityDetailsField);
            ReflectionUtils.setField(activityDetailsField, wrapper.getActivity(), activityDetailsResponse.getActivityDetails());
        }
    }
}