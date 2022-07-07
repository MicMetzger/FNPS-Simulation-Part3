package main.java.com.store;

import main.java.com.Logging.ILogQueueMessenger;
import main.java.com.Logging.LQueue;
import main.java.com.Logging.LoggerManager;
import main.java.com.runnable.ControlInterface;
import main.java.com.runnable.IInstanceConnection;
import main.java.com.runnable.InstanceConnection;
import main.java.com.runnable.ParallelStance;
import main.java.com.runnable.RunStateManager.RunStateException;



public class StoreApplication {
  public static void main(String[] args) throws InterruptedException, RunStateException {
    LoggerManager       loggerManager        = LoggerManager.getInstance();
    IInstanceConnection connectionInterface1 = ControlInterface.getInstance();
    IInstanceConnection connectionInterface2 = ControlInterface.getInstance();
    ILogQueueMessenger  connectionPointer    = (ILogQueueMessenger) connectionInterface1;
    LQueue              lquePointer          = new LQueue();

    try {
      connectionPointer.register(lquePointer);
      connectionPointer = (ILogQueueMessenger) LoggerManager.getInstance();
      connectionPointer.register(lquePointer);
      lquePointer = null;
    } catch (Exception e) {
      System.out.println("Error opening connection: " + e.getMessage());
    }

    lquePointer = new LQueue();
    try {
      connectionPointer.register(lquePointer);
    } catch (Exception e) {
      System.out.println("Error opening connection: " + e.getMessage());
    }

    final ParallelStance northStore      = new Store("Northside FNPS", lquePointer);
    InstanceConnection   northConnection = new InstanceConnection(northStore, connectionInterface1);
    northStore.connect(northConnection);
    // northStore.startSimulation();
    // Thread north = new Thread(() -> {
    //   try {
    //     northStore.startSimulation();
    //     System.out.println("Northside FNPS is connected");
    //   } catch (Exception e) {
    //     e.printStackTrace();
    //   }
    // });
    
    new Thread(() -> {
      try {
        northStore.startSimulation();
        System.out.println("Northside FNPS is connected");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
    
    lquePointer = new LQueue();
    try {
      connectionPointer.register(lquePointer);
    } catch (Exception e) {
      System.out.println("Error opening connection: " + e.getMessage());
    }

    final ParallelStance southStore      = new Store("Southside FNPS", lquePointer);
    InstanceConnection   southConnection = new InstanceConnection(southStore, connectionInterface2);
    southStore.connect(southConnection);
    // southStore.startSimulation();
    // Thread south = new Thread(() -> {
    //   try {
    //     southStore.startSimulation();
    //     System.out.println("Southside FNPS is connected");
    //   } catch (Exception e) {
    //     e.printStackTrace();
    //   }
    // });
    
    new Thread(() -> {
      try {
        southStore.startSimulation();
        System.out.println("Southside FNPS is connected");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
    
    // southStore.run();
    // northStore.run();
    // north.start();
    // south.start();

  }
}
