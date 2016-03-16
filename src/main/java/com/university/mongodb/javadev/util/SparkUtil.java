package com.university.mongodb.javadev.util;

import spark.Route;
import spark.Spark;

import java.util.Map;

/**
 * Created by Vadim Kulinsky on 16.03.16.
 */
public class SparkUtil {

    private static final FreeMarkerRenderer RENDERER = new FreeMarkerRenderer();

    private SparkUtil() {
    }


    public static Route renderOrHalt(String templatePath, Map<String, ?> params) {

        return (request, response) -> {
            try {
                return RENDERER.render(templatePath, params);
            } catch (Exception ex) {
                Spark.halt(500);
                return null;
            }
        };
    }

}
