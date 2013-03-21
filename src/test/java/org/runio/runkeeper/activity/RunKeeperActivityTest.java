package org.runio.runkeeper.activity;

import static org.junit.Assert.*;
import com.google.gson.Gson;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.runio.runkeeper.RunKeeperConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RunKeeperConfig.class })
public class RunKeeperActivityTest {

    private static final String URI = "/fitnessActivities/40";
    private static final int USER_ID = 1234567890;
    private static final String TYPE = "Other";
    private static final String SECONDARY_TYPE = "Running backwards";
    private static final String EQUIPMENT = "None";
    private static final String START_TIME = "Sat, 1 Jan 2011 12:00:00";
    private static final LocalDateTime START_TIME_LDT = new LocalDateTime(2011, 1, 1, 12, 0);
    private static final double TOTAL_DISTANCE = 70.1;
    private static final double DISTANCE_1_TIMESTAMP = 2.1;
    private static final double DISTANCE_1_DISTANCE = 20.4;
    private static final double DISTANCE_2_TIMESTAMP = 5.3;
    private static final double DISTANCE_2_DISTANCE = 40.7;
    private static final double DURATION = 10.3;
    private static final int AVERAGE_HEART_RATE = 160;
    private static final double HEART_RATE_1_TIMESTAMP = 2.3;
    private static final int HEART_RATE_1_HEART_RATE = 150;
    private static final double HEART_RATE_2_TIMESTAMP = 7.1;
    private static final int HEART_RATE_2_HEART_RATE = 170;
    private static final double TOTAL_CALORIES = 60.3;
    private static final double CALORIES_1_TIMESTAMP = 2.0;
    private static final double CALORIES_1_CALORIES = 20.1;
    private static final double CALORIES_2_TIMESTAMP = 7.1;
    private static final double CALORIES_2_CALORIES = 30.3;
    private static final double CLIMB = 30.4;
    private static final String NOTES = "I'm a note";
    private static final boolean IS_LIVE = false;
    private static final double PATH_1_TIMESTAMP = 0;
    private static final double PATH_1_ALTITUDE = 0.3;
    private static final double PATH_1_LONGITUDE = -70.95182334425782;
    private static final double PATH_1_LATITUDE = 42.312620211384676;
    private static final String PATH_1_TYPE = "start";
    private static final double PATH_2_TIMESTAMP = 8.2;
    private static final double PATH_2_ALTITUDE = 3.2;
    private static final double PATH_2_LONGITUDE = -70.95411292510983;
    private static final double PATH_2_LATITUDE = 42.31233296498011;
    private static final String PATH_2_TYPE = "end";
    private static final double IMAGES_1_LONGITUDE = -70.95252392510987;
    private static final double IMAGES_1_LATITUDE = 42.33443294498018;
    private static final double IMAGES_1_TIMESTAMP = 8.4;
    private static final String IMAGES_1_URI = "/fitnessActivities/40/image";
    private static final String IMAGES_1_THUMBNAIL_URI = "/fitnessActivities/40/thumb";
    private static final double IMAGES_2_LONGITUDE = -70.95278492510991;
    private static final double IMAGES_2_LATITUDE = 42.31230344598012;
    private static final double IMAGES_2_TIMESTAMP = 10.5;
    private static final String IMAGES_2_URI = "/fitnessActivities/41/image";
    private static final String IMAGES_2_THUMBNAIL_URI = "/fitnessActivities/41/thumb";
    private static final String SOURCE = "RunKeeper";
    private static final String ENTRY_MODE = "API";
    private static final String ACTIVITY = "http://www.runkeeper.com/activity/123";
    private static final String COMMENTS = "/fitnessActivities/40/comments";
    private static final String PREVIOUS = "/fitnessActivities/39";
    private static final String NEXT = "/fitnessActivities/41";
    private static final String NEAREST_TEAMMATE_FITNESS_ACTIVITIES_1 = "/fitnessActivities/50";
    private static final String NEAREST_TEAMMATE_FITNESS_ACTIVITIES_2 = "/fitnessActivities/52";
    private static final String NEAREST_STRENGTH_TRAINING_ACTIVITY = "/strengthTrainingActivities/1";
    private static final String NEAREST_TEAMMATE_STRENGTH_TRAINING_ACTIVITIES_1 = "/strengthTrainingActivities/2";
    private static final String NEAREST_TEAMMATE_STRENGTH_TRAINING_ACTIVITIES_2 = "/strengthTrainingActivities/3";
    private static final String NEAREST_BACKGROUND_ACTIVITY = "/backgroundActivities/1";
    private static final String NEAREST_TEAMMATE_BACKGROUND_ACTIVITY_1 = "/backgroundActivities/12";
    private static final String NEAREST_TEAMMATE_BACKGROUND_ACTIVITY_2 = "/backgroundActivities/13";
    private static final String NEAREST_SLEEP = "/sleepSet/1";
    private static final String NEAREST_TEAMMATE_SLEEP_1 = "/sleepSet/22";
    private static final String NEAREST_TEAMMATE_SLEEP_2 = "/sleepSet/23";
    private static final String NEAREST_NUTRITION = "/nutritionSet/1";
    private static final String NEAREST_TEAMMATE_NUTRITION_1 = "/nutritionSet/32";
    private static final String NEAREST_TEAMMATE_NUTRITION_2 = "/nutritionSet/33";
    private static final String NEAREST_WEIGHT = "/weightSet/1";
    private static final String NEAREST_TEAMMATE_WEIGHT_1 = "/weightSet/52";
    private static final String NEAREST_TEAMMATE_WEIGHT_2 = "/weightSet/63";
    private static final String NEAREST_GENERAL_MEASUREMENT = "/generalMeasurementSet/1";
    private static final String NEAREST_TEAMMATE_GENERAL_MEASUREMENT_1 = "/generalMeasurementSet/62";
    private static final String NEAREST_TEAMMATE_GENERAL_MEASUREMENT_2 = "/generalMeasurementSet/23";
    private static final String NEAREST_DIABETES = "/diabetesMeasurementSet/31";
    private static final String NEAREST_TEAMMATE_DIABETES_1 = "/diabetesMeasurementSet/12";
    private static final String NEAREST_TEAMMATE_DIABETES_2 = "/diabetesMeasurementSet/32";

    private static final String JSON_DATA = "{" +
            "    \"uri\": \"" + URI + "\"," +
            "    \"userID\": " + USER_ID + "," +
            "    \"type\": \"" + TYPE + "\"," +
            "    \"secondary_type\" : \"" + SECONDARY_TYPE + "\"," +
            "    \"equipment\": \"" + EQUIPMENT + "\"," +
            "    \"start_time\": \"" + START_TIME + "\"," +
            "    \"total_distance\": " + TOTAL_DISTANCE + "," +
            "    \"distance\": [" +
            "        {" +
            "            \"timestamp\": " + DISTANCE_1_TIMESTAMP + "," +
            "            \"distance\": " + DISTANCE_1_DISTANCE +
            "        }," +
            "        {" +
            "            \"timestamp\": " + DISTANCE_2_TIMESTAMP + "," +
            "            \"distance\": " + DISTANCE_2_DISTANCE +
            "        }" +
            "    ]," +
            "    \"duration\": " + DURATION + "," +
            "    \"average_heart_rate\": " + AVERAGE_HEART_RATE + "," +
            "    \"heart_rate\": [" +
            "        {" +
            "            \"timestamp\": " + HEART_RATE_1_TIMESTAMP + "," +
            "            \"heart_rate\": " + HEART_RATE_1_HEART_RATE +
            "        }," +
            "        {" +
            "            \"timestamp\": " + HEART_RATE_2_TIMESTAMP + "," +
            "            \"heart_rate\": " + HEART_RATE_2_HEART_RATE +
            "        }" +
            "    ]," +
            "    \"total_calories\": " + TOTAL_CALORIES + "," +
            "    \"calories\": [" +
            "        {" +
            "            \"timestamp\": " + CALORIES_1_TIMESTAMP + "," +
            "            \"calories\": " + CALORIES_1_CALORIES +
            "        }," +
            "        {" +
            "            \"timestamp\": " + CALORIES_2_TIMESTAMP + "," +
            "            \"calories\": " + CALORIES_2_CALORIES +
            "        }" +
            "    ]," +
            "    \"climb\": " + CLIMB + "," +
            "    \"notes\": \"" + NOTES + "\"," +
            "    \"is_live\": " + IS_LIVE + "," +
            "    \"path\": [" +
            "        {" +
            "            \"timestamp\": " + PATH_1_TIMESTAMP + "," +
            "            \"altitude\": " + PATH_1_ALTITUDE + "," +
            "            \"longitude\": " + PATH_1_LONGITUDE + "," +
            "            \"latitude\": " + PATH_1_LATITUDE + "," +
            "            \"type\": \"" + PATH_1_TYPE + "\"" +
            "        }," +
            "        {" +
            "            \"timestamp\": " + PATH_2_TIMESTAMP + "," +
            "            \"altitude\": " + PATH_2_ALTITUDE + "," +
            "            \"longitude\": " + PATH_2_LONGITUDE + "," +
            "            \"latitude\": " + PATH_2_LATITUDE + "," +
            "            \"type\": \"" + PATH_2_TYPE + "\"" +
            "        }" +
            "    ]," +
            "    \"images\": [" +
            "        {" +
            "            \"timestamp\": " + IMAGES_1_TIMESTAMP + "," +
            "            \"longitude\": " + IMAGES_1_LONGITUDE + "," +
            "            \"latitude\": " + IMAGES_1_LATITUDE + "," +
            "            \"uri\": \"" + IMAGES_1_URI + "\"," +
            "            \"thumbnail_uri\": \"" + IMAGES_1_THUMBNAIL_URI + "\"" +
            "        }," +
            "        {" +
            "            \"timestamp\": " + IMAGES_2_TIMESTAMP + "," +
            "            \"longitude\": " + IMAGES_2_LONGITUDE + "," +
            "            \"latitude\": " + IMAGES_2_LATITUDE + "," +
            "            \"uri\": \"" + IMAGES_2_URI + "\"," +
            "            \"thumbnail_uri\": \"" + IMAGES_2_THUMBNAIL_URI + "\"" +
            "        }" +
            "    ]," +
            "    \"source\": \"" + SOURCE + "\"," +
            "    \"entry_mode\": \"" + ENTRY_MODE + "\"," +
            "    \"activity\": \"" + ACTIVITY + "\"," +
            "    \"comments\": \"" + COMMENTS + "\"," +
            "    \"previous\": \"" + PREVIOUS + "\"," +
            "    \"next\": \"" + NEXT + "\"," +
            "    \"nearest_teammate_fitness_activities\": [" +
            "        \"" + NEAREST_TEAMMATE_FITNESS_ACTIVITIES_1 + "\"," +
            "        \"" + NEAREST_TEAMMATE_FITNESS_ACTIVITIES_2 + "\"" +
            "    ]," +
            "    \"nearest_strength_training_activity\" : \"" + NEAREST_STRENGTH_TRAINING_ACTIVITY + "\"," +
            "    \"nearest_teammate_strength_training_activities\": [" +
            "        \"" + NEAREST_TEAMMATE_STRENGTH_TRAINING_ACTIVITIES_1 + "\"," +
            "        \"" + NEAREST_TEAMMATE_STRENGTH_TRAINING_ACTIVITIES_2 + "\"" +
            "    ]," +
            "    \"nearest_background_activity\": \"" + NEAREST_BACKGROUND_ACTIVITY + "\"," +
            "    \"nearest_teammate_background_activities\": [" +
            "        \"" + NEAREST_TEAMMATE_BACKGROUND_ACTIVITY_1 + "\"," +
            "        \"" + NEAREST_TEAMMATE_BACKGROUND_ACTIVITY_2 + "\"" +
            "    ]," +
            "    \"nearest_sleep\": \"" + NEAREST_SLEEP + "\"," +
            "    \"nearest_teammate_sleep\": [" +
            "        \"" + NEAREST_TEAMMATE_SLEEP_1 + "\"," +
            "        \"" + NEAREST_TEAMMATE_SLEEP_2 + "\"" +
            "    ]," +
            "    \"nearest_nutrition\": \"" + NEAREST_NUTRITION + "\"," +
            "    \"nearest_teammate_nutrition\": [" +
            "        \"" + NEAREST_TEAMMATE_NUTRITION_1 + "\"," +
            "        \"" + NEAREST_TEAMMATE_NUTRITION_2 + "\"" +
            "    ]," +
            "    \"nearest_weight\": \"" + NEAREST_WEIGHT + "\"," +
            "    \"nearest_teammate_weight\": [" +
            "        \"" + NEAREST_TEAMMATE_WEIGHT_1 + "\"," +
            "        \"" + NEAREST_TEAMMATE_WEIGHT_2 + "\"]," +
            "    \"nearest_general_measurement\": \"" + NEAREST_GENERAL_MEASUREMENT + "\"," +
            "    \"nearest_teammate_general_measurements\": [" +
            "        \"" + NEAREST_TEAMMATE_GENERAL_MEASUREMENT_1 + "\"," +
            "        \"" + NEAREST_TEAMMATE_GENERAL_MEASUREMENT_2 + "\"" +
            "    ]," +
            "    \"nearest_diabetes\": \"" + NEAREST_DIABETES + "\"," +
            "    \"nearest_teammate_diabetes\": [" +
            "        \"" + NEAREST_TEAMMATE_DIABETES_1 + "\"," +
            "        \"" + NEAREST_TEAMMATE_DIABETES_2 + "\"" +
            "    ]" +
            "}";

    @Autowired
    private Gson runKeeperGson;

    /*
     Apart from making sure that the JSON deserialiation works this test also verifies that we are compatible
     with the RunKeeper Health Graph API defined at

        developer.runkeeper.com/healthgraph/fitness-activities

     Hence the complete JSON input. It does not care for optional values and whether it's for read or write.
     */
    @Test
    public void should_convert_from_json() throws Exception {
        RunKeeperActivity runKeeperActivity = runKeeperGson.fromJson(JSON_DATA, RunKeeperActivity.class);
        
        assertEquals(URI, runKeeperActivity.getUri());
        assertEquals(USER_ID, runKeeperActivity.getUserId().intValue());
        assertEquals(TYPE, runKeeperActivity.getType());
        assertEquals(SECONDARY_TYPE, runKeeperActivity.getTypeOther());
        assertEquals(EQUIPMENT, runKeeperActivity.getEquipment());
        assertEquals(START_TIME_LDT, runKeeperActivity.getStartTime());
        assertEquals(TOTAL_DISTANCE, runKeeperActivity.getTotalDistanceInKm().doubleValue(), 0);
        assertEquals(2, runKeeperActivity.getDistanceMeasurements().size());
        assertEquals(DISTANCE_1_TIMESTAMP, runKeeperActivity.getDistanceMeasurements().get(0).getSecondsSinceStartOfActivity(), 0);
        assertEquals(DISTANCE_1_DISTANCE, runKeeperActivity.getDistanceMeasurements().get(0).getDistanceInKm(), 0);
        assertEquals(DISTANCE_2_TIMESTAMP, runKeeperActivity.getDistanceMeasurements().get(1).getSecondsSinceStartOfActivity(), 0);
        assertEquals(DISTANCE_2_DISTANCE, runKeeperActivity.getDistanceMeasurements().get(1).getDistanceInKm(), 0);
        assertEquals(DURATION, runKeeperActivity.getTotalDurationInSeconds().doubleValue(), 0);
        assertEquals(AVERAGE_HEART_RATE, runKeeperActivity.getAverageHeartRateInBpm(), 0);
        assertEquals(2, runKeeperActivity.getHeartRates().size());
        assertEquals(HEART_RATE_1_TIMESTAMP, runKeeperActivity.getHeartRates().get(0).getSecondsSinceStartOfActivity(), 0);
        assertEquals(HEART_RATE_1_HEART_RATE, runKeeperActivity.getHeartRates().get(0).getHeartRateInBpm());
        assertEquals(HEART_RATE_2_TIMESTAMP, runKeeperActivity.getHeartRates().get(1).getSecondsSinceStartOfActivity(), 0);
        assertEquals(HEART_RATE_2_HEART_RATE, runKeeperActivity.getHeartRates().get(1).getHeartRateInBpm());
        assertEquals(TOTAL_CALORIES, runKeeperActivity.getTotalCalories(), 0);
        assertEquals(2, runKeeperActivity.getCaloricBurnMeasurements().size());
        assertEquals(CALORIES_1_TIMESTAMP, runKeeperActivity.getCaloricBurnMeasurements().get(0).getSecondsSinceStartOfActivity(), 0);
        assertEquals(CALORIES_1_CALORIES, runKeeperActivity.getCaloricBurnMeasurements().get(0).getCalories(), 0);
        assertEquals(CALORIES_2_TIMESTAMP, runKeeperActivity.getCaloricBurnMeasurements().get(1).getSecondsSinceStartOfActivity(), 0);
        assertEquals(CALORIES_2_CALORIES, runKeeperActivity.getCaloricBurnMeasurements().get(1).getCalories(), 0);
        assertEquals(CLIMB, runKeeperActivity.getTotalClimbInM(), 0);
        assertEquals(NOTES, runKeeperActivity.getNotes());
        assertEquals(IS_LIVE, runKeeperActivity.getLive());
        assertEquals(2, runKeeperActivity.getPath().size());
        assertEquals(PATH_1_TIMESTAMP, runKeeperActivity.getPath().get(0).getSecondsSinceStartOfActivity(), 0);
        assertEquals(PATH_1_ALTITUDE, runKeeperActivity.getPath().get(0).getAltitudeInMeters(), 0);
        assertEquals(PATH_1_LONGITUDE, runKeeperActivity.getPath().get(0).getLongitude(), 0);
        assertEquals(PATH_1_LATITUDE, runKeeperActivity.getPath().get(0).getLatitude(), 0);
        assertEquals(PATH_1_TYPE,  runKeeperActivity.getPath().get(0).getType());
        assertEquals(PATH_2_TIMESTAMP, runKeeperActivity.getPath().get(1).getSecondsSinceStartOfActivity(), 0);
        assertEquals(PATH_2_ALTITUDE, runKeeperActivity.getPath().get(1).getAltitudeInMeters(), 0);
        assertEquals(PATH_2_LONGITUDE, runKeeperActivity.getPath().get(1).getLongitude(), 0);
        assertEquals(PATH_2_LATITUDE, runKeeperActivity.getPath().get(1).getLatitude(), 0);
        assertEquals(PATH_2_TYPE, runKeeperActivity.getPath().get(1).getType());
        assertEquals(2, runKeeperActivity.getImages().size());
        assertEquals(IMAGES_1_TIMESTAMP, runKeeperActivity.getImages().get(0).getSecondsSinceStartOfActivity(), 0);
        assertEquals(IMAGES_1_LONGITUDE, runKeeperActivity.getImages().get(0).getLongitude(), 0);
        assertEquals(IMAGES_1_LATITUDE, runKeeperActivity.getImages().get(0).getLatitude(), 0);
        assertEquals(IMAGES_1_URI, runKeeperActivity.getImages().get(0).getUri());
        assertEquals(IMAGES_1_THUMBNAIL_URI, runKeeperActivity.getImages().get(0).getThumbnailUri());
        assertEquals(IMAGES_2_TIMESTAMP, runKeeperActivity.getImages().get(1).getSecondsSinceStartOfActivity(), 0);
        assertEquals(IMAGES_2_LONGITUDE, runKeeperActivity.getImages().get(1).getLongitude(), 0);
        assertEquals(IMAGES_2_LATITUDE, runKeeperActivity.getImages().get(1).getLatitude(), 0);
        assertEquals(IMAGES_2_URI, runKeeperActivity.getImages().get(1).getUri());
        assertEquals(IMAGES_2_THUMBNAIL_URI, runKeeperActivity.getImages().get(1).getThumbnailUri());
        assertEquals(SOURCE, runKeeperActivity.getSource());
        assertEquals(ENTRY_MODE, runKeeperActivity.getEntryMode());
        assertEquals(ACTIVITY, runKeeperActivity.getActivityPublicUrl());
        assertEquals(COMMENTS, runKeeperActivity.getCommentsUri());
        assertEquals(PREVIOUS, runKeeperActivity.getPreviousActivityUri());
        assertEquals(NEXT, runKeeperActivity.getNextActivityUri());
        assertEquals(2, runKeeperActivity.getNearestTeammateFitnessActivityUris().size());
        assertEquals(NEAREST_TEAMMATE_FITNESS_ACTIVITIES_1, runKeeperActivity.getNearestTeammateFitnessActivityUris().get(0));
        assertEquals(NEAREST_TEAMMATE_FITNESS_ACTIVITIES_2, runKeeperActivity.getNearestTeammateFitnessActivityUris().get(1));
        assertEquals(NEAREST_STRENGTH_TRAINING_ACTIVITY, runKeeperActivity.getNearestStengthTrainingActivityUri());
        assertEquals(2, runKeeperActivity.getNearestTeammateStrengthTrainingActivityUris().size());
        assertEquals(NEAREST_TEAMMATE_STRENGTH_TRAINING_ACTIVITIES_1, runKeeperActivity.getNearestTeammateStrengthTrainingActivityUris().get(0));
        assertEquals(NEAREST_TEAMMATE_STRENGTH_TRAINING_ACTIVITIES_2, runKeeperActivity.getNearestTeammateStrengthTrainingActivityUris().get(1));
        assertEquals(NEAREST_BACKGROUND_ACTIVITY, runKeeperActivity.getNearestBackgroundActivityUri());
        assertEquals(2, runKeeperActivity.getNearestTeammateBackgroundActivityUris().size());
        assertEquals(NEAREST_TEAMMATE_BACKGROUND_ACTIVITY_1, runKeeperActivity.getNearestTeammateBackgroundActivityUris().get(0));
        assertEquals(NEAREST_TEAMMATE_BACKGROUND_ACTIVITY_2, runKeeperActivity.getNearestTeammateBackgroundActivityUris().get(1));
        assertEquals(NEAREST_SLEEP, runKeeperActivity.getNearestSleepMeasurementUri());
        assertEquals(2, runKeeperActivity.getNearestTeammateSleepUris().size());
        assertEquals(NEAREST_TEAMMATE_SLEEP_1, runKeeperActivity.getNearestTeammateSleepUris().get(0));
        assertEquals(NEAREST_TEAMMATE_SLEEP_2, runKeeperActivity.getNearestTeammateSleepUris().get(1));
        assertEquals(NEAREST_NUTRITION, runKeeperActivity.getNearestNutritionUri());
        assertEquals(2, runKeeperActivity.getNearestTeammateNutritionUris().size());
        assertEquals(NEAREST_TEAMMATE_NUTRITION_1, runKeeperActivity.getNearestTeammateNutritionUris().get(0));
        assertEquals(NEAREST_TEAMMATE_NUTRITION_2, runKeeperActivity.getNearestTeammateNutritionUris().get(1));
        assertEquals(NEAREST_WEIGHT, runKeeperActivity.getNearestWeightUri());
        assertEquals(2, runKeeperActivity.getNearestTeammateWeightUris().size());
        assertEquals(NEAREST_TEAMMATE_WEIGHT_1, runKeeperActivity.getNearestTeammateWeightUris().get(0));
        assertEquals(NEAREST_TEAMMATE_WEIGHT_2, runKeeperActivity.getNearestTeammateWeightUris().get(1));
        assertEquals(NEAREST_GENERAL_MEASUREMENT, runKeeperActivity.getNearestGeneralMeasurementUri());
        assertEquals(2, runKeeperActivity.getNearestTeammateGeneralMeasurementUris().size());
        assertEquals(NEAREST_TEAMMATE_GENERAL_MEASUREMENT_1, runKeeperActivity.getNearestTeammateGeneralMeasurementUris().get(0));
        assertEquals(NEAREST_TEAMMATE_GENERAL_MEASUREMENT_2, runKeeperActivity.getNearestTeammateGeneralMeasurementUris().get(1));
        assertEquals(NEAREST_DIABETES, runKeeperActivity.getNearestDiabetesUri());
        assertEquals(2, runKeeperActivity.getNearestTeammateDiabetetesUris().size());
        assertEquals(NEAREST_TEAMMATE_DIABETES_1, runKeeperActivity.getNearestTeammateDiabetetesUris().get(0));
        assertEquals(NEAREST_TEAMMATE_DIABETES_2, runKeeperActivity.getNearestTeammateDiabetetesUris().get(1));
      }
    
    
   
}
