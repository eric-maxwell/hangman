package com.insitu.eric.maxwell.hangman;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;


public class Guess {
    private final boolean correct;
    private final Integer numberOfLettersLeft;
    private final Integer numberOfGuesses;
    private final Integer numberOfGuessesLeft;
    private HashMap<Integer, String> letterPositions = new HashMap<Integer,String>();

    public Guess(boolean correct, Integer numberOfGuesses, Integer numberOfGuessesLeft, HashMap<Integer, String> letterPositions){
        this.correct = correct;
        this.numberOfGuesses = numberOfGuesses;
        this.letterPositions = letterPositions;
        this.numberOfGuessesLeft = numberOfGuessesLeft;
        int numLettersLeft = 0;
        for(int position = 0; position < letterPositions.size(); position++){
            if(letterPositions.get(position) == null)
                numLettersLeft++;
        }
        this.numberOfLettersLeft = numLettersLeft;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The letter exiats in the work.", required = true)
    public boolean isCorrect() {
        return correct;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The number of guesses attempted.", required = true)
    public Integer getNumberOfGuesses() {
        return numberOfGuesses;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The number of guesses left.", required = true)
    public Integer getNumberOfGuessesLeft() {
        return numberOfGuessesLeft;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "After this guess you have this many letters left to guess.", required = true)
    public Integer getNumberOfLettersLeft() {
        return numberOfLettersLeft;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Number of positions for the random word selected.", required = true)
    public HashMap<Integer, String> getLetterPositions() {
        return letterPositions;
    }
}
