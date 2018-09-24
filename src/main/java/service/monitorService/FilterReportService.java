package main.java.service.monitorService;

import main.java.model.Report;
import ulility.MathUtility;

import java.util.List;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
public class FilterReportService {

  public void pickStockWhichRoseMoreThanXPercentInConsecutiveDays(List<Report> reportList,
                                                                   List<Report> stocksUpXPercInConsecutiveDays,
                                                                   double upPercentInConsecutiveDays) {
    for (Report report: reportList) {
      if (report.getConsecutiveRise() != null
          && report.getConsecutiveRise().getPercentageIncrease() > upPercentInConsecutiveDays) {
        stocksUpXPercInConsecutiveDays.add(report);
      }
    }
  }

  public void pickStockWhichFellMoreThanXPercentInConsecutiveDays(List<Report> reportList,
                                                                   List<Report> stocksDownXPercInConsecutiveDays,
                                                                   double downPercentInConsecutiveDays) {
    for (Report report: reportList) {
      if (report.getConsecutiveFall() != null
          && report.getConsecutiveFall().getPercentageDecline() > downPercentInConsecutiveDays) {
        stocksDownXPercInConsecutiveDays.add(report);
      }
    }
  }

  public void pickStockWhichHaveVolumeSurge(List<Report> reportList,
                                             List<Report> volumeSurgeStocks,
                                             double volumeSurgeTimes) {
    for (Report report: reportList) {
      if (report.getVolumeSurge() != null
          && report.getVolumeSurge().getVolumeMultiple() > volumeSurgeTimes) {
        volumeSurgeStocks.add(report);
      }
    }
  }

//  public void pickStockXPercAboveMonthlyLow(List<Report> reportList,
//                                            List<Report> xPercentAboveMonthlyLow) {
//    for (Report report: reportList) {
//      double ltp = report.getLastClosePrice();
//      double montlyLow = report.getMonthLowHigh().getLowVal();
//      double diffPerc = MathUtility.diffPercent(ltp, montlyLow);
//
//      if (diffPerc > 8.5D) {
//        xPercentAboveMonthlyLow.add(report);
//      }
//    }
//  }
}
