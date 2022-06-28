package main.java.com.item.supplies;

import static java.lang.Math.*;
import static main.java.com.item.pets.enums.AnimalType.randomAnimal;

import java.security.*;
import main.java.com.item.*;
import main.java.com.item.pets.enums.*;
import main.java.com.item.supplies.enums.*;



public class Toy extends Supplies {

  // The animal
  private AnimalType animal;

  /**
   * Instantiates a new Leash
   *
   * @param animal the animal
   */
  public Toy(
      String name,
      double purchasePrice,
      double listPrice,
      double salePrice,
      int daySold,
      int dayArrived,
      AnimalType animal) {
    super(name, purchasePrice, listPrice, salePrice, dayArrived, daySold);
    super.setName("Toy");
    this.animal      = animal;
    super.supplyType = SupplyType.Toy;
  }

  public Toy(AnimalType animal) {
    double newPurchasePrice = round(new SecureRandom().nextDouble(100));
    super.setPurchasePrice(newPurchasePrice);
    super.setListPrice(round(newPurchasePrice * (double) 2));
    super.setDayArrived(0);
    super.setName("Toy");
    this.animal      = animal;
    super.supplyType = SupplyType.Toy;

  }

  /**
   * Default constructor
   */
  public Toy() {
    super();
    super.supplyType = SupplyType.Toy;
  }

  public Toy(String... fArgs) {
    super(fArgs[0], Double.parseDouble(fArgs[1]), Double.parseDouble(fArgs[2]), Double.parseDouble(fArgs[3]), Integer.parseInt(fArgs[4]), Integer.parseInt(fArgs[5]));
    if (fArgs.length > 6) {
      this.animal = AnimalType.valueOf(fArgs[6]);
    } else {
      this.animal = randomAnimal();
    }
    super.setName("Toy");
    super.supplyType = SupplyType.Toy;
  }

}
