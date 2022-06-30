package main.java.com.item.supplies;

import static java.lang.Math.*;

import java.security.*;
import main.java.com.item.*;
import main.java.com.item.supplies.enums.FoodType;
import main.java.com.item.supplies.enums.SupplyType;
import main.java.com.item.pets.enums.*;



public class Food extends Supplies {
  private int        size;
  private AnimalType animal;
  private FoodType   type;

  public Food(
      String name,
      double purchasePrice,
      double listPrice,
      double salePrice,
      int dayArrived,
      int daySold,
      int size,
      AnimalType animal,
      FoodType type) {
    super(name, purchasePrice, listPrice, salePrice, dayArrived, daySold);
    this.size = size;
    this.animal = animal;
    this.type = type;
  }

  public Food() {
    super.supplyType = SupplyType.Food;
  }

  public Food(int size, AnimalType animal, FoodType type) {
    super(animal.toString() + " Food");
    double newPurchasePrice = round(new SecureRandom().nextDouble(100));
    super.setPurchasePrice(newPurchasePrice);
    super.setListPrice(round(newPurchasePrice * (double) 2));
    super.setDayArrived(0);
    this.size = size;
    this.animal = animal;
    this.type = type;
    super.supplyType = SupplyType.Food;
  }

  public Food(String... strings) {}

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getAnimalS() {
    return animal.toString();
  }

  public AnimalType getAnimal() {
    return animal;
  }

  public void setAnimal(AnimalType animal) {
    this.animal = animal;
  }

  public FoodType getType() {
    return type;
  }

  public void setType(FoodType type) {
    this.type = type;
  }
}
