package main.java.com.store;

public class StoreApplication {
  public static void main(String[] args) throws InterruptedException {
    Store storeSim = new Store();
    storeSim.initiateStaff();
    storeSim.initiateAnimals();
    storeSim.initiateSupplies();
    storeSim.initStates();
    storeSim.StartSimulation();
  }
}
