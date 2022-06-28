package main.java.com.item.supplies;

import static java.lang.Math.*;

import java.security.*;
import main.java.com.item.*;
import main.java.com.item.supplies.enums.*;



/** The type Cat liter. */
public class CatLitter extends Supplies {
  // The size of the cat liter.
  private int size;

  /**
   * Instantiates a new Cat liter.
   *
   * @param size the size
   */
  public CatLitter(int size) {
    super.setDayArrived(0);
    super.setName("Cat Litter");
    this.size = size;
  }

  /** Default Constructor of CatLiter object */
  public CatLitter() {
    super();
    super.supplyType = SupplyType.CatLitter;
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
    super.setName("CatLitter");
    double newPurchasePrice = round(new SecureRandom().nextDouble(100));
    super.setPurchasePrice(newPurchasePrice);
    super.setListPrice(round(newPurchasePrice * (double) 2));
    this.size = size;
  }

  public CatLitter(String... fArgs) {
    super(fArgs[0], Double.parseDouble(fArgs[1]), Double.parseDouble(fArgs[2]), Double.parseDouble(fArgs[3]), Integer.parseInt(fArgs[4]), Integer.parseInt(fArgs[5]));
    if (fArgs.length > 6) {
      this.size = Integer.parseInt(fArgs[6]);
    } else {
      this.size = 0;
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
