package org.runio.garmin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.runio.garmin.activity.GarminActivitySummary;
import org.runio.util.ReflectionBeanUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class GarminGsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private static final DateTimeFormatter SQL_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");

    private Gson gson;
    private Gson unmodifiedGson;

    public GarminGsonHttpMessageConverter() {
        super(new MediaType("application", "json"));
        this.unmodifiedGson = new Gson();
        this.gson = new GsonBuilder().registerTypeAdapter(GarminActivitySummary.class, new JsonDeserializer<GarminActivitySummary>() {
            @Override
            public GarminActivitySummary deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                GarminActivitySummary result = unmodifiedGson.fromJson(json, GarminActivitySummary.class);

                JsonObject activityObject = json.getAsJsonObject();

                Long timestamp = activityObject.getAsJsonObject("uploadDate").get("millis").getAsLong();
                ReflectionBeanUtils.setField("uploadDate", result, new LocalDateTime(timestamp));

                String updatedDateString = activityObject.getAsJsonObject("updatedDate").get("$").getAsString();
                ReflectionBeanUtils.setField("updatedDate", result, LocalDateTime.parse(updatedDateString, SQL_DATE_TIME_FORMATTER));

                return result;
            }
        }).create();
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
