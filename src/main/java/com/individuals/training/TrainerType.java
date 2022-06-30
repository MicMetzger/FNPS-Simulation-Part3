package main.java.com.individuals.training;


import main.java.com.item.Pet;



public enum TrainerType implements TrainerStrategy {
  Haphazard(new HaphazardReinforcement()),
  Negative(new NegativeReinforcement()),
  Positive(new PositiveReinforcement());

  private final TrainerStrategy strategy;

  TrainerType(TrainerStrategy strategy) {
    this.strategy = strategy;
  }


  @Override
  public boolean training(boolean houseBroken, Pet animal) {
    return strategy.training(houseBroken, animal);
  }
}