package com.mlc;

public class Main {

    public static void main(String[] args) {

        // Play a Game of War with a standard card deck and two players.

        // The Standard deck of cards has four suits and thirteen ranks.
        int numberOfSuits = 4;
        int numberOfRanks = 13;

        int numberOfPLayers = 2;

        // Play the Game of War
        War war = new War();
        war.play(numberOfSuits, numberOfRanks, numberOfPLayers);
    }

}
