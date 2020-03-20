package main.java.service.scannerService.candlestickPattern;

import main.java.constant.Constants;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;

import java.util.List;

public class PinbarDetection {

  public static void main(String[] args) {
    StockFact pnb = new StockFact("Punjab National Bank", "PNB.NS");
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        pnb, Constants.FETCH_HISTORY_QUARTERLY, false);

    // https://www.prorealcode.com/prorealtime-indicators/pin-bar-detection/
    for (Quote dayTick : history) {

    }
  }

}
