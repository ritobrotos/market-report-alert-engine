package main.java.service.analysis;

import constant.ConstantVariable;
import main.java.constant.ProspectiveBuyStockList;
import main.java.model.ProspectiveBuyStock;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;
import ulility.StockHistoryUtility;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ritobrotoseth on 20/07/18.
 */
public class TightPriceAction {
  // 20 Days { Close [If the stock closed positive] + Open [If the stock close negative] }
  // divide the above by 20 to find the average upper range

  // 20 days { Open [If the stock closed positive] + Close [If the stock close negative] }
  // divide the above by 20 to find the average lower range

  public static void main(String[] args) {
    Map<String, ProspectiveBuyStock> prospectiveBuyMap = ProspectiveBuyStockList.getProspectiveBuyStockMap();
    Iterator it = prospectiveBuyMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      ProspectiveBuyStock prospectStock = (ProspectiveBuyStock) pair.getValue();
      logic(prospectStock.getStock());
    }

//    StockFact britania = StockFact.builder().yahooAlias("BRITANNIA.NS").build();
//    StockFact relaxo = StockFact.builder().yahooAlias("RELAXO.NS").build();
//    StockFact mm = StockFact.builder().yahooAlias("M&M.NS").build();
//    StockFact marico = StockFact.builder().yahooAlias("MARICO.NS").build();
//    StockFact dhfl = StockFact.builder().yahooAlias("DHFL.NS").build();

//    logic(dhfl);
  }

  private static void logic(StockFact stock) {
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock,
        ConstantVariable.FETCH_HISTORY_HALF_YEARLY, true);

    int size = history.size();
    int lookbackPeriod = 40;    // 8 weeks
    double upperLowerDiffPercThreshold = 4.4;
    double addToUpperLowerRangeThreshold = 0.008;


    for(int i = lookbackPeriod; i < size; i++) {
      double lowerBound = 0.0D;
      double upperBound = 0.0D;
      double lowerRange = 0.0D;
      double upperRange = 0.0D;
      int lowerRangeCount = 0;
      int upperRangeCount = 0;
      boolean isInTpaRange = false;

      for(int j = i - (lookbackPeriod - 1); j <= i; j++) {
        if ( StockHistoryUtility.isNegative( history.get(j-1).getClose(), history.get(j).getClose() ) ) {
          // The stock is negative

          if (upperBound == 0.0D) {
            upperBound = history.get(j).getOpen();
            // This point should be added to the upperRange, not sure though
          }

          if (lowerBound == 0.0D) {
            lowerRange += history.get(j).getClose();
            lowerBound = history.get(j).getClose();
            lowerRangeCount++;
          } else if (lowerBound != 0.0D && (history.get(j).getClose()) < (lowerBound * (1 - addToUpperLowerRangeThreshold) )) {
            // Then only consider it in the lower range
            lowerRange += history.get(j).getClose();
            lowerBound = history.get(j).getClose();
            lowerRangeCount++;
          }
        }
        else {
          // The stock is posiitve ==========

          if (lowerBound == 0.0D) {
            lowerBound = history.get(j).getOpen();
            // This point should be added the lower range, not sure though
          }

          if (upperBound == 0.0D) {
            upperRange += history.get(j).getClose();
            upperBound = history.get(j).getClose();
            upperRangeCount++;
          } else if (upperBound != 0 && history.get(j).getClose() > (upperBound * (1 + addToUpperLowerRangeThreshold) ) ) {
            upperRange += history.get(j).getClose();
            upperBound = history.get(j).getClose();
            upperRangeCount++;
          }
        }
      }

      double avgUpperRange = (upperRange == 0 ? upperBound : upperRange) / (upperRangeCount == 0 ? 1 : upperRangeCount);
      double avgLowerRange = (lowerRange == 0 ? lowerBound : lowerRange) / (lowerRangeCount == 0 ? 1 : lowerRangeCount);

      Double diffPerc = MathUtility.diffPercent(avgUpperRange, avgLowerRange);
      if (diffPerc < upperLowerDiffPercThreshold) {
        isInTpaRange = true;
        Date observedDate = history.get(i).getDate();
        Date startDate = history.get(i - (lookbackPeriod - 1)).getDate();
        System.out.println(stock.getYahooAlias() + " :: " + DateUtility.dateToStringFormat1(observedDate) );
        if (i > size - 5) {
          continue;
        }
        // Forward i by 5 days
        i += 5;
      }
    }

  }

}
