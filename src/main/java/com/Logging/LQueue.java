package main.java.com.Logging;


import java.util.LinkedList;
import java.util.Queue;



public class LQueue implements ILogQueue {
  private static int           SIZE = 0;
  private static int           MAX_COUNT = 20;
  private final  int           ID;
  private static Queue<String> lineup;
  
  
  public LQueue() {
    this.ID = ++LQueue.SIZE;
    lineup = new LinkedList<String>();
  }
  
  
  /**
   * This method add a command/event msg to the queue.
   * @param msg The message to add to the queue.
   */
  @Override
  public synchronized void add(EventLog msg) {
    while (lineup.size() >= MAX_COUNT) {
      try {
        wait();
      } catch (InterruptedException | IllegalStateException e) {
        e.printStackTrace();
      }
    }
    lineup.add(msg.toString());
    notifyAll();
  }


  // @Override
  // public synchronized EventLog getEvt() {
  //   while (lineup.isEmpty()) {
  //     try {
  //       wait();
  //     } catch (InterruptedException | IllegalStateException e) {
  //       e.printStackTrace();
  //     }
  //   }
  //   notifyAll();
  //   return new EventLog(lineup.remove());
  // }


  @Override
  public String getStr() {
      while (lineup.isEmpty()) {
        try {
          wait();
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        } catch (IllegalStateException e2) {
          e2.printStackTrace();
        }
      }
      notifyAll();
      return (lineup.remove());
  }
}
