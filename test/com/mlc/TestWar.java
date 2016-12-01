package com.mlc;


import junit.framework.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class TestWar {

    @Test
    public void testPlayWarWithStandardDeckAndTwoPLayers() throws Exception {

        // The Standard deck of cards has four suits and thirteen ranks.
        int numberOfSuits = 4;
        int numberOfRanks = 13;

        int numberOfPLayers = 2;

        // Play the Game of War
        War war = new War();
        war.play(numberOfSuits, numberOfRanks, numberOfPLayers);

        // Verify that the winner now has all the played cards.
        verifyWinnerHasAllPlayedCards(numberOfSuits, numberOfRanks, numberOfPLayers, war);
    }

    @Test
    public void testPlayWarWithStandardDeckAndThreePLayers() throws Exception {

        // The Standard deck of cards has four suits and thirteen ranks.
        int numberOfSuits = 4;
        int numberOfRanks = 13;

        int numberOfPLayers = 3;

        // Play the Game of War
        War war = new War();
        war.play(numberOfSuits, numberOfRanks, numberOfPLayers);

        // Verify that the winner now has all the played cards.
        verifyWinnerHasAllPlayedCards(numberOfSuits, numberOfRanks, numberOfPLayers, war);
    }

    @Test
    public void testPlayWarWithStandardDeckAndFivePLayers() throws Exception {

        // The Standard deck of cards has four suits and thirteen ranks.
        int numberOfSuits = 4;
        int numberOfRanks = 13;

        int numberOfPLayers = 5;

        // Play the Game of War
        War war = new War();
        war.play(numberOfSuits, numberOfRanks, numberOfPLayers);

        // Verify that the winner now has all the played cards.
        verifyWinnerHasAllPlayedCards(numberOfSuits, numberOfRanks, numberOfPLayers, war);
    }

    @Test
    public void testPlayWarWithNonStandardDeckAndTwoPLayers() throws Exception {

        // The Standard deck of cards has four suits and thirteen ranks.
        int numberOfSuits = 5;
        int numberOfRanks = 15;

        int numberOfPLayers = 2;

        // Play the Game of War
        War war = new War();
        war.play(numberOfSuits, numberOfRanks, numberOfPLayers);

        // Verify that the winner now has all the played cards.
        verifyWinnerHasAllPlayedCards(numberOfSuits, numberOfRanks, numberOfPLayers, war);
    }

    private void verifyWinnerHasAllPlayedCards(int numberOfSuits, int numberOfRanks, int numberOfPLayers, War war) {
        // Verify that the winner now has all the played cards.
        int expectedNumberOfPlayedCards = ((numberOfSuits * numberOfRanks) / numberOfPLayers) * numberOfPLayers;
        LinkedList playerCards = war.getPlayerCards();
        LinkedList<Card> winnerCards = (LinkedList<Card>) playerCards.get(0);
        Assert.assertEquals(expectedNumberOfPlayedCards, winnerCards.size());
    }
}
