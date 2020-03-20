package main.java.dao;

import com.google.inject.Inject;

import main.java.model.db.BtcUsdT;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class BtcUsdDao extends MongoDao<BtcUsdT> {

  @Inject
  protected BtcUsdDao(Datastore ds) {
    super(ds);
  }

  public List<BtcUsdT> getAll() {
    Query<BtcUsdT> query = this.createQuery();
    return query.asList();
  }
}
