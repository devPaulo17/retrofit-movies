package paul.dev.listmovies.Models;

/**
 * Created by paulotrujillo on 5/3/18.
 */

public class VideoMovie {

    private String id;
    private String key;
    private String name;
    private String site;
    private String type;


    public VideoMovie(String id, String key, String site, String type){

        this.id = id;
        this.key = key;
        this.site = site;
        this.type = type;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
