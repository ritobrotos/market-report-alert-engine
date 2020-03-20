package main.java.service.monitorService;

import static main.java.model.Report.ConsecutiveFallPercentageDeclineComparator;
import static main.java.model.Report.ConsecutiveRisePercentageIncreaseComparator;
import static main.java.model.Report.GainerComparator;
import static main.java.model.Report.LoserComparator;
import static main.java.model.Report.VolumeSurgeComparator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import constant.ConstantVariable;
import main.java.constant.FnoStockList;
import main.java.constant.ProspectiveBuyStockList;
import main.java.model.ProspectiveBuyStock;
import main.java.model.Report;
import main.java.presenter.ViewAdditionalReport;
import main.java.presenter.ViewReport;
import main.java.service.CommonReportService;
import model.Indicator;
import model.Oscillator;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
  private List<Report> gainers;
  private List<Report> losers;

  private List<Oscillator> aboveSma20Stocks;
  private List<Indicator> indicators;
  private Map<String, List<Double>> stockResistanceLevels;
  private Map<String, List<Double>> stockSupportLevels;

  private static final double UP_PERCENT_IN_CONSECUTIVE_DAYS = 5.0D;
  private static final double DOWN_PERCENT_IN_CONSECUTIVE_DAYS = 3.0D;
  private static final double VOLUME_SURGE_TIMES = 3.0D;
  private static final int AVERAGE_VOLUME_PERIOD = 22;

  private static final boolean GET_DATA_FROM_CACHE = true;
  private static final boolean GET_SPECIFIC_DATE_REPORT = true;
  private static final String REPORT_DATE = "26-Jan-2020";


  private MonitorProspectBuyService() {
    prospectiveBuyMap = ProspectiveBuyStockList.getProspectiveBuyStockMap();
    reportingService = new CommonReportService();
    reportList = Lists.newArrayList();
    resistanceBreakoutStocks = Lists.newArrayList();
    aboveSma20Stocks = Lists.newArrayList();
    indicators = Lists.newArrayList();
    stockResistanceLevels = Maps.newHashMap();
    stockSupportLevels = Maps.newHashMap();
  }

  private void generateReportForProspectBuyTypeStocks() {
    Iterator it = prospectiveBuyMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry)it.next();
      ProspectiveBuyStock prospectStock = (ProspectiveBuyStock) pair.getValue();

      System.out.print(pair.getKey() + ", ");

      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          prospectStock.getStock(), ConstantVariable.FETCH_HISTORY_HALF_YEARLY, GET_DATA_FROM_CACHE);

      if (history == null) {
        continue;
      }

      fetchStockReport(prospectStock.getStock(), history);
      checkResistanceBreakout(prospectStock, history);
      checkStockAboveSmaPrice(prospectStock, history);
    }

    categorizeReport();
  }

  private void generateReportForFnoStocks() {
    List<StockFact> fnoStockList = FnoStockList.getFnoStockList();
    for (StockFact stock : fnoStockList) {
      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          stock, ConstantVariable.FETCH_HISTORY_HALF_YEARLY, GET_DATA_FROM_CACHE);

      if (history == null) {
        continue;
      }

      if (GET_SPECIFIC_DATE_REPORT) {
        Date reportDate = DateUtility.stringToDateFormat3(REPORT_DATE);
        while(true) {
          int size = history.size();
          Date lastDate = history.get(size-1).getDate();
          if (DateUtility.isLaterDateWithoutConsideringTime(lastDate, reportDate)) {
            history.remove(size - 1);
          } else {
            break;
          }
        }
      }

      fetchStockReport(stock, history);
    }

    categorizeReport();
  }

  private void fetchStockReport(StockFact stock, List<Quote> history) {
    Report report = new Report();
    report.setStockFact(stock);
    int size = history.size();
    report.setLastClosePrice(history.get(size - 1).getClose());

    reportingService.reportConsecutiveFallDays(history, report);
    reportingService.reportConsecutiveRiseDays(history, report);
    reportingService.reportVolumeSurge(history, report, AVERAGE_VOLUME_PERIOD);
    reportingService.reportMonth(history, report);
    reportingService.findGainLosePercent(history, report);

    // reportingService.findResistanceSupportPoints(history, report);

    reportList.add(report);
  }



  private void checkResistanceBreakout(ProspectiveBuyStock prospectStock, List<Quote> history) {
    int size = history.size();
    double ltp = history.get(size - 1).getClose();

//    double ltp = Double.parseDouble(LiveQuoteService.getScriptLiveQuote(prospectStock.getStock().getMcLiveAlias()).getCP());

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
    stocksUpXPercInConsecutiveDays = Lists.newArrayList();
    frService.pickStockWhichRoseMoreThanXPercentInConsecutiveDays(reportList, stocksUpXPercInConsecutiveDays,
        UP_PERCENT_IN_CONSECUTIVE_DAYS);

    stocksDownXPercInConsecutiveDays = Lists.newArrayList();
    frService.pickStockWhichFellMoreThanXPercentInConsecutiveDays(reportList, stocksDownXPercInConsecutiveDays,
        DOWN_PERCENT_IN_CONSECUTIVE_DAYS);

    volumeSurgeStocks = Lists.newArrayList();
    frService.pickStockWhichHaveVolumeSurge(reportList, volumeSurgeStocks, VOLUME_SURGE_TIMES);

    gainers = reportList.stream().filter(report -> report.getGainPercent()!= null).collect(Collectors.toList());
    losers = reportList.stream().filter(report -> report.getLosePercent() != null).collect(Collectors.toList());

    stocksUpXPercInConsecutiveDays.sort(ConsecutiveRisePercentageIncreaseComparator);
    stocksDownXPercInConsecutiveDays.sort(ConsecutiveFallPercentageDeclineComparator);
    volumeSurgeStocks.sort(VolumeSurgeComparator);
    gainers.sort(GainerComparator);
    losers.sort(LoserComparator);
    aboveSma20Stocks.sort(Oscillator.Sma20Comparator);
  }

  private void viewReport() {
    ViewReport viewReport = new ViewReport();
    ViewAdditionalReport viewAdditionalReport = new ViewAdditionalReport();

    viewReport.viewStocksWhichWentUpConsecutively(stocksUpXPercInConsecutiveDays);
    viewReport.viewStocksWhichWentDownConsecutively(stocksDownXPercInConsecutiveDays);
//    viewAdditionalReport.viewStocksWhichBrokeResistance(resistanceBreakoutStocks);
    viewReport.viewStocksXPercentHigherThanMonthlyLow(reportList);
    viewReport.viewStocksXPercentLowerThanMonthlyHigh(reportList);
    viewReport.viewStocksWhichSurgedInVolume(volumeSurgeStocks);
    viewReport.viewTodaysGainers(gainers);
    viewReport.viewTodaysLosers(losers);

    viewAdditionalReport.viewStocksWhichAreAboveSma20(aboveSma20Stocks);
  }

  private void startReportingForProspectBuyTypeStocks() {
    generateReportForProspectBuyTypeStocks();
    viewReport();
  }

  private void startReportingForFnoStocks() {
    generateReportForFnoStocks();
    viewReport();
  }

  public static void main(String[] args) {
    MonitorProspectBuyService reportGeneratorObj = new MonitorProspectBuyService();
    reportGeneratorObj.startReportingForFnoStocks();
  }
}
