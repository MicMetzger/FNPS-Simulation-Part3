package main.java.com.factory;

import main.java.com.item.*;
import main.java.com.item.supplies.*;



public class ItemFactory {
  

  public static Supplies createItem(ItemType itemType) {
    return build(itemType);
  }

  private static Supplies build(ItemType itemType) {
    return itemType.build();
  }


  public static class ItemFactoryException extends Exception {
    Throwable cause;


    public Exception getCause() {
      return (Exception) cause;
    }

    public String report() {
      if (cause instanceof ClassCastException) {
        return "Attempting an unchecked cast to super.";
      } else {
        return cause.getMessage();
      }
    }

    public ItemFactoryException(String message) {
      super(message);
    }

    public ItemFactoryException(String message, Throwable cause) {
      super(message, cause);
    }

  }

}
