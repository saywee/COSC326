package forsale.strategies;
import forsale.*;
import java.util.*;

public class Average implements Strategy{

    public int bid(PlayerRecord p, AuctionState s){
        //javac Source/*.java Source/strategies/*.java -d ./
        //Calculate spread(How close/far the card qualities are)
        // average, range(How far apart the highest and lowest cards are)
        //& midpoint(where is the middle of the current cardset) of current set of cards?
        // ie(28 - 14 + 14/2 = 21), (28 - 12 = 16;  + 16 / )2 = 20
        
        int x = 0;
        int playerAverage = 0;
        int auctionAverage = 0;
        int lowCards = 0;
        int highCards = 0;
        int min=0, max=0;
        ArrayList<Integer> allBids = new ArrayList<Integer>();
        
        playerAverage = avg(p.getCards());
        auctionAverage = avg(s.getCardsInAuction());
        List<Integer> auctionSpread = spread(s.getCardsInAuction());
        auctionSpread = Collections.sort(s.getCardsInAuction().getQuality());
        
        for(Card c : p.getCards()){
            if(c.getQuality() < 7){
                lowCards++;
            }
        }
        
        for(Card c : s.getCardsInAuction()){
            if(c.getQuality() > auctionAverage){
                highCards++;
            }
        }

        int[] bidDiffs = new int[s.getPlayers().size()-1];

        for(int i=0; i<s.getCardsInAuction().size()-1; i++){
            bidDiffs[i] = s.getCardsInAuction().get(i).getQuality() - s.getCardsInAuction().get(i+1).getQuality();
        }

        max = doThing(s.getCardsInAuction()).getQuality();
        min = minimum(s.getCardsInAuction()).getQuality();

        int range = max - min;
        int minSpread = Collections.min(auctionSpread);
        
        
        if(lowCards<=1){
            x = 0;
        }else{
            for(PlayerRecord pl : s.getPlayersInAuction()){
                if(!pl.getName().equals(p.getName())){
                    allBids.add(pl.getCurrentBid());
                }
            }
            if(highCards>0){
                
                x += Collections.max(allBids) + 1;
            }
        }
        
        return x;
    }
        
    public Card chooseCard(PlayerRecord p, SaleState s){
        //25, 30, 27, 26, 28
        int maxCheque = Collections.max(s.getChequesAvailable());
        
        Card ourMax = doThing(p.getCards());
        List<PlayerRecord> ls = s.getPlayers();
        List<Card> stuff = new ArrayList<Card>();
        for(int i = 0; i < ls.size(); i++){
            stuff.add(doThing(ls.get(i).getCards()));
        }
        
        if(maxCheque == 15){
            int x = 0;
            for(int i=0; i<p.getCards().size(); i++){
                int qual = 0;
                if(p.getCards().get(i).getQuality() > qual){
                    qual = p.getCards().get(i).getQuality();
                    x = i;
                }
            }
            return p.getCards().get(x);
        }else if(maxCheque >= 11 && maxCheque<15){
            for(Card c : p.getCards()){
                if(c.getQuality() > 10 && c.getQuality() < 25){
                    return c;
                }
            }
        }else{
            for(Card c : p.getCards()){
                if(c.getQuality()<15){
                    return c;
                }
            }
        }
        return null;
    }
    
    //calculates the average value of a cardset.
    public int avg(List<Card> cardlist){
        int x = 0;
        for(Card c : cardlist){
            x += c.getQuality();
        }
        if(cardlist.size()==0){
            return x;
        }else{
        
        return x/cardlist.size();
        }
    }
    //finds the maximum card a player holds.
    public Card doThing(List<Card> p){
        Card max = p.get(0);
        Card placeholder;
        for(int i = 1; i < p.size(); i++){
            placeholder = p.get(i);
            if(placeholder.getQuality() > max.getQuality()){
                max = placeholder;
            }
        }
        return max;
    }
    
    public Card minimum(List<Card> p){
        Card min = p.get(0);
        Card placeholder;
        for(int i = 1; i < p.size(); i++){
            placeholder = p.get(i);
            if(placeholder.getQuality() < min.getQuality()){
                min = placeholder;
            }
        }
        return min;
    }

    public List<Integer> spread(List<Card> ls){
        List<Integer> spread = new ArrayList<Integer>();
        for(int i = 0; i < ls.size() - 1; i++){
            spread.add(Math.abs(ls.get(i).getQuality() - ls.get(i+1).getQuality()));
        }

        return spread;
    }

}