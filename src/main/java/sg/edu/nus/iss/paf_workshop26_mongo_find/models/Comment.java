package sg.edu.nus.iss.paf_workshop26_mongo_find.models;

import org.bson.types.ObjectId;

public class Comment {
    /*
     *  "_id" : ObjectId("642705280ab15f0be75f157e"),
        "c_id" : "091910b9",
        "user" : "johnnyspys",
        "rating" : NumberInt(9),
        "c_text" : "Seriously one of the better games I have played as far as fun factor!  I have yet to play a game in which my opponents or I didn't have a blast.  My wife loves this game too.",
        "gid" : NumberInt(20437)
     */

    private ObjectId _id;
    private String c_id;
    private String user;
    private Float rating; // only 1-10 but for calculate average, use float
    private String c_text;
    private Integer gid;

    public ObjectId get_id() {
        return _id;
    }
    public void set_id(ObjectId _id) {
        this._id = _id;
    }
    public String getC_id() {
        return c_id;
    }
    public void setC_id(String c_id) {
        this.c_id = c_id;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }
    public String getC_text() {
        return c_text;
    }
    public void setC_text(String c_text) {
        this.c_text = c_text;
    }
    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }     


}
