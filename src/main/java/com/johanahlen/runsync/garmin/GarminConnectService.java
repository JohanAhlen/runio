package com.johanahlen.runsync.garmin;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import com.johanahlen.runsync.garmin.activity.GarminActivity;
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

    public List<GarminActivity> retrieveActivitiesAfter(long activityId) {
        SearchServiceResponse response = garminRestTemplate.getForObject("https://connect.garmin.com/proxy/activity-search-service-1.2/json/activities", SearchServiceResponse.class);
        List<GarminActivity> result = new LinkedList<GarminActivity>();
        for (ActivityWrapper wrapper : response.getResults().getActivities()) {
            if (activityId < wrapper.getActivity().getActivityId()) {
                ActivityDetailsResponse activityDetailsResponse = garminRestTemplate.getForObject("https://connect.garmin.com/proxy/activity-service-1.3/json/activityDetails/" + wrapper.getActivity().getActivityId(), ActivityDetailsResponse.class);
                Field activityDetailsField = ReflectionUtils.findField(GarminActivity.class, "activityDetails");
                ReflectionUtils.makeAccessible(activityDetailsField);
                ReflectionUtils.setField(activityDetailsField, wrapper.getActivity(), activityDetailsResponse.getActivityDetails());
                result.add(wrapper.getActivity());
            }
        }
        return result;
    }
}