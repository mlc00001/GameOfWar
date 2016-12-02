package com.mlc;

import java.util.Collections;
import java.util.LinkedList;

public class War {

    /*
        This class implements the Game of War card game.

        The description of Game of War from Wikipedia (see http://en.wikipedia.org/wiki/War_(card_game)):

        The objective of the game is to win all cards.

        The deck is divided evenly among the players, giving each a down stack. In unison, each player reveals the top
        card of their deck – this is a "battle" – and the player with the higher card takes both of the cards played
        and moves them to their stack. Aces are high, and suits are ignored.

        If the two cards played are of equal value, then there is a "war". Both players place the next card of their
        pile  face down, depending on the variant, and then another card face-up. The owner of the higher face-up card
        wins the war and adds all four (or six) cards on the table to the bottom of their deck. If the face-up cards
        are again equal then the battle repeats with another set of face-down/up cards. This repeats until one player's
        face-up card is higher than their opponent's.

        Most descriptions of War are unclear about what happens if a player runs out of cards during a war.  In some
        variants, that player immediately loses. In others, the player may play the last card in their deck as their
        face-up card for the remainder of the war or replay the game from the beginning.

        Note:  If two or more players tie for highest there is also a war - everyone plays their next card face-down
        and then turns up a third card. This continues until one of the face-up cards is higher than all the others,
        and then that player wins all the cards in a war.  This implies that only if two of three or more players
        has tied, all players play the war.

        For more on the Game of War running indefinitely, see:

        http://mathoverflow.net/questions/11503/does-war-have-infinite-expected-length
     */

    private LinkedList playerCards;
    private int numberOfPlayers;
    boolean aPlayerHasNoMoreCards = false;

    public void play(int numberOfSuits, int numberOfRanks, int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        playerCards = new LinkedList();
        for (int i = 0; i < numberOfPlayers; i++) {
            playerCards.add(new LinkedList());
        }

        // Create the deck of cards.
        CustomDeck customDeck = new CustomDeck();
        customDeck.create(numberOfSuits, numberOfRanks);

        // Shuffle the deck of cards.
        customDeck.shuffle();

        // Deal the cards.
        int numberOfDeals = customDeck.getCards().size() / numberOfPlayers;
        for (int i = 0; i < numberOfDeals; i++) {
            for (int j = 0; j < numberOfPlayers; j++) {
                ((LinkedList<Card>) playerCards.get(j)).add(customDeck.deal());
            }
        }

        // Play the first Battle of the game.  The game will continue either playing Battles or Wars until
        // there is only one player left with cards.
        playBattleOrWar(new LinkedList(), false);

        outputAllPlayerCards();

        // Some things to do to improve this method would be to add validation for the input parameters (numberOfSuits,
        // numberOfRanks and numberOfPlayers).  Also, typically a Logger would be used for logging.  But, for this
        // example, System.out.println will suffice.
    }

    private void playBattleOrWar(LinkedList<Card> cardsPlayed, boolean atWar) {

        boolean onlyOnePlayerLeft = false;

        // First, remove players who don't have any more cards from the game.  This is done here since, if there
        // is a War, a player can run out of cards before another Battle is started.
        if (aPlayerHasNoMoreCards) {
            removePlayersWithoutCardsFromTheGame();
        }

        // If there is more than one player with cards, then play a Battle or War.
        if (numberOfPlayers > 1) {
            
            // Get the cards for this Battle or War.
            LinkedList<Card> cardsFromPlayers = getCardsFromPlayers(cardsPlayed);

            if (atWar){
                
                // If one of the players now has no more cards, then remove them from the game.
                if (aPlayerHasNoMoreCards) {
                    removePlayersWithoutCardsFromTheGame();
                }

                if (numberOfPlayers > 1) {
                    // Get another card from each player still in the game.  These will be the cards used to determine
                    // who won the war.
                    cardsFromPlayers = getCardsFromPlayers(cardsPlayed);
                }
                else {
                    // There is only one player left in the game.  They win.  The war is over.
                    onlyOnePlayerLeft = true;
                    atWar = false;
                }
            }

            int winner = 0;
            int highestRank = cardsFromPlayers.get(0).getRank();

            if (!onlyOnePlayerLeft) {

                // Determine who won the Battle or War and the rank of the highest card.
                // First, set the winner to 0 and the highest rank to their card's rank. Then, loop through the rest
                // of the face up cards.
                for (int i = 1; i < cardsFromPlayers.size(); i++) {
                    // Check if the card is an Ace (rank equals 0). Then, check if the card rank is greater than
                    // the current known highest rank.
                    if ((cardsFromPlayers.get(i).getRank() == 0) ||
                            ((cardsFromPlayers.get(i).getRank() > highestRank) && (highestRank != 0))) {
                        // If there is a new highest card, then update the winner and highest rank.
                        highestRank = cardsFromPlayers.get(i).getRank();
                        winner = i;
                    }
                }

                atWar = isThereAWar(cardsFromPlayers, highestRank);
            }

            if (!atWar) {
                // Give all the played cards to the winner of the Battle or War.  But, first shuffle them to avoid
                // the game running indefinitely.
                Collections.shuffle(cardsPlayed);
                for (int i = 0; i < cardsPlayed.size(); i++) {
                    ((LinkedList<Card>) playerCards.get(winner)).add(cardsPlayed.get(i));
                }
                cardsPlayed = new LinkedList();
            }

            // Play another Battle or War.
            playBattleOrWar(cardsPlayed, atWar);
        }
        else if ((numberOfPlayers == 1)){
            // If there is only one player left, give them all the remaining played cards.
            for (int i = 0; i < cardsPlayed.size(); i++) {
                ((LinkedList<Card>) playerCards.get(0)).add(cardsPlayed.get(i));
            }
        }
    }

    private LinkedList<Card> getCardsFromPlayers(LinkedList<Card> cardsPlayed) {
        // Get the cards from the players.
        LinkedList<Card> cardsFromPlayers = new LinkedList();
        for (int i = 0; i < numberOfPlayers; i++) {
            if (!((LinkedList<Card>) playerCards.get(i)).isEmpty()) {
                cardsFromPlayers.add(((LinkedList<Card>) playerCards.get(i)).remove(0));
                if (((LinkedList<Card>) playerCards.get(i)).isEmpty()) {
                    // The player has no more cards.  They are out of the game.
                    aPlayerHasNoMoreCards = true;
                }
            }
        }

        // Add the cards we just got from the players to the cards already in play.
        cardsPlayed.addAll(cardsFromPlayers);

        return cardsFromPlayers;
    }

    private boolean isThereAWar(LinkedList<Card> cardsFromPlayers, int highestRank){
        // If more than one card in the last played cards has the same highest rank, then there is a war.
        int numberOfTimesHighestRankInPlayedCards = 0;
        for (int i = (cardsFromPlayers.size() - numberOfPlayers); i < cardsFromPlayers.size(); i++) {
            if ((cardsFromPlayers.get(i).getRank() == highestRank)){
                numberOfTimesHighestRankInPlayedCards++;
            }
        }
        return (numberOfTimesHighestRankInPlayedCards > 1) ? true : false;
    }

    private void removePlayersWithoutCardsFromTheGame() {
        LinkedList newPlayerCards = new LinkedList();

        for (int i = 0; i < playerCards.size(); i++) {
            if (!((LinkedList<Card>) playerCards.get(i)).isEmpty()) {
                newPlayerCards.add((LinkedList<Card>) playerCards.get(i));
            }
        }

        playerCards = newPlayerCards;
        numberOfPlayers = playerCards.size();
        aPlayerHasNoMoreCards = false;

        System.out.println("The number of players is now:  " + numberOfPlayers);
    }

    private void outputAllPlayerCards() {
        // Output the cards for each player.
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Player " + i + " cards:  ");
            outpoutCards((LinkedList) playerCards.get(i));
        }
    }

    private void outpoutCards(LinkedList<Card> cards) {
        System.out.println("The number of cards:  " + cards.size());
        for (Card card : cards) {
            System.out.println("The card suit: " + card.getSuit() + ", The card rank: " + card.getRank());
        }
    }

    public LinkedList getPlayerCards() {
        return playerCards;
    }

}
