package main.java.utility;

import main.java.model.Report;
import model.Quote;
import ulility.DateUtility;
import ulility.MathUtility;

import java.util.Date;
import java.util.List;

/**
 * Created by ritobrotoseth on 17/07/18.
 */
public class StockReportingUtility {

  public static int findStartIndex(List<Quote> history, String duration) {
    int index = 0;
    int size = history.size();
    int roughDuration = 30;
    Date presentDate = DateUtility.getOnlyDate(new Date());
    Date earlyDate = DateUtility.earlyDate(30, presentDate);

    if(duration.equalsIgnoreCase("MONTHLY")) {
      roughDuration = 30;
      earlyDate = DateUtility.earlyDate(30, presentDate);
    } else if(duration.equalsIgnoreCase("QUARTERLY")) {
      roughDuration = 90;
      earlyDate = DateUtility.earlyDate(90, presentDate);
    }

    int loopIndexVal = 0;
    if(size > roughDuration) {
      loopIndexVal = size - roughDuration;
    }

    for(int i = loopIndexVal; i < size; i++) {
      if(history.get(i).getDate().compareTo(earlyDate) < 0) {
        // If selected date comes before earlyDate
        continue;
      } else {
        return i;
      }
    }
    return index;
  }

  public static Report reportPeriodLowHigh(List<Quote> history) {
    double low = history.get(0).getClose();
    double high = history.get(0).getClose();

    for(int i=1; i < history.size(); i++) {
      if(history.get(i).getClose() < low) {
        low = history.get(i).getClose();
      }

      if(history.get(i).getClose() > high) {
        high = history.get(i).getClose();
      }
    }

    Report report = new Report();
    report.getPeriodLowHigh().setLowVal(low);
    report.getPeriodLowHigh().setHighVal(high);
    return report;
  }

  public static boolean isNegativeClose(int index, List<Quote> history) {
    if(history.get(index).getClose() < history.get(index-1).getClose()) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isPositiveClose(int index, List<Quote> history) {
    if(history.get(index).getClose() > history.get(index-1).getClose()) {
      return true;
    } else {
      return false;
    }
  }

}
