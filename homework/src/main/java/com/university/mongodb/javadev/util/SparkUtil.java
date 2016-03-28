package com.university.mongodb.javadev.util;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

/**
 * Created by Vadim Kulinsky on 16.03.16.
 */
public class SparkUtil {

    private static final FreeMarkerRenderer RENDERER = new FreeMarkerRenderer();

    private SparkUtil() {
    }


    public static Route renderOrHalt(final String routePath,
                                     final String templatePath,
                                     final Map<String, ?> params) {

        return new Route(routePath) {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    return RENDERER.render(templatePath, params);
                } catch (Exception ex) {
                    halt(500);
                    return null;
                }
            }
        };
    }

}
