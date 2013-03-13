package org.runsync.runkeeper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class RunKeeperGsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private static final DateTimeFormatter RUNKEEPER_DATE_FORMAT = DateTimeFormat.forPattern("EEE, d MMM yyyy HH:mm:ss");

    private Gson gson;

    public RunKeeperGsonHttpMessageConverter() {
        super(new MediaType("application", "json"));
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return RUNKEEPER_DATE_FORMAT.parseLocalDateTime(json.getAsString());
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(RUNKEEPER_DATE_FORMAT.print(src));
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
