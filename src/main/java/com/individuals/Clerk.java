package main.java.com.individuals;


import static main.java.com.utilities.Builders.NAME_TEMPLATE;

import java.security.*;



public class Clerk extends Employee {
  protected String name = "";
  

  public Clerk(int workedDays, String name) {
    super(workedDays);
    this.name = name;
    super.setACTIVE(false);
    super.setState(EmployeeState.IDLE);
    super.setType(ReceiverType.CLERK);
  }

  public Clerk(String name) {
    super();
    this.name = name;
    super.setACTIVE(false);
    super.setState(EmployeeState.IDLE);
    super.setType(ReceiverType.CLERK);
  }
  
  public Clerk(Clerk clerk) {
    super(clerk);
    this.name = clerk.name;
    super.setACTIVE(false);
    super.setState(EmployeeState.IDLE);
    super.setType(ReceiverType.CLERK);
  }

  public Clerk() {
    super();
    int num = new SecureRandom().nextInt(NAME_TEMPLATE.size());
    this.name = NAME_TEMPLATE.get(num);
    NAME_TEMPLATE.remove(num);
    super.base = this;
    super.setACTIVE(false);
    super.setState(EmployeeState.IDLE);
    super.setType(ReceiverType.CLERK);
  }
  
  
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Individual Overrides ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  @Override
  public String getNameExt() {
    return name + ", the Clerk,";
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }
  
  @Override
  public String getNameSimple() {
    return name;
  }
}
