package utilities;

import factory.ItemFactory.*;
import java.text.*;
import java.util.*;
import main.java.com.item.pets.enums.*;
import main.java.com.item.supplies.enums.*;



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

  public static int randomSizeINT(int min, int max) {
    if (min > max) {
      throw new IllegalArgumentException("Min must be less than max");
    } else {
      if (min == max || max == 0) {
        new Random().nextInt(100);
      } else {
        return new Random().nextInt(max - min) + min;
      }
    }
    return min;
  }
  
  public static double randomSizeDOUBLE(double min, double max) {
    if (min > max) {
      throw new IllegalArgumentException("Min must be less than max");
    } else {
      if (min == max || max == 0) {
        new Random().nextDouble(100.00);
      } else {
        return new Random().nextDouble() * (max - min) + min;
      }
    }
    return min;
  }
  
  public static FoodType randomFoodType() {
    return FoodType.values()[new Random().nextInt(FoodType.values().length)];
  }

}
