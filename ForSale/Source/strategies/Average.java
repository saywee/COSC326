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
        // sort by quality**
        //max = 30, min = 26, Range: 4. Spread 4,1,2,1, hence sorting gives better "spread"
        // Range == Spread, Do not want to bid. Spread 1, but close to min, dont bid.
        
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

        int range = max - min;//Current Range)
        int minSpread = Collections.min(auctionSpread);

        //if range is large, find avg and bid for cards above, UNLESS spread is small between lower values. 
        //if currentbid > threshold, dont bid
        //take max cards in game, divide by value(based on money allocated) to get range(Generated Range)
        // ^ to assign a general net worth to each card based upon allocated money(ie, threshold to purchase)
        //use the generated range and compare to current range(whether acceptable to try high bids)
        //check all against the current money we have

        //1800/30
        //360 per card
        //60, 360: 1, 5, 10, 15, 25, 30
        //4, 5, 5, 5, 5
        //spread:4.25, max card: 30. min card 1.
        // Since range is big(ie, >= range), try for highest card. If exceeding threshold(calculated based upon spread).
        //Threshold = avg spread * monetary value
        //
        //1: 60, 30: 360,
        //changed assigned general value/threshold based on cards and spread

        
        
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
