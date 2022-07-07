package main.java.com.runnable;


import main.java.com.Logging.EventLog;
import main.java.com.runnable.RunStateManager.RunStateException;
import main.java.com.runnable.RunStateManager.RunStateException.Cause;



/**
 * ParallelStance
 *
 * @Description: This class is used to manage the state of a parallel base.
 *     This is a part of the producer-consumer design pattern.
 *     The parallel base is a thread that is used to send messages to the consumer.
 *     The consumer is a thread that is used to receive messages from the parallel base.
 *
 * @link <a href="https://dzone.com/articles/producer-consumer-pattern">...</a>
 */
public class InstanceConnection {
  protected    InstanceState       state;
  public final ParallelStance      sendConnection;
  // public final ParallelBase        sendConnection;
  public final IInstanceConnection recConnection;
  // public final IInstanceConnection recConnection;



  protected enum InstanceState {
    INITIALIZED,
    ACTIVE,
    // OPEN,
    SHUTDOWN,
  }


  public InstanceConnection(ParallelStance sendConnection, IInstanceConnection recConnection) {
    this.state          = InstanceState.INITIALIZED;
    this.recConnection  = recConnection;
    this.sendConnection = sendConnection;
  }


  public void open() throws RunStateException {
    if (!this.isActive()) {
      if (this.state == InstanceState.INITIALIZED) {
        this.state = InstanceState.ACTIVE;
        recConnection.open(this);
      } else {
        throw new RunStateException("Cannot open a InstanceConnection that is not in the INITIALIZED state.", Cause.ILLEGALSTATEEXCEPTION);
      }
    }

  }


  public void shutdown() throws RunStateException {
    if (this.state == InstanceState.ACTIVE) {
      this.state = InstanceState.SHUTDOWN;
      recConnection.shutdown(this);
    } else {
      throw new RunStateException("Cannot shutdown a InstanceConnection that is not in the ACTIVE state.", Cause.ILLEGALSTATEEXCEPTION);
    }
  }


  protected boolean isActive() {
    return this.state == InstanceState.ACTIVE;
  }

  // public boolean isOpen() {
  //   return this.state == InstanceState.OPEN;
  // }


  protected boolean isClosed() {
    return this.state == InstanceState.SHUTDOWN;
  }


  public void sendMsg(EventLog msgVar) throws RunStateException {
    if (isActive()) {
      this.recConnection.sendMsgVar(msgVar);
    } else {
      throw new RunStateException("Cannot send a message to a InstanceConnection when not in the OPEN state.", Cause.ILLEGALSTATEEXCEPTION);
    }
  }


  public void getMsg() {
    this.recConnection.getMsgVar();
  }
}
