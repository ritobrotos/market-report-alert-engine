package main.java.service;

import com.google.common.collect.Lists;

import constant.ConstantVariable;
import main.java.constant.ProspectiveBuyStockList;
import main.java.model.ProspectiveBuyStock;
import main.java.model.Report;
import model.Indicator;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.IndicatorUtility;
import ulility.MathUtility;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ritobrotoseth on 19/07/18.
 */
public class StockAtrService {

  public static void main(String[] args) {
    List<Indicator> indicators = Lists.newArrayList();

    Map<String, ProspectiveBuyStock> prospectiveBuyMap = ProspectiveBuyStockList.getProspectiveBuyStockMap();
    Iterator it = prospectiveBuyMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry)it.next();
      ProspectiveBuyStock prospectStock = (ProspectiveBuyStock) pair.getValue();

      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          prospectStock.getStock(), ConstantVariable.FETCH_HISTORY_QUARTERLY, false);

      if (history == null) {
        continue;
      }

      reportAtr(history, 20, prospectStock.getStock());
    }
  }

  public static void reportAtr(List<Quote> history, int period, StockFact stock) {
    int size = history.size();
    int index = size - period;
    List<Quote> historySublist =  history.subList(index, size);

//    Indicator indicator = new Indicator();
//    indicator.setAtr(IndicatorUtility.calculateAtr(historySublist));

    double atr = IndicatorUtility.calculateAtr(historySublist);
    double atrPerc = MathUtility.twoDecimalPlace( (atr / history.get(size - 1).getClose()) * 100 );
    System.out.println(stock.getName() + "  ::  ATR Value: " + atr
        + "    ATR%: " + atrPerc);
  }
}
