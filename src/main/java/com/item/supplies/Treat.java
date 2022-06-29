package main.java.com.item.supplies;

import static java.lang.Math.round;
import static main.java.com.item.pets.enums.AnimalType.randomAnimal;
import static main.java.com.utilities.Builders.getThe_Day;

import java.security.*;
import main.java.com.item.*;
import main.java.com.item.pets.enums.*;
import main.java.com.item.supplies.enums.*;



public class Treat extends Supplies {
  private AnimalType animal;

  public Treat(AnimalType animal) {
    super();
    super.supplyType = SupplyType.Treat;
    this.animal      = animal;
    super.setName("Treat");
  }

  public Treat(
      String name,
      double purchasePrice,
      double listPrice,
      double salePrice,
      int daySold,
      int dayArrived,
      AnimalType animal) {
    super(name, purchasePrice, listPrice, salePrice, dayArrived, daySold);
    super.supplyType = SupplyType.Treat;
    this.animal      = animal;
    super.setName("Treat");

  }

  public Treat() {
    super();
    super.supplyType = SupplyType.Treat;
    this.animal      = randomAnimal();
    super.setName("Treat");
  }

  public Treat(String... fArgs) {
    super();
    super.supplyType = SupplyType.Treat;
    if (fArgs.length <= 0) {
      this.animal = randomAnimal();
      super.setName("Treat");
      super.setListPrice(round(new SecureRandom().nextDouble(100)));
      super.setPurchasePrice(0);
      super.setSalePrice(round(new SecureRandom().nextDouble(getListPrice())));
      super.setDayArrived(getThe_Day());
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

  public AnimalType getAnimal() {
    return animal;
  }

}
