package com.university.mongodb.javadev.w2.h2_3;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vadim Kulinsky on 27.03.16.
 */
public class HomeWork23 {
    public static void main(String[] args) {
        MongoClientOptions clientOptions = MongoClientOptions.builder().connectionsPerHost(20).build();
        MongoClient client = new MongoClient("127.0.0.1:27017", clientOptions);
        MongoDatabase students = client.getDatabase("students");

        List<Document> gradesToDelete = new ArrayList<>();
        MongoCollection<Document> gradesCollection = students.getCollection("grades");
        Bson sorting = Sorts.ascending("student_id", "score");
        int lastStudentId = -1;
        for (Document grade : gradesCollection
                .find(Filters.eq("type", "homework"))
                .sort(sorting)
                .into(new ArrayList<Document>())) {
            int currentStudentId = grade.getInteger("student_id");

            if (!Objects.equals(lastStudentId, currentStudentId)) {
                gradesCollection.deleteOne(grade);
            }

            lastStudentId = currentStudentId;
        }

        System.out.println(gradesCollection.count());
    }
}
