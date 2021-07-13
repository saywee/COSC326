import java.util.*;
import java.lang.*;

public class Harmonious{
  HashMap<Integer, Integer> harm = new HashMap<Integer, Integer>(2000000);
  public static void main(String[]args){
    Harmonious h = new Harmonious();
    h.getFirst();
  }
  
  public void getFirst(){
    for(int i=1; i<=2000000; i++){
      int num1 = divisors(i);
      int num2 = divisors(num1);
      
      if(num1!=num2 && num2==i && !harm.containsKey(i) && !harm.containsKey(num1)){
        harm.put(i, num1);
        harm.put(num1, num2);
        System.out.println(num2 + " " + num1);
      }
    }
  }
  
  public int divisors(int n){
    int sum = 0;
    for(int i=2; i<Math.sqrt(n); i++){
      if(n%i==0){
        sum+=i;
      }
    }
    return sum;
  }
}