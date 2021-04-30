import java.util.*;

/**
 * A program that takes a character and string as an input, using those values
 * to generate the values of characters depending on the string. The code makes
 * use of recursion to both set impossible values and generate the numbers.
 * There is no error checking implemented, so do ensure valid input.
 * 
 * @author Samuel Ng Shan Feng 2955262 COSC326
 *
 */
public class Etude3Recursive {
    /**
     * The main class stores values for each set that we might need to parse,
     * ending once there is no more input. It also stores the order of which to
     * print values, as the hash map is randomly printed.
     * 
     * @param args
     */
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        String order = "";
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            String[] split = s.split(" ");
            if (s.isEmpty()) {
                map = test(map);
                printMap(map, order);
                System.out.println();
                map.clear();
                order = "";
            } else {
                map.put(split[0], split[1]);
                order += split[0] + " ";
            }
        }
        map = test(map);
        printMap(map, order);
        sc.close();
    }

    /**
     * The method that processes the hash map, in a private method to prevent
     * any changes.
     * 
     * @param mp represents the hash map.
     * @return mp returns the processed hash map.
     */
    private static HashMap<String, String> test(HashMap<String, String> mp) {
        for (Map.Entry<String, String> entry : mp.entrySet()) {
            if (entry.getValue().contains(entry.getKey())) {
                entry.setValue("NaN");
            }
        }
        for (Map.Entry<String, String> entry : mp.entrySet()) {
            if (!entry.getValue().matches("\\d+")) {
                for (int i = 0; i < entry.getValue().length(); i++) {
                    if (circCheck(mp, entry.getKey(),
                        entry.getValue().charAt(i) + "")) {
                        entry.setValue("NaN");
                    }
                }
            }
        }
        // set values, if overflow or null exception, set to NaN.
        for (Map.Entry<String, String> entry : mp.entrySet()) {
            try {
                if (!entry.getValue().matches("\\d+")
                    && !entry.getValue().matches("NaN")) {
                    entry.setValue(
                        numberSetter(mp, entry.getValue()).toString());
                }
            } catch (Exception e) {
                entry.setValue("NaN");
            }
        }
        return mp;
    }

    /**
     * A recursive method that sets the numbers by adding to a sum value,
     * calling itself if the character points to a string instead of a number.
     * 
     * @param mp   represents the hash map that the strings and values are
     *             stored in.
     * @param iter represents the string to be iterated through.
     * @return sum returns the final value.
     */
    private static Long numberSetter(HashMap<String, String> mp, String iter) {
        // returns an exception if it hits an overflow.
        Long sum = (long) 0;
        for (int i = 0; i < iter.length(); i++) {
            if (mp.get(iter.charAt(i) + "").matches("\\d+")) {
                sum = Math.addExact(sum,
                    Long.parseLong(mp.get(iter.charAt(i) + "")));
            } else {
                sum += numberSetter(mp, mp.get(iter.charAt(i) + ""));
            }

        }
        return sum;
    }

    /**
     * A recursive method that checks if the string points to any invalid
     * settings.
     * 
     * @param mp    represents the hash map.
     * @param check represents the value to be checked.
     * @param next  represents the next value that would be checked.
     * @return returns itself, eventually getting true or false.
     */
    private static boolean circCheck(HashMap<String, String> mp, String check,
        String next) {
        // using regex to check that it does not contain any prior keys that
        // have been gone through.
        if (null == mp.get(next) || mp.get(next).matches(".*[" + check + "].*")
            || mp.get(next).equals("NaN")) {
            return true;
        }
        if (mp.get(next).matches("\\d+")) {
            return false;
        }

        // since the for loop is outside, the char at 0 is always going to
        // be the one at i.
        return circCheck(mp, check + next, mp.get(next).charAt(0) + "");
    }

    /**
     * A method to print in the desired order.
     * 
     * @param mp         represents the hash map to be printed.
     * @param printorder represents the printing order.
     */
    private static void printMap(HashMap<String, String> mp,
        String printorder) {
        String key;
        Scanner sc = new Scanner(printorder);
        while (sc.hasNext()) {
            key = sc.next();
            System.out.println(key + " " + mp.get(key));
        }
        sc.close();
    }
}