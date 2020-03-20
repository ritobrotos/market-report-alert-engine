package main.java.constant;

import main.java.model.ProspectiveBuyStock;
import model.GlobalStockMap;
import model.StockFact;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ritobrotoseth on 19/07/18.
 */
public class ProspectiveBuyStockList {

  private static ProspectiveBuyStock pbs;

  public static Map<String, ProspectiveBuyStock> getProspectiveBuyStockMap() {
    Map<String, StockFact> map = GlobalStockMap.getStockWatchlistMap();

    Map<String, ProspectiveBuyStock> prospectiveBuyMap = new HashMap<String, ProspectiveBuyStock>() {
      {
        ProspectiveBuyStock escorts = new ProspectiveBuyStock(map.get("ESCORTS"), 677);
        put("ESCORTS", escorts);

        ProspectiveBuyStock divislab = new ProspectiveBuyStock(map.get("DIVISLAB"), 1565);
        put("DIVISLAB", divislab);

        ProspectiveBuyStock advenzymes = new ProspectiveBuyStock(map.get("ADVENZYMES"), 1550);
        put("ADVENZYMES", advenzymes);

        ProspectiveBuyStock bgrenergy = new ProspectiveBuyStock(map.get("BGRENERGY"), 69);
        put("BGRENERGY", bgrenergy);

        ProspectiveBuyStock vipind = new ProspectiveBuyStock(map.get("VIPIND"), 471);
        put("VIPIND", vipind);

        ProspectiveBuyStock jswsteel = new ProspectiveBuyStock(map.get("JSWSTEEL"), 354);
        put("JSWSTEEL", jswsteel);

        ProspectiveBuyStock kotakbank = new ProspectiveBuyStock(map.get("KOTAKBANK"), 1336);
        put("KOTAKBANK", kotakbank);

        ProspectiveBuyStock take = new ProspectiveBuyStock(map.get("TAKE"), 165);
        put("TAKE", take);

        ProspectiveBuyStock endurance = new ProspectiveBuyStock(map.get("ENDURANCE"), 1296);
        put("ENDURANCE", endurance);

        ProspectiveBuyStock gnfc = new ProspectiveBuyStock(map.get("GNFC"), 383);
        put("GNFC", gnfc);

        ProspectiveBuyStock edelweiss = new ProspectiveBuyStock(map.get("EDELWEISS"), 186);
        put("EDELWEISS", edelweiss);

        ProspectiveBuyStock iifl = new ProspectiveBuyStock(map.get("IIFL"), 490);
        put("IIFL", iifl);

        ProspectiveBuyStock aarti = new ProspectiveBuyStock(map.get("AARTIIND"), 1500);
        put("AARTIIND", aarti);

        ProspectiveBuyStock dhampursug = new ProspectiveBuyStock(map.get("DHAMPURSUG"), 180);
        put("DHAMPURSUG", dhampursug);

        ProspectiveBuyStock rupa = new ProspectiveBuyStock(map.get("RUPA"), 336);
        put("RUPA", rupa);

        ProspectiveBuyStock bata = new ProspectiveBuyStock(map.get("BATAINDIA"), 1083);
        put("BATAINDIA", bata);






        // Positive for 3 quarters
        ProspectiveBuyStock apollohosp = new ProspectiveBuyStock(map.get("APOLLOHOSP"), 1287);
        put("APOLLOHOSP", apollohosp);

        // Positive for 1 quarter
        ProspectiveBuyStock indianhume = new ProspectiveBuyStock(map.get("INDIANHUME"), 317);
        put("INDIANHUME", indianhume);

        ProspectiveBuyStock balmlawrie = new ProspectiveBuyStock(map.get("BALMLAWRIE"), 0);
        put("BALMLAWRIE", balmlawrie);

        ProspectiveBuyStock godrejind = new ProspectiveBuyStock(map.get("GODREJIND"), 0);
        put("GODREJIND", godrejind);

        ProspectiveBuyStock bluestarco = new ProspectiveBuyStock(map.get("BLUESTARCO"), 0);
        put("BLUESTARCO", bluestarco);

        // This stock started doing well recently
        ProspectiveBuyStock vtl = new ProspectiveBuyStock(map.get("VTL"), 0);
        put("VTL", vtl);

        ProspectiveBuyStock heritgfood = new ProspectiveBuyStock(map.get("HERITGFOOD"), 0);
        put("HERITGFOOD", heritgfood);

        ProspectiveBuyStock prestige = new ProspectiveBuyStock(map.get("PRESTIGE"), 197);
        put("PRESTIGE", prestige);
      }
    };
    return prospectiveBuyMap;
  }
}
