package main.java.com.events;

import main.java.com.individuals.*;



/**
 * @param <Sub>  Subject
 * @param <Obs>  Observer
 * @param <Argu> Argument type
 */
public interface EventObservable {

  void addReceiver(MessageReceiver receiver);

  void removeReceiver(MessageReceiver observer);

  void notifyReceivers(String message, Object argument);

}
