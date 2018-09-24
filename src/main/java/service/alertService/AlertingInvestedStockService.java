package main.java.service.alertService;

import static main.java.model.AlertUnit.AlertUnitDescendingComparator;

import com.google.common.collect.Maps;

import main.java.constant.Constants;
import main.java.constant.InvestedStockList;
import main.java.model.Alert;
import main.java.model.AlertUnit;
import main.java.model.InvestedStock;
import main.java.presenter.AlertStyling;
import main.java.presenter.popup.ScrollablePopup;
import model.Quote;
import service.yahooFinance.HistoricalDataService;
import ulility.MathUtility;
import ulility.OscillatorUtility;
import ulility.StockHistoryUtility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
public class AlertingInvestedStockService {

  private static final int alertRefreshTimeInMins = 10;
  private Alert alert;

  public static void main(String[] args) {
    while (true) {
      new AlertingInvestedStockService().scanPrice();
      try {
        Thread.sleep(alertRefreshTimeInMins * 60 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  AlertingInvestedStockService() {
    alert = new Alert();
  }

  private void scanPrice() {
    Map<String, InvestedStock> investedMap = InvestedStockList.getInvestedStockMap();

    Iterator it = investedMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      InvestedStock investedStock = (InvestedStock) pair.getValue();
      List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
          investedStock.getStock(), Constants.FETCH_HISTORY_QUARTERLY, false);

      checkStockStatus(history, investedStock);
    }

    displayAlert();
  }

  private void checkStockStatus(List<Quote> history, InvestedStock investedStock) {
    if ( !isHistoricalDataCorrect( history ) ) {
      return;
    }

    findLoserStockAndAddToGlobalLoserList(history, investedStock);
    findGainerStockAndAddToGlobalGainerList(history, investedStock);
    findStockBelowSmaAndAddToBelowSmaList(history, investedStock);
    findTheStockPercentageDownFromItsHighestPointInThePeriod(history, investedStock);
  }

  private boolean isHistoricalDataCorrect(List<Quote> history) {
    if (history == null || history.size() < 2) {
      return false;
    }
    return true;
  }

  private void findLoserStockAndAddToGlobalLoserList(List<Quote> history, InvestedStock investedStock) {
    int size = history.size();
    double ltp = MathUtility.twoDecimalPlace(history.get(size - 1).getClose());
    double previousLtp = MathUtility.twoDecimalPlace(history.get(size - 2).getClose());

    if (ltp >= previousLtp) {
      return;
    }

    double diff = Math.abs(previousLtp - ltp);
    double diffPerc = MathUtility.twoDecimalPlace((diff/previousLtp) * 100);

    AlertUnit unit = AlertUnit.builder().percDown(diffPerc).alertType(Constants.LOSERS_ALERT_TYPE).ltp(ltp).investedStock(investedStock).build();
    alert.getLosers().add(unit);
  }

  private void findGainerStockAndAddToGlobalGainerList(List<Quote> history, InvestedStock investedStock) {
    int size = history.size();
    double ltp = MathUtility.twoDecimalPlace(history.get(size - 1).getClose());
    double previousLtp = MathUtility.twoDecimalPlace(history.get(size - 2).getClose());

    if (ltp <= previousLtp) {
      return;
    }

    double diff = Math.abs(ltp - previousLtp);
    double diffPerc = MathUtility.twoDecimalPlace((diff/previousLtp) * 100);

    AlertUnit unit = AlertUnit.builder().percUp(diffPerc).alertType(Constants.GAINERS_ALERT_TYPE).ltp(ltp).investedStock(investedStock).build();
    alert.getGainers().add(unit);
  }

  private void findStockBelowSmaAndAddToBelowSmaList(List<Quote> history, InvestedStock investedStock) {
    int longSmaPeriod = 50;
    int shortSmaPeriod = 20;

    double longPriceSma = OscillatorUtility.calculateSimpleAverage(history, longSmaPeriod);
    double shortPriceSma = OscillatorUtility.calculateSimpleAverage(history, shortSmaPeriod);

    int size = history.size();
    double ltp = history.get(size - 1).getClose();
    if (ltp < shortPriceSma) {
      double diffPerc = MathUtility.declinePercent(ltp, shortPriceSma);
      AlertUnit unit = AlertUnit.builder().percDiff(diffPerc).alertType(Constants.BELOW_SMA_20_ALERT_TYPE).ltp(ltp).investedStock(investedStock).build();
      alert.getBelowSma20().add(unit);
    }

    if (ltp < longPriceSma) {
      double diffPerc = MathUtility.declinePercent(ltp, longPriceSma);
      AlertUnit unit = AlertUnit.builder().percDiff(diffPerc).alertType(Constants.BELOW_SMA_50_ALERT_TYPE).ltp(ltp).investedStock(investedStock).build();
      alert.getBelowSma50().add(unit);
    }
  }

  private void findTheStockPercentageDownFromItsHighestPointInThePeriod(List<Quote> history, InvestedStock investedStock) {
    double highestClose = 0.0D;
    try {
      highestClose = StockHistoryUtility.findHighestCloseAfterBuyDate(history, investedStock.getBuyDate());
    } catch (NullPointerException e) {
      return;     // handle cases where buy date is not specified
    }

    int size = history.size();
    double ltp = history.get(size - 1).getClose();

    if (ltp < highestClose) {
      double percentageDown = MathUtility.declinePercent(ltp, highestClose);
      AlertUnit unit = AlertUnit.builder().percDiff(percentageDown).ltp(ltp).alertType(Constants.DOWN_FROM_PERIODIC_HIGH_ALERT_TYPE).investedStock(investedStock).build();
      alert.getPercentageDownFromPeriodHighList().add(unit);
    }
  }

  private void displayAlert() {
    AlertStyling alertStyling = new AlertStyling();
    sortLoserStocksInDescendingOrder();
    List<String> losersMsgs = alertStyling.createMsgsForHtmlTableFormat(alert.getLosers());

    sortGainersStockInDescendingOrder();
    List<String> gainersMsgs = alertStyling.createMsgsForHtmlTableFormat(alert.getGainers());

    sortStocksByPercentageDownFromPeriodHigh();

    List<String> downFromPeriodicHighMsgs = alertStyling.createTheStringMsgListToBeDisplayedInTheAlertPopup(alert.getPercentageDownFromPeriodHighList());
    List<String> belowSma20Msgs = alertStyling.createTheStringMsgListToBeDisplayedInTheAlertPopup(alert.getBelowSma20());
    List<String> belowSma50Msgs = alertStyling.createTheStringMsgListToBeDisplayedInTheAlertPopup(alert.getBelowSma50());


    HashMap<String, List<String>> msgsMap = Maps.newHashMap();
    msgsMap.put("Losers", losersMsgs);
    msgsMap.put("Gainers", gainersMsgs);
    msgsMap.put("DownFromPeriodicHigh", downFromPeriodicHighMsgs);
    msgsMap.put("BelowSma20", belowSma20Msgs);
    msgsMap.put("BelowSma50", belowSma50Msgs);

    String finalHtmlMsgBody = alertStyling.assemblAllMsgsTogetherUnderParentHtmlTag(msgsMap);

    openAlertPopupWindow(finalHtmlMsgBody);
  }

  private void sortLoserStocksInDescendingOrder() {
    alert.getLosers().sort(AlertUnitDescendingComparator);
  }

  private void sortGainersStockInDescendingOrder() {
    alert.getLosers().sort(AlertUnitDescendingComparator);
  }

  private void sortStocksByPercentageDownFromPeriodHigh() {
    alert.getPercentageDownFromPeriodHighList().sort(AlertUnitDescendingComparator);
  }

  private void openAlertPopupWindow(String content) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ScrollablePopup.openPopupWindow(content);
      }
    });
  }


}
