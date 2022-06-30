package main.java.com.individuals;


import static main.java.com.utilities.Builders.NAME_TEMPLATE;

import java.util.*;
import main.java.com.individuals.training.*;
import main.java.com.item.*;



public class Trainer extends Employee {
  private String          name = "";
  private TrainerStrategy trainingType;


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public Trainer(String trainingAlgo) {
    super();
    super.base = this;
    super.setACTIVE(false);
    super.setState(EmployeeState.IDLE);
    super.setType(ReceiverType.TRAINER);

    int num = new Random().nextInt(NAME_TEMPLATE.size());
    this.name = NAME_TEMPLATE.get(num);
    NAME_TEMPLATE.remove(num);
    this.trainingType = setTrainingType(trainingAlgo);
  }

  /**
   * Copy constructor
   *
   * @param trainer the trainer to copy.
   */
  public Trainer(Trainer trainer) {
    super(trainer);
    this.name         = trainer.name;
    this.trainingType = trainer.trainingType;
    super.setACTIVE(false);
    super.setState(EmployeeState.IDLE);
    super.setType(ReceiverType.TRAINER);
  }

  /**
   * Trainer String constructor
   *
   * @param fArgs the string to parse.
   */
  public Trainer(String... fArgs) {
    super();
    if (fArgs.length > 2) {
      this.name         = fArgs[0];
      this.trainingType = setTrainingType(fArgs[1]);
    } else {
      int num = new Random().nextInt(NAME_TEMPLATE.size());
      this.name = NAME_TEMPLATE.get(num);
      NAME_TEMPLATE.remove(num);
    }

    super.setACTIVE(false);
    super.setState(EmployeeState.IDLE);
    super.setType(ReceiverType.TRAINER);
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public TrainerStrategy setTrainingType(String trainingAlgo) {
    switch (trainingAlgo) {
      case "Haphazard" -> {
        System.out.println("Positive Reinforcement has been assigned to " + this.name);
        return new PositiveReinforcement();
      }
      case "Negative" -> {
        System.out.println("Negative Reinforcement has been assigned to " + this.name);
        return new NegativeReinforcement();
      }
      case "Positive" -> {
        System.out.println("Haphazard has been assigned to " + this.name);
        return new HaphazardReinforcement();
      }
    }
    return null;
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


  public void startTraining() {
    boolean animalExists = false;
    for (Item animal : inventory) {
      if (animal.isPet()) {
        animalExists = true;
        break;
      }
    }
    String announcement =
        animalExists ? " trains the animals" : " checks the animals for training, but notices that the case is empty. Skipping training.";
    announce(announcement);
    for (Item animal : inventory) {
      if (animal.isPet()) {
        ((Pet) animal).setHealthy(this.train(((Pet) animal).isHealthy(), ((Pet) animal)));
      }
    }
  }

  public boolean train(boolean houseBroken, Pet animal) {
    return trainingType.training(houseBroken, animal);
  }


  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Individual Overrides ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  @Override
  public String getNameExt() {
    return name + ", the Trainer,";
  }

  @Override
  public String getNameSimple() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }
  /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

}
