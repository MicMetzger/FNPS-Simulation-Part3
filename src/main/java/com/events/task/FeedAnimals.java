package main.java.com.events.task;

import static main.java.com.events.EventStatus.COMPLETE;
import static main.java.com.events.EventStatus.INCOMPLETE;
import static main.java.com.events.EventStatus.IN_PROGRESS;

import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.store.*;
import utilities.*;



public class FeedAnimals implements State {
  Store state;

  EventStatus status;

  public FeedAnimals(Store store) {
    this.state  = store;
    this.status = INCOMPLETE;
  }

  @Override
  public void enterState() {
    this.status = IN_PROGRESS;

    System.out.println("\n##################################################");
    state.currentTrainer.feedAnimals();
    state.updateInventory();
    state.updateSickAnimal();
    // updateCash();
    nextState();
  }

  @Override
  public void exitState() {
    this.status = COMPLETE;

    System.out.println("##################################################\n");
    Utilities.gapTime();
    state.goEnterState();
  }

  @Override
  public void nextState() {
    state.setStoreState(state.goDoInventory());
    exitState();
  }

  @Override
  public boolean hasTask() {
    return false;
  }

  @Override
  public EmployeeTask getTask() {
    return null;
  }

  @Override
  public EmployeeTask getTask(Employee employee) {
    return null;
  }

  @Override
  public EventStatus getStatus() {
    return null;
  }

  @Override
  public EventStatus setStatus(EventStatus status) {
    return null;
  }

  public void update(Object message) {
  }
}
