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

        // Rank 7
        put("CHOLAFIN", pbs.builder().stock(map.get("CHOLAFIN")).build());
        put("EDELWEISS", ProspectiveBuyStock.builder().stock(map.get("EDELWEISS")).build());

        ProspectiveBuyStock escorts = new ProspectiveBuyStock(map.get("ESCORTS"), 0);
        put("ESCORTS", escorts);

        put("GEOJITFSL", ProspectiveBuyStock.builder().stock(map.get("GEOJITFSL")).build());

        ProspectiveBuyStock gruh = new ProspectiveBuyStock(map.get("GRUH"), 347);
        put("GRUH", gruh);

        ProspectiveBuyStock hindunilvr = new ProspectiveBuyStock(map.get("HINDUNILVR"), 1800);
        put("HINDUNILVR", hindunilvr);

        ProspectiveBuyStock iifl = new ProspectiveBuyStock(map.get("IIFL"), 802);
        put("IIFL", iifl);

        ProspectiveBuyStock indusind = new ProspectiveBuyStock(map.get("INDUSINDBK"), 0);
        put("INDUSINDBK", indusind);

        ProspectiveBuyStock kec = new ProspectiveBuyStock(map.get("KEC"), 429);
        put("KEC", kec);

        ProspectiveBuyStock kei = new ProspectiveBuyStock(map.get("KEI"), 463);
        put("KEI", kei);

        ProspectiveBuyStock kotak = new ProspectiveBuyStock(map.get("KOTAKBANK"), 1336);
        put("KOTAKBANK", kotak);

        ProspectiveBuyStock merck = new ProspectiveBuyStock(map.get("MERCK"), 0);
        put("MERCK", merck);

        ProspectiveBuyStock mindaind = new ProspectiveBuyStock(map.get("MINDAIND"), 0);
        put("MINDAIND", mindaind);

//        ProspectiveBuyStock pageind = new ProspectiveBuyStock(map.get("PAGEIND"), 0);
//        put("PAGEIND", pageind);

        put("PPAP", ProspectiveBuyStock.builder().stock(map.get("PPAP")).build());

        ProspectiveBuyStock sundrmfast = new ProspectiveBuyStock(map.get("SUNDRMFAST"), 0);
        put("SUNDRMFAST", sundrmfast);

        ProspectiveBuyStock take = new ProspectiveBuyStock(map.get("TAKE"), 0);
        put("TAKE", take);

        ProspectiveBuyStock tataSponge = new ProspectiveBuyStock(map.get("TATASPONGE"), 0);
        put("TATASPONGE", tataSponge);

        ProspectiveBuyStock vipind = new ProspectiveBuyStock(map.get("VIPIND"), 0);
        put("VIPIND", vipind);

        ProspectiveBuyStock wstcstpapr = new ProspectiveBuyStock(map.get("WSTCSTPAPR"), 0);
        put("WSTCSTPAPR", wstcstpapr);









        // Rank 6
        put("BAJAJHLDNG", ProspectiveBuyStock.builder().stock(map.get("BAJAJHLDNG")).build());
        put("BRITANNIA", ProspectiveBuyStock.builder().stock(map.get("BRITANNIA")).build());

        ProspectiveBuyStock dhfl = new ProspectiveBuyStock(map.get("DHFL"), 0);
        put("DHFL", dhfl);

        ProspectiveBuyStock dbl = new ProspectiveBuyStock(map.get("DBL"), 0);
        put("DBL", dbl);

        ProspectiveBuyStock graphite = new ProspectiveBuyStock(map.get("GRAPHITE"), 0);
        put("GRAPHITE", graphite);

        ProspectiveBuyStock hexaware = new ProspectiveBuyStock(map.get("HEXAWARE"), 457);
        put("HEXAWARE", hexaware);

        ProspectiveBuyStock lti = new ProspectiveBuyStock(map.get("LTI"), 0);
        put("LTI", lti);

        ProspectiveBuyStock mahindra = new ProspectiveBuyStock(map.get("M&M"), 0);
        put("M&M", mahindra);

        ProspectiveBuyStock mahindcie = new ProspectiveBuyStock(map.get("MAHINDCIE"), 0);
        put("MAHINDCIE", mahindcie);

        ProspectiveBuyStock powergrid = new ProspectiveBuyStock(map.get("POWERGRID"), 0);
        put("POWERGRID", powergrid);

        ProspectiveBuyStock solarinds = new ProspectiveBuyStock(map.get("SOLARINDS"), 0);
        put("SOLARINDS", solarinds);

        ProspectiveBuyStock suven = new ProspectiveBuyStock(map.get("SUVEN"), 0);
        put("SUVEN", suven);

        ProspectiveBuyStock welent = new ProspectiveBuyStock(map.get("WELENT"), 0);
        put("WELENT", welent);

        ProspectiveBuyStock whirlpool = new ProspectiveBuyStock(map.get("WHIRLPOOL"), 0);
        put("WHIRLPOOL", whirlpool);













        // Rank 5
        ProspectiveBuyStock ashokley = new ProspectiveBuyStock(map.get("ASHOKLEY"), 0);
        put("ASHOKLEY", ashokley);

        ProspectiveBuyStock asianpaint = new ProspectiveBuyStock(map.get("ASIANPAINT"), 0);
        put("ASIANPAINT", asianpaint);

        ProspectiveBuyStock dmart = new ProspectiveBuyStock(map.get("DMART"), 0);
        put("DMART", dmart);

        ProspectiveBuyStock bajajelec = new ProspectiveBuyStock(map.get("BAJAJELEC"), 0);
        put("BAJAJELEC", bajajelec);

        put("BATAINDIA", ProspectiveBuyStock.builder().stock(map.get("BATAINDIA")).build());

        ProspectiveBuyStock chamblfert = new ProspectiveBuyStock(map.get("CHAMBLFERT"), 0);
        put("CHAMBLFERT", chamblfert);

        ProspectiveBuyStock cyient = new ProspectiveBuyStock(map.get("CYIENT"), 0);
        put("CYIENT", cyient);

        ProspectiveBuyStock dabur = new ProspectiveBuyStock(map.get("DABUR"), 0);
        put("DABUR", dabur);

        ProspectiveBuyStock fincables = new ProspectiveBuyStock(map.get("FINCABLES"), 0);
        put("FINCABLES", fincables);

        ProspectiveBuyStock flfl = new ProspectiveBuyStock(map.get("FLFL"), 0);
        put("FLFL", flfl);

        ProspectiveBuyStock godrejind = new ProspectiveBuyStock(map.get("GODREJIND"), 0);
        put("GODREJIND", godrejind);

        ProspectiveBuyStock gnfc = new ProspectiveBuyStock(map.get("GNFC"), 493);
        put("GNFC", gnfc);

        put("HAVELLS", ProspectiveBuyStock.builder().stock(map.get("HAVELLS")).build());

        ProspectiveBuyStock marico = new ProspectiveBuyStock(map.get("MARICO"), 0);
        put("MARICO", marico);

        ProspectiveBuyStock monsanto = new ProspectiveBuyStock(map.get("MONSANTO"), 0);
        put("MONSANTO", monsanto);

        ProspectiveBuyStock mphasis = new ProspectiveBuyStock(map.get("MPHASIS"), 1280);
        put("MPHASIS", mphasis);

        ProspectiveBuyStock persistent = new ProspectiveBuyStock(map.get("PERSISTENT"), 0);
        put("PERSISTENT", persistent);

        ProspectiveBuyStock polaris = new ProspectiveBuyStock(map.get("POLARIS"), 0);
        put("POLARIS", polaris);

        ProspectiveBuyStock polymed = new ProspectiveBuyStock(map.get("POLYMED"), 0);
        put("POLYMED", polymed);

        ProspectiveBuyStock radico = new ProspectiveBuyStock(map.get("RADICO"), 442);
        put("RADICO", radico);

        ProspectiveBuyStock saregama = new ProspectiveBuyStock(map.get("SAREGAMA"), 0);
        put("SAREGAMA", saregama);

        ProspectiveBuyStock schaeffler = new ProspectiveBuyStock(map.get("SCHAEFFLER"), 0);
        put("SCHAEFFLER", schaeffler);

        ProspectiveBuyStock tcs = new ProspectiveBuyStock(map.get("TCS"), 3570);
        put("TCS", tcs);

        ProspectiveBuyStock torntpower = new ProspectiveBuyStock(map.get("TORNTPOWER"), 0);
        put("TORNTPOWER", torntpower);

        ProspectiveBuyStock vmart = new ProspectiveBuyStock(map.get("VMART"), 0);
        put("VMART", vmart);







        // Rank 4
        ProspectiveBuyStock greavescot = new ProspectiveBuyStock(map.get("GREAVESCOT"), 0);
        put("GREAVESCOT", greavescot);

        ProspectiveBuyStock jublfood = new ProspectiveBuyStock(map.get("JUBLFOOD"), 0);
        put("JUBLFOOD", jublfood);

        ProspectiveBuyStock justdial = new ProspectiveBuyStock(map.get("JUSTDIAL"), 0);
        put("JUSTDIAL", justdial);

        ProspectiveBuyStock mindtree = new ProspectiveBuyStock(map.get("MINDTREE"), 0);
        put("MINDTREE", mindtree);

        ProspectiveBuyStock nilkamal = new ProspectiveBuyStock(map.get("NILKAMAL"), 0);
        put("NILKAMAL", nilkamal);

        ProspectiveBuyStock rupa = new ProspectiveBuyStock(map.get("RUPA"), 0);
        put("RUPA", rupa);

        ProspectiveBuyStock techm = new ProspectiveBuyStock(map.get("TECHM"), 0);
        put("TECHM", techm);

        ProspectiveBuyStock titan = new ProspectiveBuyStock(map.get("TITAN"), 0);
        put("TITAN", titan);









        // Rank 3
        ProspectiveBuyStock aarti = new ProspectiveBuyStock(map.get("AARTIIND"), 1356);
        put("AARTIIND", aarti);

        ProspectiveBuyStock intellect = new ProspectiveBuyStock(map.get("INTELLECT"), 0);
        put("INTELLECT", intellect);

        ProspectiveBuyStock relaxo = new ProspectiveBuyStock(map.get("RELAXO"), 0);
        put("RELAXO", relaxo);









        // Rank 2
        ProspectiveBuyStock fsl = new ProspectiveBuyStock(map.get("FSL"), 0);
        put("FSL", fsl);

        ProspectiveBuyStock ipcalab = new ProspectiveBuyStock(map.get("IPCALAB"), 0);
        put("IPCALAB", ipcalab);

        ProspectiveBuyStock jswsteel = new ProspectiveBuyStock(map.get("JSWSTEEL"), 0);
        put("JSWSTEEL", jswsteel);

        ProspectiveBuyStock nationalalum = new ProspectiveBuyStock(map.get("NATIONALUM"), 0);
        put("NATIONALUM", nationalalum);

        ProspectiveBuyStock tatachem = new ProspectiveBuyStock(map.get("TATACHEM"), 0);
        put("TATACHEM", tatachem);



        // Don't have time to rank
        ProspectiveBuyStock endurance = new ProspectiveBuyStock(map.get("ENDURANCE"), 1570);
        put("ENDURANCE", endurance);

      }
    };
    return prospectiveBuyMap;
  }
}
