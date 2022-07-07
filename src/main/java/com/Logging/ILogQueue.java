package main.java.com.Logging;


public interface ILogQueue {
  void add(EventLog msg);
  // EventLog getEvt();
  String getStr();
}
