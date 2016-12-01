package com.mlc;

import java.util.Collections;
import java.util.LinkedList;

public class CustomDeck implements Deck{

    LinkedList<Card> cards = new LinkedList<>();

    /* Create the deck of cards */
    public void create( int numberOfSuits, int numberOfRanks ){
        for(int suit = 0; suit < numberOfSuits; suit++){
            for (int rank = 0; rank < numberOfRanks; rank++)
                cards.add(new Card(suit,rank));
        }
        // outpoutDeck();
    }

    /* Shuffle the deck */
    public void shuffle(){
        Collections.shuffle((cards));
        // outpoutDeck();
    }

    /* Deal a card from the deck */
    public Card deal(){
        return cards.remove(0);
    }

    private void outpoutDeck(){
        System.out.println("The number of cards:  " + cards.size());
        for (Card card : cards) {
            System.out.println("The card suit: " + card.getSuit() + ", The card rank: " + card.getRank());
        }
    }

    public LinkedList<Card> getCards() {
        return cards;
    }
}
