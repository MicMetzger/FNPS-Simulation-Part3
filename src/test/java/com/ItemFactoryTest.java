package test.java.com;

import static org.junit.jupiter.api.Assertions.*;
import static utilities.Builders.randomItemType;

import factory.*;
import factory.ItemFactory.*;
import java.util.*;
import main.java.com.item.*;
import org.junit.jupiter.api.*;



class ItemFactoryTest {
  protected List<Item>     itemFactory;
  protected List<Supplies> suppliesFactory;

  @BeforeEach
  void setUp() {
    itemFactory     = new ArrayList<Item>(1);
    suppliesFactory = new ArrayList<Supplies>(1);
  }


  /**
   * <p>
   * The test is to create a random item type and then assure that the ItemFactory.createItem() method returns an Item referenced by it's subclass,
   * Supplies..
   * </p>
   * <p>
   * (Exception) ItemFactoryException
   * </p>
   */
  @Test
  void testItemFactoryBuilderTypeCastTest() {
    setUp();
    assertDoesNotThrow(() -> {
      try {
        itemFactory.add(ItemFactory.createItem(randomItemType()));
      } catch (Exception e) {
        throw new ItemFactoryException(e.getMessage());
      }
    });
  }


  /**
   * Test ItemFactory builder to generate Items of varying type and successfully append them to an Item List array.
   *
   * @throws Exception the exception e
   * @
   */
  @Test
  void testItemFactoryBuilderRandomTestWithoutParameters() {
    suppliesFactory.add(ItemFactory.createItem(randomItemType()));
    suppliesFactory.add(ItemFactory.createItem(randomItemType()));
    suppliesFactory.add(ItemFactory.createItem(randomItemType()));
    suppliesFactory.add(ItemFactory.createItem(randomItemType()));
    suppliesFactory.add(ItemFactory.createItem(randomItemType()));
    suppliesFactory.add(ItemFactory.createItem(randomItemType()));

    assertEquals(itemFactory.size(), 6);
    assertTrue(itemFactory.get(0) instanceof Supplies);
    System.out.println(itemFactory.get(0));
    assertTrue(itemFactory.get(1) instanceof Supplies);
    System.out.println(itemFactory.get(1));
    assertTrue(itemFactory.get(2) instanceof Supplies);
    System.out.println(itemFactory.get(2));
    assertTrue(itemFactory.get(3) instanceof Supplies);
    System.out.println(itemFactory.get(3));
    assertTrue(itemFactory.get(4) instanceof Supplies);
    System.out.println(itemFactory.get(4));
    assertTrue(itemFactory.get(5) instanceof Supplies);
    System.out.println(itemFactory.get(5));
  }

  // @Test
  // void testItemFactoryBuilderTypeOfTreatAllAnimals() {
  //  
  // }

  // @Test
  // void testItemFactoryBuilderTypeOfToy() {
  //  
  // }

  // @Test
  // void testItemFactoryBuilderTypeOfLitter() {
  //  
  // }

  // @Test
  // void testItemFactoryBuilderTypeOfFoodAllAnimals() {
  //  
  // }

  @AfterAll
  static void tearDownAll() throws Exception {
    System.out.println("ItemFactoryTest complete");
  }

}