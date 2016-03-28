package com.university.mongodb.javadev.w2.mongoclient;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.university.mongodb.javadev.util.JsonUtil.printJson;
import static java.util.Arrays.asList;

/**
 * Created by Vadim Kulinsky on 26.03.16.
 */
public class MongoClientApp {

    public static void main(String[] args) {
        MongoClientOptions clientOptions = MongoClientOptions.builder().connectionsPerHost(20).build();
        MongoClient client = new MongoClient("127.0.0.1:27017", clientOptions);
        MongoDatabase testDb = client.getDatabase("test");

        System.out.println("-----------insert demo -----------------");
        findDemo(testDb);

        System.out.println("-----------find demo -----------------");
        findDemo(testDb);
    }

    private static void insertDemo(MongoDatabase db) {
        MongoCollection<Document> peopleCollection = db.getCollection("people");
        peopleCollection.drop();

        Document vadim = new Document("name", "Vadim")
                .append("age", 33)
                .append("email", "kulinskyvs@gmail.com")
                .append("hobbies", asList("running", "cycling", "swimming"));
        Document hanna = new Document("name", "Hanna")
                .append("age", 34)
                .append("email", "nusnik@gmail.com")
                .append("hobbies", asList("cooking"));


        printJson(vadim);
        peopleCollection.insertMany(asList(vadim, hanna));
        printJson(vadim);
    }


    private static void findDemo(MongoDatabase db) {
        MongoCollection<Document> counters = db.getCollection("counters");
        counters.drop();

        for (int i = 0; i < 10; i++) {
            counters.insertOne(new Document("up", i)
                    .append("down", 10 - i)
                    .append("rand", new Random().nextInt(10)));
        }

        //------------------------------------------------------
        System.out.println("Find first:");
        printJson(counters.find().first());

        //------------------------------------------------------
        System.out.println("Find all with into (up > 3 and down < 6):");
        Bson filter = new Document("up", new Document("$gt", 3)).append(
                "down", new Document("$lt", 6));
        Bson projection = new Document("up", 1).append("down", 1).append("_id", 0);
        Bson sorting = new Document("up", -1).append("down", 1);
        List<Document> allCounters = counters
                .find(filter)
                .projection(projection)
                .sort(sorting)
                .limit(4)
                .skip(2)
                .into(new ArrayList<Document>());
        for (Document counter : allCounters) {
            printJson(counter);
        }


        //------------------------------------------------------
        System.out.println("Find all with iteration (up > 3 and down < 6):");
        filter = Filters.and(Filters.gt("up", 3), Filters.lt("down", 6));
        projection = Projections.fields(
                Projections.include("up", "down"),
                Projections.excludeId()
        );
        sorting = Sorts.orderBy(Sorts.descending("down"), Sorts.ascending("down"));
        MongoCursor<Document> counterCursor = counters
                .find(filter)
                .projection(projection)
                .sort(sorting)
                .limit(4)
                .skip(2)
                .iterator();
        try {
            while (counterCursor.hasNext()) {
                printJson(counterCursor.next());
            }
        } finally {
            counterCursor.close();
        }


        System.out.println("Count:");
        System.out.println(counters.count());

    }
}
