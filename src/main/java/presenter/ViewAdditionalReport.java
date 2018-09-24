package main.java.presenter;

import main.java.model.ProspectiveBuyStock;
import model.Oscillator;

import java.util.List;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
public class ViewAdditionalReport {

  public void viewStocksWhichBrokeResistance(List<ProspectiveBuyStock> resistanceBreakoutStocks) {
    System.out.println("\n\n\n");
    for (ProspectiveBuyStock stock: resistanceBreakoutStocks) {
      System.out.println(stock.getStock().getYahooAlias() + " broke resistance.");
    }
  }

  public void viewStocksWhichAreAboveSma20(List<Oscillator> aboveSma20Stocks) {
    System.out.println("\n\n\n");
    for (Oscillator smaIndicator: aboveSma20Stocks) {
      if (smaIndicator.getSma20() != null) {
        System.out.println(smaIndicator.getStock().getYahooAlias()
            + " is above SMA 20 by " + smaIndicator.getSma20().getDiffPercent());
      }
    }
  }
}
