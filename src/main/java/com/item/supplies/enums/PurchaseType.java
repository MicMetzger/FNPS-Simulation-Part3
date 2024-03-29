package main.java.com.item.supplies.enums;

import main.java.com.item.pets.enums.*;



interface DesiredItem {
	Enum<?> desire();

}


public enum PurchaseType implements DesiredItem {
	Food(SupplyType.Food),
	Leash(SupplyType.Leash),
	CatLiter(SupplyType.CatLitter),
	Toy(SupplyType.Toy),
	Treat(SupplyType.Treat),
	Dog(AnimalType.Dog),
	Bird(AnimalType.Bird),
	Cat(AnimalType.Bird),
	Ferret(AnimalType.Ferret),
	Snake(AnimalType.Snake);

	private final String name;


	PurchaseType(Enum<?> type) {
		name = String.valueOf(type);
	}


	@Override
	public Enum<?> desire() {
		return this;
	}
}
