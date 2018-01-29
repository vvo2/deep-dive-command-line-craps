package edu.cnm.deepdive;

import java.util.Random;

public class Craps {

  private int plays = 0;
  private int wins = 0;
  private State state = State.COME_OUT;
  private Random rng = new Random();

  public int getPlays() {
    return plays;
  }

  public int getWins() {
    return wins;
  }

  public State getState() {
    return state;
  }

  public void reset() {
    wins = 0;
    plays = 0;
    state = state.COME_OUT;
  }

  public int roll(){
    int sum = rng.nextInt(6) + rng.nextInt(6) + 2;
    state = state.roll(sum); //sum to roll, roll from state
    if (state == State.WIN){
      wins++;
    }
    return sum;
  }

  public void play(){
    state = State.COME_OUT;
    plays++;
  }

  public enum State { //enum is a static class
    COME_OUT, WIN, LOSS, POINT, TERMINAL; //constant are UPPER CASE
    //name of enum are default strings
    private int point = 0;

    public State roll(int diceSum){ //this return a State for enum
      switch (this){ //this refer to the instance running
        case COME_OUT:
          switch (diceSum){
            case 2:
            case 3:
            case 12:
              return LOSS; //dont need break since it return
            case 7:
            case 11:
              return WIN;
            default:
              POINT.point = diceSum; //assign point instance to remember diceSum
              return POINT;
          }
        case POINT:
          if (diceSum == point){
            return WIN;
          } else if (diceSum == 7){
            return LOSS;
          } else {
            return this;
          }
        default:
          return this;
      }
    }
   public State playAgain() {
      if (this == WIN || this == LOSS){
        return COME_OUT;
      } else {
        return this;
      }
   }

   public  State surrender(){
      if (this != COME_OUT) {
        return TERMINAL;
      } else {
        return this;
      }
   }
  }

}
