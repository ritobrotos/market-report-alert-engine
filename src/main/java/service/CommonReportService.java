package main.java.service;

import main.java.model.Report;
import main.java.service.automation.BreakoutService;
import main.java.utility.StockReportingUtility;
import model.Quote;
import ulility.IndicatorUtility;
import ulility.MathUtility;
import ulility.StockHistoryUtility;

import java.util.List;

/**
 * Created by ritobrotoseth on 17/07/18.
 */
public class CommonReportService {

  /**
   * Reports for how many days this stock is -ve closing and how much this stock
   * has lost in this bearish phase.
   * */
  public void reportConsecutiveFallDays(List<Quote> history, Report consolidatedReport) {
    int size = history.size();
    if(StockReportingUtility.isNegativeClose(size-1, history)) {
      int count = 1;
      for(int i = size-2; i >= 1; i--) {
        if(StockReportingUtility.isNegativeClose(i, history)) {
          count++;
        } else {
          break;
        }
      }
      double decline = history.get(size - (1+count)).getClose() - history.get(size-1).getClose();
      double percentageDec = MathUtility.twoDecimalPlace((decline / history.get(size - (1+count)).getClose()) * 100);

      consolidatedReport.getConsecutiveFall().setPercentageDecline(percentageDec);
      consolidatedReport.getConsecutiveFall().setNoOfDays(count);
      return;
    }

    consolidatedReport.setConsecutiveFall(null);
  }

  /**
   * Reports for how many days this stock is +ve closing and how much this stock
   * has gained in this bullish phase.
   */
  public void reportConsecutiveRiseDays(List<Quote> history, Report consolidatedReport) {
    int size = history.size();
    if(StockReportingUtility.isPositiveClose(size-1, history)) {
      int count = 1;
      for(int i = size-2; i >= 1; i--) {
        if(StockReportingUtility.isPositiveClose(i, history)) {
          count++;
        } else {
          break;
        }
      }
      double increase = history.get(size-1).getClose() - history.get(size - (1+count)).getClose();
      double percentageInc = MathUtility.twoDecimalPlace((increase / history.get(size - (1+count)).getClose()) * 100);

      consolidatedReport.getConsecutiveRise().setPercentageIncrease(percentageInc);
      consolidatedReport.getConsecutiveRise().setNoOfDays(count);
      return;
    }

    consolidatedReport.setConsecutiveRise(null);
  }

  /**
   * This method will create a report on monthly performance of the stock, which
   * will include the monthly high and monthy low and where the stock
   * stands right now with respect to the two ends.
   * */
  public void reportMonth(List<Quote> history, Report consolidatedReport) {
    Report report = reportPeriodic(history, "MONTHLY");
    consolidatedReport.getMonthLowHigh().setHighVal(report.getPeriodLowHigh().getHighVal());
    consolidatedReport.getMonthLowHigh().setLowVal(report.getPeriodLowHigh().getLowVal());
  }

  /**
   * This method will create a report on quarterly performance of the stock, which
   * will include the quarterly high and quarterly low and where the stock
   * stands right now with respect to the two ends.
   * */
  public void reportQuaterly(List<Quote> history, Report consolidatedReport) {
    Report report = reportPeriodic(history, "QUARTERLY");
    consolidatedReport.getQuarterLowHigh().setHighVal(report.getPeriodLowHigh().getHighVal());
    consolidatedReport.getQuarterLowHigh().setLowVal(report.getPeriodLowHigh().getLowVal());
  }

  /**
   * The reportPeriodic method is being used by both reportMonth and reportQuaterly method
   * */
  public Report reportPeriodic(List<Quote> history, String durationType) {
    int index = StockReportingUtility.findStartIndex(history, durationType);
    int size = history.size();
    List<Quote> historySublist =  history.subList(index, size);
    Report report = StockReportingUtility.reportPeriodLowHigh(historySublist);
    return report;
  }

  public void reportVolumeSurge(List<Quote> history, Report consolidatedReport, int averageVolumePeriod) {
    double volumeAvg = StockHistoryUtility.getAverageVolume(history, averageVolumePeriod, true);
    double lastVolume = history.get(history.size() - 1).getVolume();
    double volumeMultiple = lastVolume / volumeAvg;

    if (volumeMultiple > 2) {
      consolidatedReport.getVolumeSurge().setVolumeMultiple(volumeMultiple);
      return;
    }
    consolidatedReport.setVolumeSurge(null);
  }

  public void findResistanceSupportPoints(List<Quote> history, Report consolidatedReport) {
    BreakoutService breakoutService = new BreakoutService(history, consolidatedReport.getStockFact());
    consolidatedReport.setSupportLevels(breakoutService.getSupportLevels());
    consolidatedReport.setResistanceLevels(breakoutService.getResistanceLevels());
  }

}
