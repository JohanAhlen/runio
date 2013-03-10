package com.johanahlen.runsync.garmin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.johanahlen.runsync.garmin.activity.Activity;
import com.johanahlen.runsync.garmin.activity.ActivityDetails;
import com.johanahlen.runsync.garmin.activity.ActivityDetailsResponse;
import com.johanahlen.runsync.garmin.activity.ActivitySummary;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.ReflectionUtils;

public class GarminGsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private static final DateTimeFormatter sqlDatTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");

    private Gson gson;

    public GarminGsonHttpMessageConverter() {
        super(new MediaType("application", "json"));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ActivityDetailsResponse.class, new JsonDeserializer<ActivityDetailsResponse>() {
            @Override
            public ActivityDetailsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                JsonObject originalJsonObject = json.getAsJsonObject();
                JsonElement jsonElement = originalJsonObject.get("com.garmin.activity.details.json.ActivityDetails");

                ActivityDetailsResponse response = new ActivityDetailsResponse();
                Field activityDetailsField = ReflectionUtils.findField(ActivityDetailsResponse.class, "activityDetails");
                ReflectionUtils.makeAccessible(activityDetailsField);
                ReflectionUtils.setField(activityDetailsField, response, context.deserialize(jsonElement, new TypeToken<ActivityDetails>(){}.getType()));
                return response;
            }
        });

        gsonBuilder.registerTypeAdapter(Activity.class, new JsonDeserializer<Activity>() {
            @Override
            public Activity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                Activity original = new Gson().fromJson(json, Activity.class);

                JsonObject activityObject = json.getAsJsonObject();
                Long timestamp = activityObject.getAsJsonObject("uploadDate").get("millis").getAsLong();
                Field uploadDateField = ReflectionUtils.findField(Activity.class, "uploadDate");
                ReflectionUtils.makeAccessible(uploadDateField);
                ReflectionUtils.setField(uploadDateField, original, new LocalDateTime(timestamp));

                String updatedDateString = activityObject.getAsJsonObject("updatedDate").get("$").getAsString();
                Field updatedDateField = ReflectionUtils.findField(Activity.class, "updatedDate");
                ReflectionUtils.makeAccessible(updatedDateField);

                ReflectionUtils.setField(updatedDateField, original, LocalDateTime.parse(updatedDateString, sqlDatTimeFormatter));
                return original;
            }
        });

        gsonBuilder.registerTypeAdapter(ActivitySummary.class, new JsonDeserializer<ActivitySummary>() {
            @Override
            public ActivitySummary deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject originalJsonObject = json.getAsJsonObject();
                JsonObject replacementJsonObject = new JsonObject();
                for (Map.Entry<String, JsonElement> elementEntry : originalJsonObject.entrySet())
                {
                    String key = elementEntry.getKey();
                    JsonElement value = originalJsonObject.get(key);
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    replacementJsonObject.add(key, value);
                }
                return new Gson().fromJson(replacementJsonObject, ActivitySummary.class);
            }
        });
        this.gson = gsonBuilder.create();
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return this.gson.fromJson(new InputStreamReader(inputMessage.getBody()), clazz);

    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        this.gson.toJson(o, new OutputStreamWriter(outputMessage.getBody()));
    }
}
