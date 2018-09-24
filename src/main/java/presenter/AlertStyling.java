package main.java.presenter;

import com.google.common.collect.Lists;

import main.java.constant.Constants;
import main.java.model.AlertUnit;
import ulility.MathUtility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ritobrotoseth on 21/07/18.
 */
public class AlertStyling {

  public List<String> createTheStringMsgListToBeDisplayedInTheAlertPopup(List<AlertUnit> alertUnits) {
    if (alertUnits.size() < 1) {
      return null;
    }

    String alertType = alertUnits.get(0).getAlertType();

    List<String> msgs = Lists.newArrayList();

    for(int i=0; i < alertUnits.size(); i++) {
      String stockName = alertUnits.get(i).getInvestedStock().getStock().getName();
      if (stockName.length() > 15) {
        stockName = stockName.substring(0, 15);
      }
      String msg = "";
      if (alertType.equalsIgnoreCase(Constants.DOWN_FROM_PERIODIC_HIGH_ALERT_TYPE)) {
        msg = stockName + " || Periodic Down:" + alertUnits.get(i).getPercDiff() + "%";
      } else if (alertType.equalsIgnoreCase(Constants.BELOW_SMA_20_ALERT_TYPE)) {
        msg = stockName + " || Below: " + alertUnits.get(i).getPercDiff() + "%";
      } else if (alertType.equalsIgnoreCase(Constants.BELOW_SMA_50_ALERT_TYPE)) {
        msg = stockName + " || Below: " + alertUnits.get(i).getPercDiff() + "%";
      }

      msgs.add(msg);
    }
    return msgs;
  }

  public List<String> createMsgsForHtmlTableFormat(List<AlertUnit> alertUnits) {
    if (alertUnits.size() < 1) {
      return null;
    }

    String alertType = alertUnits.get(0).getAlertType();

    List<String> msgs = Lists.newArrayList();

    for(int i=0; i < alertUnits.size(); i++) {
      String stockName = alertUnits.get(i).getInvestedStock().getStock().getName();
      if (stockName.length() > 15) {
        stockName = stockName.substring(0, 15);
      }
      String msg = "";
      double diffPerc = MathUtility.diffPercent(alertUnits.get(i).getLtp(),
          alertUnits.get(i).getInvestedStock().getBuyPoint());

      if (alertType.equalsIgnoreCase(Constants.LOSERS_ALERT_TYPE)) {
        double dayChange = -1 * alertUnits.get(i).getPercDown();
        msg = stockName
            + " \t " + dayChange + "%"
            + " \t " + alertUnits.get(i).getInvestedStock().getBuyPoint()
            + " \t " + alertUnits.get(i).getLtp()
            + " \t " + diffPerc + "%";
      } else if (alertType.equalsIgnoreCase(Constants.GAINERS_ALERT_TYPE)) {
        msg = stockName
            + " \t " + alertUnits.get(i).getPercUp() + "%"
            + " \t " + alertUnits.get(i).getInvestedStock().getBuyPoint()
            + " \t " + alertUnits.get(i).getLtp()
            + " \t " + diffPerc + "%";
      }

      msgs.add(msg);
    }
    return msgs;
  }

  public String assemblAllMsgsTogetherUnderParentHtmlTag(HashMap<String, List<String>> msgsMap) {
    String alertText = "<html>";
    alertText += displayTimeInAlertMsg();

    alertText += convertMsgToHtmlTabularFormat("Loser Stocks", msgsMap.get("Losers"));
    alertText += "<br><br>";
    alertText += convertMsgToHtmlTabularFormat("Gainer Stocks", msgsMap.get("Gainers"));
    alertText += "<br><br>";
    alertText += convertMsgListToHtmlDivFormat("Periodic Down Percent", msgsMap.get("DownFromPeriodicHigh"));
    alertText += "<br><br>";
    alertText += convertMsgListToHtmlDivFormat("Below SMA 20", msgsMap.get("BelowSma20"));
    alertText += "<br><br>";
    alertText += convertMsgListToHtmlDivFormat("Below SMA 50", msgsMap.get("BelowSma50"));

    alertText += "<br> </html>";

    return alertText;
  }

  private String displayTimeInAlertMsg() {
    final DateFormat dateFormat = new SimpleDateFormat("HH:mm");
    Calendar now = Calendar.getInstance();
    return "<p><b>Alert Time: " + dateFormat.format(now.getTime()) + "</b></p> <br>";
  }

  private String convertMsgToHtmlTabularFormat(String heading, List<String> msgs) {
    String alertText = "<p><b>" + heading + "</b></p>";
    alertText += "<table width=\"90%\">";
    alertText += "<tr>"
        + "<th width=\"40%\">Stock</th> <th width=\"15%\">Day Chg%</th> "
        + "<th width=\"15%\">Avg Cost</th> <th width=\"15%\">LTP</th> "
        + "<th width=\"15%\">Net Chg%</th>"
        + "</tr>";

    if (msgs == null || msgs.isEmpty()) {
      alertText += "</table>";
      return alertText;
    }

    printAlertMsgsInTerminal(msgs);
    for (String msg : msgs) {
      String columnComponents[] = msg.split(" \t ");
      for (int i = 0; i < columnComponents.length; i++) {
        if (i == 0) {
          alertText += "<tr>";
        }
        alertText += "<td>" + columnComponents[i] + "</td>";
        if (i == columnComponents.length - 1) {
          alertText += "</tr>";
        }
      }
    }

    alertText += "</table>";
    return alertText;
  }

  private String convertMsgListToHtmlDivFormat(String heading, List<String> msgs) {
    String alertText = "<p><b>" + heading + "</b></p>";

    if (msgs == null || msgs.isEmpty()) {
      return alertText;
    }


    if (msgs.size() > 0) {
      printAlertMsgsInTerminal(msgs);
      for(String msg : msgs) {
        alertText += msg + "<br>";
      }
    }
    return alertText;
  }

  private void printAlertMsgsInTerminal(List<String> msgs) {
    System.out.println("\n\n\n");
    for(String msg: msgs) {
      System.out.println(msg);
    }
  }
}
