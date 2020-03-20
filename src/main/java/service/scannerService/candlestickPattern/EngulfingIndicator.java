package main.java.service.scannerService.candlestickPattern;

import main.java.constant.Constants;
import main.java.constant.FnoStockList;
import main.java.constant.NiftyStockList;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;

import java.util.List;

/**
 * Ref:
 * https://github.com/mdeverdelhan/ta4j-origins/blob/master/ta4j/src/main/java/eu/verdelhan/ta4j/indicators/candles/BullishEngulfingIndicator.java
 * https://github.com/mdeverdelhan/ta4j-origins/blob/master/ta4j/src/main/java/eu/verdelhan/ta4j/indicators/candles/BearishEngulfingIndicator.java
 *
 * Improvements Required:
 * Have to remove the patterns where the engulfing is happening on Doji
 */
public class EngulfingIndicator {

  private static double GREATER_THAN_DIFFERENCE_FACTOR = 1.002;
  private static double LESSER_THAN_DIFFERENCE_FACTOR = 0.998;
  private static double LESSER_THAN_DIFFERENCE_FACTOR_FOR_BULLISH_ENGULFING = 0.995;
  private static double CANDLE_SIZE_PERCENTAGE = 1.5;
  private static int HISTORICAL_PERIOD = Constants.FETCH_HISTORY_QUARTERLY;

  private static double totalEntries = 0;
  private static final boolean DATA_FROM_CACHE = true;
  private static final boolean DATE_CONDITION_APPLICABLE = true;
  private static final String DATE_SCANNING = "09-Jan-2020";

  public static void main(String[] args) {
    EngulfingIndicator engulfingIndicator = new EngulfingIndicator();
    engulfingIndicator.findTotalEntries();
//    testEngulfingIndicatorFunctionality();
  }

//  private static void testEngulfingIndicatorFunctionality() {
//    StockFact bata = new StockFact("Bharat Heavy Electricals Limited", "BHEL.NS");
//
//    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
//        bata, HISTORICAL_PERIOD, DATA_FROM_CACHE);
//
//    findBearishEngulfing(history);
//    findBullishEngulfing(history);
//  }

  private void findTotalEntries() {
    List<StockFact> fnoStockList = FnoStockList.getFnoStockList();
    for (StockFact stock : fnoStockList) {
      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          stock, HISTORICAL_PERIOD, DATA_FROM_CACHE);

      if (history == null || history.size() <= 1) {
        continue;
      }

      System.out.println(stock.getName());
      if(DATE_CONDITION_APPLICABLE) {
        findBearishBullishEngulfingDateSpecific(history, DATE_SCANNING);
      } else {
        findBearishBullishEngulfingAllDays(history);
      }

//      findBullishEngulfing(history);
      System.out.println("==================");
    }
    System.out.println("Total Entries: " + totalEntries);
  }

  private static void findBearishBullishEngulfingDateSpecific(List<Quote> history, String specificDate) {
    for (int i = 1; i < history.size(); i++) {
      double prevOpenPrice = history.get(i-1).getOpen();
      double prevClosePrice = history.get(i-1).getClose();
      double currOpenPrice = history.get(i).getOpen();
      double currClosePrice = history.get(i).getClose();

      String dateString = DateUtility.dateToStringFormat1(history.get(i).getDate());
      if (!dateString.equalsIgnoreCase(specificDate)) {
        continue;
      }

      isBearishEngulfingCriteriaValid(prevOpenPrice, prevClosePrice, currOpenPrice, currClosePrice, history.get(i));
      isBullishEngulfingCriteriaValid(prevOpenPrice, prevClosePrice, currOpenPrice, currClosePrice, history.get(i));
    }
  }

  private static void findBearishBullishEngulfingAllDays(List<Quote> history) {
    for (int i = 1; i < history.size(); i++) {
      double prevOpenPrice = history.get(i-1).getOpen();
      double prevClosePrice = history.get(i-1).getClose();
      double currOpenPrice = history.get(i).getOpen();
      double currClosePrice = history.get(i).getClose();

      isBearishEngulfingCriteriaValid(prevOpenPrice, prevClosePrice, currOpenPrice, currClosePrice, history.get(i));
      isBullishEngulfingCriteriaValid(prevOpenPrice, prevClosePrice, currOpenPrice, currClosePrice, history.get(i));
    }
  }

//  private static void findBearishEngulfing(List<Quote> history) {
//    for (int i = 1; i < history.size(); i++) {
//      double prevOpenPrice = history.get(i-1).getOpen();
//      double prevClosePrice = history.get(i-1).getClose();
//      double currOpenPrice = history.get(i).getOpen();
//      double currClosePrice = history.get(i).getClose();
//
//      isBearishEngulfingCriteriaValid(prevOpenPrice, prevClosePrice, currOpenPrice, currClosePrice, history.get(i));
//    }
//  }

  private static void isBearishEngulfingCriteriaValid(double prevOpenPrice, double prevClosePrice, double currOpenPrice,
                                                      double currClosePrice, Quote quote) {
    double declinePercent = MathUtility.declinePercent(currClosePrice, currOpenPrice);
    if (currOpenPrice > (prevOpenPrice * GREATER_THAN_DIFFERENCE_FACTOR)
        && currOpenPrice > (prevClosePrice * GREATER_THAN_DIFFERENCE_FACTOR)
        && currClosePrice < (prevOpenPrice * LESSER_THAN_DIFFERENCE_FACTOR)
        && currClosePrice < (prevClosePrice * LESSER_THAN_DIFFERENCE_FACTOR)
        && declinePercent >= CANDLE_SIZE_PERCENTAGE) {
      System.out.println("Bearish Engulfing on: " + DateUtility.dateToStringFormat1(quote.getDate())
        + " :: Candle size in Percent: " + declinePercent);
      totalEntries++;
    }
  }

//  private static void findBullishEngulfing(List<Quote> history) {
//    for (int i = 1; i < history.size(); i++) {
//      double prevOpenPrice = history.get(i - 1).getOpen();
//      double prevClosePrice = history.get(i - 1).getClose();
//      double currOpenPrice = history.get(i).getOpen();
//      double currClosePrice = history.get(i).getClose();
//
//      if (currOpenPrice == 0.0D) {
//        continue;
//      }
//
//      isBullishEngulfingCriteriaValid(prevOpenPrice, prevClosePrice, currOpenPrice, currClosePrice, history.get(i));
//    }
//  }

  /**
   * Condition for Bullish Engulfing:
   * Current Open Price is less than prev open price
   * Current Open Price is less than prev close price
   * Current Close Price is greater than prev open price
   * Current Close Price is greater than prev close price
   * CANDLE_SIZE_PERCENTAGE is used to make sure we are eliminating all the small candles which doesn't show buy strength
   */
  private static void isBullishEngulfingCriteriaValid(double prevOpenPrice, double prevClosePrice, double currOpenPrice,
                                               double currClosePrice, Quote quote) {
    double risePercent = MathUtility.risePercent(currClosePrice, currOpenPrice);
    if (currOpenPrice < (prevOpenPrice * LESSER_THAN_DIFFERENCE_FACTOR_FOR_BULLISH_ENGULFING)
        && currOpenPrice < (prevClosePrice * LESSER_THAN_DIFFERENCE_FACTOR_FOR_BULLISH_ENGULFING)
        && currClosePrice > (prevOpenPrice * GREATER_THAN_DIFFERENCE_FACTOR)
        && currClosePrice > (prevClosePrice * GREATER_THAN_DIFFERENCE_FACTOR)
        && risePercent >= CANDLE_SIZE_PERCENTAGE) {
      System.out.println("Bullish Engulfing on: " + DateUtility.dateToStringFormat1(quote.getDate())
        + " :: Candle size in Percent: " + risePercent);
      totalEntries++;
    }
  }
}
