package com.university.mongodb.javadev.spark;

import com.google.common.collect.ImmutableMap;
import com.university.mongodb.javadev.util.SparkUtil;
import spark.Spark;

public class SparkTestApp {
    public static void main(String[] args) throws Exception {
        Spark.get("/", SparkUtil.renderOrHalt("hello.ftl", ImmutableMap.of("name", "Vadim")));
    }
}
