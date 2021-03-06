package main.java.service.scannerService.candlestickPattern;

import main.java.constant.Constants;
import main.java.constant.FnoStockList;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;

import java.util.List;

public class Hammer {

  private static double totalEntries = 0;

  private static final int CANDLE_BODY_COEFFICIENT_FOR_LONG_WICK_CONDITION_1 = 2;
  private static final double CANDLE_BODY_COEFFICIENT_FOR_LONG_WICK_CONDITION_2 = 3;

  private static final double CANDLE_BODY_COEFFICIENT_FOR_SHORT_WICK_CONDITION_1 = 0.5D;
  private static final double CANDLE_BODY_COEFFICIENT_FOR_SHORT_WICK_CONDITION_2 = 1.0D;

  private static final boolean DATA_FROM_CACHE = true;

  private static final String DATE_SCANNING = "09-Jan-2020";
  private static final boolean DATE_CONDITION_APPLICABLE = true;
  private static final int LOOK_BACK_DAYS = 5;
  private static int HISTORICAL_PERIOD = Constants.FETCH_HISTORY_QUARTERLY;

  public static void main(String[] args) {
    findTotalEntries();
//    test();
  }

  private static void findTotalEntries() {
    List<StockFact> fnoStockList = FnoStockList.getFnoStockList();
    for (StockFact stock : fnoStockList) {
      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          stock, HISTORICAL_PERIOD, DATA_FROM_CACHE);

      if (history == null || history.size() <= 1) {
        continue;
      }

      System.out.println(stock.getName());
      if(DATE_CONDITION_APPLICABLE) {
        findHammerShootingStarCandleDateSpecific(history, DATE_SCANNING);
      } else {
        findHammerShootingStarCandleAllDays(history);
      }

      System.out.println("==================");
    }
    System.out.println("Total Entries: " + totalEntries);
  }

//  private static void test() {
//    StockFact stock = new StockFact("Container Corporation of India Limited", "CONCOR.NS");
//    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
//        stock, Constants.FETCH_HISTORY_QUARTERLY, DATA_FROM_CACHE);
//    findHammerShootingStarCandleDateSpecific(history);
//  }

  private static void findHammerShootingStarCandleAllDays(List<Quote> history) {
    for (int i = 0; i < history.size(); i++) {
      checkCandle(history, i);
    }
  }

  private static void findHammerShootingStarCandleDateSpecific(List<Quote> history, String specificDate) {
    for (int i = 0; i < history.size(); i++) {
      String dateString = DateUtility.dateToStringFormat1(history.get(i).getDate());
      if (!dateString.equalsIgnoreCase(specificDate)) {
        continue;
      }
      checkCandle(history, i);
    }
  }

  private static void checkCandle(List<Quote> history, int index) {
    double currOpenPrice = history.get(index).getOpen();
    double currClosePrice = history.get(index).getClose();
    double currLowPrice = history.get(index).getLow();
    double currHighPrice = history.get(index).getHigh();

    double lengthOfCandleBody = Math.abs(currOpenPrice - currClosePrice);
    double minInOpenClose = Math.min(currClosePrice, currOpenPrice);
    double lengthOfCandleLowerWick = minInOpenClose - currLowPrice;
    double maxInOpenClose = Math.max(currClosePrice, currOpenPrice);
    double lengthOfCandleUpperWick = currHighPrice - maxInOpenClose;

    if (isHammerCriteriaValid(lengthOfCandleLowerWick, lengthOfCandleBody, lengthOfCandleUpperWick)) {
      checkIfHammerOrHangingMan(history, index);
    }

    if (isShootingStarCriteriaValid(lengthOfCandleLowerWick, lengthOfCandleBody, lengthOfCandleUpperWick, history.get(index))) {
      checkIfShootingStarOrInvertedHammer(history, index);
    }
  }

  private static boolean isShootingStarCriteriaValid(double lowerWickLength, double bodyLength,
                                                     double upperWickLength, Quote quote) {
    if ( (upperWickLength > (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_LONG_WICK_CONDITION_1)
            && lowerWickLength <= (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_SHORT_WICK_CONDITION_1) )
        ||
        (upperWickLength > (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_LONG_WICK_CONDITION_2)
            && lowerWickLength <= (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_SHORT_WICK_CONDITION_2) )
    ) {
      return true;
    }
    return false;
  }

  private static void checkIfShootingStarOrInvertedHammer(List<Quote> history, int currentIndex) {
    if(currentIndex - LOOK_BACK_DAYS >= 0) {
      double lookBackDayClosePrice = history.get(currentIndex - LOOK_BACK_DAYS).getClose();
      Quote currentQuote = history.get(currentIndex);
      double currClosePrice = currentQuote.getClose();
      if (lookBackDayClosePrice > currClosePrice) {
        System.out.println("Formed Inverted Hammer on: " + DateUtility.dateToStringFormat1(currentQuote.getDate()));
      } else {
        getShootingStarStrength(currentQuote);
      }
      totalEntries++;
    }
  }

  private static void getShootingStarStrength(Quote currentQuote) {
    if (currentQuote.isBearish()) {
      System.out.println("Formed Strong Shooting Star on: " + DateUtility.dateToStringFormat1(currentQuote.getDate()));
    } else {
      System.out.println("Formed Weak Shooting Star on: " + DateUtility.dateToStringFormat1(currentQuote.getDate()));
    }
  }

  /**
   *
   * @param lowerWickLength
   * @param bodyLength
   * @param upperWickLength
   * @return true/false
   *
   * Criteria:
   * Length of lower wick should be double the size of candle body
   * There should not be upper wick of very small upper wick, length of upper wick should be less than half the size of candle body
   */
  private static Boolean isHammerCriteriaValid(double lowerWickLength, double bodyLength, double upperWickLength) {
    if ( (lowerWickLength > (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_LONG_WICK_CONDITION_1)
            && upperWickLength <= (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_SHORT_WICK_CONDITION_1) )
        ||
        (lowerWickLength > (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_LONG_WICK_CONDITION_2)
            && upperWickLength <= (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_SHORT_WICK_CONDITION_2) )
        ||
        (upperWickLength <= (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_SHORT_WICK_CONDITION_2)
            && lowerWickLength > ( 3 * upperWickLength)
            && lowerWickLength > (bodyLength * CANDLE_BODY_COEFFICIENT_FOR_LONG_WICK_CONDITION_1) )
    ){
      return true;
    }
    return false;
  }

  private static void checkIfHammerOrHangingMan(List<Quote> history, int currentIndex) {
    if(currentIndex - LOOK_BACK_DAYS >= 0) {
      double lookBackDayClosePrice = history.get(currentIndex - LOOK_BACK_DAYS).getClose();
      Quote currentQuote = history.get(currentIndex);
      double currClosePrice = currentQuote.getClose();
      if (lookBackDayClosePrice > currClosePrice) {
        System.out.println("Formed Hammer on: " + DateUtility.dateToStringFormat1(currentQuote.getDate()));
      } else {
        System.out.println("Formed Hanging Man on: " + DateUtility.dateToStringFormat1(currentQuote.getDate()));
      }
      totalEntries++;
    }
  }

}
