package main.java.com.individuals;

import main.java.com.individuals.MessageReceiver.*;
import main.java.com.utilities.*;



public class EmployeePool extends ObjectPool<Employee, ReceiverType, String> {
  @Override
  public Employee createObject(ReceiverType type, String argument) {
    switch (type) {
      case TRAINER -> {return new Trainer(argument);}
      case CLERK -> {return new Clerk();}
      default -> {return null;}
    }
  }
}
