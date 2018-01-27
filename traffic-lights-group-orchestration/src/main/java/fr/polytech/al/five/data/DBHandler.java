package fr.polytech.al.five.data;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

public class DBHandler {

    private static final Logger LOGGER = Logger.getLogger(DBHandler.class);
    private MongoDatabase mongoDB;

    public DBHandler(){
        MongoClient mongoClient = new MongoClient(System.getenv("DB_ADDRESS"),
                                        Integer.parseInt(System.getenv("DB_PORT")));
        mongoDB = mongoClient.getDatabase(System.getenv("DB_NAME"));

        if(mongoDB != null){
            LOGGER.info("Connected to requested database");
        }
        else {
            LOGGER.info("Connection failure");
        }
    }

    private void insertDocumentIntoCollection(Document document, String collectionName){
        MongoCollection<Document> collection = mongoDB.getCollection(collectionName);
        if(collection == null){
            LOGGER.info("Creating collection: " + collectionName);
            mongoDB.createCollection(System.getenv(collectionName));
            collection = mongoDB.getCollection(collectionName);
        }

        LOGGER.info("Inserting document into collection");
        collection.insertOne(document);

    }

    public void testRequests(){
        Document document = new Document();
        document.append("car_id", 3);
        document.append("car_priority", 30);
        document.append("last_passed_tl", 1);
        document.append("grp_id", 2);
        document.append("avg_speed", 20);

        insertDocumentIntoCollection(document, "test");
    }
}
