package net.joelreeves.flickrphotodemo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class TestJsonLoader {
    public final static String FULL_PHOTO_RESPONSE = "responses/PhotoResponse.json";
    public final static String PHOTO_TEST = "responses/Photo.json";

    public static <T> T load(String filePath, Class<T> aClass) {
        final ObjectMapper mapper = new ObjectMapper();
        InputStream stream = aClass.getClassLoader().getResourceAsStream(filePath);
        T item = null;
        try {
            item = mapper.readValue(stream, aClass);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return item;
    }
}
