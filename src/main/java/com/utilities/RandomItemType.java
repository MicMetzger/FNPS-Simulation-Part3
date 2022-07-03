package main.java.com.utilities;


import java.util.Random;
import main.java.com.factory.ItemType;



public interface RandomItemType {
  static ItemType randomItemType() {
    return ItemType.values()[new Random().nextInt(ItemType.values().length)];
  }
}
