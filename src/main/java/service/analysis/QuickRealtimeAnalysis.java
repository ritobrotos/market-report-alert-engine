package main.java.service.analysis;

import main.java.constant.Constants;
import main.java.constant.FnoStockList;
import main.java.service.analysis.quickRealtimeAnalysis.StocksAtPeriodicHigh;
import main.java.service.analysis.quickRealtimeAnalysis.StocksWithTallerWick;
import main.java.service.analysis.quickRealtimeAnalysis.StocksWithVolumeSurge;
import model.Quote;
import model.QuoteSupplementary;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;

import java.util.List;

public class QuickRealtimeAnalysis {

  private static final boolean FETCH_DATA_FROM_CACHE = false;
  private static final int MISSING_DAYS_ALLOWED = 10;

  private static class StocksAtPeriodicHighVariables {
    static final double ALLOW_DECLINE_FROM_TOP = 5.0D;
  }

  private static class StocksWithVolumeSurgeVariables {
    static final int AVERAGE_VOLUME_PERIOD = 20;
    static final double REQUIRED_AVG_VOL_MULTIPLE = 4;
  }

  private static class StocksWithTallerWickVariable {
    static final int AVERAGE_WICK_SIZE_PERIOD = 20;
    // The multiple size is kept as 3 so that it can figure out stocks which
    static final double REQUIRED_AVG_WICK_SIZE_MULTIPLE = 3;
  }

  public static void main(String[] args) {
    QuickRealtimeAnalysis analysis = new QuickRealtimeAnalysis();
    analysis.beginAnalysis();
  }

  private void beginAnalysis() {
    // Fetch List Of Stocks
    List<StockFact> stockList = FnoStockList.getFnoStockList();

    for (int i = 0; i < stockList.size(); i++) {
      QuoteSupplementary quoteSupplementary = null;
      HistoricalDataService dataService = new HistoricalDataService();
      try {
        quoteSupplementary = dataService.getHistoricalTicksWithCacheOption(
            stockList.get(i), Constants.FETCH_HISTORY_YEARLY, FETCH_DATA_FROM_CACHE);
      } catch (RuntimeException e) {
        System.out.println("Exception thrown while fetching history for stock " + stockList.get(i).getName());
      }


      if (quoteSupplementary == null || quoteSupplementary.getHistoricTicks() == null
          || quoteSupplementary.getHistoricTicks().isEmpty()) {
        continue;
      }

      List<Quote> history = quoteSupplementary.getHistoricTicks();
      if(quoteSupplementary.getMissingTicks() > MISSING_DAYS_ALLOWED) {
        System.out.println("Missing dates are more for the stock: " + stockList.get(i).getName());
        continue;
      }

      // Find Stocks at Periodic High
      StocksAtPeriodicHigh stocksAtPeriodicHigh = new StocksAtPeriodicHigh();
      stocksAtPeriodicHigh.findStocksAtPeriodicHigh(history, stockList.get(i),
          StocksAtPeriodicHighVariables.ALLOW_DECLINE_FROM_TOP);

      // Find stocks with surge in Volume
      StocksWithVolumeSurge stocksWithVolumeSurge = new StocksWithVolumeSurge();
      stocksWithVolumeSurge.checkVolumeSurge(history, stockList.get(i),
          StocksWithVolumeSurgeVariables.AVERAGE_VOLUME_PERIOD, StocksWithVolumeSurgeVariables.REQUIRED_AVG_VOL_MULTIPLE);

      // Find stocks with Taller Wick
      StocksWithTallerWick stocksWithTallerWick = new StocksWithTallerWick();
      stocksWithTallerWick.checkTallerWick(history, stockList.get(i), StocksWithTallerWickVariable.AVERAGE_WICK_SIZE_PERIOD,
          StocksWithTallerWickVariable.REQUIRED_AVG_WICK_SIZE_MULTIPLE);
    }

  }
}
