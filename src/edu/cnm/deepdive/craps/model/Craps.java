package edu.cnm.deepdive.craps.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Craps {

  private State state = State.COME_OUT;
  private Random rng = new Random();
  private List<int[]> rolls = new LinkedList<>();

  public List<int[]> getRolls(){
    List<int[]> copy = new LinkedList<>(rolls);
    return copy;
  }

  protected void reset() {
    state = state.COME_OUT;
    rolls.clear();
  }

  public State play(){
    reset();
    do {
      roll();
    }while(state == State.POINT);
    return state;
  }

  protected int roll(){
    int[] dice = {
      1 + rng.nextInt(6),
      1 + rng.nextInt(6),
    };
    int sum = dice[0] + dice[1];
    rolls.add(dice);
    state = state.roll(sum); //sum to roll, roll from state
    return sum;
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
              return LOSS; //don't need break since it return
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
