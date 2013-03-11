package com.johanahlen.runsync.garmin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.johanahlen.runsync.garmin.activity.GarminActivity;
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

    private static final DateTimeFormatter sqlDateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");

    private Gson gson;

    public GarminGsonHttpMessageConverter() {
        super(new MediaType("application", "json"));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GarminActivity.class, new JsonDeserializer<GarminActivity>() {
            @Override
            public GarminActivity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                GsonBuilder builder = new GsonBuilder();

                GarminActivity original = new Gson().fromJson(json, GarminActivity.class);

                JsonObject activityObject = json.getAsJsonObject();
                Long timestamp = activityObject.getAsJsonObject("uploadDate").get("millis").getAsLong();
                Field uploadDateField = ReflectionUtils.findField(GarminActivity.class, "uploadDate");
                ReflectionUtils.makeAccessible(uploadDateField);
                ReflectionUtils.setField(uploadDateField, original, new LocalDateTime(timestamp));

                String updatedDateString = activityObject.getAsJsonObject("updatedDate").get("$").getAsString();
                Field updatedDateField = ReflectionUtils.findField(GarminActivity.class, "updatedDate");
                ReflectionUtils.makeAccessible(updatedDateField);

                ReflectionUtils.setField(updatedDateField, original, LocalDateTime.parse(updatedDateString, sqlDateTimeFormatter));
                return original;
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
