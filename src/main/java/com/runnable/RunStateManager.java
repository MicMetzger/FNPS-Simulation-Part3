package main.java.com.runnable;


import main.java.com.runnable.RunStateManager.RunStateException.Cause;



public abstract class RunStateManager implements Runnable {
  private RunState state;
  // static  RunStateException exMsg;
  // private boolean  isRunning = false;
  // private boolean  isPaused  = false;



  private enum RunState {
    INITIALIZED,
    ACTIVE,
    SUSPENDED,
    KILLED,
  }


  public RunStateManager() {
    this.state = RunState.INITIALIZED;
  }


  public RunStateManager(RunState state) {
    this.state = state;
  }

  // public abstract void run();


  /**
   * Part of the sequence that starts the thread.
   * Overrided by the store to start an instance thread.
   *
   * @throws RunStateException if the thread is not in the INITIALIZED state.
   */
  public void startSimulation() throws RunStateException {
    if (this.state == RunState.INITIALIZED) {
      this.state = RunState.ACTIVE;
    } else {
      throw new RunStateException("Cannot start a RunStateManager that is not in the INITIALIZED state.", Cause.ILLEGALSTATEEXCEPTION);
    }
  }


  public synchronized boolean isRunning() {
    return this.state == RunState.ACTIVE;
  }


  // public synchronized void pause() throws Exception {
  public synchronized void suspend() throws RunStateException {
    if (this.state == RunState.ACTIVE) {
      this.state = RunState.SUSPENDED;
    } else {
      throw new RunStateException("State: " + state.name(), Cause.ILLEGALSTATEEXCEPTION);
    }
  }


  public synchronized boolean isSuspended() {
    return this.state == RunState.SUSPENDED;
  }


  public synchronized void resume() throws RunStateException {
    if (this.state == RunState.SUSPENDED) {
      this.state = RunState.ACTIVE;
    } else {
      throw new RunStateException("RunStateManager is not suspended", Cause.ILLEGALSTATEEXCEPTION);
    }
  }


  public synchronized void kill() {
    this.state = RunState.KILLED;
  }


  public synchronized boolean isKilled() {
    return this.state == RunState.KILLED;
  }


  protected boolean isInitialized() {
    return this.state == RunState.INITIALIZED;
  }


  public static class RunStateException extends Exception {
    private Cause  cause;
    private String message;
    private Object object;


    public RunStateException(String message, Cause cause) {
      this.message = message;
      this.cause   = cause;
    }


    enum Cause {
      DEADLOCK("Deadlocked state"),
      ILLEGALSTATEEXCEPTION("Illegal transition"),
      // SYNC("Synchronization error"),
      UNHANDLEDSTATEREJECTION("Undefined cause");

      private final String message;


      Cause(String message) {
        this.message = message;
      }
    }


    public String getMessage() {
      // if (message == null) {
      //   return "Undefined cause";
      // } else {
      switch (cause) {
        case DEADLOCK: {
          return "Detected " + Cause.DEADLOCK.name() + " cause.\n" + "The state transition you try to do is not allowed.";
        }
        case ILLEGALSTATEEXCEPTION: {
          return "Detected " + Cause.ILLEGALSTATEEXCEPTION.name() + " cause.\n" + "The state transition you try to do is not allowed.";
        }
        case UNHANDLEDSTATEREJECTION: {
          return "Detected " + Cause.UNHANDLEDSTATEREJECTION.name() + " cause.\n" +
                 "The state transition you try to do is not allowed.";
        }
        default: {
          return "Undefined cause";
        }
      }

    }
  }
}


  
