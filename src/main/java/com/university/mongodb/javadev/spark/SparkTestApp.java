package com.university.mongodb.javadev.spark;

import com.google.common.collect.ImmutableMap;
import com.university.mongodb.javadev.util.SparkUtil;
import spark.Spark;

import java.util.Arrays;
import java.util.Optional;

public class SparkTestApp {

    public static void main(String[] args) throws Exception {
        Spark.get("/", SparkUtil.renderOrHalt("testapp/hello.ftl", ImmutableMap.of("name", "Vadim")));

        Spark.get("/fruits",
                SparkUtil.renderOrHalt(
                        "testapp/fruitsPicker.ftl",
                        ImmutableMap.of("fruits", Arrays.asList("orange", "banana", "apple"))));

        Spark.post("/favorite_fruit",
                (request, response) ->
                        Optional.ofNullable(request.queryParams("fruit")).
                                map(fruit -> "Your favorite fruit is " + fruit).
                                orElse("Please select your favorite fruit")
        );
    }
}
