package main.java.service.analysis;

import constant.ConstantVariable;
import lombok.Data;
import model.Quote;
import model.StockFact;
import service.yahooFinance.HistoricalDataService;
import ulility.DateUtility;
import ulility.MathUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * Created by ritobrotoseth on 07/09/18.
 */
public class MaximumPeriodicDrawdown {

  public static void main(String[] args) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      try {
        System.out.print("Enter the stock Alias: ");
        String stockAlias = reader.readLine();

        if (stockAlias.equalsIgnoreCase("exit")) {
          break;
        }

        // Logic
        StockFact stock = StockFact.builder().yahooAlias(stockAlias).build();
        logic(stock);

        System.out.println("\n\n\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static void logic(StockFact stock) {
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock,
        ConstantVariable.FETCH_HISTORY_HALF_YEARLY, true);

    if (history == null || history.isEmpty()) {
      return;
    }

    int size = history.size();
    double high = history.get(0).getClose();
    double low = 100000.0D;
    Date lowDate = null;
    for (int i = 1; i < size - 1; i++) {
      if (history.get(i).getClose() > high) {
        // Find the previous drawdown
        if (low < high) {
          double drawdownPerc = MathUtility.declinePercent(low, high);
          low = 100000.0D;
          if (drawdownPerc > 2) {
            System.out.println(drawdownPerc + " Date: " + DateUtility.dateToStringFormat1(lowDate));
          }
        }
        high = history.get(i).getClose();
      } else {
        if (history.get(i).getLow() < low) {
          low = history.get(i).getLow();
          lowDate = history.get(i).getDate();
        }
      }
    }
  }

}
