package main.java.com.runnable;

// import main.java.com.Logging.LoggerManager.getLogger;
// import java.net.ConnectException;
import main.java.com.Logging.EventLog;
import main.java.com.Logging.ILogQueue;
import main.java.com.Logging.LoggerManager;
import main.java.com.Logging.LoggerManager.Logger;



public abstract class ParallelStance extends RunStateManager {
  Logger logger;
  private static int                ID = 0;
  private final  int                ID_TAG;
  private final  String             STORE_THREAD_NAME;
  private        InstanceConnection connection;


  public ParallelStance() {
    super();
    ID_TAG            = ID++;
    STORE_THREAD_NAME = "PetStore-" + ID_TAG;
    connection        = null;
    // logger            = LoggerManager.getInstance().getLogger(STORE_THREAD_NAME);
  }
  

  public ParallelStance(String name, ILogQueue logQueue) {
    super();
    ID_TAG            = ID++;
    STORE_THREAD_NAME = name;
    connection        = null;
    // logger            = LoggerManager.getInstance().getLogger(STORE_THREAD_NAME);
  }


  /**
   * String Constructor
   * Provides the ID of a parallel base
   */
  public ParallelStance(String name) {
    super();
    ID_TAG            = ID++;
    STORE_THREAD_NAME = name;
    connection        = null;
    // logger            = LoggerManager.getInstance().getLogger(STORE_THREAD_NAME);
  }


  /**
   * Checks if the thread is running,
   * the thread is open for sending messages,
   *
   * @return true if the thread is running, open for sending messages, false otherwise.   *
   */
  protected boolean isSYNCED() {
    if (connection == null) {
      return false;
    } else {
      return connection.isActive();
    }
  }

  public synchronized final void connect(InstanceConnection connection) {
    // this.connection = connection;
    try {
      connection.open();
      this.connection = connection;
    } catch (RunStateException e) {
      // logger.error("Cannot open connection to " + connection.getStore().getName());
    }
    notify();
  }

  @Override
  public void startSimulation() throws RunStateException {
    try {
      super.startSimulation();
    } catch (RunStateException e) {
      logger.info(e.getMessage());
      System.out.println(e.getMessage());
    }
  }


  @Override
  public void suspend() {
    try {
      super.suspend();
    } catch (RunStateException e) {
      // logger.info(e.getMessage());
      System.out.println(e.getMessage());
    }
  }


  @Override
  public void resume() throws RunStateException {
    try {
      super.resume();
    } catch (RunStateException e) {
      // logger.info(e.getMessage());
      System.out.println(e.getMessage());
    }
  }


  @Override
  public void kill() {
    super.kill();
    logger.info("Killed");

  }


  public final void sendEvent(EventLog event) {
    if (connection != null) {
      try {
        connection.sendMsg(event);
      } catch (RunStateException e) {
        logger.info(e.getMessage());
        System.out.println(e.getMessage());
      }
    } else {
      logger.info("No connection to send message to");
      System.out.println("No connection to send message to");
    }
  }
}


