package com.university.mongodb.javadev.exam.t7;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Created by Vadim Kulinsky on 27.04.16.
 */
public class Task {

    public static void main(String[] args) {
        MongoClientOptions clientOptions = MongoClientOptions.builder().connectionsPerHost(20).build();
        MongoClient client = new MongoClient("127.0.0.1:27017", clientOptions);
        MongoDatabase school = client.getDatabase("photo");

        MongoCollection<Document> imagesCollection = school.getCollection("images");
        MongoCollection<Document> albumsCollection = school.getCollection("albums");

        int i = 0;
        int numberOfRemoved = 0;
        for (Document image : imagesCollection.find()) {
            i++;
            Bson filter = new Document("images", image.get("_id"));

            if (!albumsCollection.find(filter).limit(1).iterator().hasNext()) {
                //remove the document
                System.out.println(i+".. removing the document "+image.get("_id"));
                imagesCollection.deleteOne(image);
                numberOfRemoved++;
            } else {
                System.out.println(i+".. skiping the document "+image.get("_id"));
            }
        } //for all the images
        System.out.println("number of removed:"+numberOfRemoved);
    }
}
