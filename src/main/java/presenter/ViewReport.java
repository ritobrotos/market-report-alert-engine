package main.java.presenter;

import main.java.model.Report;
import ulility.MathUtility;

import java.util.List;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
public class ViewReport {

  public void viewStocksWhichWentUpConsecutively(List<Report> reports) {
    System.out.println("\n\n\n");
    for (Report report: reports) {
      if (report.getConsecutiveRise() != null) {
        System.out.println("LTP: " + MathUtility.twoDecimalPlace(report.getLastClosePrice()) + " :::   "
            + report.getStockFact().getYahooAlias() + " is up for "
            + report.getConsecutiveRise().getNoOfDays() + " day/s and went up "
            + report.getConsecutiveRise().getPercentageIncrease() + "%");

//        System.out.println("Resistance Levels: ");
//        for (int i = 0; i < report.getResistanceLevels().size(); i++) {
//          System.out.print(report.getResistanceLevels().get(i) + " ,  ");
//        }
      }
      System.out.println();
    }
  }

  public void viewStocksWhichWentDownConsecutively(List<Report> reports) {
    System.out.println("\n\n\n");
    for (Report report: reports) {
      if (report.getConsecutiveFall() != null) {
        System.out.println("LTP: " + MathUtility.twoDecimalPlace(report.getLastClosePrice()) + " :::   "
            + report.getStockFact().getYahooAlias() + " is down for "
            + report.getConsecutiveFall().getNoOfDays() + " day/s and went down "
            + report.getConsecutiveFall().getPercentageDecline() + "%");

        System.out.println("Support Levels: ");
        for(int i = 0; i < report.getSupportLevels().size(); i++) {
          System.out.print(report.getSupportLevels().get(i) + " ,  ");
        }
      }
      System.out.println();
    }
  }

  public void viewStocksWhichSurgedInVolume(List<Report> reports) {
    System.out.println("\n\n\n");
    for (Report report: reports) {
      if (report.getVolumeSurge() != null) {
        System.out.println(report.getStockFact().getYahooAlias() + " volume surged "
            + report.getVolumeSurge().getVolumeMultiple() + " times");
      }
    }
  }

  public void viewStocksXPercentHigherThanMonthlyLow(List<Report> reports) {
    System.out.println("\n\n\n");
    for (Report report: reports) {
      double ltp = report.getLastClosePrice();
      double monthlyLow = report.getMonthLowHigh().getLowVal();
      double diffPerc = MathUtility.diffPercent(ltp, monthlyLow);

      if (diffPerc > 8.5D) {
        System.out.println(report.getStockFact().getYahooAlias()
            + " is above " + diffPerc + "% "
            + " from Monthly Low of " + MathUtility.twoDecimalPlace(monthlyLow));
      }
    }
  }

  public void viewStocksXPercentLowerThanMonthlyHigh(List<Report> reports) {
    System.out.println("\n\n\n");
    for (Report report: reports) {
      double ltp = report.getLastClosePrice();
      double monthlyHigh = report.getMonthLowHigh().getHighVal();
      double diffPerc = MathUtility.declinePercent(ltp, monthlyHigh);

      if (diffPerc > 8.5D) {
        System.out.println(report.getStockFact().getYahooAlias()
            + " is below " + diffPerc + "% "
            + " from Monthly High of " + MathUtility.twoDecimalPlace(monthlyHigh));
      }
    }
  }

  public void viewTodaysGainers(List<Report> reports) {
    System.out.println("\n\n\n");
    for (Report report : reports) {
      System.out.println(report.getStockFact().getYahooAlias() + " gained: " + report.getGainPercent());
    }
  }

  public void viewTodaysLosers(List<Report> reports) {
    System.out.println("\n\n\n");
    for (Report report : reports) {
      System.out.println(report.getStockFact().getYahooAlias() + " lost: " + report.getLosePercent());
    }
  }
}
