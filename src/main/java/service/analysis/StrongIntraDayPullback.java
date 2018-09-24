package main.java.service.analysis;

import constant.ConstantVariable;
import constant.YHDStockUrlList;
import main.java.constant.ProspectiveBuyStockList;
import main.java.model.ProspectiveBuyStock;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.IndicatorUtility;
import ulility.MathUtility;
import ulility.StockHistoryUtility;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ritobrotoseth on 25/07/18.
 */
public class StrongIntraDayPullback {

  public static void main(String[] args) {
    Map<String, ProspectiveBuyStock> prospectiveBuyMap = ProspectiveBuyStockList.getProspectiveBuyStockMap();
    Iterator it = prospectiveBuyMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      ProspectiveBuyStock prospectStock = (ProspectiveBuyStock) pair.getValue();
      find(prospectStock.getStock());
    }
  }

  private static void find(StockFact stock) {
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock,
        ConstantVariable.FETCH_HISTORY_HALF_YEARLY, true);

    int size = history.size();
    for (int i = 10; i < size; i++) {
      Quote tick = history.get(i);

      List<Quote> historySublist =  history.subList(i-10, i);
      double atr = IndicatorUtility.calculateAtr(historySublist);
      double atrPerc = (atr/history.get(i).getClose()) * 100;

      // Check that the stock was in downtrend when this event happened
      double avgPrice = StockHistoryUtility.getAverageClosePrice(historySublist);

      double pullBackPercent = MathUtility.diffPercent(tick.getClose(), tick.getLow());
      // Difference between open and low should be more than 1%
      double openlowPerc = MathUtility.diffPercent(tick.getOpen(), tick.getLow());
      if (pullBackPercent > ( 1.5 * atrPerc )
          && openlowPerc > atrPerc
          && history.get(i).getClose() < (avgPrice)) {
        System.out.println(stock.getYahooAlias() + "   ::   " + DateUtility.dateToStringFormat1(tick.getDate()) );
      }
    }
  }
}
