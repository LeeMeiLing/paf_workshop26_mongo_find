package sg.edu.nus.iss.paf_workshop26_mongo_find.models;

import java.sql.Timestamp;
import java.time.Instant;

import org.bson.Document;

import jakarta.json.JsonObject;

public class Game {

     /*
      * {
            "_id" : ObjectId("6427051274977736e832f5f7"),
            "gid" : NumberInt(6),
            "name" : "Mare Mediterraneum",
            "year" : NumberInt(1989),
            "ranking" : NumberInt(8208),
            "users_rated" : NumberInt(79),
            "url" : "https://www.boardgamegeek.com/boardgame/6/mare-mediterraneum",
            "image" : "https://cf.geekdo-images.com/micro/img/ldvo1mbj_Kec_8tc8MUjE-zUddQ=/fit-in/64x64/pic28424.jpg"
        }
      */

    /*
     * {
            game_id: <ID field>,   // this is _id, not gid
            name: <Name field>,
            year: <Year field>,
            ranking: <Rank field>,
            average: <Average field>,    // calculate the average rating from comments, divide by users_rated?
            users_rated: <Users rated field>,
            url: <URL field>,
            thumbnail: <Thumbnail field>,
            timestamp: <result timestamp> // generate timestamp
        }
     */
   
    private String game_id; // _id
    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Float average;
    private Integer users_rated;
    private String url;
    private String thumbnail;
    private Timestamp timestamp; // try datetime

    public String getGame_id() {
        return game_id;
    }
    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }
    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public Float getAverage() {
        return average;
    }
    public void setAverage(Float average) {
        this.average = average;
    }
    public Integer getUsers_rated() {
        return users_rated;
    }
    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Game [game_id=" + game_id + ", gid=" + gid + ", name=" + name + ", year=" + year + ", ranking="
                + ranking + ", average=" + average + ", users_rated=" + users_rated + ", url=" + url + ", thumbnail="
                + thumbnail + ", timestamp=" + timestamp + "]";
    }
    public static JsonObject toJson(Game game){

        return null;
    }    

    public static Game toGame(Document doc, Float average){

        Game game = new Game();
        game.setGame_id(doc.getObjectId("_id").toString());
        game.setGid(doc.getInteger("gid"));
        game.setName(doc.getString("name"));
        game.setYear(doc.getInteger("year"));
        game.setRanking(doc.getInteger("ranking"));
        game.setAverage(average);
        game.setUsers_rated(doc.getInteger("users_rated"));
        game.setUrl(doc.getString("url"));
        game.setThumbnail(doc.getString("image"));
        game.setTimestamp(Timestamp.from(Instant.now()));
        return game;

    }    

    

    
}
