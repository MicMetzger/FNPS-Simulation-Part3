package main.java.com.store;

import main.java.com.Logging.LoggerManager;



public class StoreApplication {
  public static void main(String[] args) throws InterruptedException {
    LoggerManager loggerManager = LoggerManager.getInstance();

    Store storeSim = new Store();
    storeSim.initiateStaff();
    storeSim.initiateAnimals();
    storeSim.initiateSupplies();
    storeSim.initStates();
    storeSim.StartSimulation();
  }
}
