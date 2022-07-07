package main.java.com.Logging;


public class CloseEvent extends EventLog {
  private final String closeMsg;
  private final String closeMsgSender;
  
  
  public CloseEvent(String closeMsg, String closeMsgSender) {
    super(EventType.EVENT_CONNECTION_CLOSE);
    this.closeMsg       = closeMsg;
    this.closeMsgSender = closeMsgSender;
  }


  public String getMsg() {
    return closeMsg;
  }
  
  public String getCloseMsgSender() {
    return this.closeMsgSender;
  }
}
