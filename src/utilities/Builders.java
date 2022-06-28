package utilities;

import factory.ItemFactory.*;
import java.text.*;
import java.util.*;
import main.java.com.item.pets.enums.*;



public record Builders() {

  // Data helpers
  public static final ArrayList<String> COLORS =
      new ArrayList<String>(Arrays.asList("Black", "Brown", "White", "Gray", "Red"));

  public static final boolean[] randomSelectionbool = {true, false};

  public static final DecimalFormat sizeFormat = new DecimalFormat("#####.00");

  public static ArrayList<String> NAME_TEMPLATE = new ArrayList<String>(
      Arrays.asList("Kevin", "Andrew", "Michelle", "Chris", "Paul", "Jack", "Alex", "John", "David", "Sarah"));


  public static ItemType randomItemType() {
    return ItemType.values()[new Random().nextInt(ItemType.values().length)];
  }

  public static AnimalType randomAnimalType() {
    return AnimalType.values()[new Random().nextInt(AnimalType.values().length)];
  }

}
