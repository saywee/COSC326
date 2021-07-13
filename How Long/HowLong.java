import java.util.*;

public class HowLong{
    public ArrayList<String> rules = new ArrayList<String>();
    public ArrayList<Character> colours = new ArrayList<Character>();
    public ArrayList<Integer> lengths = new ArrayList<Integer>();
    public ArrayList<String> combos = new ArrayList<String>();

    public static void main(String[]args){
        HowLong e = new HowLong();
        e.getLines();
    }


    public void getLines(){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.trim().isEmpty() && rules.size()>0){
                getCos();
                print();
                rules.clear();
                colours.clear();
                lengths.clear();
                combos.clear();
                System.out.println();
            }else{
                rules.add(line);
            }
        }
    }

    public void getCos(){
        String[] rule = null;
        for(int i=0; i<rules.size(); i++){
            rule = rules.get(i).split(" ");
            colours.add(rule[0].charAt(0));

            try{
                lengths.add(Integer.parseInt(rule[1]));
                combos.add("NaN");
            }catch(NumberFormatException e){
                combos.add(rule[1]);
                lengths.add(0);
            }
        }
        //System.out.print(rule[0] + " " + rule[1]);
        for(int h=0; h<2; h++){
            for(int j=0; j<combos.size(); j++){
                if(!combos.get(j).equals("NaN")){
                    updateLength(j, combos.get(j));
                }
            }
        }
    }

    public void updateLength(int ind, String s){
        int length = 0;
        // System.out.println("colours " + colours.size() + " combos " + combos.size() + " lengths " + lengths.size() + " rules " + rules.size());
        //      for(String rule : rules){
        //        System.out.println(rule);
        //      }
        for(int i=0; i<s.length(); i++){
            if(colours.contains(s.charAt(i))){
                if(lengths.get(colours.indexOf(s.charAt(i))) != 0){
                    length += lengths.get(colours.indexOf(s.charAt(i)));
                }
            }else{
                combos.set(ind, "Nil");
            }
        }
        lengths.set(ind, length);
    }
    
    public boolean circChk(int ind, String comb){
       for(int j=0; j<combos.size(); j++){
        if(combos.get(j)!=null && combos.get(j).length()==comb.length()){
          System.out.println(combos.get(j) + " " + colours.get(j));
          for(int i=0; i<comb.length(); i++){
            if(colours.contains(comb.charAt(i))){
              return true;
            }
          }
        }
      }
      return false;
    }

    public void print(){
        //System.out.println("colours " + colours.size() + " combos " + combos.size() + " lengths " + lengths.size());
        for(int i=0; i<colours.size(); i++){
            if(!combos.get(i).equals("Nil")){
                System.out.println(colours.get(i) + " " + lengths.get(i));
            }else{
                System.out.println(colours.get(i) + " NaN");
            }
        }
    }
}
