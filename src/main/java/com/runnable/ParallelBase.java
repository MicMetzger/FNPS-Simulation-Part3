package main.java.com.runnable;




// import main.java.com.Logging.LoggerManager.getLogger;
import main.java.com.Logging.LoggerManager;
import main.java.com.Logging.LoggerManager.Logger;



public abstract class ParallelBase extends RunStateManager {
  Logger logger;
  private static int ID = 0;
  private final int ID_TAG;
  private final String STORE_THREAD_NAME;
  
  public ParallelBase() {
    super();
    ID_TAG = ID++;
    STORE_THREAD_NAME = "PetStore-" + ID_TAG;
  }
  
  public ParallelBase(String name) {
    super();
    ID_TAG = ID++;
    STORE_THREAD_NAME = name;
    logger = LoggerManager.getInstance().getLogger(STORE_THREAD_NAME);
  }
  
  

  
  @Override
  public void start() throws RunStateException {
    try {
      super.start();
    } catch (RunStateException e) {
      logger.info(e.getMessage());
    }
  }
  
  @Override
  public void suspend() {
    try {
      super.suspend();
    } catch (RunStateException e) {
      logger.info(e.getMessage());
    }
  }
  
  @Override
  public void resume() throws RunStateException {
    try {
      super.resume();
    } catch (RunStateException e) {
      logger.info(e.getMessage());
    }
  }
  
  @Override
  public void kill() {
      super.kill();
      logger.info("Killed");
    
  }
}


