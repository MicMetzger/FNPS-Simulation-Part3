package main.java.com.item.supplies;

import static java.lang.Math.*;
import static main.java.com.utilities.Builders.getThe_Day;

import java.security.*;
import main.java.com.item.*;
import main.java.com.item.supplies.enums.*;



/**
 * The type Cat liter.
 */
public class CatLitter extends Supplies {
  // The size of the cat liter.
  private int size;

  /**
   * Instantiates a new Cat liter.
   *
   * @param size the size
   */
  public CatLitter(int size) {
    super();
    
    this.size        = size;
    super.supplyType = SupplyType.CatLitter;
    super.setDayArrived(0);
    super.setName("Cat Litter");
    super.setListPrice(round(new SecureRandom().nextDouble(100)));
    super.setPurchasePrice(0);
    super.setSalePrice(round(new SecureRandom().nextDouble(50)));
    super.setDayArrived(getThe_Day());
    super.setDaySold(0);
  }

  /**
   * Default Constructor of CatLiter object
   */
  public CatLitter() {
    super();

    super.supplyType = SupplyType.CatLitter;
    this.size        = 10;
    super.setName("CatLitter");
    super.setListPrice(round(new SecureRandom().nextDouble(100)));
    super.setPurchasePrice(0);
    super.setSalePrice(round(new SecureRandom().nextDouble(getListPrice())));
    super.setDayArrived(getThe_Day());
    super.setDaySold(0);
  }

  public CatLitter(
      String name,
      double purchasePrice,
      double listPrice,
      double salePrice,
      int daySold,
      int dayArrived,
      int size) {
    super(name, purchasePrice, listPrice, salePrice, dayArrived, daySold);

    super.setDayArrived(0);
    super.setName("Cat Litter");
    super.supplyType = SupplyType.CatLitter;
    // double newPurchasePrice = round(new SecureRandom().nextDouble(100));
    // super.setPurchasePrice(newPurchasePrice);
    // super.setListPrice(round(newPurchasePrice * (double) 2));
    this.size = size;
  }

  public CatLitter(String[] fArgs) {
    super();

    if (fArgs.length <= 0) {
      this.size = 10;
      super.setName("CatLitter");
      super.setListPrice(round(new SecureRandom().nextDouble(100)));
      super.setPurchasePrice(0);
      super.setSalePrice(round(new SecureRandom().nextDouble(getListPrice())));
      super.setDayArrived(getThe_Day());
      super.setDaySold(0);
    } else {
      // TODO: Implement this. Check what's not being passed in.
      for (int i = 0; i < fArgs.length; i++) {
        switch (fArgs[i]) {
          case "size" -> this.size = Integer.parseInt(fArgs[i + 1]);
          case "name" -> super.setName(fArgs[i + 1]);
          case "purchasePrice" -> super.setPurchasePrice(Double.parseDouble(fArgs[i + 1]));
          case "listPrice" -> super.setListPrice(Double.parseDouble(fArgs[i + 1]));
          case "salePrice" -> super.setSalePrice(Double.parseDouble(fArgs[i + 1]));
          case "daySold" -> super.setDaySold(Integer.parseInt(fArgs[i + 1]));
          case "dayArrived" -> super.setDayArrived(Integer.parseInt(fArgs[i + 1]));
        }
      }
    }
  }

  /**
   * Gets the cat liter size.
   *
   * @return the size
   */
  public int getSize() {
    return size;
  }

  /**
   * Sets the cat liter size.
   *
   * @param size the cat liter size
   */
  public void setSize(int size) {
    this.size = size;
  }

}
