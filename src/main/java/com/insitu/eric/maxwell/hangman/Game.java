package com.insitu.eric.maxwell.hangman;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class Game implements Serializable{

    private UUID id;
    private String friendlyName;
    @JsonIgnore
    private final String word;
    private final Integer numberOfLetters;
    private Integer numberOfGuesses;
    private final Integer numberOfGuessesAllowed = 6;
    private HashMap<Integer, String> letterPositions = new HashMap<Integer,String>();

    public Game(java.util.UUID id, String name, String word) {
        this.id = id;
        this.friendlyName = name;
        this.numberOfLetters = word.length();
        this.numberOfGuesses = 0;
        this.word = word;

        for(int position = 0; position < this.word.length(); position++){
            this.letterPositions.put(position, null);
        }

        Game.save(this);

    }

    public static void save(Game game){
        try {
            FileOutputStream fout = new FileOutputStream(game.getId().toString());
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(game);
            oos.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static Game load(String id){
        try {
            FileInputStream streamIn = new FileInputStream(id);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            Game game = (Game) objectinputstream.readObject();
            return game;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkAnswer(String word){
        if(this.word.toUpperCase().equals(word.toUpperCase())){
            return true;
        }
        return false;
    }

    public Guess checkGuess(String letter){
        numberOfGuesses++;
        int index = this.word.toUpperCase().indexOf(letter.toUpperCase());
        if(index > -1){
            this.letterPositions.put(index, letter.toUpperCase());
            while(index > -1){
                index = this.word.toUpperCase().indexOf(letter.toUpperCase(),  index + 1);
                if(index > -1){
                    this.letterPositions.put(index, letter.toUpperCase());
                }
            }
            Game.save(this);
            return new Guess(true, numberOfGuesses, numberOfGuessesAllowed - numberOfGuesses, this.letterPositions);
        }
        Game.save(this);
        return new Guess(false, numberOfGuesses, numberOfGuessesAllowed - numberOfGuesses, this.letterPositions);
    }

    public Word getWord() {
        return new Word(this.word);
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The unique identifier for the game.", required = true)
    public java.util.UUID getId() {
        return id;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The friendly name of the game.", required = true)
    public String getFriendlyName() {
        return friendlyName;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The number of letters in the word to be guessed.", required = true)
    public Integer getNumberOfLetters() {
        return numberOfLetters;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The number of letters guessed.", required = true)
    public Integer getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public HashMap<Integer, String> getLetterPositions() {
        return letterPositions;
    }

    public void setLetterPositions(HashMap<Integer, String> letterPositions) {
        this.letterPositions = letterPositions;
    }
}
