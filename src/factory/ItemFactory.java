package factory;

import java.lang.reflect.*;
import java.util.*;
import main.java.com.item.*;
import main.java.com.item.supplies.*;



public class ItemFactory {

  public enum ItemType {
    CATLITTER(CatLitter::new),
    FOOD(Food::new),
    LEASH(Leash::new),
    TOY(Toy::new),
    TREAT(Treat::new);


    private final ItemBuilder<Supplies, String[]> builder;
    // private final ItemBuilder<Supplies> builder;

    ItemType(ItemBuilder<Supplies, String[]> builder) {
      this.builder = builder;
    }

    private Supplies build(String... fArgs) {
      return builder.build(fArgs);
    }
  }

  public static Supplies createItem(ItemType itemType) {
    return build(itemType);
  }

  private static Supplies build(ItemType itemType) {
    return itemType.builder.build();
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
