package main.java.service.monitorService;

import static main.java.model.Report.ConsecutiveFallPercentageDeclineComparator;
import static main.java.model.Report.ConsecutiveRisePercentageIncreaseComparator;
import static main.java.model.Report.VolumeSurgeComparator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import constant.ConstantVariable;
import main.java.constant.ProspectiveBuyStockList;
import main.java.model.ProspectiveBuyStock;
import main.java.model.Report;
import main.java.presenter.ViewAdditionalReport;
import main.java.presenter.ViewReport;
import main.java.service.CommonReportService;
import main.java.service.automation.BreakoutService;
import main.java.utility.StockReportingUtility;
import model.Indicator;
import model.Oscillator;
import model.Quote;
import service.yahooFinance.HistoricalDataService;
import ulility.MathUtility;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ritobrotoseth on 14/07/18.
 */
public class MonitorProspectBuyService {

  private Map<String, ProspectiveBuyStock> prospectiveBuyMap = null;
  private CommonReportService reportingService;

  private List<ProspectiveBuyStock> resistanceBreakoutStocks;

  private List<Report> reportList;
  private List<Report> stocksUpXPercInConsecutiveDays;
  private List<Report> stocksDownXPercInConsecutiveDays;
  private List<Report> volumeSurgeStocks;
  private List<Oscillator> aboveSma20Stocks;
  private List<Indicator> indicators;
  private Map<String, List<Double>> stockResistanceLevels;
  private Map<String, List<Double>> stockSupportLevels;

  private static final double upPercentInConsecutiveDays = 5.0D;
  private static final double downPercentInConsecutiveDays = 3.0D;
  private static final double volumeSurgeTimes = 5.0D;
  private static final int averageVolumePeriod = 22;

  private MonitorProspectBuyService() {
    prospectiveBuyMap = ProspectiveBuyStockList.getProspectiveBuyStockMap();
    reportingService = new CommonReportService();
    reportList = Lists.newArrayList();
    resistanceBreakoutStocks = Lists.newArrayList();
    stocksUpXPercInConsecutiveDays = Lists.newArrayList();
    stocksDownXPercInConsecutiveDays = Lists.newArrayList();
    volumeSurgeStocks = Lists.newArrayList();
    aboveSma20Stocks = Lists.newArrayList();
    indicators = Lists.newArrayList();

    stockResistanceLevels = Maps.newHashMap();
    stockSupportLevels = Maps.newHashMap();
  }

  private void generateReport() {
    Iterator it = prospectiveBuyMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry)it.next();
      ProspectiveBuyStock prospectStock = (ProspectiveBuyStock) pair.getValue();

      System.out.print(pair.getKey() + ", ");

      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          prospectStock.getStock(), ConstantVariable.FETCH_HISTORY_HALF_YEARLY, false);

      if (history == null) {
        continue;
      }

      Report report = new Report();
      report.setStockFact(prospectStock.getStock());
      int size = history.size();
      report.setLastClosePrice(history.get(size - 1).getClose());

      reportingService.reportConsecutiveFallDays(history, report);
      reportingService.reportConsecutiveRiseDays(history, report);
      reportingService.reportVolumeSurge(history, report, averageVolumePeriod);
      reportingService.reportMonth(history, report);
      reportingService.findResistanceSupportPoints(history, report);

      reportList.add(report);

      checkResistanceBreakout(prospectStock, history);
      checkStockAboveSmaPrice(prospectStock, history);
    }

    categorizeReport();
  }



  private void checkResistanceBreakout(ProspectiveBuyStock prospectStock, List<Quote> history) {
    int size = history.size();
    double ltp = history.get(size - 1).getClose();
    if (prospectStock.getResistanceLevel() != 0) {
      if (ltp > prospectStock.getResistanceLevel()) {
        resistanceBreakoutStocks.add(prospectStock);
      }
    }
  }

  private void checkStockAboveSmaPrice(ProspectiveBuyStock prospectStock, List<Quote> history) {
    Oscillator indicator = new Oscillator(prospectStock.getStock());
    double sma20 = indicator.getSma20().getSma20Val(history);
    int size = history.size();
    double ltp = history.get(size - 1).getClose();

    if (ltp > sma20) {
      double risePerc = MathUtility.risePercent(ltp, sma20);
      indicator.getSma20().setDiffPercent(risePerc);
      aboveSma20Stocks.add(indicator);
    }
  }

  private void categorizeReport() {
    FilterReportService frService = new FilterReportService();
    frService.pickStockWhichRoseMoreThanXPercentInConsecutiveDays(reportList, stocksUpXPercInConsecutiveDays, upPercentInConsecutiveDays);
    frService.pickStockWhichFellMoreThanXPercentInConsecutiveDays(reportList, stocksDownXPercInConsecutiveDays, downPercentInConsecutiveDays);
    frService.pickStockWhichHaveVolumeSurge(reportList, volumeSurgeStocks, volumeSurgeTimes);

    stocksUpXPercInConsecutiveDays.sort(ConsecutiveRisePercentageIncreaseComparator);
    stocksDownXPercInConsecutiveDays.sort(ConsecutiveFallPercentageDeclineComparator);
    volumeSurgeStocks.sort(VolumeSurgeComparator);
    aboveSma20Stocks.sort(Oscillator.Sma20Comparator);
  }

  private void viewReport() {
    ViewReport viewReport = new ViewReport();
    ViewAdditionalReport viewAdditionalReport = new ViewAdditionalReport();

    viewReport.viewStocksWhichWentUpConsecutively(stocksUpXPercInConsecutiveDays);
    viewReport.viewStocksWhichWentDownConsecutively(stocksDownXPercInConsecutiveDays);
    viewAdditionalReport.viewStocksWhichBrokeResistance(resistanceBreakoutStocks);
    viewReport.viewStocksXPercentHingherThanMonthlyLow(reportList);
    viewReport.viewStocksWhichSurgedInVolume(volumeSurgeStocks);
    viewAdditionalReport.viewStocksWhichAreAboveSma20(aboveSma20Stocks);
  }

  private void startReporting() {
    generateReport();
    viewReport();
  }

  public static void main(String[] args) {
    MonitorProspectBuyService reportGeneratorObj = new MonitorProspectBuyService();
    reportGeneratorObj.startReporting();
  }
}
