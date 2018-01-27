package fr.polytech.al.five.data;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class QueryHandler {

    private static final Logger LOGGER = Logger.getLogger(QueryHandler.class);
    private MongoDatabase mongoDB;

    public boolean initQueryHandler(){
        MongoClient mongoClient = new MongoClient(System.getenv("DB_ADDRESS"),
                Integer.parseInt(System.getenv("DB_PORT")));
        mongoDB = mongoClient.getDatabase(System.getenv("DB_NAME"));

        return mongoDB != null;
    }

    public void insertDocumentIntoCollection(Document document, String collectionName) {
        MongoCollection<Document> collection = mongoDB.getCollection(collectionName);
        if (collection == null) {
            LOGGER.info("Creating collection: " + collectionName);
            mongoDB.createCollection(System.getenv(collectionName));
            collection = mongoDB.getCollection(collectionName);
        }

        LOGGER.info("Inserting document into collection");
        collection.insertOne(document);

    }

    public List<Integer> selectArrayWhere(String attributeName, Object attributeValue, String arrayName, String collectionName){
        MongoCursor<Document> dbCursor = selectWhere(attributeName, attributeValue, collectionName);

        if (!dbCursor.hasNext()){
            return new ArrayList<>();
        }

        Document document = dbCursor.next();
        return (List<Integer>) document.get(arrayName);
    }

    private FindIterable<Document> selectWhereIterable(List<String> attributesNames, List<Object> attributesValues, String collectionName){
        if(attributesNames.size() != attributesValues.size()){
            return null;
        }

        int i = 0;
        List<Bson> searchParameter = new ArrayList<>();
        for (String attributeName : attributesNames) {
            searchParameter.add(Filters.eq(attributeName, attributesValues.get(i)));
            i++;
        }

        MongoCollection<Document> collection = mongoDB.getCollection(collectionName);
        if (collection == null) {
            return null;
        }

        return collection.find(Filters.and(searchParameter));
    }

    public MongoCursor<Document> selectWhere(List<String> attributesNames, List<Object> attributesValues, String collectionName) {
        return selectWhereIterable(attributesNames, attributesValues, collectionName).iterator();
    }

    public MongoCursor<Document> selectWhere(String attributeName, Object attributeValue, String collectionName){
        List<String> attributesNames = new ArrayList<>();
        List<Object> attributesValues = new ArrayList<>();
        attributesNames.add(attributeName);
        attributesValues.add(attributeValue);

        return selectWhere(attributesNames, attributesValues, collectionName);
    }

    public MongoCursor<Document> selectWhereSorted(String attributeName, Object attributeValue, String sortCriterion,
                                                   int sortOrder, String collectionName) {
        List<String> attributesNames = new ArrayList<>();
        List<Object> attributesValues = new ArrayList<>();
        attributesNames.add(attributeName);
        attributesValues.add(attributeValue);
        FindIterable<Document> collection = selectWhereIterable(attributesNames, attributesValues, collectionName);

        if (collection != null) {
            return collection.sort(Filters.eq(sortCriterion, sortOrder)).iterator();
        }

        return null;
    }

    public void update(List<String> attributesNames, List<Object> attributesValues, String attributeToChange,
                       Object newValue, String collectionName){
        if (attributesNames.size() != attributesValues.size()) {
            return;
        }

        int i = 0;
        List<Bson> criteria = new ArrayList<>();
        for (String attributeName : attributesNames) {
            criteria.add(Filters.eq(attributeName, attributesValues.get(i)));
            i++;
        }

        MongoCollection<Document> collection = mongoDB.getCollection(collectionName);
        if (collection == null) {
            return;
        }

        Document newDocument = new Document();
        newDocument.put(attributeToChange, newValue);

        Document updateDoc = new Document();
        updateDoc.put("$set", newDocument);

        collection.updateOne(Filters.and(criteria), updateDoc);
    }

    public void update(String attributeName, Object attributeValue, String attributeToChange,
                       Object newValue, String collectionName){
        List<String> attributesNames = new ArrayList<>();
        List<Object> attributesValues = new ArrayList<>();
        attributesNames.add(attributeName);
        attributesValues.add(attributeValue);

        update(attributesNames, attributesValues, attributeToChange, newValue, collectionName);
    }

    public void remove(List<String> attributesNames, List<Object> attributesValues, String collectionName) {
        if (attributesNames.size() != attributesValues.size()) {
            return;
        }

        int i = 0;
        List<Bson> conditions = new ArrayList<>();
        for (String attributeName : attributesNames) {
            conditions.add(Filters.eq(attributeName, attributesValues.get(i)));
            i++;
        }

        MongoCollection<Document> collection = mongoDB.getCollection(collectionName);
        if (collection == null) {
            return;
        }
        Bson searchQuery = Filters.and(conditions);

        collection.deleteOne(searchQuery);
    }

    public void remove(String attributeName, Object attributeValue, String collectionName){
        List<String> attributesNames = new ArrayList<>();
        List<Object> attributesValues = new ArrayList<>();
        attributesNames.add(attributeName);
        attributesValues.add(attributeValue);

        remove(attributesNames, attributesValues, collectionName);
    }
}
