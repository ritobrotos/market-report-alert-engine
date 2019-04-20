package main.java.constant;

import com.google.common.collect.Lists;

import model.StockFact;

import java.util.List;

public class NiftyStockList {

  public static List<StockFact> getNiftyStockList() {
    List<StockFact> niftyStockList = Lists.newArrayList(
        new StockFact("HDFC Bank Limited", "HDFCBANK.NS"),
        new StockFact("Reliance Industries Limited", "RELIANCE.NS"),
        new StockFact("ITC Limited", "ITC.NS"),
        new StockFact("Housing Development Finance Corporation Limited", "HDFC.NS"),
        new StockFact("Infosys Limited", "INFY.NS"),
        new StockFact("ICICI Bank Limited", "ICICIBANK.NS"),
        new StockFact("Tata Consultancy Services Limited", "TCS.NS"),
        new StockFact("LARSEN & TOUBRO LTD", "LT.NS"),
        StockFact.builder().name("Kotak Mahindra Bank Limited").yahooAlias("KOTAKBANK.NS").build(),
        new StockFact("Hindustan Unilever Limited", "HINDUNILVR.NS"),

        new StockFact("Axis Bank Limited", "AXISBANK.NS"),
        new StockFact("State Bank of India", "SBIN.NS"),
        new StockFact("Maruti Suzuki India Limited", "MARUTI.NS"),
        new StockFact("IndusInd Bank Limited", "INDUSINDBK.NS"),
        new StockFact("Wipro Limited", "WIPRO.NS"),
        new StockFact("Mahindra & Mahindra Limited", "M&M.NS"),
        new StockFact("Bajaj Finance Limited", "BAJFINANCE.NS"),
        new StockFact("Asian Paints Ltd", "ASIANPAINT.BO"),
        new StockFact("Indian Oil Corporation Limited", "IOC.NS"),
        new StockFact("Oil & Natural Gas Corp Ltd", "ONGC.NS"),

        new StockFact("HCL Technologies Ltd", "HCLTECH.NS"),
        new StockFact("Hero MotoCorp Limited", "HEROMOTOCO.NS"),
        new StockFact("NTPC Limited", "NTPC.NS"),
        new StockFact("Sun Pharmaceutical Industries Limited", "SUNPHARMA.NS"),
        new StockFact("Tech Mahindra Limited", "TECHM.NS"),
        new StockFact("Bajaj Auto Limited", "BAJAJ-AUTO-EQ.NS"),
        new StockFact("Bharti Airtel Limited", "BHARTIARTL.NS"),
        StockFact.builder().name("JSW Steel Limited").yahooAlias("JSWSTEEL.NS").build(),
        new StockFact("Power Grid Corporation of India Limited", "POWERGRID.NS"),
        new StockFact("Tata Steel Limited", "TATASTEEL.NS"),

        new StockFact("Titan Company Limited", "TITAN.NS"),
        new StockFact("UltraTech Cement Limited", "ULTRACEMCO.NS"),
        new StockFact("GAIL (India) Limited", "GAIL.NS"),
        new StockFact("Hindustan Petroleum Corporation Limited", "HINDPETRO.NS"),
        new StockFact("Vedanta Limited", "VEDL.NS"),
        new StockFact("Bharat Petroleum Corporation Limited", "BPCL.NS"),
        new StockFact("Coal India Limited", "COALINDIA.NS"),
        new StockFact("Dr. Reddy's Laboratories Limited", "DRREDDY.NS"),
        new StockFact("Eicher Motors Ltd", "EICHERMOT.NS"),
        new StockFact("Grasim Industries Limited", "GRASIM.NS"),

        new StockFact("Hindalco Industries Limited", "HINDALCO.NS"),
        new StockFact("Tata Motors Limited", "TATAMOTORS.NS"),
        new StockFact("Yes Bank Limited", "YESBANK.NS"),
        new StockFact("Adani Ports and Special Economic Zone Limited", "ADANIPORTS.NS"),
        new StockFact("Cipla Limited", "CIPLA.NS"),
        new StockFact("Indiabulls Housing Finance Limited", "IBULHSGFIN.NS"),
        new StockFact("UPL Limited", "UPL.NS"),
        new StockFact("Zee Entertainment Enterprises Limited", "ZEEL.NS"),
        new StockFact("Bharti Infratel Limited", "INFRATEL.NS")
    );
    return niftyStockList;
  }
}
