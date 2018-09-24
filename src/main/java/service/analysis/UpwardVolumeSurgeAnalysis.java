package main.java.service.analysis;

import constant.YHDStockUrlList;
import dto.VolumeAnalysisDto;
import main.java.constant.Constants;
import main.java.utility.StockReportingUtility;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;
import ulility.StockHistoryUtility;

import java.util.List;

/**
 * Created by ritobrotoseth on 22/09/18.
 */
public class UpwardVolumeSurgeAnalysis {
  private static final double VOL_SURGE_SEEKED = 5.0D;
  private static final int AVG_VOL_PERIOD = 22;
  private static final int RETROSPECT_DAYS = 100;
  private static final double PRICE_RISE_PERCENT_SEEKED = 4.0D;

  public static void main(String[] args) {
    List<StockFact> watchList = YHDStockUrlList.getStockWatchList();

    for (StockFact stock : watchList) {
      logic(stock);
    }
  }

  private static void logic(StockFact stock) {
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock, Constants.FETCH_HISTORY_YEARLY, true);
    if (history == null || history.isEmpty()) {
      System.out.println("Cannot find history for: " + stock.getYahooAlias());
      return;
    }

//    lookupUpwardVolumeSurgeForEntireRetrospectPeriod(history, stock);
    checkupwardVolumeSurge(history, stock);
  }

  private static void lookupUpwardVolumeSurgeForEntireRetrospectPeriod(List<Quote> history, StockFact stock) {
    int quoteSize = history.size();
    for (int i = quoteSize - RETROSPECT_DAYS; i < quoteSize; i++) {
      if (StockHistoryUtility.isNegative(i, history)) {
        continue;
      }

      List<Quote> subHistory = history.subList(0, i+1);
      checkupwardVolumeSurge(subHistory, stock);
    }
  }

  private static void checkupwardVolumeSurge(List<Quote> subHistory, StockFact stock) {
    VolumeAnalysisDto volumeAnalysis = StockHistoryUtility.isVolumeSpike(
        StockHistoryUtility.getLastNdaysQuotes(subHistory, AVG_VOL_PERIOD),
        VOL_SURGE_SEEKED);

    if (volumeAnalysis.isVolumeSurge() && StockHistoryUtility.isStockInUptrend(subHistory)) {
      System.out.print(stock.getYahooAlias() + ", Surged: " + volumeAnalysis.getVolumeSurgeTimes()
          + " times on " + DateUtility.dateToStringFormat1(volumeAnalysis.getEventDate()) );

      double risePercent = MathUtility.risePercent(subHistory.get(subHistory.size()-1).getClose(),
          subHistory.get(subHistory.size()-2).getClose() );

      if (risePercent > PRICE_RISE_PERCENT_SEEKED) {
        System.out.print(", Price risen: " + risePercent + "%");
      }

      System.out.println("\n\n");
    }
  }
}
