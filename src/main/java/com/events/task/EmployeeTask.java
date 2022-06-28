package main.java.com.events.task;

import main.java.com.events.*;
import main.java.com.individuals.*;
import main.java.com.store.*;



public abstract class EmployeeTask {
  private   TaskType    taskType;
  protected EventStatus eventStatus;
  private   Employee    employee;
  private   State       eventInstance;
  public    Store       instance;

  // private State getEventInstance() {
  //   return eventInstance;
  // }

  // private void setEventInstance(State eventInstance) {
  //   this.eventInstance = eventInstance;
  // }

  public EmployeeTask(TaskType taskType, State eventInstance) {
    this.taskType      = taskType;
    this.eventStatus   = EventStatus.INCOMPLETE;
    this.employee      = null;
    this.eventInstance = eventInstance;
  }

  public EmployeeTask(TaskType taskType, EventStatus eventStatus, Employee employee, State eventInstance) {
    this.taskType      = taskType;
    this.eventStatus   = eventStatus;
    this.employee      = employee;
    this.eventInstance = eventInstance;
  }

  /**
   * Sets the employee to carry out the task.
   *
   * @param employee the employee
   */
  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  /**
   * Gets employee carrying out the task.
   *
   * @return the employee
   */
  public Employee getEmployee() {
    return employee;
  }

  /**
   * Gets employee task.
   *
   * @return the employee task
   */
  public EmployeeTask getEmployeeTask() {
    return this;
  }

  /**
   * The task type selector that also works as a header for the employee event logging.
   */
  public enum TaskType {
    TASK_SELLING("Selling"),
    TASK_OPENING("Opening"),
    TASK_CLOSING("Closing"),
    TASK_PROCESSING("Processing"),
    TASK_INVENTORY("Inventory"),
    TASK_TRAINING("Training"),
    TASK_CLEANING("Cleaning"),
    TASK_BANKING("Banking"),
    TASK_FEEDING("Feeding");

    private final String name;

    public String taskname() {
      return name;
    }

    TaskType(String name) {
      this.name = name;
    }
  }


  /**
   * Gets task type.
   *
   * @return the task type
   */
  public TaskType getTaskType() {
    return taskType;
  }


  /**
   * Status change.
   *
   * @param status the status
   */
  protected void statusChange(EventStatus status) {
    switch (status) {
      case INCOMPLETE -> this.eventStatus = EventStatus.IN_PROGRESS;
      case IN_PROGRESS -> this.eventStatus = EventStatus.COMPLETE;
    }
  }

  public EventStatus getStatus() {
    return eventStatus;
  }


  abstract public void run();
}
