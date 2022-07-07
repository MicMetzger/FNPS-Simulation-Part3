package main.java.com.store;

import static main.java.com.events.EventStatus.COMPLETE;
import static main.java.com.events.EventStatus.INCOMPLETE;
import static main.java.com.utilities.Builders.*;
import static main.java.com.utilities.RandomItemType.randomItemType;

import main.java.com.Logging.LoggerManager.Logger;
import main.java.com.factory.*;
import java.security.*;
import java.util.*;
import main.java.com.Logging.*;
import main.java.com.events.*;
import main.java.com.events.task.*;
import main.java.com.individuals.*;
import main.java.com.individuals.MessageReceiver.*;
import main.java.com.individuals.track.TrackerManager;
import main.java.com.item.*;
import main.java.com.item.addOns.*;
import main.java.com.item.pets.*;
import main.java.com.item.pets.enums.*;
import main.java.com.item.supplies.*;
import main.java.com.runnable.ParallelStance;
import main.java.com.runnable.RunStateManager;



public class Store extends ParallelStance implements EventObservable {
  private        Logger                     logger;
  private final  Thread                     runner;
  private static ArrayList<RunStateManager> threads = new ArrayList<RunStateManager>(2);
  public final   String                     name;
  // private final       Object MONITOR = new Object();
  State newDay, startDay, endDay, processDelivery, feedAnimals, visitBank, checkRegister, doInventory, trainAnimals, openStore, cleanStore, endSimulation, currentState, endState, previousState;
  // static List<State> states;


  // The store's Inventory.
  ArrayList<Item>            inventory;
  ArrayList<Pet>             sick;
  ArrayList<Item>            soldItems;
  ArrayList<Customer>        customers;
  ArrayList<DeliveryPackage> mailBox;
  MessageReceiver            inventoryReceiver;
  // The store's individuals.
  ArrayList<Employee>        clerks            = new ArrayList<Employee>();
  ArrayList<Employee>        trainers          = new ArrayList<Employee>();
  static ArrayList<MessageReceiver> employeeReceivers = new ArrayList<MessageReceiver>();
  ArrayList<MessageReceiver> receivers;
  ArrayList<MessageReceiver> customerReceivers;
  public Employee currentClerk;
  public Employee currentTrainer;

  private State state;

  // Money + day management
  private       double  bankWithdrawal;
  private       double  cash;
  public static int     day;
  private       boolean INITIALIZED = false;
  // private static final class InstanceHolder {
  //   private static final Store instance = new Store();
  //
  // }
  //
  // public static Store getInstance() {
  //   return InstanceHolder.instance;
  // }


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /**
   * Instantiates a new Store. Main entry point.
   *
   * <p>Default constructor
   */
  public Store() {
    super();
    name   = toString();
    logger = LoggerManager.getInstance().getLogger(this);
    runner = new Thread(this, toString());
    threads.add(this);
    customers      = new ArrayList<Customer>();
    inventory      = new ArrayList<Item>();
    sick           = new ArrayList<Pet>();
    mailBox        = new ArrayList<DeliveryPackage>();
    soldItems      = new ArrayList<Item>();
    bankWithdrawal = 0;
    cash           = 0;
    day            = 0;

    init();
  }


  public Store(String name, ILogQueue eventQueue) {
    super(name, eventQueue);
    this.name = name;
    logger    = LoggerManager.getInstance().getLogger(this);
    runner    = new Thread(this, toString());
    threads.add(this);
    customers      = new ArrayList<Customer>();
    inventory      = new ArrayList<Item>();
    sick           = new ArrayList<Pet>();
    mailBox        = new ArrayList<DeliveryPackage>();
    soldItems      = new ArrayList<Item>();
    bankWithdrawal = 0;
    cash           = 0;
    day            = 0;
    init();
  }


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  void init() {
    if (!INITIALIZED) {
      initiateStaff();
      initiateAnimals();
      initiateSupplies();
      initStates();
      this.state = new NewDay(this);
    }
  }


  /**
   * Initiate starting objects.
   */
  public synchronized void initiateStaff() {
    EmployeePool pool = new EmployeePool();
    clerks.add(pool.createObject(ReceiverType.CLERK, ""));
    clerks.add(pool.createObject(ReceiverType.CLERK, ""));
    clerks.add(pool.createObject(ReceiverType.CLERK, ""));
    trainers.add(pool.createObject(ReceiverType.TRAINER, "Haphazard"));
    trainers.add(pool.createObject(ReceiverType.TRAINER, "Negative"));
    trainers.add(pool.createObject(ReceiverType.TRAINER, "Positive"));
    employeeReceivers.addAll(clerks);
    employeeReceivers.addAll(trainers);
  }


  public void initiateAnimals() {
    // (size, color, broken, purebred) / (breed, age, health)
    inventory.add(new Dog(Double.parseDouble(sizeFormat.format(new Random().nextDouble(50.0))),
        Color.values()[new Random().nextInt(Color.values().length)] /*colors.get(new Random().nextInt(colors.size()))*/,
        randomSelectionbool[new Random().nextInt(1)],
        randomSelectionbool[new Random().nextInt(1)]));

    // (color, broken, purebred) / (breed, age, health)
    inventory.add(new Cat(COLORS.get(new Random().nextInt(COLORS.size())), randomSelectionbool[new Random().nextInt(1)],
        randomSelectionbool[new Random().nextInt(1)]));

    // (size, mimicry, exotic, papers) / (breed, age, health)
    inventory.add(
        new Bird(Double.parseDouble(sizeFormat.format(new Random().nextDouble(8))), randomSelectionbool[new Random().nextInt(1)],
            randomSelectionbool[new Random().nextInt(1)],
            randomSelectionbool[new Random().nextInt(1)]));

    // (color, broken, purebred) / (breed, age, health)
    inventory.add(new Ferret(Color.values()[new Random().nextInt(Color.values().length)], randomSelectionbool[new Random().nextInt(1)]));

    // (size) / (breed, age, health)
    inventory.add(new Snake(Double.parseDouble(sizeFormat.format(new SecureRandom().nextDouble(8)))));
  }


  public void initiateSupplies() {
    inventory.add(new Food(new Random().nextInt(100), randomAnimalType(), randomFoodType()));
    inventory.add(new CatLitter(randomSizeINT(0, 100)));
    inventory.add(new Toy(randomAnimalType()));
    inventory.add(new Leash(randomAnimalType()));
    inventory.add(new Treat(randomAnimalType()));

    inventory.add(ItemFactory.createItem(randomItemType()));
    inventory.add(ItemFactory.createItem(randomItemType()));
    inventory.add(ItemFactory.createItem(randomItemType()));
    inventory.add(ItemFactory.createItem(randomItemType()));
    inventory.add(ItemFactory.createItem(randomItemType()));
    inventory.add(ItemFactory.createItem(randomItemType()));
  }


  public void initStates() {
    // states          = Collections.synchronizedList(new ArrayList<State>());
    newDay          = new NewDay(this);
    startDay        = new StartDay(this);
    endDay          = new EndDay(this);
    feedAnimals     = new FeedAnimals(this);
    visitBank       = new VisitBank(this);
    doInventory     = new DoInventory(this);
    processDelivery = new ProcessDelivery(this);
    cleanStore      = new CleanStore(this);
    trainAnimals    = new TrainAnimals(this);
    openStore       = new OpenStore(this);
    endSimulation   = new EndSimulation(this);

    // states.add(startDay);
    // states.add(endDay);
    // states.add(feedAnimals);
    // states.add(visitBank);
    // states.add(checkRegister);
    // states.add(doInventory);
    // states.add(openStore);
    // states.add(cleanStore);
    // states.add(endSimulation);
  }


  // public ArrayList<State> getStates() {
  //   return (ArrayList<State>) states;
  // }


  /**
   * @param staffList
   * @return
   */
  synchronized Employee pickAvailableStaff(ArrayList<Employee> staffList) {
    SecureRandom rand   = new SecureRandom();
    boolean      isSick = rand.nextInt(100) < 10;
    // TEST #1: Add a check for noo employees.
    if (staffList.size() == 0) {
      initiateStaff();
    }
    Employee potentialStaff = staffList.get(rand.nextInt(3));
    if (potentialStaff.getWorkDays() <= 3) {
      if (isSick) {
        logger.info(potentialStaff.getNameExt() + " is feeling sick today. Selecting another staffer...");
        System.out.println(potentialStaff.getNameExt() + " is feeling sick today. Selecting another staffer...");
        return pickAvailableStaff(staffList);
      }

      /* Passing store info to the staff */
      potentialStaff.setInventory(this.inventory);
      potentialStaff.setSickPet(this.sick);
      potentialStaff.setMailBox(this.mailBox);
      potentialStaff.setCash(this.cash);
      potentialStaff.incWorkDays();
      potentialStaff.arriveAtStore();

      // update work days of the staffs
      for (Employee staff : staffList) {
        if (staff != potentialStaff) {
          staff.dayoff();
        } else {
          potentialStaff.incWorkDays();
        }
      }
      return potentialStaff;
    } else {
      // randomly selected staff unable to work
      return pickAvailableStaff(staffList);
    }
  }


  /**
   * Select staff to man store for this day.
   */
  synchronized public List<Employee> selectStaff() {
    currentClerk = pickAvailableStaff(clerks);
    currentClerk.setACTIVE(true);
    currentClerk.setTask(null);

    currentTrainer = pickAvailableStaff(trainers);
    currentTrainer.setACTIVE(true);
    currentTrainer.setTask(null);

    // employeeReceivers = new ArrayList<MessageReceiver>(Arrays.asList(currentClerk, currentTrainer));
    return Arrays.asList(currentClerk, currentTrainer);
  }


  /* Reference: http://en.wikipedia.org/wiki/Poisson_distribution#Generating_Poisson-distributed_random_variables */
  public int getPoissonValue(double mean) {
    SecureRandom r = new SecureRandom();
    double       L = Math.exp(-mean);
    int          k = 1;
    double       p = 1.0;
    do {
      p = p * r.nextDouble();
      ++k;
    } while (p > L);
    return k - 1;
  }


  public double addOnsHelper(ArrayList<String> addOns, int quantity, Item baseItem) {
    if (quantity < 0) {
      return baseItem.getSalePrice();
    }

    // adds decorators recursively until quantity is negative
    return switch (addOns.get(quantity)) {
      case "Insurance" -> addOnsHelper(addOns, quantity - 1, new Insurance(baseItem));
      case "Vet" -> addOnsHelper(addOns, quantity - 1, new VetCheckup(baseItem));
      case "Microchip" -> addOnsHelper(addOns, quantity - 1, new Microchip(baseItem));
      default -> 0;
    };

  }


  public double addRandomAddons(Item item) {
    ArrayList<String> addOns = new ArrayList<String>(Arrays.asList("Insurance", "Vet", "Microchip"));
    Collections.shuffle(addOns);
    return addOnsHelper(addOns, new SecureRandom().nextInt(3), item);
  }


  synchronized public void openStore() {
    double saleprice = 0;
    String print     = "";
    // Poisson distribution
    int count = attractCustomers(getPoissonValue(3.0));
    print = this.currentClerk.getNameExt() + " opens the store, " + this.name + ". \nCurrent inventory: " + this.inventory.size() + " item(s)\n Register: " + this.cash;
    System.out.println(print);
    logger.info(print);
    print = (count + " potential customers enter the store...");
    System.out.println(print);
    logger.info(print);
    for (Customer customer : this.customers) {
      boolean selecting = customer.inspectInventory(this.inventory);
      if (selecting) {
        this.inventory.remove(customer.obj);
        System.out.println("The customer has made a selection!");
        logger.info(
            "[+] The customer purchases " + customer.obj.getName() + " at $" + customer.getPurchasePrice() + (customer.discount ? " after a 10% discount"
                                                                                                                                : ""));
        System.out.println(
            "[+] The customer purchases " + customer.obj.getName() + " at $" + customer.getPurchasePrice() + (customer.discount ? " after a 10% discount"
                                                                                                                                : ""));
        if (customer.obj.isPet()) {
          double total = addRandomAddons(customer.obj);
          saleprice += total;
          System.out.println(" ++ $" + total);
        } else {
          System.out.println(" ++ $" + customer.getPurchasePrice());
          this.cash += customer.getPurchasePrice();
        }
        customer.obj.setDaySold(day);
        this.soldItems.add(customer.obj);

        // Tracker Log
        try {
          logger.info(TrackerManager.getInstance().add(currentClerk, customer.obj));
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
    currentClerk.earn(saleprice);
    this.cash += saleprice;

    System.out.println("Current inventory: " + this.inventory.size() + " item(s)\nCash: " + this.cash);
  }


  private int attractCustomers(int count) {
    customers.clear();
    for (int i = 0; i < count; i++) {
      customers.add(new Customer());
    }

    return count;
  }


  public void updateInventory() {
    this.inventory = currentClerk.getInventory();
  }


  public void updateSickAnimal() {
    this.sick = currentClerk.getSickAnimal();
  }


  public void updateMailBox() {
    this.mailBox = currentClerk.getMailBox();
  }


  public void updateCash() {
    this.cash = currentClerk.getCash();
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Getters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /**
   * the mailbox
   *
   * @return mailBox
   */
  public ArrayList<DeliveryPackage> getMailbox() {
    return this.mailBox;
  }


  public List<Pet> getAnimals() {
    List<Pet> list = new ArrayList<Pet>();
    inventory.forEach(item -> {
      if (item instanceof Pet) {
        list.add((Pet) item);
      }
    });
    return list;
  }


  public List<Supplies> getSupplies() {
    List<Supplies> list = new ArrayList<Supplies>();
    inventory.forEach(item -> {
      if (item instanceof Supplies) {
        list.add((Supplies) item);
      }
    });
    return list;
  }


  public List<Employee> getEmployees() {
    List<Employee> list = new ArrayList<Employee>();
    list.addAll(clerks);
    list.addAll(trainers);
    return list;
  }


  public double getCash() {
    return cash;

  }


  public double getBankWithdrawal() {
    return bankWithdrawal;
  }


  public boolean checkRegister() {
    currentClerk.checkRegister();
    return this.getCash() > 200;
  }


  public ArrayList<Item> getInventory() {
    return inventory;
  }


  public ArrayList<Pet> getSick() {
    return sick;
  }


  public ArrayList<Item> getSoldItems() {
    return this.soldItems;
  }


  public ArrayList<Customer> getCustomers() {
    return customers;
  }


  public static int getDay() {
    return day;
  }


  public Customer getCustomer(int ID) {
    for (Customer c : customers) {
      if (c.getID() == ID) {
        return c;
      }
    }
    return null;
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mutators ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public double goToBank(Employee employee) {
    employee.goToBank();
    // double value = employee.goToBank();
    addWithdrawal();
    return 1000;
  }


  private void addWithdrawal() {
    System.out.println("$" + (double) 1000 + " was withdrawn from the bank.\n");
    logger.info("$" + (double) 1000 + " was withdrawn from the bank.");
    this.cash += currentClerk.getCash();
    this.bankWithdrawal += 1000;
    logger.info("Total withdrawal: " + bankWithdrawal);
    logger.info("Total cash: " + cash);
    System.out.println("Total withdrawal: " + bankWithdrawal);
    System.out.println("Total cash: " + cash);
  }


  public void addCash(double cash) {
    this.cash = Double.parseDouble(sizeFormat.format(this.cash += cash));
    if (cash < 200) {
      System.out.println("$" + cash + " was removed from the register.");
    } else {
      System.out.println("$" + cash + " was added to the register.");
    }
  }


  public void resetDay() {
    day = 0;
  }


  // Temporary method to test the store
  public static void incrementDay() {
    day++;
  }


  public void closeStore() {
    logger.info(currentClerk.getNameExt() + " closes the store. \nCurrent inventory: " + inventory.size() + " item(s)\nRegister: " + cash);
    logger.info("\nCurrent inventory: " + inventory.size() + " item(s)\nCash: " + cash);
    System.out.println(currentClerk.getNameExt() + " closes the store. \nCurrent inventory: " + inventory.size() + " item(s)\nRegister: " + cash);
    System.out.println("\nCurrent inventory: " + inventory.size() + " item(s)\nCash: " + cash);
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ State Operations ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public void setStoreState(State state) {
    previousState = currentState;
    currentState  = state;
  }


  public State goStartDay() {
    return startDay;
  }


  public State goCheckRegister() {
    return checkRegister;
  }


  public State goVisitBankState() {
    return visitBank;
  }


  public State goFeedAnimals() {
    return feedAnimals;
  }


  public State goDoInventory() {
    return doInventory;
  }


  public State goTrainAnimals() {
    return trainAnimals;
  }


  public State goOpenStore() {
    return openStore;
  }


  public State goCleanStore() {
    return cleanStore;
  }


  public State goEndDay() {
    return endDay;
  }


  public void goEndSimulation() {
    currentState = endSimulation;
  }


  public void goEnterState(State state) throws InterruptedException {
    this.state = state;
    state.enterState();
  }


  private void timePasses() throws InterruptedException {
    if (day < 30) {
      if (state.getStatus() == COMPLETE) {
        if (newDay.getClass().equals(state.getClass())) {
          goEnterState(startDay);
        } else if (startDay.getClass().equals(state.getClass())) {
          goEnterState(processDelivery);
        } else if (processDelivery.getClass().equals(state.getClass())) {
          goEnterState(doInventory);
        } else if (doInventory.getClass().equals(state.getClass())) {
          goEnterState(feedAnimals);
        } else if (feedAnimals.getClass().equals(state.getClass())) {
          goEnterState(openStore);
        } else if (openStore.getClass().equals(state.getClass())) {
          goEnterState(trainAnimals);
        } else if (trainAnimals.getClass().equals(state.getClass())) {
          goEnterState(cleanStore);
        } else if (cleanStore.getClass().equals(state.getClass())) {
          goEnterState(endDay);
        } else if (endDay.getClass().equals(state.getClass())) {
          this.suspend();
        }
      } else {
        if (state.getStatus() == INCOMPLETE) {
          // state.observe();
          state.enterState();
        }
      }
      // incrementDay();
    } else {
      // goEndSimulation();
      goEnterState(new EndSimulation(this));

    }

  }


  public void observe() {
    this.state.observe();
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Receiver Overrides ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /**
   * Add Receiver.
   *
   * @param receiver the observer to add if it is not already added.
   */
  @Override
  public void addReceiver(MessageReceiver receiver) {
    //    assert observer != null;
    if (receiver != null) {
      if (receivers == null) {
        receivers = new ArrayList<MessageReceiver>(1);
      }
      if (!receivers.contains(receiver)) {
        if (receiver.getType() == ReceiverType.CLERK) {
          clerks.add((Clerk) receiver);
        } else if (receiver.getType() == ReceiverType.TRAINER) {
          trainers.add((Trainer) receiver);
        }
        receivers.add(receiver);
      }
    } else {
      throw new IllegalArgumentException("Observer cannot be null.");
    }

  }


  /**
   * Remove a single receiver.
   *
   * @param receiver the observer to be removed if it is registered.
   */
  @Override
  public void removeReceiver(MessageReceiver receiver) {
    if (receiver != null) {
      synchronized (this) {
        if (receivers != null && receivers.remove(receiver) && receivers.isEmpty()) {
          receivers = new ArrayList<>(1);
        }
      }
    }

  }


  /**
   * Notify <b>all</b> receivers of an event.
   *
   * @param argument the Runnable to run
   */
  @Override
  public void notifyReceivers(String message, Object argument) {
    ArrayList<MessageReceiver> receiversLocal;

    if (receivers != null) {
      synchronized (this) {
        receiversLocal = new ArrayList<MessageReceiver>(receivers);
      }
      for (MessageReceiver receiver : receiversLocal) {
        receiver.update(message, argument);
      }
    }
  }


  /**
   * Notify <b>all employee</b> receivers of an event.
   *
   * @param argument the Runnable to run
   * @return
   */
  @Override
  public long notifyEmployees(String message, Object argument) {
    ArrayList<MessageReceiver> receiversLocal;
    // byte                       callback = 0;
    // callback = Byte.parseByte((""));

    if (receivers != null) {
      synchronized (this) {
        receiversLocal = new ArrayList<MessageReceiver>(employeeReceivers);
      }
      for (MessageReceiver receiver : receiversLocal) {
        receiver.update(message, argument);
      }
    }
    // return callback;
    return 0;
  }


  /**
   * Notify <b>all customer</b> receivers of an event.
   *
   * @param argument the Runnable to run
   */
  @Override
  public void notifyCustomers(String message, Object argument) {
    ArrayList<MessageReceiver> receiversLocal;

    if (receivers != null) {
      synchronized (this) {
        receiversLocal = new ArrayList<MessageReceiver>(customerReceivers);
      }
      for (MessageReceiver receiver : receiversLocal) {
        receiver.update(message, argument);
      }
    }
  }


  /**
   * When an object implementing interface {@code Runnable} is used
   * to create a thread, starting the thread causes the object's
   * {@code run} method to be called in that separately executing
   * thread.
   * <p>
   * The general contract of the method {@code run} is that it may
   * take any action whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    logger.info("Simulation started on " + new Date() + " for" + this.name);
    while (!isKilled()) {
      try {
        timePasses();

        if (isSuspended()) {

          int counter = 0;
          for (var t : threads) {
            if (t.isSuspended()) {
              counter++;
            }
          }
          if (counter == threads.size()) {
            for (RunStateManager t2 : threads) {
              t2.resume();
            }
          }

          while (isSuspended()) {
            continue;
          }
          logger.info("Store: " + this.name + ". RESUMED");
          System.out.println("Store: " + this.name + ". RESUMED");
          goEnterState(newDay);

        }
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      } catch (RunStateException e) {
        throw new RuntimeException(e);
      }

    }
    if (isKilled()) {
      logger.info("Store: " + this.name + ". KILLED");
      System.out.println("Store: " + this.name + ". KILLED");
    }
  }


  @Override
  public synchronized void startSimulation() {
    // this.notify();
    try {
      // this.notify();
      while (!isSYNCED()) {
        wait();
      }
      super.startSimulation();
      this.runner.start();
    } catch (IllegalMonitorStateException | InterruptedException | RunStateException e1) {
      logger.info(e1.getMessage());
    }
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

}
