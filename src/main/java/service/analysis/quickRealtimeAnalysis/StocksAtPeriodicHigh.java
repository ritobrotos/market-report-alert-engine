package main.java.service.analysis.quickRealtimeAnalysis;

import com.google.common.collect.Lists;

import main.java.constant.Constants;
import main.java.constant.FnoStockList;
import main.java.utility.PdfUtility;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.MathUtility;

import java.util.List;

/**
 * Created by ritobrotoseth on 11/09/18.
 */
public class StocksAtPeriodicHigh {

  private static final double ALLOW_DECLINE_FROM_TOP = 7.0D;
  private static final boolean FETCH_DATA_FROM_CACHE = false;

  public static void main(String[] args) {
    performAnalysis();
  }

  private static void performAnalysis() {
    List<StockFact> stockList = FnoStockList.getFnoStockList();
    List<StockFact> topStockList = Lists.newArrayList();
    for (int i = 0; i < stockList.size(); i++) {
      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          stockList.get(i), Constants.FETCH_HISTORY_YEARLY, FETCH_DATA_FROM_CACHE);

      if (history == null || history.isEmpty()) {
        continue;
      }

      StocksAtPeriodicHigh obj = new StocksAtPeriodicHigh();
      obj.findStocksAtPeriodicHigh(history, stockList.get(i), ALLOW_DECLINE_FROM_TOP);
    }

    writeToPdf(topStockList);
  }

  public void findStocksAtPeriodicHigh(List<Quote> history, StockFact stock, double allowDeclineFromTop) {
    List<StockFact> topStockList = Lists.newArrayList();
    double highestPriceSoFar = history.get(0).getClose();
    for (int j = 1; j < history.size(); j++) {
      if (history.get(j).getClose() > highestPriceSoFar) {
        highestPriceSoFar = history.get(j).getClose();
      }
    }

    int size = history.size();
    if (history.get(size - 1).getClose() == highestPriceSoFar
        || MathUtility.declinePercent(history.get(size - 1).getClose(), highestPriceSoFar) <= allowDeclineFromTop) {
      topStockList.add(stock);
      System.out.println("Stocks At High: " + stock.getName());
    }
  }

  private static void writeToPdf(List<StockFact> topStockList) {
    List<String> stockNames = StockFact.convertStockFactToYahooAlias(topStockList);
    new PdfUtility().writeToPdf(stockNames, "/Users/ritobrotoseth/Documents/Reports/topStocks.pdf");
  }
}
