package main.java.com.individuals;

import static main.java.com.item.pets.enums.Animal.*;
import static main.java.com.utilities.Builders.counter;

import java.security.*;
import java.text.*;
import java.util.*;
import main.java.com.Logging.LoggerManager;
import main.java.com.Logging.LoggerManager.Logger;
import main.java.com.events.*;
import main.java.com.events.task.*;
import main.java.com.item.*;
import main.java.com.item.supplies.enums.FoodType;
import main.java.com.item.pets.*;
import main.java.com.item.pets.enums.*;
import main.java.com.item.supplies.*;
import main.java.com.store.*;



public class Employee implements Individual, MessageReceiver {

  //  private List<Watcher> watcher = new ArrayList<>();
  ArrayList<Item>            inventory;
  ArrayList<Pet>             sick;
  ArrayList<DeliveryPackage> mailBox;
  protected     Employee      base;
  private       double        cash;
  private       double        earning;
  private       int           sold;
  private       int           workedDays;
  private       double        cashWithdrawn;
  private       EmployeeTask  task;
  private       EmployeeState state;
  private       boolean       ACTIVE;
  private       ReceiverType  type;
  private final int    ID;
  private       Logger logger;



  public enum EmployeeState {
    IDLE("Idle"),
    OCCUPIED("Occupied"),
    UNAVAILABLE("Unavailable");

    private final String name;


    EmployeeState(String state) {
      this.name = state;
    }
  }


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public Employee(int workedDays) {
    ID              = counter.incrementAndGet();
    logger          = LoggerManager.getInstance().getLogger(this);
    this.workedDays = workedDays;
    inventory       = new ArrayList<>();
    cash            = 0;
    sold            = 0;
    earning         = 0;
    mailBox         = new ArrayList<>();
    state           = EmployeeState.IDLE;
    ACTIVE          = false;
  }


  public Employee() {
    ID         = counter.incrementAndGet();
    logger          = LoggerManager.getInstance().getLogger(this);
    workedDays = 0;
    inventory  = new ArrayList<>();
    cash       = 0;
    sold       = 0;
    earning    = 0;
    mailBox    = new ArrayList<>();
    state      = EmployeeState.IDLE;
    ACTIVE     = false;
  }


  public Employee(Employee employee) {
    ID              = counter.incrementAndGet();
    logger          = LoggerManager.getInstance().getLogger(this);
    this.workedDays = employee.workedDays;
    this.inventory  = employee.inventory;
    this.cash       = employee.cash;
    this.sold       = employee.sold;
    this.earning    = employee.earning;
    this.mailBox    = employee.mailBox;
    this.state      = employee.state;
    this.ACTIVE     = employee.ACTIVE;
    this.type       = employee.type;
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
                            Getters 
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /**
   * Gets the total dollar and cent earning gained from sales closed by this employee.
   *
   * @return the earning total dollar and cent amount.
   */
  public double getEarning() {
    return earning;
  }


  /**
   * Gets the total number of items sold by this specific employee.
   *
   * @return the sold amount of items.
   */
  public int getSold() {
    return sold;
  }


  /**
   * Gets the employee's state.
   *
   * @return the state
   */
  public EmployeeState getState() {
    return state;
  }


  /**
   * Gets the current event task assigned to this employee if one exists.
   *
   * @return the task
   */
  public EmployeeTask getTask() {
    return task;
  }


  /**
   * Gets employee ID.
   *
   * @return the ID
   */
  public int getID() {
    return ID;
  }


  /**
   * Reports if the employee is active and working in a store for the day.
   *
   * @return the ACTIVE
   */
  public boolean isACTIVE() {
    return ACTIVE;
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
                            Setters 
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /**
   * Updates the amount the employee has earned through sales.
   *
   * @param earning the amount earned from a sale.
   */
  private void setEarning(double earning) {
    this.earning += earning;
  }


  /**
   * Sets the state of the employee.
   *
   * @param state the state to set.
   */
  public void setState(EmployeeState state) {
    this.state = state;
  }


  /**
   * Registers a task to the employee.
   *
   * @param task the event task for this employee to set
   */
  public void setTask(EmployeeTask task) {
    this.task = task;
  }


  /**
   * Sets the MessageReceiver Individual object type.
   *
   * @param trainer the Individual MessageReceiver type being set.
   */
  public void setType(ReceiverType trainer) {
    this.type = trainer;
  }


  /**
   * @param ACTIVE the aCTIVE to set
   */
  public void setACTIVE(boolean ACTIVE) {
    this.ACTIVE = ACTIVE;
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
                            Methods 
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  private void upSold() {
    this.sold++;
  }


  public void announce(String announcement) {
    logger.info(getNameExt() + announcement);
    System.out.println(getNameExt() + announcement);
  }


  public void arriveAtStore() {
    String announcement = " enters the store...";
    announce(announcement);
  }


  private void leaveTheStore() {
    String announcement = " leaves the store...";
    announce(announcement);
  }


  public void checkRegister() {
    String announcement = " checks the register...";
    announce(announcement);
  }


  public double goToBank(double cashWithdrawal) {
    if (cashWithdrawal <= 0) {cashWithdrawal = 1000;}
    String announcement = " goes to the bank...";
    announce(announcement);

    this.cash += 1000;
    cashWithdrawn += 1000;
    return cashWithdrawal;

  }


  public double goToBank() {
    String announcement = " goes to the bank, and withdraws $1000.00";
    announce(announcement);

    this.cash += 1000;
    cashWithdrawn += 1000;
    return cashWithdrawn;
  }


  public void processDeliveries() {
    String announcement = " goes through today's deliveries...";
    announce(announcement);
    ArrayList<DeliveryPackage> RECEIVED_PACKAGES = new ArrayList<DeliveryPackage>();

    if (mailBox.size() != 0) {
      mailBox.forEach(item -> {
        if (item.getExpectedDeliveryDate() == workedDays) {
          String announcementDelivery = " " + item.getPackageName() + " has arrived and is added to the inventory.";
          announce(announcementDelivery);
          inventory.add(item.getItem());
          RECEIVED_PACKAGES.add(item);
        }
      });
      mailBox.removeAll(RECEIVED_PACKAGES);
    } else { // mailbox empty
      String announcementError = " notices that the mailbox is empty!";
      announce(announcementError);
    }
  }


  public void doInventory() {
    // String announcement = "places an order for ";  //TODO\

    Random            rand            = new Random();
    ArrayList<String> itemToBeRemoved = new ArrayList<String>();
    ArrayList<String> ITEM_TO_ORDER = new ArrayList<String>(
        Arrays.asList("Dog", "Cat", "Bird", "Food", "Leash", "CatLitter", "Snake", "Ferret", "Treat"));
    String announcement        = " checking the inventory...";
    double totalInventoryValue = 0.0;

    announce(announcement);
    for (Item item : inventory) {
      // Initially (Day one), the value should be all zero as all purchase prices should be zero.
      totalInventoryValue += item.getPurchasePrice();
      String itemName = item.getClass().getSimpleName();
      if (ITEM_TO_ORDER.contains(itemName)) {
        itemToBeRemoved.add(itemName);
      }
    }

    announce(" reporting the total inventory value. Total Value: $" + totalInventoryValue);
    ITEM_TO_ORDER.removeAll(itemToBeRemoved);
    itemToBeRemoved.clear();

    // ITEM_TO_ORDER is now left with items that need to be ordered (0 stock)
    for (String name : ITEM_TO_ORDER) {
      announce(" notices " + name + " needs to be purchased.");
      int    expectedDeliveryDate = workedDays + rand.nextInt(3);
      double purchasePrice        = rand.nextInt(100);
      if (cash >= purchasePrice) {
        mailBox.add(orderItem(name, expectedDeliveryDate, purchasePrice));
        cash -= purchasePrice;
      } else {
        // insufficient money
        announce("Purchase failed: Insufficient money");
        itemToBeRemoved.add(name);
      }
    }
    ITEM_TO_ORDER.clear();
  }


  public void feedAnimals() {
    String announcement = " goes to feed the animals...";
    announce(announcement);
    ArrayList<Item> itemsToBeRemoved = new ArrayList<>();
    inventory.forEach(item -> {
      // TODO: this evaluation seems to fail, may be an issue when purchasing animals and initializing
      if (item.isPet()) {
        // 5% chance of getting sick
        /* = ((Pet) item).setHealthy(rand.nextInt(0,100) < 5);*/
        boolean getsSick = new SecureRandom().nextInt(100) < 5;
        if (getsSick) {
          ((Pet) item).setHealthy(false);
          announce(" visits a " + ((Pet) item).getClass().getSimpleName() + ", and the pet got sick...");
          sick.add(((Pet) item));
          itemsToBeRemoved.add(item); // preventing error
        } else {
          announce(" visits a " + ((Pet) item).getClass().getSimpleName());
          announce(" feeds the " + ((Pet) item).getClass().getSimpleName());
        }
      }
    });
    inventory.removeAll(itemsToBeRemoved);
    itemsToBeRemoved.clear();

    for (Pet pet : sick) {
      // 25% change of recovering
      switch (pet.setHealthy(new SecureRandom().nextInt(100) < 25)) {
        case 0 -> announce(
            " feeds a sick " + pet.getClass().getSimpleName() + " and the pet remains ill...");
        case 1 -> {
          announce(" feeds a sick " + pet.getClass().getSimpleName()
                   + " and the pet recovered from its sickness...");
          inventory.add(pet);
          itemsToBeRemoved.add(pet); // preventing error
        }
      }
    }
    sick.removeAll(itemsToBeRemoved);
  }


  /**
   * @param name                 the name to set
   * @param expectedDeliveryDate the expectedDeliveryDate to set
   * @param purchasePrice        the purchasePrice to set
   * @return Delivery Package
   */
  public DeliveryPackage orderItem(String name, int expectedDeliveryDate, double purchasePrice) {
    DecimalFormat           sizeFormat = new DecimalFormat("#####.00");
    final ArrayList<String> colors     = new ArrayList<String>(Arrays.asList("Black", "Brown", "White", "Gray", "Red"));
    DeliveryPackage         newPackage = new DeliveryPackage(name, expectedDeliveryDate);
    int                     daySold    = 0;
    int                     salePrice  = 0;
    int                     age        = new Random().nextInt(15);

    String announcement = " purchasing " + name + " for $" + purchasePrice;
    announce(announcement);

    switch (name) {
      case "Dog" -> newPackage.setItem(new Dog(name, expectedDeliveryDate, daySold, purchasePrice, purchasePrice * 2, salePrice,
          DOGS.get(new Random().nextInt(4)), age, new Random().nextInt(2) == 1,
          Double.parseDouble(sizeFormat.format(new Random().nextDouble(50))), Color.values()[new Random().nextInt(Color.values().length)],
          new Random().nextInt(2) == 1, new Random().nextInt(2) == 1));
      case "Cat" -> newPackage.setItem(new Cat(name, expectedDeliveryDate, daySold, purchasePrice, purchasePrice * 2, salePrice,
          CATS.get(new Random().nextInt(4)), age, new Random().nextInt(2) == 1, colors.get(new Random().nextInt(colors.size())),
          new Random().nextInt(2) == 1, new Random().nextInt(2) == 1));
      case "Bird" -> newPackage.setItem(new Bird(name, expectedDeliveryDate, daySold, purchasePrice, purchasePrice * 2, salePrice,
          BIRDS.get(new Random().nextInt(4)), age, new Random().nextInt(2) == 1,
          Double.parseDouble(sizeFormat.format(new Random().nextDouble(8))), new Random().nextInt(2) == 1, new Random().nextInt(2) == 1,
          new Random().nextInt(2) == 1));
      case "Food" -> newPackage.setItem(
          new Food(name, purchasePrice, purchasePrice * 2, salePrice, daySold, expectedDeliveryDate, new Random().nextInt(100),
              AnimalType.values()[new Random().nextInt(AnimalType.values().length)], FoodType.values()[new Random().nextInt(
              FoodType.values().length)]));
      case "Leash" -> newPackage.setItem(new Leash(name, purchasePrice, purchasePrice * 2, salePrice, daySold, expectedDeliveryDate,
          AnimalType.values()[new Random().nextInt(AnimalType.values().length)]));

      //      case "Toy": {
      //        newPackage.setItem(new Toy(name, purchasePrice, purchasePrice * 2, salePrice, daySold, expectedDeliveryDate,
      //            AnimalType.values()[new Random().nextInt(AnimalType.values().length)]));
      //        break;
      //      }
      case "CatLitter" -> newPackage.setItem(
          new CatLitter(name, purchasePrice, purchasePrice * 2, salePrice, daySold, expectedDeliveryDate, new Random().nextInt(100)));
      case "Snake" -> newPackage.setItem(
          new Snake(SNAKES.get(new Random().nextInt(4)), age, true, Double.parseDouble(sizeFormat.format(new Random().nextDouble(8))),
              name, 0, 0, purchasePrice, purchasePrice * 2, 0));
      case "Ferret" -> newPackage.setItem(
          new Ferret(FERRETS.get(new Random().nextInt(4)), age, true, Color.values()[new Random().nextInt(Color.values().length)],
              false, name, 0, 0, purchasePrice, purchasePrice * 2, 0));
      case "Treat" -> newPackage.setItem(new Treat(name, purchasePrice, purchasePrice * 2, salePrice, daySold, expectedDeliveryDate,
          AnimalType.values()[new Random().nextInt(AnimalType.values().length)]));
    }
    return newPackage;
  }


  public double checkCashOnHand() {
    return this.cash;
  }


  public void cleanStore() {
    String announcement = " cleans the store...";
    announce(announcement);
    ArrayList<Pet> ESCAPING_ANIMALS = new ArrayList<Pet>();

    announce(" cleans the animal cage.");
    for (Item item : inventory) {
      if (item.getClass().getCanonicalName().contains("pet")) {
        if (new SecureRandom().nextInt(100) < 50) {
          announcement = " accidentally has escape " + item.getName();
          announce(announcement);
          if (new SecureRandom().nextInt(100) < 50) { // clerk catches escaping animal
            announce(" catches " + item.getName() + " and puts it back in the cage");
          } else { // animal escaped
            System.out.println("[-] " + item.getName() + " successfully escapes for freedom..........");
            ESCAPING_ANIMALS.add(((Pet) item));
          }
        }
      }
    }
    inventory.removeAll(ESCAPING_ANIMALS);
    ESCAPING_ANIMALS.clear();

    announce(" cleans the sick animal cage.");
    for (Pet item : sick) {
      if (new SecureRandom().nextInt(100) < 5) {
        announcement = " accidentally has escape " + item.getName();
        announce(announcement);
        if (new SecureRandom().nextInt(100) < 50) { // clerk catches escaping animal
          announce(" catches " + item.getName() + " and puts it back in the cage");
        } else { // animal escaped
          System.out.println("[-] " + item.getName() + " desperately escapes for freedom..........");
          ESCAPING_ANIMALS.add(item);
        }
      }
    }
    sick.removeAll(ESCAPING_ANIMALS);
  }


  public int getWorkDays() {
    return workedDays;
  }


  public void incWorkDays() {
    workedDays++;
  }


  public void dayoff() {
    workedDays = 0;
    state      = EmployeeState.UNAVAILABLE;
    ACTIVE     = false;
  }


  public void setInventory(ArrayList<Item> newInventory) {
    this.inventory = newInventory;
  }


  public void setSickPet(ArrayList<Pet> newSickAnimals) {
    this.sick = newSickAnimals;
  }


  public ArrayList<DeliveryPackage> getMailBox() {
    return this.mailBox;
  }


  public void setMailBox(ArrayList<DeliveryPackage> newMailbox) {
    this.mailBox = newMailbox;
  }


  public void setCash(double newCash) {
    this.cash = newCash;
  }


  public void earn(double amount) {
    this.cash += amount;
    this.earning += amount;
    announce("Earned $" + amount + " from a sale.");
  }


  public double getCash() {
    double transfer = this.cash;
    this.cash = 0;
    return transfer;
  }


  public ArrayList<Pet> getSickAnimal() {
    return sick;
  }


  public ArrayList<Item> getInventory() {
    return inventory;
  }


  private void execute() {
    if (task != null && task.getStatus() == EventStatus.INCOMPLETE) {
      state = EmployeeState.OCCUPIED;
      task.run();
    } else if (task != null && task.getStatus() == EventStatus.COMPLETE) {
      state = EmployeeState.IDLE;
      System.out.println("[-] " + base.getNameExt() + " is done with the task of " + task.getTaskType().taskname() + ".");
      task = null;
    }
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
                      Individual Overrides 
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  @Override
  public String getNameExt() {
    return base.getNameExt();
  }


  @Override
  public String getNameSimple() {
    return base.getNameSimple();
  }


  @Override
  public void setName(String name) {
    base.setName(name);
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
                  MessageReceiver Overrides 
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  @Override
  public ReceiverType getType() {
    return type;
  }


  @Override
  public void sendMessage(String message) {
    System.out.println("[-] " + base.getNameExt() + ": " + message);
  }


  @Override
  /*  synchronized  */ public void update(String message, Object event) {
    if (event instanceof State) {
      if (((State) event).hasTask()) {
        if ((((State) event).getStatus() == EventStatus.INCOMPLETE) && !(((State) event).getStatus().isAssigned()) && ACTIVE
            && getState() == EmployeeState.IDLE) {
          task = ((State) event).getTask(this);
          ((State) event).getStatus().setAssigned(true);
          execute();
        }
      }
    }
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
}

