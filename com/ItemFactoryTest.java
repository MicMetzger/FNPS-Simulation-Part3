package java.com;

import static org.junit.jupiter.api.Assertions.*;
import static main.java.com.utilities.Builders.randomItemType;

import main.java.com.factory.*;
import main.java.com.factory.ItemFactory.*;
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
   * The test is to create a random item type and then assure that the ItemFactory.createItem() 
   * method returns an Item referenced by it's subclass, Supplies.
   * </p>
   * <p>
   * (Exception) ItemFactoryException
   * </p>
   */
  @Test
  void testItemFactoryBuilderTypeCastTest() {
    // setUp();
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

    assertEquals(suppliesFactory.size(), 6);
    assertTrue(suppliesFactory.get(0) instanceof Supplies);
    System.out.println(suppliesFactory.get(0));
    assertTrue(suppliesFactory.get(1) instanceof Supplies);
    System.out.println(suppliesFactory.get(1));
    assertTrue(suppliesFactory.get(2) instanceof Supplies);
    System.out.println(suppliesFactory.get(2));
    assertTrue(suppliesFactory.get(3) instanceof Supplies);
    System.out.println(suppliesFactory.get(3));
    assertTrue(suppliesFactory.get(4) instanceof Supplies);
    System.out.println(suppliesFactory.get(4));
    assertTrue(suppliesFactory.get(5) instanceof Supplies);
    System.out.println(suppliesFactory.get(5));
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