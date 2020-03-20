package main.java.service.scannerService.candlestickPattern;

import main.java.constant.Constants;
import main.java.constant.FnoStockList;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;

import java.util.List;

/**
 * Ref:
 * https://github.com/mdeverdelhan/ta4j-origins/blob/master/ta4j/src/main/java/eu/verdelhan/ta4j/indicators/candles/BullishHaramiIndicator.java
 * https://github.com/mdeverdelhan/ta4j-origins/blob/master/ta4j/src/main/java/eu/verdelhan/ta4j/indicators/candles/BearishHaramiIndicator.java
 *
 */
public class HaramiIndicator {

  private static double totalEntries = 0;
  private static final boolean DATA_FROM_CACHE = false;
  private static final int CANDLE_BODY_COEFFICIENT = 2;
  private static final String DATE_SCANNING = "15-Apr-2019";
  private static final boolean DATE_CONDITION_APPLICABLE = false;

  public static void main(String[] args) {
    findTotalEntries();
  }

  private static void findTotalEntries() {
    List<StockFact> fnoStockList = FnoStockList.getFnoStockList();
    for (StockFact stock : fnoStockList) {
      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          stock, Constants.FETCH_HISTORY_QUARTERLY, DATA_FROM_CACHE);

      if (history == null || history.size() <= 1) {
        continue;
      }

      System.out.println(stock.getName());
      findBearishHarami(history);
      findBullishHarami(history);
      System.out.println("==================");
    }
    System.out.println("Total Entries: " + totalEntries);
  }

  private static void findBearishHarami(List<Quote> history) {
    for (int i = 1; i < history.size(); i++) {
      if (history.get(i-1).isBullish() && history.get(i).isBearish()) {
        final double prevOpenPrice = history.get(i-1).getOpen();
        final double prevClosePrice = history.get(i-1).getClose();
        final double currOpenPrice = history.get(i).getOpen();
        final double currClosePrice = history.get(i).getClose();

        if (currOpenPrice > prevOpenPrice && currOpenPrice < prevClosePrice
            && currClosePrice > prevOpenPrice && currClosePrice < prevClosePrice) {
          System.out.println("Bearish Harami Formed on: " + DateUtility.dateToStringFormat1(
              history.get(i).getDate()));
          totalEntries++;
        }
      }
    }
  }

  private static void findBullishHarami(List<Quote> history) {
    for (int i = 1; i < history.size(); i++) {
      if (history.get(i-1).isBearish() && history.get(i).isBullish()) {
        final double prevOpenPrice = history.get(i-1).getOpen();
        final double prevClosePrice = history.get(i-1).getClose();
        final double currOpenPrice = history.get(i).getOpen();
        final double currClosePrice = history.get(i).getClose();

        if (currOpenPrice < prevOpenPrice && currOpenPrice > prevClosePrice
            && currClosePrice < prevOpenPrice && currClosePrice > prevClosePrice) {
          System.out.println("Bullish Harami Formed on: " + DateUtility.dateToStringFormat1(
              history.get(i).getDate()));
          totalEntries++;
        }
      }
    }
  }
}
