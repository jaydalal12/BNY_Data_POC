/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.gigaspaces.utils.CsvReader;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mycompany.app.model.*;
import org.bson.Document;
import org.openspaces.core.*;
import org.openspaces.core.space.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.gigaspaces.internal.io.BootIOUtils.getResourcePath;

public class Program {

    public static void main(String[] args) {
        GigaSpace gigaSpace = getOrCreateSpace(args.length == 0 ? "mySpace" : args[0]);
        System.out.println("Connected to space " + gigaSpace.getName());
        loadMysqlData(gigaSpace);
        loadCSVData(gigaSpace);
        System.out.println("Program completed successfully");
    }

    public static GigaSpace getOrCreateSpace(String spaceName) {
        if (spaceName == null) {
            System.out.println("Space name not provided - creating an embedded space...");
            return new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer("mySpace")).create();
        } else {
            System.out.printf("Connecting to space %s...%n", spaceName);
            try {
                return new GigaSpaceConfigurer(new SpaceProxyConfigurer(spaceName)).create();
            } catch (CannotFindSpaceException e) {
                System.err.println("Failed to find space: " + e.getMessage());
                throw e;
            }
        }
    }

    public static void loadCSVData(GigaSpace gigaSpace){
            try (Stream<StockPrice> stream = new CsvReader().read(getResourcePath("stockPrice.csv"), StockPrice.class)) {
                stream.forEach(gigaSpace::write);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void loadMysqlData(GigaSpace gigaSpace){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/opsdb";
            Connection conn = DriverManager.getConnection(url, "root", "mysql");
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM STOCK_INFORMATION");
            StockInformation stockInfo = null;
            while (resultSet.next()) {
                stockInfo = new StockInformation();
                stockInfo.setSymbol(resultSet.getString("SYMBOL"));
                stockInfo.setCompanyName(resultSet.getString("COMPANY_NAME"));
                gigaSpace.write(stockInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFromMongoDb(){
        List<StockEarningDate> stockEarningDates = new ArrayList<StockEarningDate>();
        MongoClient mongo = new MongoClient();
        MongoDatabase database = mongo.getDatabase("opsdb");
        MongoCollection<Document> aa = database.getCollection("com.mycompany.app.model.StockEarningDate");
        FindIterable<Document> fi = aa.find();
        MongoCursor<Document> cursor = fi.iterator();
        try {
            StockEarningDate stockEarningDate = null;
            while(cursor.hasNext()) {
                Document doc = cursor.next();
                stockEarningDate = new StockEarningDate();
                stockEarningDate.setSymbol(doc.get("_id").toString());
                String aaa = doc.get("nextEarningsDate").toString();
                stockEarningDate.setNextEarningsDate(LocalDate.parse(doc.get("nextEarningsDate").toString()));
                stockEarningDates.add(stockEarningDate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
    }
}

