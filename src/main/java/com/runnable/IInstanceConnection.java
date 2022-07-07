package main.java.com.runnable;


import main.java.com.Logging.EventLog;



public interface IInstanceConnection {
  void open(InstanceConnection conn);
  void shutdown(InstanceConnection conn);
  void sendMsgVar(EventLog msgVar);
  void getMsgVar();
}
