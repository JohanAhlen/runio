package org.runio.runkeeper;

import java.io.IOException;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class RunKeeperGsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    @Autowired
    private Gson runKeeperGson;

    public RunKeeperGsonHttpMessageConverter() {
        super(new MediaType("application", "json"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return this.runKeeperGson.fromJson(new InputStreamReader(inputMessage.getBody()), clazz);

    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody().write(this.runKeeperGson.toJson(o).getBytes());
    }
}
