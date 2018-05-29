package xyz.msyawqi.syawqi.cataloguemovie;

import org.json.JSONObject;

/**
 * Created by muhammadsyawqi on 20/03/18.
 */

public class MovieItem {
    private int id;
    private String title;
    private String description;
    private String dateRelease;
    private String image;

    public  MovieItem(JSONObject object){
        try {
            int id = object.getInt("id");
            String name = object.getString("title");
            String description = object.getString("overview");
            String dateRelease = object.getString("release_date");
            String image = "https://image.tmdb.org/t/p/w500" + object.getString("poster_path");

            this.id = id;
            this.title = name;
            this.description = description;
            this.dateRelease = dateRelease;
            this.image = image;

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return dateRelease;
    }
    public void setDate(String dateRelease) {
        this.dateRelease = dateRelease;
    }
    public String getDesc() {
        return description;
    }
    public void setDesc(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
