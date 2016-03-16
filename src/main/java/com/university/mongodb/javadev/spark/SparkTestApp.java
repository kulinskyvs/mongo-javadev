package com.university.mongodb.javadev.spark;

import spark.Spark;

public class SparkTestApp {
    public static void main(String[] args) throws Exception {
        Spark.get("/", (request, response) -> "Hello from Spark test application!!");
    }
}
