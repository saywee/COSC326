import java.util.*;

public class Dates{
    ArrayList<String> months = new ArrayList<String>();
    ArrayList<String> inputDate = new ArrayList<String>();
    int day = 0;
    int month = 0;
    String m;
    int year = 0;
    boolean leap = false;
    String message = "";
    String[] dt = null;

    public static void main(String[]args){
        Dates d = new Dates();
        d.getDates();
    }

    public Dates(){
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");
    }

    public void getDates(){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String date = sc.nextLine();
            if(date.trim().isEmpty()){
                System.out.println();
            }else{
                inputDate.add(date);
                getDate();
                message = "";
                day = 0;
                //year = 0;
                month = 0;
                leap=false;
                dt = null;
                inputDate.clear();
            }
        }
    }
    private void getDate(){
        for(String input : inputDate){
            valiDate(input);
        }
    }

    private void valiDate(String d){
      if(d.charAt(d.length()-1)=='/' || d.charAt(d.length()-1)=='-' || d.charAt(d.length()-1)==' '){
        message = "Incorrect input. extra '/', 'space', or '-'";
      }
      
      if(d.contains(" ")){
        dt=d.split(" ");
      }else if(d.contains("-")){
        dt=d.split("-");
      }else if(d.contains("/")){
        dt=d.split("/");
      }else{
        dt = d.split(" ");
      }
      if(dt.length == 1){
        message = "Incorrect number of inputs.";
        System.out.println(String.format("%s - INVALID: %s", inputDate.get(0), message));
      }else if(dt.length == 2){
        message = "Incorrect number of inputs.";
        System.out.println(String.format("%s - INVALID: %s", inputDate.get(0), message));
      }else if(dt.length == 3){
        //check if year is valid and if it is a leap year
        valiYear(dt[2]);
        //check if month is valid.
        valiMonth(dt[1]);
        
        //check if day is valid.
        validae(dt[0]);
        
      }else{
        message = "Incorrect number of inputs.";
        System.out.print(inputDate.get(0) + " ");
        System.out.println(String.format("- INVALID: %s", message));
      }
    }
    /* a method that checks for the month input if it is a numerical
     * input or in word form.
     * if in word form, it will be checked if months arrayList contains
     * the given word, and updates the month variable accordingly.
     *
     * @param mo is the month to be checked.
     */
    private void valiMonth(String mo){
        try{
            if(mo.length() > 2 && isNumeric(mo)){
                message = "Incorrect number format for month";
            }else{
                month = Integer.parseInt(mo);
            }
        }catch(NumberFormatException e){
            for(int i=0; i<months.size(); i++){
                if(months.get(i).toLowerCase().equals(mo) || months.get(i).equals(mo) || months.get(i).toUpperCase().equals(mo)){
                    month = i+1;
                    break;
                }else{
                    month = -1;
                }
            }
            
            if(month == -1){
                message = "Invalid month. Month not found.";
            }
        }
        if(month<1 || month>12){
            message = "month out of range.";
        }
    }

    /* a method that checks the day input is a numerical number.
     *  if input is accepted, it will be checked if it is within the range
     * depending on the associated month, which will then be printed out
     * depending if there is an error or not.
     *
     * @param da is the day to be checked.
     */
    private void validae(String da){
        try{
          if(da.length()>2 && isNumeric(da)){
            message = "Incorrect date format";
          }else{
            day = Integer.parseInt(da);
          }
        }catch(NumberFormatException e){
            message = "Date has to be a number";
        }
        if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){
            if(day<1 || day>31){
                message = "Day out of range";
            }
        }else if(month==4 || month==6 || month==9 || month==11){
            if(day<1 || day>30){
                message = "Day out of range";
            }
        }else if(month==2){
            if(leap){
                if(day<1 || day>29){
                    message = "Day out of range";
                }
            }else{
                if(day<1 || day>28){
                    message = "Day out of range";
                }
            }
        }

        if(message.isEmpty()){
            if(day<10){
                System.out.println(String.format("0%d %s %d", day, months.get(month-1), year));
            }else{
                System.out.println(String.format("%d %s %d", day, months.get(month-1), year));
            }
        }else{
            if(month <1 || month > 12){
                if(month != -1){
                    System.out.println(String.format("%s - INVALID: %s", inputDate.get(0), message));
                }else{
                    System.out.println(String.format("%s - INVALID: %s", inputDate.get(0), message));
                }
            }
            else{
                System.out.println(String.format("%s - INVALID: %s", inputDate.get(0), message));
            }
        }
    }
    /*
     *  a method that checks if year inpt is valid.
     *  while at the same time chceck if the year is a leap year
     *  and sets the leap boolean variable to true.
     *
     *  @param yr is the year string to be checked.
     */
    private void valiYear(String yr){
        try{
            if(yr.length()>2){
                int check = Integer.parseInt(yr);
                if(check < 1753 || check > 3000){
                    message = "Year out of range";
                }else{
                    year = Integer.parseInt(yr);
                }
            }else if(yr.length()<2){
              message = "Incorrect format for Year";
            }else{
                year = Integer.parseInt(yr);
            }

        }catch(NumberFormatException e){
            message = "Invalid format for Year";
        }

        if(year>49 && year<100){
            year += 1900;
        }else if(year<50){
            year += 2000;
        }
        // if(year<1753 || year>3000){
        //  message = "Year our of range";
        //}
        //      if(year%4==0){
        //        if(year%100==0 && year%400==0){
        //        leap = true;
        //        System.out.println("Leap year");
        //        }
        //      }else{
        //        leap = false;
        //      }
        if(year%100 ==0){
            if (year%400==0){
                leap = true;
            }else{
                leap = false;
            }
        }
        else if (year%4 ==0){
            leap = true;
        }

    }


    public boolean isNumeric(String s){
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException e){
            return false;
        }catch(NullPointerException e){
            return false;
        }
        return true;
    }
}
