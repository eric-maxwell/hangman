package com.insitu.eric.maxwell.hangman;

import java.util.HashMap;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;


public class Token {
    private final UUID id;
    private HashMap<Integer, String> letterPositions = new HashMap<Integer,String>();

    public Token(Game game){
        this.id = game.getId();
        this.letterPositions = game.getLetterPositions();
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The unique identifier for the game.", required = true)
    public UUID getId() {

        return id;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Number of positions for the random word selected.", required = true)
    public HashMap<Integer, String> getLetterPositions() {
        return letterPositions;
    }

    public void setLetterPositions(HashMap<Integer, String> letterPositions) {
        this.letterPositions = letterPositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token = (Token) o;

        return id != null ? id.equals(token.id) : token.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
