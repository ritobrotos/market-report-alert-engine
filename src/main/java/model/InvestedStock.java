package main.java.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.StockFact;

import java.util.Date;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
@Builder
@Getter
@Setter
public class InvestedStock {

  private StockFact stock;

  private double buyPoint;
  private Date buyDate;
  private double initialStoploss;
  private double trailingStoploss;
  private Date trackDate;

  private int quantityHolding;

  private double resistanceLevel;
  private double supportLevel;


}
