package main.java.com.events.task;

import static main.java.com.events.EventStatus.COMPLETE;
import static main.java.com.events.EventStatus.INCOMPLETE;
import static main.java.com.events.EventStatus.IN_PROGRESS;

import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.store.*;
import main.java.com.utilities.*;



public class OpenStore implements State {
  Store        state;
  EmployeeTask task;
  EventStatus  status;

  public OpenStore(Store store) {
    this.state  = store;
    this.status = INCOMPLETE;
  }

  @Override
  public long enterState() {
    this.status = IN_PROGRESS;

    System.out.println("\n##################################################");
    state.openStore();
    state.updateCash();
    this.status = COMPLETE;

    System.out.println("##################################################\n");
    Utilities.gapTime();
    return 0;
  }
    // state.setStoreState(state.goCleanStore());


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
