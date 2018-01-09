/*
package com.dafy.base.gateway.data.dao.mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.core.MongoTemplate;

*/
/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/25
 *//*

*/
/*@ImportResource("classpath:test-mongodb.xml")*//*

public class AppServiceMapperTest {

    private static final String LOCALHOST = "127.0.0.1";

    private static final String DB_NAME = "itest";

    private static final int MONGO_TEST_PORT = 27028;

    private static Mongo mongo;

    private MongoTemplate template;

    private AppServiceMapper appServiceMapper;

    @Before
    public void setUp() throws Exception {


        MongodStarter starter = MongodStarter.getDefaultInstance();
        String bindIp = "localhost";
        int port = 12345;
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = null;
        try {
            mongodExecutable = starter.prepare(mongodConfig);
            MongodProcess mongod = mongodExecutable.start();

            MongoClient mongo = new MongoClient(bindIp, port);
            MongoDatabase database = mongo.getDatabase("test");
            database.createCollection("testCol");
            MongoCollection<Document> collection = database.getCollection("testCol");
            collection.insertOne(new Document("key", "value"));

        } finally {
            if (mongodExecutable != null)
                mongodExecutable.stop();
        }

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findByAppKey() throws Exception {
    }

}*/
