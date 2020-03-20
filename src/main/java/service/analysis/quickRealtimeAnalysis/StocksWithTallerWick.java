package main.java.service.analysis.quickRealtimeAnalysis;

import model.Quote;
import model.StockFact;
import ulility.StockHistoryUtility;

import java.util.List;

public class StocksWithTallerWick {

  public void checkTallerWick(List<Quote> history, StockFact stock, int averageVolumePeriod, double requiredAvgWickSizeMultiple) {
    double avgWickSize = StockHistoryUtility.getAverageWickSize(history, averageVolumePeriod, true);
    double lastWickSize = history.get(history.size() - 1).getHigh() - history.get(history.size() - 1).getLow();
    double wickSizeMultiple = lastWickSize / avgWickSize;

    if (wickSizeMultiple >= requiredAvgWickSizeMultiple) {
      System.out.println("Taller Wick Size in Stock: " + stock.getName());
    }
  }
}
