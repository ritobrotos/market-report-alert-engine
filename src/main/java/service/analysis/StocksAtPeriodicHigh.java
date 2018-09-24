package main.java.service.analysis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import constant.YHDStockUrlList;
import dto.StockFactDto;
import main.java.constant.Constants;
import main.java.utility.PdfUtility;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ritobrotoseth on 11/09/18.
 */
public class StocksAtPeriodicHigh {

  private static final double ALLOW_DECLINE_FROM_TOP = 8.0D;

  public static void main(String[] args) {
    List<StockFact> watchList = YHDStockUrlList.getStockWatchList();
    List<StockFact> topStockList = Lists.newArrayList();
    Map<String, StockFactDto> ignoreList = createStockIgnoreList();

    for (int i = 0; i < watchList.size(); i++) {
      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          watchList.get(i), Constants.FETCH_HISTORY_YEARLY, true);

      if (history == null || history.isEmpty()) {
        continue;
      }

      double high = history.get(0).getClose();
      for (int j = 1; j < history.size(); j++) {
        if (history.get(j).getClose() > high) {
          high = history.get(j).getClose();
        }
      }

      int size = history.size();
      if ( history.get(size - 1).getClose() == high
          || MathUtility.declinePercent(history.get(size - 1).getClose(), high) <= ALLOW_DECLINE_FROM_TOP) {
        if (ignoreList.get(watchList.get(i).getYahooAlias()) == null) {
          topStockList.add(watchList.get(i));
        }
        System.out.println(watchList.get(i).getName());
      }
    }

    writeToPdf(topStockList);
  }

  private static Map<String, StockFactDto> createStockIgnoreList() {
    Map<String, StockFactDto> ignoreList = Maps.newHashMap();
    addToIgnoreList(ignoreList, "23-09-2018", "WIPRO.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "SYNGENE.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "ITC.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "POWERGRID.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "TRENT.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "PIDILITIND.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "ATUL.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "DRREDDY.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "SUNPHARMA.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "OMAXE.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "GAIL.NS");
    addToIgnoreList(ignoreList, "23-09-2018", "TATACHEM.NS");

    return ignoreList;
  }

  private static StockFact setYahooAlias(String yahooAlias) {
    return StockFact.builder().yahooAlias(yahooAlias).build();
  }

  private static Date setIgnoreDate(String stringDate) {
    return DateUtility.stringToDateFormat2(stringDate);
  }

  private static void addToIgnoreList(Map<String, StockFactDto> ignoreList, String ignoreDate, String name) {
    ignoreList.put(name,
      StockFactDto.builder()
          .dateVariable( setIgnoreDate(ignoreDate) )
          .stockFact( setYahooAlias(name) ).build()
    );
  }

  private static void writeToPdf(List<StockFact> topStockList) {
    List<String> stockNames = StockFact.convertStockFactToYahooAlias(topStockList);
    new PdfUtility().writeToPdf(stockNames, "/Users/ritobrotoseth/Documents/Reports/topStocks.pdf");
  }
}
