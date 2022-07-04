package main.java.com.events.task;

import static main.java.com.events.EventStatus.*;

import main.java.com.Logging.LoggerManager.Logger;
import main.java.com.utilities.Pair;
import java.util.*;
import main.java.com.Logging.*;
import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.store.*;



/**
 * End day. Completion of daily route.
 *
 * <p>Clean-up and preparation for sequence restart.
 */
public class EndDay implements State {
  Store                       state;
  EmployeeTask                task;
  EventStatus                 status;
  int                         Day;
  List<Employee>              employeeLog;
  List<Pair<Integer, Double>> employeeEarnings;
  Logger                      logger;


  public EndDay(Store store) {
    this.state  = store;
    this.status = INCOMPLETE;
    logger      = LoggerManager.getInstance().getLogger(this);

  }


  @Override
  public long enterState() {
    this.status = IN_PROGRESS;

    employeeLog      = new ArrayList<Employee>(state.getEmployees());
    employeeEarnings = new ArrayList<Pair<Integer, Double>>();
    employeeLog.forEach(employee -> employeeEarnings.add(new Pair<Integer, Double>(employee.getSold(), employee.getEarning())));
    // Logger.LOG(EventLog.tracking(employeeLog, employeeEarnings, state.day));

    System.out.println("\n##################################################");
    System.out.println("The workday comes to an end...");
    logger.info(EventLog.tracking(employeeLog, employeeEarnings, Store.getDay()));
    // TODO: 4
    // empty register and store cash in Store
    state.updateCash();
    this.status = COMPLETE;
    System.out.println("##################################################\n");
    return 0;
  }

  // stateMachine.setStoreState(stateMachine.goEndDay());


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
