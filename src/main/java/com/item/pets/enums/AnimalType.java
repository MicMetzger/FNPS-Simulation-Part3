package main.java.com.item.pets.enums;

public enum AnimalType {
  Dog("Dog"),
  Bird("Bird"),
  Cat("Cat"),
  Ferret("Ferret"),
  Snake("Snake");

  public static AnimalType randomAnimal() {
    return AnimalType.values()[(int) (Math.random() * AnimalType.values().length)];
  }

  AnimalType(String animal) {}
}
