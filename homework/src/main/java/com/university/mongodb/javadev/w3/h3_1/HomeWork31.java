package com.university.mongodb.javadev.w3.h3_1;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/**
 * Created by Vadim Kulinsky on 05.04.16.
 */
public class HomeWork31 {

    public static void main(String[] args) {
        MongoClientOptions clientOptions = MongoClientOptions.builder().connectionsPerHost(20).build();
        MongoClient client = new MongoClient("127.0.0.1:27017", clientOptions);
        MongoDatabase school = client.getDatabase("school");


        MongoCollection<Document> studentsCollection = school.getCollection("students");
        for (Document studentDocument : studentsCollection.find()) {
            List<Document> scores = (List<Document>)studentDocument.get("scores");
            Document minimalHomeworkScore = findMinimalHomeworkScore(scores);

            if (null != minimalHomeworkScore) {
                scores.remove(minimalHomeworkScore);
                studentsCollection.replaceOne(eq("_id", studentDocument.get("_id")), studentDocument);
            }
        }

        System.out.println(studentsCollection.count());

    }

    static Document findMinimalHomeworkScore(List<Document> scores) {
        if (null == scores || scores.isEmpty()) {
            return null;
        } else {
            Document minimalHomeworkScore = null;
            for (Document score : scores) {
                if (Objects.equals("homework", score.getString("type")) &&
                    (null == minimalHomeworkScore ||
                     score.getDouble("score") < minimalHomeworkScore.getDouble("score"))) {
                    minimalHomeworkScore =  score;
                }
            }
            return minimalHomeworkScore;
        }
    }
}
