package main.java.service.analysis.archive;

import constant.ConstantVariable;
import main.java.constant.ProspectiveBuyStockList;
import main.java.model.ProspectiveBuyStock;
import model.Oscillator;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.IndicatorUtility;
import ulility.MathUtility;
import ulility.StockHistoryUtility;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ritobrotoseth on 26/07/18.
 * The objecting is
 * to find stocks that are respecting there SMA-20 for last 20-trading days
 * and also showing a trend towards upside.
 */
public class RespectingSma {

  private static final int SMA = 20;

  public static void main(String[] args) {

    Map<String, ProspectiveBuyStock> prospectiveBuyMap = ProspectiveBuyStockList.getProspectiveBuyStockMap();
    Iterator it = prospectiveBuyMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      ProspectiveBuyStock prospectStock = (ProspectiveBuyStock) pair.getValue();
      find(prospectStock.getStock());
    }
  }

  private static void find(StockFact stock) {
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock,
        ConstantVariable.FETCH_HISTORY_HALF_YEARLY, true);

    int size = history.size();
    boolean record = false;
    Date recordDate = null;
    int recordCounter = 0;


    for (int i = SMA-1; i < size; i++) {
      Quote tick = history.get(i);
      List<Quote> subhistory = history.subList(i-(SMA-1), i+1);

      double ltp = history.get(i).getClose();
      double avg = StockHistoryUtility.getAverageClosePrice(subhistory);

      if (ltp < avg) {
        if (record == true) {
          if (recordCounter > 10) {
            System.out.println(stock.getYahooAlias());
            System.out.println("Record date: " + DateUtility.dateToStringFormat1(recordDate)
              + "  End Date: " + DateUtility.dateToStringFormat1(history.get(i).getDate()));
            System.out.println("Above trading days: " + recordCounter);
            System.out.println("\n\n");
          }
          record = false;
          recordCounter = 0;
        }
        continue;
      } else if (record == true && i == size-1 && recordCounter > 10) {
        System.out.println(stock.getYahooAlias());
        System.out.println("Record date: " + DateUtility.dateToStringFormat1(recordDate)
            + "  End Date: " + DateUtility.dateToStringFormat1(history.get(i).getDate()));
        System.out.println("Above trading days: " + recordCounter);
        System.out.println("\n\n");
      }

      if (record == false) {
        record = true;
        recordDate = history.get(i).getDate();
        recordCounter++;
      }

      if (record == true) {
        recordCounter++;
      }

    }
  }

}
