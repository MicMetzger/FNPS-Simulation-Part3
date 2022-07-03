package main.java.com.item;

import java.util.ArrayList;
import main.java.com.factory.ItemType;
import main.java.com.item.pets.Bird;
import main.java.com.item.pets.Cat;
import main.java.com.item.pets.Dog;
import main.java.com.item.pets.enums.Animal;



public class Inventory {

  // I specifically wanted to try to do this for now with single level ArrayLists
  // there may be better collections or nested lists that might make some
  // operations more efficient...

  // I'm also just dealing with Pets in inventory, keeping a bunch of Supplies and deciding whether to use
  // Items or Pets/Supplies in your ArrayLists is for you to do...

  public ArrayList<Pet> pets;
  public ArrayList<Pet> sickPets;
  public ArrayList<Pet> arrivingPets;
  public ArrayList<Pet> soldPets;

  // I was too lazy to do a nice naming scheme for pets, so I just give them numbers.  Sad...
  static int petNumber;

  Inventory() {

    pets         = new ArrayList<>();
    arrivingPets = new ArrayList<>();
    soldPets     = new ArrayList<>();
    sickPets     = new ArrayList<>();

    petNumber = 1;
    initializeInventory(pets);
  }

  void initializeInventory(ArrayList<Pet> list) {
    for (int i = 0; i < 3; i++) {
      for (ItemType type : ItemType.values()) {
        Pet pet = makeNewPetByType(type);
        list.add(pet);
      }
    }
  }

  // There may be fancier ways to do this with things like Java newInstance
  // and the reflection framework, this has the advantage of being pretty clean and readable
  // we're not applying patterns here yet, otherwise this really wants to use a factory
  Pet makeNewPetByType(ItemType type) {
    Pet pet;
    switch (type.toString()) {
      case "CAT" -> pet = new Cat();
      case "DOG" -> pet = new Dog();
      case "BIRD" -> pet = new Bird();
      default -> {
        // Logger("Error in makeNewItemByType - unexpected type enum");
        pet = null;
      }
    }
    pet.setName("Pet " + petNumber);
    petNumber += 1;
    return pet;
  }

  // add(), remove() can be done directly to the list
  // overall count can come from size()
  // count of specific item types
  int countPetsByType(ArrayList<Pet> list, Animal type) {
    int count = 0;
    for (Pet pet : list) {if (pet.getAnimalType() == type) {count += 1;}}
    return count;
  }

  // helper to get value of items in a list
  double getValueOfPets(ArrayList<Pet> list) {
    double value = 0;
    for (Pet pet : list) {value = value + pet.getPurchasePrice();}
    return value;
  }


}
