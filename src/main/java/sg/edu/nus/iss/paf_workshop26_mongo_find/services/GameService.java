package sg.edu.nus.iss.paf_workshop26_mongo_find.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paf_workshop26_mongo_find.exceptions.GameNotFoundException;
import sg.edu.nus.iss.paf_workshop26_mongo_find.models.Game;
import sg.edu.nus.iss.paf_workshop26_mongo_find.models.GameName;
import sg.edu.nus.iss.paf_workshop26_mongo_find.models.Response;
import sg.edu.nus.iss.paf_workshop26_mongo_find.repositories.GameRepository;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepo;

    public Optional<Response> findGamesByName(String gameName, int limit, int offset){

        List<Document> docs = gameRepo.findGamesByName(gameName,limit,offset);
        // List<Game> docs = gameRepo.findGamesByName(gameName,limit,offset);

        /*
        *  games: [ <array of games> ],
            offset: <offset or default value>,
            limit: <limit or default value>,
            total: <total number of games>,
            timestamp: <result timestamp>  // generate timestamp
        */
        /*
        *  game_id: <ID field>,
            name: <Name field>
        */

        if(docs.isEmpty()){
            return Optional.empty();
        }

        List<GameName> games = docs.stream().map(d -> GameName.toGame(d)).collect(Collectors.toList());

        // System.out.println(">>>> Games");
        // System.out.println(games);

        Response response = new Response();
        response.setGames(games);
        response.setLimit(limit);
        response.setOffset(offset);
        response.setTotal(games.size());
        response.setTimestamp(LocalDateTime.now());

        return Optional.of(response);
    }

    public Optional<Response> findGamesByRank(int limit, int offset){

        List<Document> docs = gameRepo.findGamesByRank(limit,offset);
     
        if(docs.isEmpty()){
            return Optional.empty();
        }

        List<GameName> games = docs.stream().map(d -> GameName.toGame(d)).collect(Collectors.toList());

        Response response = new Response();
        response.setGames(games);
        response.setLimit(limit);
        response.setOffset(offset);
        response.setTotal(games.size());
        response.setTimestamp(LocalDateTime.now());

        System.out.println(">>>> Response");
        System.out.println(response);

        return Optional.of(response);
    }

    public Optional<Game> findGameByGid(int gid, int limit, int offset){

        Optional<Document> doc = gameRepo.findGameByGid(gid, limit, offset);
     
        if(doc.isEmpty()){
            return Optional.empty();
        }

        // List<Float> ratings = gameRepo.findCommentRatingsbyGid(gid);
        // use mongodb query to find all rating for that game
        /*
         * db.comments.find(
                { gid: 20437 },
                { rating:1 }
            )
         */

        // Integer rating_count = ratings.size();
        // if ( rating_count> 0){
        //     float sum = 0f;

        //     for(int i=0; i < ratings.size(); i++){
        //         sum = sum + ratings.get(i);
        //     }
        //     Float average = sum/rating_count;
        // }

        // Use aggregate query to get average rating of the game from comments
        Float average = gameRepo.findAverageRating(gid);

        Game game = Game.toGame(doc.get(),average);
        return Optional.of(game);
    }

    public Optional<Game> findGameById(String game_id, int limit, int offset) throws GameNotFoundException{

        
        Optional<Document> doc = gameRepo.findGameById(game_id, limit, offset);
     
        if(doc.isEmpty()){
            return Optional.empty();
        }

        Integer gid = doc.get().getInteger("gid");

        // Use aggregate query to get average rating of the game from comments
        Float average = gameRepo.findAverageRating(gid);

        Game game = Game.toGame(doc.get(),average);
        return Optional.of(game);
        
    }
    
    
    

}
