package main.java.com.item.supplies;

import java.util.*;
import main.java.com.item.*;
import main.java.com.item.supplies.enums.*;

/** */
public class Supplies extends Item {
  SupplyType supplyType;

  public Supplies() {
    super();
  }

  public Supplies(String name) {
    super(name, 0.0, new Random().nextDouble(25.0), 0.0, 0, 0, false);
  }

  /**
   * Instantiates a new Item.
   *
   * @param name the name
   * @param dayArrived the day arrived
   * @param daySold the day sold
   * @param purchasePrice the purchase price
   * @param listPrice the list price
   * @param salePrice the sale price
   */
  public Supplies(
      String name,
      double purchasePrice,
      double listPrice,
      double salePrice,
      int dayArrived,
      int daySold) {
    super(name, purchasePrice, listPrice, salePrice, dayArrived, daySold, false);
  }

  public Supplies(String name, double pPrice) {
    super(name, pPrice, new Random().nextDouble(25.0), 0.0, 0, 0, false);
  }

  public Supplies(String name, double pPrice, double lPrice) {
    super(name, pPrice, lPrice, 0.0, 0, 0, false);
  }

  public Supplies(String name, double pPrice, double lPrice, double sPrice) {
    super(name, pPrice, lPrice, sPrice, 0, 0, false);
  }

  public Supplies(String name, double pPrice, double lPrice, double sPrice, int aDay) {
    super(name, pPrice, lPrice, sPrice, aDay, 0, false);
  }

  public Supplies(Item suppliesCopy, double purchasePrice, int soldDay) {
    super(
        suppliesCopy.getName(),
        purchasePrice,
        suppliesCopy.getListPrice(),
        suppliesCopy.getSalePrice(),
        suppliesCopy.getDayArrived(),
        soldDay,
            false);
  }

  public SupplyType getSupplyType() {
    return supplyType;
  }

}
