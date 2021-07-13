import java.util.*;

public class Wordchains {
    private HashSet<String> dict = new HashSet<String>();
    Queue<String> problems = new LinkedList<>();
    Queue<String> q = new LinkedList<>();
    HashMap<String, ArrayList<String>> neighbours;
    HashMap<String, String> seen;
    Stack<ArrayList<String>> stack;
    // List<String> problems = new ArrayList<String>();
    String target = "";
    String from = "";
    int depth;

    public static void main(String[] args) {
        Wordchains w = new Wordchains();
        w.storeDict();
        w.wordchain();

    }

    private void storeDict() {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.trim().isEmpty()) {
                break;
            } else {
                problems.add(line);
            }
        }
        // System.out.println(problems);
        while (sc.hasNextLine()) {
            dict.add(sc.nextLine());
        }

    }

    public void wordchain() {
        while (!problems.isEmpty()) {
            String problem = problems.remove();
            // System.out.println("to be executed : " + problem);
            execute(problem);

        }
    }

    public void execute(String word) {
        depth = 0;
        neighbours = new HashMap<>();
        //System.out.print(neighbours);
        seen = new HashMap<>();
        String[] prob = null;
        // System.out.println("problem is : " + problem);
        prob = word.split(" ");
        // System.out.println("prob is :" + prob.length);
        if (prob.length < 2 || prob.length > 3) {
            System.out.println(prob[0] + " impossible");
        }
        target = prob[1];
        from = prob[0];

        if (dict.contains(target) && dict.contains(from)
            && target.length() == from.length()) {
            if (prob.length == 2) {
                q.add(from);
                queues();
                if (!neighbours.containsKey(target)) {
                    System.out.println(from + " " + target + " impossible");
                    seen.clear();
                } else {

                }
            } else {
                String ans = "";
                try {
                    depth = Integer.parseInt(prob[2]);
                    ArrayList<String> start = new ArrayList<>();
                    start.add(from);
                    ans = depth2(start);
                } catch (Exception e) {
                    System.out.println("depth has to be a numerical number");
                }

                if (!ans.equals("impossible")) {
                    System.out.println(ans);
                } else {
                    System.out
                        .println(from + " " + target + " " + depth + " " + ans);
                }
            }

        } else {
            System.out.println(from + " " + target + " impossible");
        }
    }
    // System.out.println(neighbours);

    private void neighbour(String word) {
        char[] characters = word.toCharArray();
        ArrayList<String> neighs = new ArrayList<String>();
        for (int i = 0; i < characters.length; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (characters[i] == c) {
                    continue;
                }
                char[] newCharacters = Arrays.copyOf(characters,
                    characters.length);
                newCharacters[i] = c;
                String newWord = new String(newCharacters);
                if (dict.contains(newWord)) {
                    if (!seen.containsKey(newWord) && depth == 0) {
                        seen.put(newWord, word);
                        q.add(newWord);
                    }
                }
            }
        }

        neighbours.put(word, neighs);
        if (word.equals(target)) {
            System.out.println(getChain(seen, target));
        }
    }

    private void neighbour2(String word, ArrayList<String> al) {
        char[] characters = word.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (characters[i] == c) {
                    continue;
                }
                char[] newCharacters = Arrays.copyOf(characters,
                    characters.length);
                newCharacters[i] = c;
                String newWord = new String(newCharacters);
                if (dict.contains(newWord)) {
                    if (depth != 0 && al != null) {
                        if (!al.contains(newWord) && al.size() <= depth) {
                            ArrayList<String> nList = new ArrayList<String>(al);
                            nList.add(newWord);
                            if (!stack.contains(nList)) {
                                stack.add(nList);
                            }
                        }
                    }
                }
            }
        }
    }

    public void queues() {
        while (!q.isEmpty()) {
            String stackedWord = q.remove();
            if (!neighbours.containsKey(stackedWord)) {
                neighbour(stackedWord);
            }
        }
    }

    public String getChain(HashMap<String, String> h, String word) {
        String cont = "";
        if (word.equals(from)) {
            return from + cont;
        } else {
            cont = getChain(h, h.get(word)) + " " + word;
        }
        return cont;
    }

    public String depth2(ArrayList<String> word) {
        stack = new Stack<>();
        ArrayList<String> current;
        String answer = "";
        stack.push(word);
        while (!stack.isEmpty()) {
            current = stack.pop();
            if (current.size() == depth && current.contains(target)) {
                for (int i = 0; i < current.size(); i++) {
                    if (i != current.size() - 1) {
                        answer = answer + current.get(i) + " ";
                    } else {
                        answer = answer + current.get(i);
                        stack.clear();
                    }
                }
                    
                return answer;
            } else {
                neighbour2(current.get(current.size() - 1), current);
            }
        }
        return "impossible";
    }

}
