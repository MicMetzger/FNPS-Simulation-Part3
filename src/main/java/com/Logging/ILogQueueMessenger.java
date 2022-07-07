package main.java.com.Logging;


public interface ILogQueueMessenger {
  /**
   * Add a listener to the control queue.
   * @param logQueue The message to send.
   */
  void register(ILogQueue logQueue);
}
