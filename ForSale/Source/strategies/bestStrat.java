package forsale.strategies;
import forsale.*;
import java.util.*;

public class bestStrat implements Strategy{

    public int bid(PlayerRecord p, AuctionState s){
        int x = 0;
        //If less players, take more low cards
        //If more players, take less low cards
        //Check cards first, then decide on whether to bid
        //Use relative math (is it worth it to buy? have a cutoff point)
        //Remember not to end up with all low cards. Can use average value
        //Can check against remaining possible average?
        //maybe calculate all other player's average values to contend, forcing other averages below ours.
        //bid on cards more than avg, with cutoff point(ONLY IF CAN STILL TAKE LOW)
       
        return x;
    }

    public Card chooseCard(PlayerRecord p, SaleState s){
        return null;
    }
}
