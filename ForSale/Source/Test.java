package forsale;

import forsale.strategies.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael Albert
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<Player> players = new ArrayList<>();
       for(int i = 0; i < 2; i++) {
           players.add(new Player("N" + ((char) ('A' + i)), new NullStrategy()));
           players.add(new Player("R"+ ((char) ('A' + i)), new RandomStrategy()));
       }
       players.add(new Player("AVG", new Average()));
       players.add(new Player("Sam" , new SamStratTest()));
        java.util.Collections.shuffle(players);
        GameManager g = new GameManager(players);
        g.run();
        System.out.println(g);
    }

}
