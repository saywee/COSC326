import java.util.*;
public class Elevators {
    //The general statistics of the building.
    public static final int floors = 10;
    public static final int traveltime = 5;
    public static final int openclose = 10;

    
    public static void main(String[] args) {
        Boolean up = null;//rest state == null, so if up == null, elevator is resting
        Random rd = new Random();//to simulate a random location for the elevator.
        
        
        
        //Need to simulate time between presses
    }
    
    public class Person {
        //current floor Person is on
        private int cfloor;
        //floor person wants to go to
        private int dfloor;
        //how long person has been waiting
        private int waitTime;
        //direction of person
        private Boolean dir;
        public Person(int cfloor, int dfloor, Boolean dir) {
            this.cfloor = cfloor;
            this.dfloor = dfloor;
            waitTime = 0;
            this.dir = dir;
        }
        
        //get current floor of person
        public int currentFloor() {
            return cfloor;
        }
        
        //get desired floor of person
        public int desiredFloor() {
            return dfloor;
        }
        
        //how long the person has been waiting, determined by travel time in elevators.
        public int pWait() {
            return waitTime;
        }
        //method to increase the person's waiting time.
        public void incTimeWait() {
            
        }
    }
}
