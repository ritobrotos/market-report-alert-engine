package main.java.service.automation;

import com.google.common.collect.Lists;

import constant.ConstantVariable;
import constant.YHDStockUrlList;
import main.java.constant.Constants;
import main.java.constant.FnoStockList;
import model.Quote;
import model.StockFact;
import org.apache.commons.collections4.CollectionUtils;
import service.yahooFinance.HistoricalDataService;
import ulility.MathUtility;
import ulility.StockHistoryUtility;

import java.util.List;

/**
 * Created by ritobrotoseth on 22/07/18.
 */
public class BreakoutService {
  private List<Quote> history;
  private List<Double> supportLevels;
  private List<Double> resistanceLevels;
  final int SEGMENT_SIZE = 4;
  final double TOTAL_POINTS_FRACTION = 0.20D;
  private static final boolean GET_DATA_FROM_CACHE = true;

  public static void main(String srgs[]) {
//    StockFact stock = new StockFact("Power Grid Corporation of India Limited", "POWERGRID.NS");

    List<StockFact> fnoStockList = FnoStockList.getFnoStockList();
    for (StockFact stock : fnoStockList) {
      new BreakoutService(stock);
    }
  }

  public BreakoutService(StockFact stock) {
    history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock,
        ConstantVariable.FETCH_HISTORY_HALF_YEARLY, GET_DATA_FROM_CACHE);

    if (CollectionUtils.isNotEmpty(history)) {
      int size = history.size();
      System.out.println(stock.getName() + " ~~ LTP :: " + MathUtility.twoDecimalPlace(history.get(size-1).getClose()) );
      displaySupportResistanceLevels();
    }
  }

  public BreakoutService(List<Quote> historyParam, StockFact stock) {
    history = historyParam;
    findSupportResistanceLevels();
  }

  public void findSupportResistanceLevels() {
    List<Double> localMins = Lists.newArrayList();
    List<Double> localMaxs = Lists.newArrayList();

    createLocalMinMaxList(localMins, localMaxs);

    findSupportLevels(localMins);

    findResistanceLevels(localMaxs);
  }

  private void displaySupportResistanceLevels() {
    List<Double> localMins = Lists.newArrayList();
    List<Double> localMaxs = Lists.newArrayList();

    createLocalMinMaxList(localMins, localMaxs);

    findSupportLevels(localMins);

    findResistanceLevels(localMaxs);

    printSupportLevels();
    printResistanceLevels();
  }

  /**
   *
   * @param localMins contains the list of local minima selected in the range
   * @param localMaxs contains the list of local maxima selected in the range
   */
  private void createLocalMinMaxList(List<Double> localMins, List<Double> localMaxs) {
    if(history != null) {
      int localMinSkipCount = 0;
      int localMaxSkipCount = 0;

      for(int i = 0; i < history.size(); i = i + SEGMENT_SIZE) {
        int endIndex = i + SEGMENT_SIZE;
        if (endIndex > history.size()) {
          endIndex = history.size();
        }

        double localMin = MathUtility.twoDecimalPlace( findLocalMin(i, endIndex, history, "close") );
        localMinSkipCount = smoothLocalMins(localMins, localMin, localMinSkipCount);

        double localMax = findLocalMax(i, endIndex, history, "close");
        localMaxs.add(localMax);
      }
    }
//    System.out.println("localMins: " + localMins);
  }

  /**
   * This function is the one which finds the local min and decides whether to add that min to the
   * localmin list or not.
   * @param localMins list is passed as a parameter to which localmin found is added
   * */
  private int smoothLocalMins(List<Double> localMins, double localMin, int localMinSkipCount) {
    if (localMins.size() > 0) {
      if ( shouldSkipMin( localMins.get(localMins.size() - 1), localMin ) ) {
        if (localMinSkipCount > 3) {    // If 3 segments have already been skipped then we should add it.
          localMins.add(localMin);
          localMinSkipCount = 0;
        }
        localMinSkipCount++;
      } else {
        // If that local min is smaller than the last min in the list then replace it with
        // this new local min
        localMins.set(localMins.size() - 1, localMin);
        localMinSkipCount = 0;
      }
    } else {
      localMins.add(localMin);
    }
    return localMinSkipCount;
  }

  private boolean shouldSkipMin(double latestMin, double localMin) {
    if (localMin < latestMin) {
      return false;
    } else {
      return true;
    }
  }


  /**
   * Earlier we were using Kmeans algo to find the support levels now that
   * algo has been replaced by smoothLocalMins algorithm
   * */
  private void findSupportLevels(List<Double> localMins) {
    /*
     * The below Kmeans algo is not used anymore, so we simply set the local means to
     * the supportLevels
    if (localMins.size() > 0) {
      KMeans kmeans = new KMeans(localMins);
      kmeans.calculate();
      supportLevels = extractLevels(kmeans, localMins.size());
    }
    */

    supportLevels = localMins;
  }

  /**
   * Kmeans algo is used to find the resistance levels
   * */
  private void findResistanceLevels(List<Double> localMaxs) {
    if (localMaxs.size() > 0) {
      KMeans kmeans = new KMeans(localMaxs);
      kmeans.calculate();
      resistanceLevels = extractLevels(kmeans, localMaxs.size());
    }
  }

  private List<Double> extractLevels(KMeans kmeans, int maximaMinimaSize) {
    List<Double> supportLevel = Lists.newArrayList();
    if (kmeans != null) {
      List<Cluster> clusters = kmeans.getClusters();
      for (Cluster cluster : clusters) {
        if ( cluster.getPoints().size() > (TOTAL_POINTS_FRACTION * maximaMinimaSize) ) {
          Point point = cluster.getCentroid();
          supportLevel.add(MathUtility.twoDecimalPlace(point.getX()));
        }
      }
    }
    return supportLevel;
  }

  /**
   *
   * @param start index of the list
   * @param end index of the list
   * @param history contains the historical price list
   * @param priceType which you want to select like open, close, high, and low
   * @return the minimum value of the pricetype in the given range
   */
  private double findLocalMin(int start, int end, List<Quote> history, String priceType) {
    double localMin = StockHistoryUtility.getValue(priceType, history.get(start));
    for (int i = start + 1 ; i < end; i++) {
      if ( StockHistoryUtility.getValue(priceType, history.get(i)) < localMin ) {
        localMin = StockHistoryUtility.getValue(priceType, history.get(i));
      }
    }
    return localMin;
  }

  /**
   *
   * @param start index of the list
   * @param end index of the list
   * @param history contains the historical price list
   * @param priceType which you want to select like open, close, high, and low
   * @return the maximum value of the pricetype in the given range
   */
  private double findLocalMax(int start, int end, List<Quote> history, String priceType) {
    double localMax = StockHistoryUtility.getValue(priceType, history.get(start));
    for (int i = start + 1 ; i < end; i++) {
      if ( StockHistoryUtility.getValue(priceType, history.get(i)) > localMax ) {
        localMax = StockHistoryUtility.getValue(priceType, history.get(i));
      }
    }
    return localMax;
  }

  public List<Double> getSupportLevels() {
    return supportLevels;
  }

  public void printSupportLevels() {
    if (CollectionUtils.isNotEmpty(supportLevels)) {
      System.out.print("Support Level ::  ");
      for (int i = 0; i < supportLevels.size(); i++) {
        System.out.print(supportLevels.get(i) + ",  ");
      }
      System.out.println();
    }
  }

  public List<Double> getResistanceLevels() {
    return resistanceLevels;
  }

  public void printResistanceLevels() {
    if (CollectionUtils.isNotEmpty(resistanceLevels)) {
      System.out.print("Resistance Level ::  ");
      for (int i = 0; i < resistanceLevels.size(); i++) {
        System.out.print(resistanceLevels.get(i) + ",  ");
      }
      System.out.println();
    }
  }

  public static class LocalMaxMin {
    double max;
    double min;
  }
}
