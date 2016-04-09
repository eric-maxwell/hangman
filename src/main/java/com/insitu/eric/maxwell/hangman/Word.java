package com.insitu.eric.maxwell.hangman;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class Word {
    private String contents;

    public Word(String word){
        this.contents = word;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The the random word for the game.", required = true)
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
