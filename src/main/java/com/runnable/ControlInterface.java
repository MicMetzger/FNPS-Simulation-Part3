package main.java.com.runnable;

import static main.java.com.Logging.EventLog.EventType.EVENT_CONNECTION_CLOSE;

import main.java.com.Logging.EventLog;
import main.java.com.Logging.ILogQueue;
import main.java.com.Logging.ILogQueueMessenger;

import java.util.ArrayList;
import java.util.List;



/**
 * ControlInterface
 *
 * @Description: This class is the base class for all parallel instances.
 *     It provides the basic functionality for a parallel instance.
 * @link <a href="https://dzone.com/articles/producer-consumer-pattern">...</a>
 */
public class ControlInterface implements IInstanceConnection, ILogQueueMessenger {

  private static ControlInterface         controlInstance = null;
  private        ILogQueue                logQueue        = null;
  private final  List<InstanceConnection> connections;


  public static ControlInterface getInstance() {
    if (controlInstance == null) {controlInstance = new ControlInterface();}
    return controlInstance;
  }


  private ControlInterface() {
    connections = new ArrayList<InstanceConnection>();
  }


  public String toString() {
    return ("@" + Integer.toHexString(hashCode()));
  }


  /**
   * Add a listener to the control queue.
   * Then instantiate a new connection object and add it to the list of connections.
   *
   * @param connection The message to send.
   */
  @Override
  public void open(InstanceConnection connection) {
    connections.add(connection);
  }


  /**
   * Remove a listener from the control queue.
   * Then remove the connection object from the list of connections.
   * After which, if the list of connections is empty, send a close message to the log queue,
   * so that the log queue can close.
   *
   * @param connection The message to send.
   */
  @Override
  public void shutdown(InstanceConnection connection) {
    if (connections.remove(connection)) {
      if (connections.isEmpty()) {
        logQueue.add(EventLog.close());
      }
    }
  }


  @Override
  public void sendMsgVar(EventLog msgVar) {

  }


  @Override
  public void getMsgVar() {

  }


  /**
   * Add a listener to the control queue.
   *
   * @param logQueue The message to send.
   */
  @Override
  public void register(ILogQueue logQueue) {
    if (this.logQueue == null) {this.logQueue = logQueue;}
  }
}
