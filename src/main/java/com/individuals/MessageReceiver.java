package main.java.com.individuals;

public interface MessageReceiver {
  void sendMessage(String message);

  void update(String message, Object state);

  ReceiverType getType();

  void setType(ReceiverType type);
  
  enum ReceiverType {
    TRAINER,
    CLERK,
    Customer,
    Item,
    Pet,
    Register
  }
}
