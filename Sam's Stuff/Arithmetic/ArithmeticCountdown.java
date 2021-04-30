import java.util.*;

public class ArithmeticCountdown {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String numstring = "";
        String[] rules;
        while (sc.hasNextLine()) {
            numstring = sc.nextLine();
            rules = sc.nextLine().split(" ");
            System.out.println(rules[1] + " " + rules[0]
                + lr(rules[1], numstring, Integer.parseInt(rules[0])));
        }
        sc.close();
    }

    public static String lr(String oo, String numbers, int target) {
        Scanner sc = new Scanner(numbers);
        String s = "";
        ArrayList<Integer> nums = new ArrayList<Integer>();
        while (sc.hasNextInt()) {
            nums.add(sc.nextInt());
        }
        if (oo.equals("L"))
            s = recGen(nums.get(0), nums, 0, target);
        else if (oo.equals("N")) {
            s = bedmas(0, nums, 0, target, nums.get(0));
        }
        if (null == s) {
            s = "impossible";
        }
        sc.close();
        return " " + s;
    }

    private static String recGen(int sum, List<Integer> l, int depth,
        int target) {
        if (sum > target) {
            return null;
        }
        if (depth == l.size() - 1) {
            if (sum == target) {
                return l.get(depth).toString();
            } else {
                return null;
            }
        }
        String a = recGen(sum * l.get(depth + 1), l, depth + 1, target);
        if (a != null) {
            return l.get(depth).toString() + " * " + a;
        }
        String b = recGen(sum + l.get(depth + 1), l, depth + 1, target);
        if (b != null) {
            return l.get(depth).toString() + " + " + b;
        } else {
            return null;
        }
    }

    private static String bedmas(int sum, List<Integer> l, int depth,
        int target, int prev) {
        if (sum + prev > target || sum > target || prev > target) {
            return null;
        }
        if (depth == l.size() - 1) {
            if (sum + prev == target) {
                return l.get(depth).toString();
            } else {
                return null;
            }
        }
        String a = bedmas(sum, l, depth + 1, target, prev * l.get(depth + 1));
        if (a != null) {
            return l.get(depth).toString() + " * " + a;
        }
        String b = bedmas(prev + sum, l, depth + 1, target, l.get(depth + 1));
        if (b != null) {
            return l.get(depth).toString() + " + " + b;
        } else {
            return null;
        }
    }
}
