package main.java.com.events;
import main.java.com.individuals.*;



/**
 *
 */
public interface EventObservable {

  void addReceiver(MessageReceiver receiver);

  void removeReceiver(MessageReceiver observer);

  void notifyReceivers(String message, Object argument);

  void notifyEmployees(String message, Object argument);

  void notifyCustomers(String message, Object argument);

}
