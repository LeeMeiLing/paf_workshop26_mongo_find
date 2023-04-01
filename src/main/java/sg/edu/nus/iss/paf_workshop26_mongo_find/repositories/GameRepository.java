package sg.edu.nus.iss.paf_workshop26_mongo_find.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_workshop26_mongo_find.exceptions.GameNotFoundException;

@Repository
public class GameRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.games.find( { name: {$regex: "gameName", $options: "i"} } )
     */

    // public List<Game> findGamesByName(String gameName, int limit, int offset){
    public List<Document> findGamesByName(String gameName, int limit, int offset){

        // create criteria
        // create query

            // Query query = Query.query(
            //     Criteria.where("name").regex(".*" + gameName + ".*","i")    // use pattern /.*name.*/ to search
            //     ).skip(offset).limit(limit);
        Query query = Query.query(
            Criteria.where("name").regex(gameName,"i") // use exact name (case insensitive) to search
            ).skip(offset).limit(limit);

        query.fields().include("_id","name");

        // perform query: mongoTemplate.find(query, return type, collection_name)
        List<Document> docs = mongoTemplate.find(query, Document.class,"games"); // return Empty list if game not found
        // List<Game> docs = mongoTemplate.find(query, Game.class,"games"); 

        return docs;

    }

    /*
     * db.games.find(
            {},
            {_id:1, name:1}
        ).sort(
            {ranking:1}
        ).skip(offset).limit(limit)

     */
    public List<Document> findGamesByRank(int limit, int offset){

        // create criteria
        // create query
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "ranking"))
                .skip(offset)
                .limit(limit)
                .fields().include("_id","name");

        // perform query: mongoTemplate.find(query, return type, collection_name)
        List<Document> docs = mongoTemplate.find(query, Document.class,"games"); // return Empty list if game not found

        System.out.println(">>>> GameRepo docs = " + docs); // debug

        return docs;

    }

    // find by gid
    /*
     * db.games.find( {_id: ObjectId("6427051274977736e8331ef0")} )
     */
    public Optional<Document> findGameByGid(int gid, int limit, int offset){

        // create criteria
        // create query
        Query query = Query.query(
            Criteria.where("gid").is(gid)
        );

        // perform query
        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, "games"));

    }

    // find by _id
    /*
     * db.games.find( { _id: ObjectId("6427051274977736e832f5f7")} )
     */
    public Optional<Document> findGameById(String game_id, int limit, int offset) throws GameNotFoundException{

        
        try{
            // create ObjectId
            ObjectId doc_id = new ObjectId(game_id);

            // perform query
            return Optional.ofNullable(mongoTemplate.findById(doc_id, Document.class, "games"));

        }catch(Exception ex){
            throw new GameNotFoundException("Invalid Id");
        }

    }

    /*
     * db.comments.aggregate([
            { $match: { gid: 88 } },
            { $group: 
                { 
                    _id: "$gid" ,
                    count : { $sum:1 },
                    average: { $avg: "$rating" }
                }
            }
        ])
     */
    public Float findAverageRating(int gid){

        // create operation
        MatchOperation matchByGid = Aggregation.match(Criteria.where("gid").is(gid));
        GroupOperation groupByGid = Aggregation.group("gid")
                                                .count().as("count")
                                                .avg("rating").as("average");
                                
        // create pipeline
        Aggregation pipeline = Aggregation.newAggregation(matchByGid, groupByGid);
        

        // perform query: mongoTemplate.aggregate(pipeline, collection_name, return_type)
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "comments",Document.class);

        Float averate_rating = results.getUniqueMappedResult().getDouble("average").floatValue();

        return averate_rating;

    }



}
