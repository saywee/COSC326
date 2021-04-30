import java.util.*;

public class Date {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = "";
        while (sc.hasNextLine()) {
            s = sc.nextLine();
            if (s.isEmpty())
                System.out.println();
            else {
                checkTrue(s);
            }
        }
        sc.close();
    }

    public static void checkTrue(String s) {

        String[] accepted = {
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec" };
        // This regex is taken from
        // https://stackoverflow.com/questions/15491894/regex-to-validate-date-format-dd-mm-yyyy
        if (!s.matches(
            "^(?:(?:31(\\/|-|\\s)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\s)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\s)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\s)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
            if (!checkFormat(s, accepted)) {
                return;
            }
        }
        char[] seps = {
            '-',
            ' ',
            '/' };
        long count = 0;
        char knownsep = ' ';
        for (char c : seps) {
            count += s.chars().filter(ch -> ch == c).count();
            if (count > 2) {
                System.out.println(
                    s + " - INVALID: Issue with separators. Too many.");
                return;
            }
        }
        for (char c : seps) {
            count = s.chars().filter(ch -> ch == c).count();
            if (count == 2) {
                knownsep = c;
                break;
            }
            count = 0;
        }
        if (count != 2) {
            System.out.println(s
                + " - INVALID: Issue with separators, either different, too many or too little.");
            return;
        }

        // checking for valid Day Month Year input(ie, just number, number/word/
        // number)
        String[] split = s.split(knownsep + "");

        if (split.length < 3) {
            System.out
                .println(s + " - INVALID: Currently missing a parameter.");
            return;
        }

        int[] vals = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                if (i == 1 && !split[i].matches("\\d+")) {
                    int index = 1;
                    for (String str : accepted) {
                        if (str.matches(split[i])
                            || str.toUpperCase().matches(split[i])
                            || str.toLowerCase().matches(split[i])) {
                            split[i] = index + "";
                            break;
                        }
                        index++;
                    }
                    if (!split[i].equals(index + "")) {
                        System.out.println(s
                            + " - INVALID: Month word input follows 3 letter months: all uppercase, all lowercase or first letter uppercase.");
                        return;
                    }
                }
                vals[i] = Integer.parseInt(split[i]);
            } catch (Exception e) {
                System.out.println(s
                    + " - INVALID: Unable to parse the entry. Some values are not a digit where needed.");
                return;
            }
        }

        if (!split[0].matches("(?<!\\d)(\\d{1}|\\d{2})(?!\\d)")) {
            System.out.println(
                s + " - INVALID: Day value must be 1 to 2 positive digits.");
            return;
        }

        if (split[1].matches("\\d+")) {
            if (!split[1].matches("(?<!\\d)(\\d{1}|\\d{2})(?!\\d)")) {
                System.out.println(s
                    + " - INVALID: Month value must be 1 to 2 digits, or a month in three letters.");
                return;
            }
        }

        if (!split[2].matches("(?<!\\d)(\\d{2}|\\d{4})(?!\\d)")) {
            System.out
                .println(s + " - INVALID: Year value must be 2-4 digits.");
            return;
        }

        if (split[2].matches("\\d{4}")) {
            if (Integer.parseInt(split[2]) < 99) {
                System.out.println(s
                    + " - INVALID: Year is a 2 digit value, but has 4 digits.");
                return;
            }
        }
        // date goes from 50++, resetting at 0, incrementing til 49
        if (vals[2] >= 50 && vals[2] <= 99) {
            vals[2] += 1900;
        } else if (vals[2] <= 49 && vals[2] >= 00) {
            vals[2] += 2000;
        }
        if (vals[2] < 1753 || vals[2] > 3000) {
            System.out.println(s
                + " - INVALID: Year out of range, values from 1753 to 3000, or 0 to 99.");
            return;
        }
        if (vals[1] < 1 || vals[1] > 12) {
            System.out.println(s
                + " - INVALID: Month out of range, values from 1-12 or with Text.");
            return;
        }
        // errors from switch case based upon previous valid values.
        switch (vals[1]) {
            case 2:
                if (isLeapYear(vals[2])) {
                    if (vals[0] < 1 || vals[0] > 29) {

                        System.out.println(s
                            + " - INVALID: Day out of range, this is a leap year, values range 1-29.");
                        return;
                    }
                } else {
                    if (vals[0] < 1 || vals[0] > 28) {
                        System.out.println(s
                            + " - INVALID: Day out of range, this is not a leap year, values range 1-28.");
                        return;
                    }
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (vals[0] < 1 || vals[0] > 30) {
                    System.out.println(s
                        + " - INVALID: Day out of range, this month only has days from 1-30.");
                    return;
                }
                break;
            default:
                if (vals[0] < 1 || vals[0] > 31) {
                    System.out.println(s
                        + " - INVALID: Day out of range, this month only has days from 1-31.");
                    return;
                }
                break;
        }
        split[0] = (vals[0] < 10) ? "0" + vals[0] : vals[0] + "";
        System.out
            .println(split[0] + " " + accepted[vals[1] - 1] + " " + vals[2]);
    }

    // In case we want to add another check for the format with the accepted
    // strings, we would edit this code.
    public static boolean checkFormat(String s, String[] acc) {
        for (String a : acc) {
            if (s.contains(a)) {
                return true;
            }
        }
        System.out
            .println(s + " - INVALID: format does not match any known dates.");
        return false;
    }

    // taken from
    // https://stackoverflow.com/questions/1021324/java-code-for-calculating-leap-year
    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }
}
