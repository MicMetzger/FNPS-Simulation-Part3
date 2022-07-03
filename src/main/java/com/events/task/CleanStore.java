package main.java.com.events.task;

import static main.java.com.events.EventStatus.COMPLETE;
import static main.java.com.events.EventStatus.INCOMPLETE;
import static main.java.com.events.EventStatus.IN_PROGRESS;

import main.java.com.Logging.LoggerManager;
import main.java.com.Logging.LoggerManager.Logger;
import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.store.*;
import main.java.com.utilities.*;



public class CleanStore implements State {
  Store        state;
  EmployeeTask task;
  EventStatus status;
  Logger      logger;


  public CleanStore(Store store) {
    this.state  = store;
    this.status = INCOMPLETE;
    logger          = LoggerManager.getInstance().getLogger(this.getClass().getCanonicalName());
  }


  @Override
  public long enterState() throws InterruptedException {
    this.status = IN_PROGRESS;

    System.out.println("\n##################################################");
    state.currentClerk.cleanStore();
    state.updateInventory();
    state.updateSickAnimal();
    System.out.println("##################################################\n");
    this.status = COMPLETE;
    // state.goEnterState(new EndDay(state));
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


  public void update(Object message) {
  }

}
