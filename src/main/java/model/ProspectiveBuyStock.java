package main.java.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import model.StockFact;

import java.util.Comparator;

/**
 * Created by ritobrotoseth on 14/07/18.
 */
@Builder
@AllArgsConstructor
public class ProspectiveBuyStock {

  private StockFact stock;
  private double resistanceLevel;
  private double supportLevel;

  public ProspectiveBuyStock(StockFact stock, double resistanceLevel) {
    this.stock = stock;
    this.resistanceLevel = resistanceLevel;
  }

  public StockFact getStock() {
    return stock;
  }

  public void setStock(StockFact stock) {
    this.stock = stock;
  }

  public double getResistanceLevel() {
    return resistanceLevel;
  }

  public void setResistanceLevel(double resistanceLevel) {
    this.resistanceLevel = resistanceLevel;
  }

  public double getSupportLevel() {
    return supportLevel;
  }

  public void setSupportLevel(double supportLevel) {
    this.supportLevel = supportLevel;
  }

  //  public static Comparator<ProspectiveBuyStock> StockExtraVariable1Comparator = new Comparator<ProspectiveBuyStock>() {
//    @Override
//    public int compare(ProspectiveBuyStock o1, ProspectiveBuyStock o2) {
//      double stock1 = o1.getStock().getExtraVariable1();
//      double stock2 = o2.getStock().getExtraVariable1();
//
//      // Descending Order
//      return (stock2 > stock1) ? 1 : -1;
//    }
//  };
}
