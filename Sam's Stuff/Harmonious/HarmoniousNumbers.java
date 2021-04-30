
public class HarmoniousNumbers {

    public static void main(String[] args) {
        for (int i = 1; i <= 2000000; i++) {
            int a = getDivisorSum(i);
            if (a > i) {
                int b = getDivisorSum(a);
                if (b == i && b != a) {
                    System.out.println(b + " " + a);
                }
            }
        }
    }

    // Code taken from:
    // https://www.geeksforgeeks.org/find-divisors-natural-number-set-1/
    // and modified by me.
    public static int getDivisorSum(int n) {
        int sum = 0;// for amicable numbers, change this to 1.(adding 1,
                    // basically).
        double param = Math.sqrt(n);
        for (int i = 2; i <= param; i++) {
            if (n % i == 0)
                if (n / i == i) {
                    sum += i;
                } else {
                    sum += (i + n / i);
                }
        }
        return sum;
    }
}
