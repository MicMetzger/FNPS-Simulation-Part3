package main.java.com.individuals.track;

import static main.java.com.utilities.Builders.get_The_Day;

import java.io.*;
import java.util.*;
import main.java.com.Logging.EventLog;
import main.java.com.individuals.*;
import main.java.com.item.Item;
import main.java.com.utilities.*;



public class TrackerManager {
  // Lazy instantiation of TrackerManager.
  private static         TrackerManager instance;
  private static         int            ID          = 0;
  protected static final int            MAX_TRAFFIC = 5;
  private static final   File           trackerFile = new File("tracker.txt");
  private static final   String         DELIMITER   = ",";
  private static final   String         NEW_LINE    = "\n";
  private static final   String         HEADER      = "Name,Items Sold,Purchase Price Total";

  // private Employee[] employees;   // array of employees from this simulation
  // private double     priceTotal;  // Pet history     
  // private int        itemSold;    // Item history
  HashMap<Employee, TrackerQueue> employeeHistory;
  // Map<Employee, LinkedList<Item>> employeeHistory;

  private TrackerManager() {
    // employeeHistory = new HashMap<Employee, Pair<Integer, Double>>();
    employeeHistory = new HashMap<Employee, TrackerQueue>();
  }

  public static TrackerManager getInstance() {
    if (instance == null) {
      instance = new TrackerManager();
    }
    return instance;
  }

  public String add(Employee employee, Item item) throws InterruptedException {
    if (employeeHistory.containsKey(employee)) {
      if (employee.hashCode() == employeeHistory.get(employee).hashCode()) {
        employeeHistory.get(employee).add(item);
      } else {
        employeeHistory.put(employee, new TrackerQueue(employee));
        employeeHistory.get(employee).add(item);
      }
      TrackerQueue history = employeeHistory.get(employee);
      history.add(item);
      return writeEntry(history);
    } else {
      TrackerQueue log = new TrackerQueue(employee, item);
      employeeHistory.put(employee, log);
      return writeEntry(log);
    }
  }
  
  public String writeEntry(TrackerQueue log) {
    return log.writeLast();
  }

  class TrackerQueue {
    private       Queue<EventLog>       queue;
    private       int                   id;
    private       int                   itemCount;
    private       double                priceTotal;
    private final Employee              employeeObj;
    private final int                   employeeLock;
    private       EventLog              eventLog;
    private       Pair<Integer, Double> earnings;

    TrackerQueue(Employee employee) {
      // queue     = new LinkedList<EventLog>();
      id                = ID++;
      itemCount         = 0;
      priceTotal        = 0;
      queue             = new LinkedList<EventLog>();
      this.employeeObj  = employee;
      this.employeeLock = employee.hashCode();
    }

    TrackerQueue(Employee employee, Item item) throws InterruptedException {
      // queue     = new LinkedList<EventLog>();
      id                = ++ID;
      itemCount         = 0;
      priceTotal        = 0;
      queue             = new LinkedList<EventLog>();
      this.employeeObj  = employee;
      this.employeeLock = employee.hashCode();
      add(item);
    }

    public synchronized void add(EventLog event) throws InterruptedException {
      // while (queue.size() >= MAX_TRAFFIC) {
      //   try {
      //     wait();
      //   } catch (InterruptedException e) {
      //     e.printStackTrace();
          queue.add(event);
          // TrackerManager.this.notifyAll();
        // }
      // }
    }

    public synchronized void add(Item item) throws InterruptedException {
      // while (queue.size() >= MAX_TRAFFIC) {
      //   try {
      //     wait();
      //   } catch (InterruptedException e) {
      //     e.printStackTrace();
          itemCount++;
          priceTotal += item.getSalePrice();
          earnings = (new Pair<Integer, Double>(itemCount, item.getSalePrice()));
          queue.add(EventLog.tracking(employeeObj, earnings, get_The_Day()));
          // TrackerManager.this.notifyAll();
      //   }
      // }
    }
    
    public String writeLast() {
      return queue.peek().toString();
    }

    public String writeAll() {
      StringBuilder sb = new StringBuilder();
      for (EventLog event : queue) {
        sb.append(event.toString());
      }
      return sb.toString();
    }

    @Override
    public int hashCode() {
      return employeeLock;
    }

    @Override
    public String toString() {
      return "@TrackerQueue_" + id;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final TrackerQueue other = (TrackerQueue) obj;
      return this.id == other.id;
    }


  }


}
