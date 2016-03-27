package com.university.mongodb.javadev.util;

import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;

/**
 * Created by Vadim Kulinsky on 27.03.16.
 */
public class JsonUtil {

    public static String asJsonString(Document document) {
        JsonWriter jsonWriter = new JsonWriter(
                new StringWriter(),
                new JsonWriterSettings(JsonMode.SHELL, true)
        );
        new DocumentCodec().encode(jsonWriter, document,
                EncoderContext.builder().isEncodingCollectibleDocument(true).build());

        try {
            return jsonWriter.getWriter().toString();
        } finally {
            jsonWriter.flush();
        }
    }

    public static void printJson(Document document) {
        System.out.println( asJsonString( document));
    }
}
