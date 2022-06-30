package main.java.com.item.supplies;

import static java.lang.Math.*;
import static main.java.com.item.pets.enums.AnimalType.randomAnimal;
import static main.java.com.utilities.Builders.get_The_Day;

import java.security.*;
import main.java.com.item.*;
import main.java.com.item.supplies.enums.SupplyType;
import main.java.com.item.pets.enums.*;



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
    super();
    super.supplyType = SupplyType.Toy;
    if (fArgs.length <= 0) {
      this.animal = randomAnimal();
      super.setName("Treat");
      super.setListPrice(round(new SecureRandom().nextDouble(1, 100)));
      super.setPurchasePrice(0);
      super.setSalePrice(round(new SecureRandom().nextDouble(0, getListPrice())));
      super.setDayArrived(get_The_Day());
      super.setDaySold(0);
    } else {
      // TODO: Implement this. Check what's not being passed in.
      for (int i = 0; i < fArgs.length; i++) {
        switch (fArgs[i]) {
          case "animalType" -> this.animal = randomAnimal();
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

}
