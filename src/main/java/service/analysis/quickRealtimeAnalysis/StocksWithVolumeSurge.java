package main.java.service.analysis.quickRealtimeAnalysis;

import model.Quote;
import model.StockFact;
import ulility.StockHistoryUtility;

import java.util.List;

public class StocksWithVolumeSurge {

  public void checkVolumeSurge(List<Quote> history, StockFact stock, int averageVolumePeriod, double requiredAvgVolMultiple) {
    double volumeAvg = StockHistoryUtility.getAverageVolume(history, averageVolumePeriod, true);
    double lastVolume = history.get(history.size() - 1).getVolume();
    double volumeMultiple = lastVolume / volumeAvg;

    if (volumeMultiple >= requiredAvgVolMultiple) {
      System.out.println("Volume Surge in Stock: " + stock.getName());
    }
  }
}
