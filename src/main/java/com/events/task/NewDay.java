package main.java.com.events.task;

import static main.java.com.events.EventStatus.*;

import java.util.*;
import java.util.logging.Logger;
import main.java.com.Logging.*;
import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.store.*;
import main.java.com.utilities.*;



public class NewDay implements State {
  Store                       state;
  EventStatus status;
  Logger      logger;
  

  public NewDay(Store store) {
    this.state  = store;
    this.status = INCOMPLETE;
  }

  @Override
  public long enterState() {
    this.status = IN_PROGRESS;
    Store.incrementDay();

    System.out.println("\n**************************************************");
    // if (state.getDay() == 30) {
    //   state.goEndSimulation();
    //   exitState();
    // }
    System.out.println("Day: " + Store.getDay());
    // logger.info(EventLog.newDayEvent(Store.getDay()).toString());
    this.status = COMPLETE;
    System.out.println("**************************************************\n");
    Utilities.gapTime();
    return 0;
  }


  @Override
  public boolean hasTask() {
    return false;
  }

  @Override
  public void observe() {

  }

  @Override
  public EmployeeTask getTask(Employee employee) {
    return null;
  }

  @Override
  public EventStatus getStatus() {
    return status;
  }

  @Override
  public void setStatus(EventStatus status) {
    this.status = status;
  }

}
