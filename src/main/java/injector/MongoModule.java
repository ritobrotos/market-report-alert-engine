package main.java.injector;

import com.google.inject.AbstractModule;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import main.java.model.db.BtcUsdT;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;

import java.util.Collections;

public class MongoModule extends AbstractModule {

  private final String mongoHost;
  private final String mongoPort;
  private final String dbName;

  private Datastore datastore;
  private MongoClient mongo;
  private Morphia morphia;

  public MongoModule() {
    mongoHost = "localhost";
    mongoPort = "27017";
    dbName = "mudrex";
  }

  @Override
  protected void configure() {
    MorphiaLoggerFactory.reset();
    init();
    bind();
  }

  private void init() {
    mongo = createMongoClient();
    morphia = new Morphia();

//    morphia.map(BtcUsdT.class);

    datastore = createDataStore();
  }

  private MongoClient createMongoClient() {
    MongoClient mongo = configureSingleMongo(null);
    return mongo;
  }

  private MongoClient configureSingleMongo(MongoCredential credential) {
    ServerAddress serverAddress = new ServerAddress(mongoHost, Integer.parseInt(mongoPort));
    if (credential == null) {
      return new MongoClient(serverAddress);
    } else {
      return new MongoClient(serverAddress, Collections.singletonList(credential));
    }
  }

  private Datastore createDataStore() {
    Datastore datastore = morphia.createDatastore(mongo, dbName);
    datastore.ensureIndexes();
    datastore.ensureCaps();
    datastore.setDefaultWriteConcern(WriteConcern.ACKNOWLEDGED);
    return datastore;
  }

  private void bind() {
    bind(Morphia.class).toInstance(morphia);
    bind(Datastore.class).toInstance(datastore);
  }
}
