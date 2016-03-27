package com.university.mongodb.javadev.w1.spark;

import com.google.common.collect.ImmutableMap;
import com.university.mongodb.javadev.util.SparkUtil;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.Arrays;

public class SparkTestApp {

    public static void main(String[] args) throws Exception {
        Spark.get(SparkUtil.renderOrHalt("/", "w1/spark/hello.ftl", ImmutableMap.of("name", "Vadim")));

        Spark.get(
                SparkUtil.renderOrHalt(
                        "/fruits",
                        "w1/spark/fruitsPicker.ftl",
                        ImmutableMap.of("fruits", Arrays.asList("orange", "banana", "apple"))));

        Spark.post(new Route("/favorite_fruit") {
                       @Override
                       public Object handle(Request request, Response response) {
                           String fruit = request.queryParams("fruit");
                           return (null != fruit) ?
                                   "Your favorite fruit is " + fruit :
                                   "Please select your favorite fruit";
                       }
                   }
        );
    }
}
