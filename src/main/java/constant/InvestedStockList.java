package main.java.constant;

import main.java.model.InvestedStock;
import model.GlobalStockMap;
import model.StockFact;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
public class InvestedStockList {
  public static Map<String, InvestedStock> getInvestedStockMap() {
    Map<String, StockFact> map = GlobalStockMap.getStockWatchlistMap();

    Map<String, InvestedStock> investedMap = new HashMap<String, InvestedStock>() {
      {
        put("DCMSHRIRAM", InvestedStock.builder().buyPoint(391.5).stock(map.get("DCMSHRIRAM"))
          .buyDate(setDate(2018, Calendar.SEPTEMBER, 7)).build() );

        put("GRAPHITE", InvestedStock.builder().buyPoint(1073).stock(map.get("GRAPHITE"))
            .buyDate(setDate(2018, Calendar.AUGUST, 8)).build() );

        put("MAHINDCIE", InvestedStock.builder().buyPoint(291).stock(map.get("MAHINDCIE"))
          .buyDate(setDate(2018, Calendar.SEPTEMBER, 7)).build() );

        put("MPHASIS", InvestedStock.builder().buyPoint(1207).stock(map.get("MPHASIS"))
            .buyDate(setDate(2018, Calendar.AUGUST, 21)).build() );

        put("RELAXO", InvestedStock.builder().buyPoint(809).stock(map.get("RELAXO"))
            .buyDate(setDate(2018, Calendar.AUGUST, 16)).build() );
      }
    };
    return investedMap;
  }

  private static Date setDate(int y, int m, int d) {
    return new GregorianCalendar(y, m, d).getTime();
  }
}
