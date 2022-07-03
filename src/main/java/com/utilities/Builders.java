package main.java.com.utilities;

import java.util.ArrayList;
import java.text.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import main.java.com.factory.ItemType;
import main.java.com.individuals.training.TrainerType;
import main.java.com.item.supplies.enums.FoodType;
import main.java.com.item.pets.enums.*;
import main.java.com.store.Store;



public record Builders() {

  // TODO: THIS IS A TEST VALUE/METHOD
  private static int The_Day = 0;


  // TODO: THIS IS A TEST VALUE/METHOD
  static void set_The_Day() {
    Store.incrementDay();
    The_Day = Store.getDay();
  }


  // TODO: THIS IS A TEST VALUE/METHOD
  public static int get_The_Day() {
    return Store.getDay();
  }


  // Data helpers
  public static final ArrayList<String> COLORS =
      new ArrayList<String>(Arrays.asList("Black", "Brown", "White", "Gray", "Red"));

  public static Integer IndividualID = 0;

  public static final boolean[] randomSelectionbool = {true, false};

  public static final DecimalFormat sizeFormat = new DecimalFormat("#####.00");

  public static final AtomicInteger counter = new AtomicInteger(0);

  public static ArrayList<String> NAME_TEMPLATE = new ArrayList<String>(
      Arrays.asList("Kevin", "Andrew", "Michelle", "Chris", "Paul", "Jack", "Alex", "John", "David", "Sarah"));





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


  public static TrainerType randomTrainingType() {
    return TrainerType.values()[new Random().nextInt(TrainerType.values().length)];
  }

}
