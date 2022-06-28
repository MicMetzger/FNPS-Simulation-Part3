package main.java.com.item.supplies.enums;

public enum SupplyType {
  Food("Food"),
  Leash("Leash"),
  CatLitter("CatLiter"),
  Toy("Toy"),
  Treat("Treat");

  private final String name;

  SupplyType(String type) {
    name = type;
  }
}
