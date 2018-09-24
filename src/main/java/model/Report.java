package main.java.model;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import model.StockFact;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ritobrotoseth on 14/07/18.
 */
@Setter
@Getter
public class Report {

  private ConsecutiveFall consecutiveFall;
  private ConsecutiveRise consecutiveRise;
  private LowHigh monthLowHigh;
  private LowHigh quarterLowHigh;
  private LowHigh periodLowHigh;
  private double lastClosePrice;
  private StockFact stockFact;
  private VolumeSurge volumeSurge;
  private List<String> additionalCommentary;
  private List<Double> supportLevels;
  private List<Double> resistanceLevels;

  public Report() {
    consecutiveFall = new ConsecutiveFall();
    consecutiveRise = new ConsecutiveRise();
    monthLowHigh = new LowHigh();
    quarterLowHigh = new LowHigh();
    periodLowHigh = new LowHigh();
    volumeSurge = new VolumeSurge();
    additionalCommentary = Lists.newArrayList();
    supportLevels = Lists.newArrayList();
    resistanceLevels = Lists.newArrayList();
  }

  public Report(StockFact stockFact) {
    this();
    this.stockFact = stockFact;
  }

  public ConsecutiveFall getConsecutiveFall() {
    return consecutiveFall;
  }

  public void setConsecutiveFall(ConsecutiveFall consecutiveFall) {
    this.consecutiveFall = consecutiveFall;
  }

  public ConsecutiveRise getConsecutiveRise() {
    return consecutiveRise;
  }

  public void setConsecutiveRise(ConsecutiveRise consecutiveRise) {
    this.consecutiveRise = consecutiveRise;
  }

  public LowHigh getMonthLowHigh() {
    return monthLowHigh;
  }

  public void setMonthLowHigh(LowHigh monthLowHigh) {
    this.monthLowHigh = monthLowHigh;
  }

  public LowHigh getQuarterLowHigh() {
    return quarterLowHigh;
  }

  public void setQuarterLowHigh(LowHigh quarterLowHigh) {
    this.quarterLowHigh = quarterLowHigh;
  }

  public LowHigh getPeriodLowHigh() {
    return periodLowHigh;
  }

  public void setPeriodLowHigh(LowHigh periodLowHigh) {
    this.periodLowHigh = periodLowHigh;
  }

  public double getLastClosePrice() {
    return lastClosePrice;
  }

  public void setLastClosePrice(double lastClosePrice) {
    this.lastClosePrice = lastClosePrice;
  }

  public StockFact getStockFact() {
    return stockFact;
  }

  public void setStockFact(StockFact stockFact) {
    this.stockFact = stockFact;
  }

  public VolumeSurge getVolumeSurge() {
    return volumeSurge;
  }

  public void setVolumeSurge(VolumeSurge volumeSurge) {
    this.volumeSurge = volumeSurge;
  }

  public List<String> getAdditionalCommentary() {
    return additionalCommentary;
  }

  public void setAdditionalCommentary(List<String> additionalCommentary) {
    this.additionalCommentary = additionalCommentary;
  }

  public abstract class ConsecutiveTrend {
    private int noOfDays;
    private double basePrice;

    public int getNoOfDays() {
      return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
      this.noOfDays = noOfDays;
    }

    public double getBasePrice() {
      return basePrice;
    }

    public void setBasePrice(double basePrice) {
      this.basePrice = basePrice;
    }
  }

  public class ConsecutiveFall extends ConsecutiveTrend {
    private double percentageDecline;

    public double getPercentageDecline() {
      return percentageDecline;
    }

    public void setPercentageDecline(double percentageDecline) {
      this.percentageDecline = percentageDecline;
    }
  }

  public class ConsecutiveRise extends ConsecutiveTrend {
    private double percentageIncrease;

    public double getPercentageIncrease() {
      return percentageIncrease;
    }

    public void setPercentageIncrease(double percentageIncrease) {
      this.percentageIncrease = percentageIncrease;
    }
  }

  public class LowHigh {
    private double lowVal;
    private double highVal;

    public double getLowVal() {
      return lowVal;
    }

    public void setLowVal(double lowVal) {
      this.lowVal = lowVal;
    }

    public double getHighVal() {
      return highVal;
    }

    public void setHighVal(double highVal) {
      this.highVal = highVal;
    }
  }

  public class VolumeSurge {
    private double avgVolume;
    private double dayVolume;
    private double volumeMultiple;
    private Date surgeDate;

    public double getAvgVolume() {
      return avgVolume;
    }

    public void setAvgVolume(double avgVolume) {
      this.avgVolume = avgVolume;
    }

    public double getDayVolume() {
      return dayVolume;
    }

    public void setDayVolume(double dayVolume) {
      this.dayVolume = dayVolume;
    }

    public double getVolumeMultiple() {
      return volumeMultiple;
    }

    public void setVolumeMultiple(double volumeMultiple) {
      this.volumeMultiple = volumeMultiple;
    }

    public Date getSurgeDate() {
      return surgeDate;
    }

    public void setSurgeDate(Date surgeDate) {
      this.surgeDate = surgeDate;
    }
  }

  public static Comparator<Report> ConsecutiveRisePercentageIncreaseComparator = new Comparator<Report>() {
    @Override
    public int compare(Report o1, Report o2) {
      double risePercent1 = o1.getConsecutiveRise().getPercentageIncrease();
      double risePercent2 = o2.getConsecutiveRise().getPercentageIncrease();

      // Descending Order
      return (risePercent2 > risePercent1) ? 1 : -1;
    }
  };

  public static Comparator<Report> ConsecutiveFallPercentageDeclineComparator = new Comparator<Report>() {
    @Override
    public int compare(Report o1, Report o2) {
      double fallPercent1 = o1.getConsecutiveFall().getPercentageDecline();
      double fallPercent2 = o2.getConsecutiveFall().getPercentageDecline();

      // Descending Order
      return (fallPercent2 > fallPercent1) ? 1 : -1;
    }
  };

  public static Comparator<Report> VolumeSurgeComparator = new Comparator<Report>() {
    public int compare(Report o1, Report o2) {
      double volumeMultiple1 = o1.getVolumeSurge().getVolumeMultiple();
      double volumeMultiple2 = o2.getVolumeSurge().getVolumeMultiple();

      // Descending Order
      return (volumeMultiple2 > volumeMultiple1) ? 1 : -1;
    }
  };
}
