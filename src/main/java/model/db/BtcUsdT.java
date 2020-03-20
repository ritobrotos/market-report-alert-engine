package main.java.model.db;

import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "btcusdt", noClassnameStored = true)
@Data
public class BtcUsdT {
  @Id
  @Embedded("_id")
  private ObjectId objectId;
}
