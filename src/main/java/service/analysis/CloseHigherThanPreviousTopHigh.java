package main.java.service.analysis;

import com.google.common.collect.Lists;

import constant.ConstantVariable;
import main.java.constant.ProspectiveBuyStockList;
import main.java.model.ProspectiveBuyStock;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ritobrotoseth on 09/08/18.
 * The code searches for the pattern where the recent price of the stock closes
 * above the high point of the highest close day.
 */
public class CloseHigherThanPreviousTopHigh {

  private static double tradeHighClose = 0.0D;
  private static int tradeUnitHolding = 0;
  private static double tradeAvgUnitPrice = 0.0D;
  private static int tradeLastIndex = 0;
  private static double tradeBaseEntryPrice = 0.0D;
  private static int tradePositionNumber = 0;
  private static double tradeLastEnterPrice = 0.0D;
  private static double tradeSinglePositionSize = 10000.00D;

  private static double longTermTradeReturn = 0.0D;

  private static final boolean printTradeEntryLogs = true;
  private static final boolean printTradeExitLogs = true;


  public static void main(String[] args) {
    startAnalysingSingleStocks();
//    startAnalysingAllProspectiveBuyStocks();
  }

  private static void startAnalysingSingleStocks() {
    StockFact hul = StockFact.builder().yahooAlias("HINDUNILVR.NS").build();
    StockFact vip = StockFact.builder().yahooAlias("VIPIND.NS").build();
    StockFact relaxo = StockFact.builder().yahooAlias("RELAXO.NS").build();
    StockFact jswSteel = StockFact.builder().yahooAlias("JSWSTEEL.NS").build();
    StockFact kei = StockFact.builder().yahooAlias("KEI.NS").build();
    StockFact kec = StockFact.builder().yahooAlias("KEC.NS").build();
    StockFact welent = StockFact.builder().yahooAlias("WELENT.NS").build();
    StockFact jublfood = StockFact.builder().yahooAlias("JUBLFOOD.NS").build();
    StockFact take = StockFact.builder().yahooAlias("TAKE.NS").build();

    new CloseHigherThanPreviousTopHigh().logic(take);
  }

  private static void startAnalysingAllProspectiveBuyStocks() {
    Map<String, ProspectiveBuyStock> prospectiveBuyMap = ProspectiveBuyStockList.getProspectiveBuyStockMap();
    Iterator it = prospectiveBuyMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      ProspectiveBuyStock prospectStock = (ProspectiveBuyStock) pair.getValue();
      new CloseHigherThanPreviousTopHigh().logic(prospectStock.getStock());
    }
  }

  private void logic (StockFact stock) {
    resetAllGlobalValues();

    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock,
        ConstantVariable.FETCH_HISTORY_YEARLY, true);

    if (history == null || history.isEmpty()) {
      return;
    }

    double highClose = history.get(0).getClose();
    double highOfHighestDay = history.get(0).getHigh();
    Date breakoutDay = history.get(0).getDate();

    int size = history.size();

    // Its better to go through the 40 days to get a perspective of
    // the trend of the stock.
    for (int i = 1; i < 40; i++) {
      Quote quote = history.get(i);
      if (quote.getClose() > highOfHighestDay) {
        highClose = quote.getClose();
        highOfHighestDay = quote.getHigh();
        breakoutDay = quote.getDate();
      }
    }

//    printBreakouts(breakoutDay, highClose, highOfHighestDay, 0.0D);

    // Hence forth will check for the breakouts
    for (int i = 40; i < size; i++) {
      Quote quote = history.get(i);
      if (quote.getClose() > highOfHighestDay) {    // The Breakout condition
        double percentRise = MathUtility.risePercent(quote.getClose(), highClose);
        highClose = quote.getClose();
        highOfHighestDay = quote.getHigh();
        breakoutDay = quote.getDate();
        tradeEntrySetup(highClose, i, breakoutDay);
//        printBreakouts(breakoutDay, highClose, highOfHighestDay, MathUtility.twoDecimalPlace(percentRise));
      } else {
        tradeExitSetup(quote.getClose(), quote.getLow(), quote.getDate());
      }
    }

    System.out.println("Trade return on " + stock.getYahooAlias() + " is: " + longTermTradeReturn + "\n");
  }

  private void printBreakouts(Date breakoutDay, double highClose,
                              double highOfHighestDay, double percenRise) {
    System.out.println(DateUtility.dateToStringFormat1(breakoutDay)
        + " :: " + MathUtility.twoDecimalPlace(highClose)
        + " :: " + MathUtility.twoDecimalPlace(highOfHighestDay)
        + " :: " + percenRise);
  }

  /**
   * Entry Rule: For Every Breakout I will take position of 10K
   * Entry Condition: Next entry should be post 5 trading days from the last entry
   *  that is, if entry is taken on index 5, the next entry can be taken on or after 11th index.
   *  The entry should be taken in such a way that with each entry the risk factor should go down
   *
   * Exit Rule 1: If any day the stock closes 10% below the highest close
   * Exit Rule 2: If any day the stock low goes 12% below the highest close, trigger SL.
   * */
  private void tradeEntrySetup(double highClose, int index, Date tradeDay) {
    tradeHighClose = highClose;   // We are tracking this to figure out the exit position.

    if (index - tradeLastIndex <= 5) {
      return;   // Don't take any position
    }

    // Plug n Play
//    if (!isEntryConstraintTwoSatisfied(highClose)) {
//      return;
//    }

    if (!isEntryConstraintThreeSatisfied(highClose)) {
      return;
    }

    if (tradePositionNumber == 0) {
      tradeBaseEntryPrice = highClose;
      tradePositionNumber++;
    }

    tradeLastEnterPrice = highClose;
    int unitTaken = (int) Math.floor(tradeSinglePositionSize / highClose);
    double totalPositionSize = (tradeAvgUnitPrice * tradeUnitHolding) + (unitTaken * highClose);
    tradeUnitHolding += unitTaken;
    tradeAvgUnitPrice = MathUtility.twoDecimalPlace(totalPositionSize / tradeUnitHolding);
    printTradeEntry(tradeDay, unitTaken, highClose);

    tradeLastIndex = index;
  }


  private void entryConstraintOne() {
  }

  private boolean isEntryConstraintTwoSatisfied(double highClose) {
    if (tradePositionNumber == 0) {
      return true;
    }

    List<Double> percentagesAboveBaseEntryPrice = Lists.newArrayList(0.0D, 7.0D, 14.5D, 22.3D, 30.5D, 39.2D);

    double percentRise = MathUtility.risePercent(highClose, tradeBaseEntryPrice);
    if (percentRise > percentagesAboveBaseEntryPrice.get(tradePositionNumber)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * This entry constraint make sure that the next breakout entry is always 3% higher than the previous entry.
   * */
  private boolean isEntryConstraintThreeSatisfied(double highClose) {
    if (tradePositionNumber == 0) {
      return true;
    }

    double percentRise = MathUtility.risePercent(highClose, tradeLastEnterPrice);
    if (percentRise > 3) {
      return true;
    } else {
      return false;
    }
  }

  private void printTradeEntry(Date tradeDay, int unitTaken, Double highClose) {
    if (!printTradeEntryLogs) {
      return;
    }

    System.out.println("Trade Entry On: " + DateUtility.dateToStringFormat1(tradeDay)
        + ", Bought Unit: " + unitTaken
        + ", At price: " + MathUtility.twoDecimalPlace(highClose));
    System.out.println("Total Unit Holding: " + tradeUnitHolding + ", Avg Unit Price: "
        + MathUtility.twoDecimalPlace(tradeAvgUnitPrice) + "\n");
  }

  private void tradeExitSetup(double close, double low, Date tradeDay) {
    double closeDiff = MathUtility.declinePercent(close, tradeHighClose);
    double lowDiff = MathUtility.declinePercent(low, tradeHighClose);
    if (lowDiff > 12) {   // Even if lowDiff is greater than 12, the SL will get tiggered when the stock is down 12% from tradeHighClose
      double slTriggeredPrice = MathUtility.twoDecimalPlace(0.88 * tradeHighClose);
      double tradeReturn = (slTriggeredPrice - tradeAvgUnitPrice) * tradeUnitHolding;
      printTradeExit(tradeDay, slTriggeredPrice, tradeReturn);
      resetAllTradeValues();
    } else if (closeDiff > 10) {  // Exit trade at close price
      double tradeReturn = (close - tradeAvgUnitPrice) * tradeUnitHolding;
      printTradeExit(tradeDay, close, tradeReturn);
      resetAllTradeValues();
    }
  }

  private void printTradeExit(Date tradeDay, Double exitPrice, Double tradeReturn){
    longTermTradeReturn += tradeReturn;
    if (!printTradeExitLogs) {
      return;
    }
    System.out.println("\n"
        + "Trade Exit On: " + DateUtility.dateToStringFormat1(tradeDay)
        + ", Exit Price: " + MathUtility.twoDecimalPlace(exitPrice)
        + ", Trade Return: " + MathUtility.twoDecimalPlace(tradeReturn) + "\n\n\n");
  }

  private void resetAllTradeValues() {
    tradeUnitHolding = 0;
    tradeAvgUnitPrice = 0.0D;
    tradeHighClose = 0.0D;
    tradePositionNumber = 0;
    tradeBaseEntryPrice = 0.0D;
  }

  private void resetAllGlobalValues() {
    tradeUnitHolding = 0;
    tradeAvgUnitPrice = 0.0D;
    tradeHighClose = 0.0D;
    tradeLastIndex = 0;
    longTermTradeReturn = 0.0D;
  }
}
