package main.java.com.events.task;

import main.java.com.events.*;
import main.java.com.individuals.*;



public interface State {
  enum StateStatus {
    INCOMPLETE,
    IN_PROGRESS,
    COMPLETE
  }
  long enterState() throws InterruptedException;
  boolean hasTask();
  void observe();
  EmployeeTask getTask(Employee employee);
  EventStatus getStatus();
  void setStatus(EventStatus status);
}
