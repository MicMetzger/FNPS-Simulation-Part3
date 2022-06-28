package main.java.com.item.supplies;

import static main.java.com.item.pets.enums.AnimalType.randomAnimal;

import main.java.com.item.*;
import main.java.com.item.pets.enums.*;
import main.java.com.item.supplies.enums.*;



public class Treat extends Supplies {
  private final AnimalType animal;

  public Treat(AnimalType animal) {
    super();
    super.setName("Treat");
    this.animal      = animal;
    super.supplyType = SupplyType.Treat;
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
    super.setName("Treat");
    this.animal      = animal;
    super.supplyType = SupplyType.Treat;

  }

  public Treat() {
    super();
    this.animal = randomAnimal();
    super.setName("Treat");
    super.supplyType = SupplyType.Treat;
  }

  public Treat(String... fArgs) {
    super(fArgs[0], Double.parseDouble(fArgs[1]), Double.parseDouble(fArgs[2]), Double.parseDouble(fArgs[3]), Integer.parseInt(fArgs[4]), Integer.parseInt(fArgs[5]));
    if (fArgs.length > 6) {
      this.animal = AnimalType.valueOf(fArgs[6]);
    } else {
      this.animal = randomAnimal();
    }
    super.setName("Treat");
    super.supplyType = SupplyType.Treat;
  }

  public AnimalType getAnimal() {
    return animal;
  }

}
