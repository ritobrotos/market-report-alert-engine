package main.java.model;

import com.google.common.collect.Lists;

import lombok.Getter;
import model.StockFact;

import java.util.List;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
@Getter
public class Alert {

  private List<AlertUnit> losers;
  private List<AlertUnit> gainers;
  private List<AlertUnit> belowSma50;
  private List<AlertUnit> belowSma20;
  private List<AlertUnit> percentageDownFromPeriodHighList;

  public Alert() {
    losers = Lists.newArrayList();
    belowSma50 = Lists.newArrayList();
    belowSma20 = Lists.newArrayList();
    gainers = Lists.newArrayList();
    percentageDownFromPeriodHighList = Lists.newArrayList();
  }

}
