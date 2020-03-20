package main.java.dao;

import com.google.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class MongoDao<T> extends BasicDAO<T, ObjectId> {

  @Inject
  protected MongoDao(Datastore ds) {
    super(ds);
  }
}
