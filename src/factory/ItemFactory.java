package factory;

import main.java.com.item.*;
import main.java.com.item.supplies.*;



public class ItemFactory {

  public enum ItemType {
    CATLITTER(CatLitter::new),
    FOOD(Food::new),
    LEASH(Leash::new),
    TOY(Toy::new),
    TREAT(Treat::new);

    private final ItemBuilder<Supplies> builder;

    ItemType(ItemBuilder<Supplies> builder) {
      this.builder = builder;
    }
  }

  public static Supplies createItem(ItemType itemType) {
    return switch (itemType) {
      case CATLITTER -> itemType.builder.build();
      case FOOD -> itemType.builder.build();
      case LEASH -> itemType.builder.build();
      case TOY -> itemType.builder.build();
      case TREAT -> itemType.builder.build();
    };
  }

  public Supplies build(ItemType itemType) {
    return itemType.builder.build();
  }

}
