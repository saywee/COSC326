package forsale.strategies;

import forsale.*;
import java.util.*;

public class SamStratTest implements Strategy {

    public static int rounds = 0;//A way to memorize the rounds elapsed. Tested and yes it works.
    public int bid(PlayerRecord p, AuctionState s) {
        int x;
        int[] quals = new int[3];
        //sort the cards into their respective qualities.
        for(int i = 0; i < s.getCardsInAuction().size(); i++) {
            if(s.getCardsInAuction().get(i).getQuality() <= 10) {//Low quality cards
                quals[0] += 1;
            } else if(s.getCardsInAuction().get(i).getQuality() <= 20) {//Medium Quality
                quals[1] += 1;
            } else {//Anything remaining is high quality cards.
                quals[2] += 1;
            }
        }
        rounds++;
        x = choose(quals, s);
        return x;
    }

    //the way to choose values based on card qualities?
    public int choose(int[] qualities, AuctionState s) {
        int x = 0;
        if(qualities[0] >= 2) {//If two or more low Quality cards, don't add anything?
            x += 0;
        }
        if(qualities[1] >= 2) {//If 2 or more medium quality cards, add 1?
            x += 1;
        }
        
        if(qualities[2] >= 2) {//If 2 or more high quality cards, add more, based on current bid?
            if(s.getCurrentBid() < 4) {//if the current bid is an acceptable threshold, needs to be based on how many rounds have elapsed.
                x += s.getCurrentBid();
            } else {
               x += 1;//if not acceptable, just go with a smaller bid.
            }
        }
        return x;
    }
    
    public Card chooseCard(PlayerRecord p, SaleState s) {
        
        return null;
    }

    // calculates the average value of a card set.
    public int avg(List<Card> cardlist) {
        int x = 0;
        for (Card c : cardlist) {
            x += c.getQuality();
        }
        if (cardlist.size() == 0) {
            return x;
        } else {

            return x / cardlist.size();
        }
    }

    // finds the maximum card a player holds.
    public Card doThing(List<Card> p) {
        Card max = p.get(0);
        Card placeholder;
        for (int i = 1; i < p.size(); i++) {
            placeholder = p.get(i);
            if (placeholder.getQuality() > max.getQuality()) {
                max = placeholder;
            }
        }
        return max;
    }

    public Card minimum(List<Card> p) {
        Card min = p.get(0);
        Card placeholder;
        for (int i = 1; i < p.size(); i++) {
            placeholder = p.get(i);
            if (placeholder.getQuality() < min.getQuality()) {
                min = placeholder;
            }
        }
        return min;
    }

    public List<Integer> spread(List<Card> ls) {
        List<Integer> spread = new ArrayList<Integer>();
        for (int i = 0; i < ls.size() - 1; i++) {
            spread.add(
                Math.abs(ls.get(i).getQuality() - ls.get(i + 1).getQuality()));
        }

        return spread;
    }

}
