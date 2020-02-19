package com.datasource;

import com.gigaspaces.datasource.DataIterator;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import com.mycompany.app.model.Employee;
import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomSpaceDataIterator implements DataIterator<Object> {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private List<Employee> spaceObjects = null;
    private final String objectName;
    private int index;
    private int id;

    public CustomSpaceDataIterator(String _objectName, int _id) {
        this.objectName = _objectName;
        this.id = _id;
    }

    @Override
    public boolean hasNext() {
        if (spaceObjects == null) {
            try {
                logger.info("start getData2222");
                spaceObjects = getDataFromMongo();
            } catch (Exception e) {
                logger.error("Exception while load data from source:" + e);
                return false;
            }
        }
        boolean returnVal = index >= 0 && index < spaceObjects.size();
        if (index >= spaceObjects.size()) {
            spaceObjects.clear();
        }
        return returnVal;
    }

    @Override
    public Object next() {
        final Employee record = spaceObjects.get(index);
        index++;
        try {
            return record;
        } catch (Exception e) {
            logger.error("Cannot deserialize json to object:" + e);
        }
        return null;
    }

    @Override
    public void close() {
        spaceObjects = null;
    }

    protected List<Employee> getDataFromMongo() {
        logger.info("start getData");
        List<Employee> objectList = new ArrayList<Employee>();
        String client_url = "mongodb://localhost:27017";
        MongoClientURI uri = new MongoClientURI(client_url);
        MongoClient mongo = MongoClients.create(uri.getURI());//"mongodb://localhost:27017");
        MongoDatabase database = mongo.getDatabase("opsdb");
        logger.info("start getData 2 ");
        MongoCollection<Document> aa = database.getCollection("Employee");
        FindIterable<Document> fi = aa.find();
        MongoCursor<Document> cursor = fi.iterator();
        try {
            logger.info("start getData 3");
            Employee employee = null;
            while(cursor.hasNext()) {
                Document doc = cursor.next();
                logger.info("1111");
                employee = new Employee();
                logger.info("1122");
                employee.setId(Integer.parseInt(doc.get("id").toString()));
                logger.info("11");
                employee.setName(doc.get("name").toString());
                logger.info("112");
                employee.setTitle(doc.get("title").toString());
                logger.info("113");
                employee.setDepartment(doc.get("department").toString());
                logger.info("114");
                employee.setBirthday(LocalDate.parse(doc.get("birthday").toString()));
                logger.info("115");
                employee.setSalary(Double.parseDouble(doc.get("salary").toString()));
                logger.info("116");
                objectList.add(employee);
            }
        } finally {
            cursor.close();
        }
        logger.info("start getData 4");
        return objectList;
    }
}
