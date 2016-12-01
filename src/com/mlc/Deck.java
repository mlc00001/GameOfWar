package com.mlc;

public interface Deck {

    /* Create the deck of cards */
    public void create( int numberOfSuits, int numberOfRanks );

    /* Shuffle the deck */
    public void shuffle();

    /* Deal a card from the deck */
    public Card deal();

}
