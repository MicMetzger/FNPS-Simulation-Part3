package main.java.com.factory;
import main.java.com.item.Supplies;
import main.java.com.item.supplies.*;



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

    protected Supplies build(String... fArgs) {
      return builder.build(fArgs);
    }
  }
