package main.java.service.analysis;

import constant.ConstantVariable;
import main.java.constant.FnoStockList;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;
import ulility.StockHistoryUtility;

import java.util.Date;
import java.util.List;

public class UpwardStrength {

  private static final int LOOK_BACK_PERIOD = 10;
  private static final boolean GET_DATA_FROM_CACHE = true;
  private static final double STRENGTH_PERCENT = 1.5;

  public static void main(String[] args) {
    List<StockFact> fnoStockList = FnoStockList.getFnoStockList();
    for (StockFact stock : fnoStockList) {
      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          stock,
          ConstantVariable.FETCH_HISTORY_HALF_YEARLY, GET_DATA_FROM_CACHE);

      if (history == null) {
        continue;
      }

      System.out.println("========== " + stock.getYahooAlias());
      findStrengthCandle(history);
      System.out.println("==================================");
    }
  }

  private static void findStrengthCandle(List<Quote> history) {
    int size = history.size();
    if (size <= LOOK_BACK_PERIOD) {
      return;
    }

    double negativeStrength = 0;
    Date negativeStrengthDate = null;
    double positiveStrength = 0;
    Date positiveStrengthDate = null;
    for (int i = size - LOOK_BACK_PERIOD; i < size; i++) {
      if (StockHistoryUtility.isNegative(i, history)) {
        double currentNegativeStrength = MathUtility.declinePercent(history.get(i).getClose(), history.get(i-1).getClose());
        negativeStrengthDate = currentNegativeStrength > negativeStrength ? history.get(i).getDate() : negativeStrengthDate;
        negativeStrength = currentNegativeStrength > negativeStrength ? currentNegativeStrength : negativeStrength;
      }

      if (StockHistoryUtility.isPositive(i, history)) {
        double currentPositiveStrength = MathUtility.risePercent(history.get(i).getClose(), history.get(i-1).getClose());
        positiveStrengthDate = currentPositiveStrength > positiveStrength ? history.get(i).getDate() : positiveStrengthDate;
        positiveStrength = currentPositiveStrength > positiveStrength ? currentPositiveStrength : positiveStrength;
      }
    }

    checkStrengthCriteria(negativeStrength, positiveStrength, negativeStrengthDate, positiveStrengthDate);
  }

  private static void checkStrengthCriteria(double negativeStrength, double positiveStrength, Date negativeStrengthDate,
                                            Date positiveStrengthDate) {
    if (negativeStrength > positiveStrength) {
      if (MathUtility.absDiffPercent(negativeStrength, positiveStrength) > STRENGTH_PERCENT) {
        System.out.println("Negative Strength On: " + DateUtility.dateToStringFormat1(negativeStrengthDate));
      }
    } else {
      if (MathUtility.absDiffPercent(positiveStrength, negativeStrength) > STRENGTH_PERCENT) {
        System.out.println("Positive Strength On: " + DateUtility.dateToStringFormat1(positiveStrengthDate));
      }
    }
  }
}
