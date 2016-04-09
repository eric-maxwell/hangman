package com.insitu.eric.maxwell.hangman;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/v1/hangman/game")
public class HangmanController {

    private static final String template = "Welcome to hangman, %s!";
    private final AtomicLong counter = new AtomicLong();

    @ApiOperation(value = "new", nickname = "new")
    @RequestMapping(method = RequestMethod.GET, path="/new/{name}", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "friendly name", required = false, dataType = "string", paramType = "path", defaultValue="Unforgiven")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Token.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public Token createGame(@PathVariable(value="name") String name) {
        RestTemplate restTemplate = new RestTemplate();
        String word = restTemplate.getForObject("http://randomword.setgetgo.com/get.php", String.class);
        return new Token(new Game(java.util.UUID.randomUUID(),
                String.format(template, name), word));
    }

    @ApiOperation(value = "guess", nickname = "guess")
    @RequestMapping(method = RequestMethod.GET, path="/{id}/guess/{letter}", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "string", paramType = "path", defaultValue=""),
            @ApiImplicitParam(name = "letter", value = "letter", required = false, dataType = "string", paramType = "path", defaultValue="")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Guess.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public Guess guess(@PathVariable(value="id") String id, @PathVariable(value="letter") String letter) {
            Game game = Game.load(id);
            return game.checkGuess(letter);
    }

    @ApiOperation(value = "check", nickname = "check")
    @RequestMapping(method = RequestMethod.GET, path="/{id}/check/{word}", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "string", paramType = "path", defaultValue=""),
            @ApiImplicitParam(name = "word", value = "word", required = false, dataType = "string", paramType = "path", defaultValue="")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Boolean.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public Boolean checkAnswer(@PathVariable(value="id") String id, @PathVariable(value="word") String word) {
        Game game = Game.load(id);
        return game.checkAnswer(word);
    }

    @ApiOperation(value = "word", nickname = "word")
    @RequestMapping(method = RequestMethod.GET, path="/{id}/word", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "string", paramType = "path", defaultValue="")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Word.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public Word getWord(@PathVariable(value="id") String id) {
        Game game = Game.load(id);
        return game.getWord();
    }
}