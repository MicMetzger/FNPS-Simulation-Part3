package main.java.com.runnable;


import main.java.com.runnable.RunStateManager.RunStateException.Cause;



public abstract class RunStateManager implements Runnable {
  private RunState state;
  RunStateException exception;
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


  public void start() throws RunStateException {
    if (this.state == RunState.INITIALIZED) {
      this.state = RunState.ACTIVE;
      new Thread(this).start();
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


  public synchronized boolean isPaused() {
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


  class RunStateException extends Exception {
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
      if (exception.message == null) {
        return "Undefined cause";
      } else {
        return switch (cause) {
          case DEADLOCK -> "Detected " + Cause.DEADLOCK.name() + " cause.\n" +
                           "The state transition you try to do is not allowed.";
          case ILLEGALSTATEEXCEPTION -> "Detected " + Cause.ILLEGALSTATEEXCEPTION.name() + " cause.\n" +
                                        "The state transition you try to do is not allowed.";
          case UNHANDLEDSTATEREJECTION -> "Detected " + Cause.UNHANDLEDSTATEREJECTION.name() + " cause.\n" +
                                          "The state transition you try to do is not allowed.";
        };
      }
    }
  }
}

  
