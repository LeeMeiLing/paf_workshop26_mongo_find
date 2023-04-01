package sg.edu.nus.iss.paf_workshop26_mongo_find.models;

import java.time.LocalDateTime;
import java.util.List;

public class Response {
    
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

     private List<GameName> games;
     private Integer offset;
     private Integer limit;
     private Integer total;
     private LocalDateTime timestamp;

    public List<GameName> getGames() {
        return games;
    }
    public void setGames(List<GameName> games) {
        this.games = games;
    }
    public Integer getOffset() {
        return offset;
    }
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
 
    @Override
    public String toString() {
        return "Response [games=" + games + ", offset=" + offset + ", limit=" + limit + ", total=" + total
                + ", timestamp=" + timestamp + "]";
    }
  
}
