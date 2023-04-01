package sg.edu.nus.iss.paf_workshop26_mongo_find.models;

import org.bson.Document;

public class GameName {
    
    private String game_id; // _id
    private String name;

    public String getGame_id() {
        return game_id;
    }
    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "GameName [game_id=" + game_id + ", name=" + name + "]";
    }

    public static GameName toGame(Document doc){

        GameName gameName = new GameName();
        gameName.setGame_id(doc.getObjectId("_id").toString()); // convert to string, else _id will shown as timestamp when auto convert response to ResponseEntity<Response>
        gameName.setName(doc.getString("name"));
        return gameName;
    }    
    
}
