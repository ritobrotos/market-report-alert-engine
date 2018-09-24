package main.java.service.analysis.archive;

import com.google.common.collect.Lists;

import constant.ConstantVariable;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;

import java.util.Date;
import java.util.List;

/**
 * Created by ritobrotoseth on 22/07/18.
 */
public class HugeHighCloseGap {

  private static List<Double> data = Lists.newArrayList();

  public static void main(String[] args) {
    StockFact polymed = StockFact.builder().name("Poly Medicure Limited").yahooAlias("POLYMED.NS").build();
    StockFact asianPaint = StockFact.builder().name("Asian Paints Ltd").yahooAlias("ASIANPAINT.BO").build();
    StockFact fsl = StockFact.builder().name("Firstsource Solutions Limited").yahooAlias("FSL.NS").build();
    StockFact take = StockFact.builder().name("TAKE Solutions Limited").yahooAlias("TAKE.NS").build();
    StockFact jublfood = StockFact.builder().name("Jubilant FoodWorks Limited").yahooAlias("JUBLFOOD.NS").build();
    StockFact bata = StockFact.builder().name("Bata India Limited").yahooAlias("BATAINDIA.NS").build();


    figureUptrend(bata);

  }

  private static void figureDownTrend(StockFact stock) {
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock,
        ConstantVariable.FETCH_HISTORY_HALF_YEARLY, true);

    int size = history.size();
    double diff = 0.0D;
    double diffAvg = 0.0D;
    for (int i = 0; i < size; i++) {
      double upCloseGap = history.get(i).getHigh() - history.get(i).getClose();
      double lowCloseGap = Math.abs(history.get(i).getLow() - history.get(i).getClose());
      diff += upCloseGap - lowCloseGap;
      if (i == 0) {
        diffAvg =  diff;
      } else {
        diffAvg = MathUtility.twoDecimalPlace((diffAvg + diff)/2);
      }


      Date date = history.get(i).getDate();
      System.out.println(DateUtility.dateToStringFormat1(date)
          + " :: " + MathUtility.twoDecimalPlace(diff)
          + " :: " + diffAvg);
    }
  }

  private static void figureUptrend(StockFact stock) {
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock,
        ConstantVariable.FETCH_HISTORY_HALF_YEARLY, true);

    int size = history.size();
    double diff = 0.0D;
    double diffAvg = 0.0D;
    int incrementCounter = 0;

    for (int i = 0; i < size; i++) {
      double lowCloseGap = Math.abs(history.get(i).getLow() - history.get(i).getClose()); // If the gap is big it shows strength
      double highCloseGap = history.get(i).getHigh() - history.get(i).getClose();   // If the gap is big it shows weakness

      diff += lowCloseGap - highCloseGap;
      if (i == 0) {
        diffAvg =  diff;
      } else {
        diffAvg = MathUtility.twoDecimalPlace((diffAvg + diff)/2);
      }
//      data.add(diffAvg);
//      int diffsize = data.size();
//      if (diffsize >= 5) {
//        if (data.get(diffsize - 1) > data.get(diffsize - 2)) {
//          incrementCounter++;
//          if (incrementCounter >= 5) {
//            Date date = history.get(i).getDate();
//            System.out.println(DateUtility.dateToStringFormat1(date));
//          }
//        } else {
//          incrementCounter = 0;
//        }
//      }


      Date date = history.get(i).getDate();
      System.out.println(DateUtility.dateToStringFormat1(date)
              + "\t" + MathUtility.twoDecimalPlace(diff)
//          + "\t" + diffAvg
          + "\t" + MathUtility.twoDecimalPlace(history.get(i).getClose()));
    }
  }

  private static void showStrengthDays(StockFact stock) {
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock,
        ConstantVariable.FETCH_HISTORY_HALF_YEARLY, true);

    int size = history.size();
    int strengthDays = 0;
    for (int i = 0; i < size; i++) {
      double lowCloseGap = Math.abs(history.get(i).getLow() - history.get(i).getClose()); // If the gap is big it shows strength
      double highCloseGap = history.get(i).getHigh() - history.get(i).getClose();   // If the gap is big it shows weakness

      if ((lowCloseGap - highCloseGap) > 0) {
        strengthDays++;
      } else {
        strengthDays--;
      }

      Date date = history.get(i).getDate();
      System.out.println(DateUtility.dateToStringFormat1(date)
          + "\t" + strengthDays);
    }
  }
}
