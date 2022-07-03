package main.java.com.events.task;

import static main.java.com.events.EventStatus.*;

import java.util.*;
import main.java.com.Logging.*;
import main.java.com.Logging.LoggerManager.Logger;
import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.item.*;
import main.java.com.store.*;
import main.java.com.utilities.*;



/**
 * Start day.
 *
 * <p>Daily route starting point.
 */
public class StartDay implements State {
  Store          state;
  EventStatus    status;
  List<Employee> employees;
  List<Pet>      pets;
  List<Supplies> supplies;
  Logger         logger;

  public StartDay(Store store) {
    this.state  = store;
    this.status = INCOMPLETE;
    this.logger = LoggerManager.getInstance().getLogger(this.getClass());
  }

  @Override
  public long enterState() throws InterruptedException {
    this.status = IN_PROGRESS;

    employees = state.selectStaff();
    pets      = state.getAnimals();
    supplies  = state.getSupplies();

    System.out.println("\n#################################################");
    if (!state.checkRegister()) {
      System.out.println("Register cash is low... ");
      new VisitBank(state).enterState();
    } else {
      System.out.println("Cash is sufficient");
    }

    logger.info(EventLog.startDayEvent(employees, employees.size(), pets, pets.size(), supplies, supplies.size()));
    System.out.println("##################################################\n");
    Utilities.gapTime();
    this.status = COMPLETE;

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
