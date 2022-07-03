package main.java.com.events.task;

import static main.java.com.events.EventStatus.*;

import java.io.*;
import main.java.com.Logging.*;
import main.java.com.Logging.LoggerManager.ILogger;
import main.java.com.events.*;
import main.java.com.individuals.Employee;
import main.java.com.store.*;



public class EndSimulation implements State {
  Store       state;
  EventStatus status;
  ILogger     logger;


  public EndSimulation(Store store) {
    this.state  = store;
    this.status = INCOMPLETE;
  }


  @Override
  public long enterState() {
    this.status = IN_PROGRESS;

    System.out.println(
        "\n\n_______________ STATS _______________\n" + "Total Cash: $" + state.getCash() + "\n" + "Total withdrawal: $" + state.getBankWithdrawal() + "\n"
        + "_______________________________________________" + "\n\n" + "\n\n_______________ Items Sold _______________" + "\n");
    state.getSoldItems().forEach(item -> System.out.println(item.getName() + " $" + item.getSalePrice() + ", Sold on: DAY " + item.getDaySold()));
    System.out.println("_______________________________________________");

    // System.out.println("\n\n_______________ Animals _______________");
    // state.getAnimals().forEach(animal -> System.out.println(animal.getName() + ", Value: $" + animal.getSalePrice()));
    // System.out.println("_______________________________________________");

    System.out.println("\n\n_______________ Employees _______________");
    state.getEmployees().forEach(employee -> System.out.println(employee.getNameSimple() + " Earning: $" + employee.getEarning()/* + employee.() */));
    System.out.println("_______________________________________________");

    System.out.println("\n\n_______________ Remaining Items _______________");
    state.getInventory().forEach(item -> System.out.println(item.getName() + ", Value: $" + item.getListPrice()));
    System.out.println("_______________________________________________");

    System.out.println("\n\n____________ Remaining Sick Animals ___________");
    state.getSick().forEach(item -> System.out.println(item.getName() + ", Value: $" + item.getListPrice()));
    System.out.println("_______________________________________________");

    this.status = COMPLETE;
    System.out.println("\n\n ~Fin~");
    try {
      LoggerManager.SAVE();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.exit(0);
    return 0;
  }


  @Override
  public boolean hasTask() {
    return false;
  }


  @Override
  public EventStatus getStatus() {
    return status;
  }


  @Override
  public void setStatus(EventStatus status) {
    this.status = status;
  }


  @Override
  public void observe() {

  }


  @Override
  public EmployeeTask getTask(Employee employee) {
    return null;
  }


}
