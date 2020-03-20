package main.java.service.analysis;

import static dto.VolumeAnalysisDto.VolumeSurgeTimesDescendingComparator;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import constant.YHDStockUrlList;
import dto.VolumeAnalysisDto;
import main.java.constant.Constants;
import main.java.utility.PdfUtility;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;
import ulility.StockHistoryUtility;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Created by ritobrotoseth on 22/09/18.
 */
public class UpwardVolumeSurgeAnalysis {
  private static final double VOL_SURGE_SEEKED = 9.0D;
  private static final int AVG_VOL_PERIOD = 22;
  private static final int RETROSPECT_DAYS = 15;
  private static final double PRICE_RISE_PERCENT_SEEKED = 2.0D;
  private static final boolean APPLY_STOCK_IN_UPTREND_FILTER = false;

  private static Set<String> ignoreStock = Sets.newHashSet(
//      "TATAINVEST.NS", "BAJAJHLDNG.NS", "JETAIRWAYS.NS", "SRIPIPES.NS", "CUMMINSIND.NS",
//      "FLFL.NS", "PFS.NS", "IDEA.NS", "LAXMIMACH.NS", "PCJEWELLER.NS", "ADANITRANS.NS",
//      "8KMILES.NS", "MAANALU.NS", "RCIIND.BO", "BGRENERGY.NS", "UCOBANK.NS", "ITI.NS",
//      "KESORAMIND.NS", "HSIL.NS", "VHL.NS", "ECLERX.NS", "HIMATSEIDE.NS", "BALLARPUR.NS",
//      "SCHNEIDER.NS", "INDOCO.NS", "COX&KINGS.NS", "DEEPAKFERT.BO"
  );

  public static void main(String[] args) {
    searchUpwardVolumeSurgeOnListOfStocksAndGeneratePdfReport();
//    searchUpwardVolumeSurgeInSingleStock();
  }

  private static void searchUpwardVolumeSurgeInSingleStock() {
    List<VolumeAnalysisDto> volumeSurgeDaysInStock = Lists.newArrayList();
    StockFact stock = new StockFact("Divi's Laboratories Limited", "DIVISLAB.NS");
    logic(stock, volumeSurgeDaysInStock);
  }

  private static void searchUpwardVolumeSurgeOnListOfStocksAndGeneratePdfReport() {
    List<StockFact> watchList = YHDStockUrlList.getStockWatchList();

    List<VolumeAnalysisDto> volumeSurgeStocks = Lists.newArrayList();
    for (StockFact stock : watchList) {
      if (ignoreStock.contains(stock.getYahooAlias())) {
        continue;
      }
      System.out.println(stock.getName());
      logic(stock, volumeSurgeStocks);
    }

    volumeSurgeStocks.sort(VolumeSurgeTimesDescendingComparator);

    writeToPdf(volumeSurgeStocks);
  }

  private static void logic(StockFact stock, List<VolumeAnalysisDto> volumeSurgeStocks) {
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock, Constants.FETCH_HISTORY_YEARLY, true);
    if (history == null || history.isEmpty()) {
      System.out.println("Cannot find history for: " + stock.getYahooAlias());
      return;
    }

    lookupUpwardVolumeSurgeForEntireRetrospectPeriod(history, stock, volumeSurgeStocks);
  }

  private static void lookupUpwardVolumeSurgeForEntireRetrospectPeriod(List<Quote> history, StockFact stock,
                                                                       List<VolumeAnalysisDto> volumeAnalysis) {
    int quoteSize = history.size();
    for (int i = quoteSize - RETROSPECT_DAYS; i < quoteSize; i++) {
      if (StockHistoryUtility.isNegative(i, history)) {
        continue;
      }

      List<Quote> subHistoryTillIthDayForVolSurgeAndUptrendAnalysis = history.subList(0, i+1);
      Optional<VolumeAnalysisDto> optionalValue = checkUpwardVolumeSurge(subHistoryTillIthDayForVolSurgeAndUptrendAnalysis, stock);
      if (optionalValue.isPresent()) {
        volumeAnalysis.add(optionalValue.get());
      }
    }
  }

  private static Optional<VolumeAnalysisDto> checkUpwardVolumeSurge(List<Quote> subHistory, StockFact stock) {
    VolumeAnalysisDto volumeAnalysis = StockHistoryUtility.isVolumeSpike(
        StockHistoryUtility.getLastNdaysQuotes(subHistory, AVG_VOL_PERIOD),
        VOL_SURGE_SEEKED);

    if (volumeAnalysis.isVolumeSurge() && checkStocKInUptrend(subHistory) ) {
      StockHistoryUtility.isStockInUptrend(subHistory);
      double risePercent = MathUtility.risePercent(subHistory.get(subHistory.size()-1).getClose(),
          subHistory.get(subHistory.size()-2).getClose() );

      volumeAnalysis.setStock(stock);

      if (risePercent > PRICE_RISE_PERCENT_SEEKED) {
        volumeAnalysis.setPriceSurge(true);
        volumeAnalysis.setPriceSurgePercent(risePercent);
      }

//      volumeSurgeStocks.add(volumeAnalysis);
      return Optional.of(volumeAnalysis);
    }

    return Optional.empty();
  }

  private static boolean checkStocKInUptrend(List<Quote> subHistory) {
    if (APPLY_STOCK_IN_UPTREND_FILTER == false) {
      return true;
    }
    return StockHistoryUtility.isStockInUptrend(subHistory);
  }

  private static void writeToPdf(List<VolumeAnalysisDto> volumeSurgeStocks) {
    System.out.println("\n\n");

    List<String> volumeSurgeList = Lists.newArrayList();
    for (VolumeAnalysisDto volumeSurgeStock: volumeSurgeStocks) {
      String text = volumeSurgeStock.getStock().getYahooAlias()
          + ", Surged: " + volumeSurgeStock.getVolumeSurgeTimes()
          + " times on " + DateUtility.dateToStringFormat1(volumeSurgeStock.getEventDate());

      if (volumeSurgeStock.isPriceSurge()) {
        text += ". Price risen: " + volumeSurgeStock.getPriceSurgePercent() + "%";
        volumeSurgeList.add(text);
      }
      System.out.println(text);
    }
    new PdfUtility().writeToPdf(volumeSurgeList, "/Users/ritobrotoseth/Documents/Reports/volSurgeStocks.pdf");
  }
}
