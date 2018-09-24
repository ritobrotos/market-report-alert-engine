package main.java.model;

import lombok.Builder;
import lombok.Getter;
import main.java.constant.Constants;

import java.util.Comparator;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
@Builder
@Getter
public class AlertUnit {

  private InvestedStock investedStock;
  private double percDown;
  private double percUp;
  private double percDiff;
  private double ltp;
  private String alertType;

  public static Comparator<AlertUnit> AlertUnitDescendingComparator = new Comparator<AlertUnit>() {
    @Override
    public int compare(AlertUnit o1, AlertUnit o2) {
      double alertUnit1 = 0.0D;
      double alertUnit2 = 0.0D;

      if (o1.alertType.equalsIgnoreCase(Constants.GAINERS_ALERT_TYPE)) {
        alertUnit1 = o1.getPercUp();
        alertUnit2 = o2.getPercUp();
      } else if (o1.alertType.equalsIgnoreCase(Constants.LOSERS_ALERT_TYPE)) {
        alertUnit1 = o1.getPercDown();
        alertUnit2 = o2.getPercDown();
      } else if (o1.alertType.equalsIgnoreCase(Constants.DOWN_FROM_PERIODIC_HIGH_ALERT_TYPE)) {
        alertUnit1 = o1.getPercDiff();
        alertUnit2 = o2.getPercDiff();
      }

      // Descending Order
      return (alertUnit2 > alertUnit1) ? 1 : -1;
    }
  };
}
