package main.java.com.events.task;

import static main.java.com.events.EventStatus.*;

import main.java.com.Logging.LoggerManager;
import main.java.com.Logging.LoggerManager.Logger;
import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.store.*;
import main.java.com.utilities.*;



public class TrainAnimals implements State {
  Store        state;
  EmployeeTask task;
  EventStatus status;
  Logger      logger;
  
  public TrainAnimals(Store store) {
    this.state  = store;
    this.status = INCOMPLETE;
    this.logger = LoggerManager.getInstance().getLogger(this);
  }

  @Override
  public long enterState() {
    this.status = IN_PROGRESS;

    System.out.println("\n##################################################");
    ((Trainer) state.currentTrainer).startTraining();
    this.status = COMPLETE;

    System.out.println("\n##################################################");
    Utilities.gapTime();
    return 0;
  }
    // state.setStoreState(state.goOpenStore());


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