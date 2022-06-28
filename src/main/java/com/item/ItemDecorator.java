package main.java.com.item;
import main.java.com.item.*;

public abstract class ItemDecorator extends Item {
    protected Item concreteItem;

    public ItemDecorator(Item newItem) {
        concreteItem = newItem;
    }

    @Override
    public double getSalePrice() {
        System.out.println("Base price: " + concreteItem.getSalePrice());
        return concreteItem.getSalePrice();
    }
}
