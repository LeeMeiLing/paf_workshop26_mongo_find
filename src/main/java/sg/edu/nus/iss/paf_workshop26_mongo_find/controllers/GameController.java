package sg.edu.nus.iss.paf_workshop26_mongo_find.controllers;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.paf_workshop26_mongo_find.exceptions.GameNotFoundException;
import sg.edu.nus.iss.paf_workshop26_mongo_find.models.Game;
import sg.edu.nus.iss.paf_workshop26_mongo_find.models.Response;
import sg.edu.nus.iss.paf_workshop26_mongo_find.services.GameService;

@RestController
public class GameController {

    @Autowired
    private GameService gameSvc;
    
    @GetMapping(path={"/games"}, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getGamesByName(@RequestParam(required=true) String gameName, @RequestParam(defaultValue = "25") int limit, @RequestParam(defaultValue = "0") int offset) throws GameNotFoundException{

        Optional<Response> response = gameSvc.findGamesByName(gameName,limit,offset);
        if(response.isEmpty()){
            throw new GameNotFoundException(String.format("Cannot find any game with name \" %s \"",gameName));
        }
        return new ResponseEntity<>(response.get(), HttpStatus.OK); // if optional.isEmpty, return message
    }

    @GetMapping(path={"/games/rank"}, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getGamesByRank(@RequestParam(defaultValue = "25") int limit, @RequestParam(defaultValue = "0") int offset) throws GameNotFoundException{

        Optional<Response> response = gameSvc.findGamesByRank(limit,offset);
        if(response.isEmpty()){
            throw new GameNotFoundException("No result found, try other limit & offset value");
        }

        return new ResponseEntity<>(response.get(), HttpStatus.OK); // if optional.isEmpty, return message
    }

    // use gid to find
    @GetMapping(path={"/games/{gid}"}, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> getGamesByGid(@PathVariable Integer gid, @RequestParam(defaultValue = "25") int limit, @RequestParam(defaultValue = "0") int offset) throws GameNotFoundException{

        Optional<Game> result = gameSvc.findGameByGid(gid,limit,offset);
        if(result.isEmpty()){
            throw new GameNotFoundException("Cannot find any game with gid <" + gid + ">");
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    // use ObjectId "_id" to find
    @GetMapping(path={"/games/id/{game_id}"}, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Game> getGamesById(@PathVariable String game_id, @RequestParam(defaultValue = "25") int limit, @RequestParam(defaultValue = "0") int offset) throws GameNotFoundException{

        Optional<Game> result = gameSvc.findGameById(game_id,limit,offset);
        if(result.isEmpty()){
            throw new GameNotFoundException("Cannot find any game with Id <" + game_id + ">");
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);

    }

}
