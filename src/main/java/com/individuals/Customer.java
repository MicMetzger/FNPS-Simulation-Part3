package main.java.com.individuals;

import static java.lang.Math.round;

import java.security.*;
import java.util.*;
import main.java.com.item.*;



public class Customer implements Individual, MessageReceiver {
  PurchaseType desire;
  ReceiverType type = ReceiverType.Customer;
  public Item obj;
  boolean isFinished;
  public boolean discount;
  String name;
  double purchasePrice;

  public Customer() {
    desire        = PurchaseType.values()[new SecureRandom().nextInt(PurchaseType.values().length)];
    obj           = null;
    purchasePrice = 0;
    discount      = false;
    isFinished    = false;
  }

  public boolean inspectInventory(ArrayList<Item> inventory) {
    System.out.println("The customer inspects the store's offerings...");
    inventory.forEach(
        item -> {
          SecureRandom rand = new SecureRandom();
          int          roll = rand.nextInt(0, 10);

          if (roll == 1) {
            // TODO: add a functionality where the desired item is out of stock
            if (rand.nextInt(100) < 50) { // 50% chance of buying the item at listPrice
              obj           = item;
              purchasePrice = obj.getListPrice();
              obj.setSalePrice(purchasePrice);
            } else {
              if (rand.nextInt(100) < 75) {
                discount      = true;
                obj           = item;
                purchasePrice =
                    round(obj.getListPrice() - (obj.getListPrice() / 100) * 10); // 10% off discount
                obj.setSalePrice(purchasePrice);
              }
            }
          }
        });
    return obj != null;
  }

  public double getPurchasePrice() {
    return purchasePrice;
  }

  public boolean hasBoughtAtDisountPrice() {
    return discount;
  }

  public PurchaseType getDesire() {
    return desire;
  }

  public void Cycle() {
    try {
      Thread.sleep(new SecureRandom().nextInt(1000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    isFinished = true;
  }


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ MessageReceiver Overrides ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  @Override
  public void sendMessage(String message) {

  }

  @Override
  public void update(String watched, Object event) {

  }

  @Override
  public ReceiverType getType() {
    return null;
  }

  @Override
  public void setType(ReceiverType type) {

  }


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Individual Overrides ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  @Override
  public String getNameSimple() {
    return name + ", the Customer,";
  }

  @Override
  public String getNameExt() {
    return getNameSimple() + ", the Customer,";
  }

  @Override
  public void setName(String name) {

  }

}
