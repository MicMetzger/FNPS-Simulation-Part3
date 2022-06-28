package test.java.com;

import static org.junit.jupiter.api.Assertions.*;
import static utilities.Builders.randomItemType;

import factory.*;
import java.util.*;
import main.java.com.item.*;
import org.junit.jupiter.api.*;



class ItemFactoryTest {
  protected List<Item> itemFactory;

  @BeforeEach
  void setUp() {
    itemFactory = new ArrayList<>();
  }

  /**
   * Test ItemFactory builder to generate Items of varying type
   * and successfully append them to an Item List array.
   *
   * @throws Exception the exception e
   * @
   */
  @Test
  void testItemFactoryBuilderRandomTest() throws Exception {
    try {

      itemFactory.add(ItemFactory.createItem(randomItemType()));
      itemFactory.add(ItemFactory.createItem(randomItemType()));
      itemFactory.add(ItemFactory.createItem(randomItemType()));
      itemFactory.add(ItemFactory.createItem(randomItemType()));
      itemFactory.add(ItemFactory.createItem(randomItemType()));
      itemFactory.add(ItemFactory.createItem(randomItemType()));

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

    } catch (Exception e) {
      fail("ItemFactory.createItem() failed");
    }

  }

  // @Test
  // void testItemFactoryBuilderRandomTestWithParameters() {
  //  
  // }  

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
  static void tearDownAfterClass() throws Exception {

  }

}