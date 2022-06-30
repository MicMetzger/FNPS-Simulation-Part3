package main.java.com.events.task;

import static main.java.com.events.EventStatus.*;

import main.java.com.Logging.*;
import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.individuals.Employee.*;
import main.java.com.store.*;



public class VisitBank implements State {
  Store        storeState;
  EventStatus  status;
  EmployeeTask task;

  public VisitBank(Store store) {
    this.storeState = store;
    this.status     = INCOMPLETE;
  }

  /**
   * The type Banking.
   */
  class Banking extends EmployeeTask implements State {
    double   amount;
    double   cash;
    Employee employee;


    Banking(Employee employee, Store store, VisitBank visitBank) {
      super(TaskType.TASK_BANKING, INCOMPLETE, employee, visitBank);
      instance = store;
    }


    /**
     * Run the task.
     */
    @Override
    public void run() {
      if (status == INCOMPLETE) {
        status = IN_PROGRESS;
        super.statusChange(IN_PROGRESS);
        employee = this.getEmployee();
        amount   = storeState.goToBank(getEmployee());
        cash     = storeState.getCash();
        end();
      }
    }


    /**
     * End the task, and log the completion.
     */
    public void end() {
      super.statusChange(COMPLETE);

      Logger.LOG(EventLog.bankingEvent(employee, amount, cash));
      super.getStatus().setAssigned(false);
      getEmployee().setTask(null);
      getEmployee().setState(EmployeeState.IDLE);
    }

    @Override
    public void enterState() {

    }

    @Override
    public void exitState() {

    }

    @Override
    public void nextState() {

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
    public EventStatus setStatus(EventStatus status) {
      return null;
    }

  }

  @Override
  public void enterState() {
    storeState.notifyEmployees("VisitBank", task);
    exitState();
  }

  @Override
  public void exitState() {}

  @Override
  public void nextState() {

  }

  @Override
  public boolean hasTask() {
    return true;
  }

  @Override
  public EmployeeTask getTask() {
    return task = new Banking(null, storeState, this);
  }

  public EmployeeTask getTask(Employee employee) {
    return task = new Banking(employee, storeState, this);
  }

  @Override
  public EventStatus getStatus() {
    return status;
  }

  @Override
  public EventStatus setStatus(EventStatus status) {
    this.status = status;
    return status;
  }

}
