package main.java.com.events.task;

import static main.java.com.events.EventStatus.*;

import main.java.com.Logging.*;
import main.java.com.Logging.LoggerManager.ILogger;
import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.individuals.Employee.*;
import main.java.com.store.*;



public class VisitBank implements State, Runnable {
  Store        storeState;
  EventStatus  status;
  EmployeeTask task;


  public VisitBank(Store store) {
    this.storeState = store;
    this.status     = INCOMPLETE;
  }


  /**
   * When an object implementing interface {@code Runnable} is used to create a thread, starting the thread causes the object's {@code run} method to be
   * called in that separately executing thread.
   * <p>
   * The general contract of the method {@code run} is that it may take any action whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {

  }


  /**
   * The type Banking.
   */
  class Banking extends EmployeeTask implements State {
    double      amount;
    double      cash;
    Employee    employee;
    EventStatus status;
    ILogger     logger;


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
        // TODO: FIX THIS
        amount = storeState.goToBank(getEmployee());
        cash   = storeState.getCash();
        end();
      }
    }


    /**
     * End the task, and log the completion.
     */
    public void end() {
      super.statusChange(COMPLETE);
      logger.info(EventLog.bankingEvent(employee, amount, cash));
      super.getStatus().setAssigned(false);
      getEmployee().setTask(null);
      getEmployee().setState(EmployeeState.IDLE);
      // notify();
    }


    @Override
    public long enterState() {

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
    public void setStatus(EventStatus status) {
      this.status = status;
    }

  }


  @Override
  public long enterState() throws InterruptedException {
    storeState.notifyEmployees("VisitBank", task);

    return 0;
  }


  @Override
  public boolean hasTask() {
    return false;
  }


  @Override
  public void observe() {

  }


  public EmployeeTask getTask(Employee employee) {
    return task = new Banking(employee, storeState, this);
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
