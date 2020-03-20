package java.service;

import main.java.constant.Constants;
import main.java.model.Report;
import main.java.service.CommonReportService;
import model.Quote;
import model.StockFact;
import org.junit.Before;
import org.junit.runner.RunWith;
import service.yahooFinance.HistoricalDataService;

import java.util.List;

public class CommonReportServiceTest {

  private CommonReportService reportingService;

  private static final boolean DATA_FROM_CACHE = false;

  @Before
  public void init() {
    reportingService = new CommonReportService();
    StockFact stock = new StockFact("GRASIM", "GRASIM.NS");
    List<Quote> history = HistoricalDataService.getHistoricalDataWithCacheOption(
        stock, Constants.FETCH_HISTORY_QUARTERLY, DATA_FROM_CACHE);
    Report report = new Report();
    report.setStockFact(stock);
    int size = history.size();
    report.setLastClosePrice(history.get(size - 1).getClose());
    reportingService.reportConsecutiveFallDays(history, report);
  }
}
