package xyz.msyawqi.syawqi.cataloguemovie.database;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {

    private int id;
    private int movie;
    private String title;
    private String description;
    private String dateRelease;
    private String image;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getMovie() {
        return id;
    }
    public void setMovie(int id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.movie);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.dateRelease);
        dest.writeString(this.image);
    }

    public Favorite() {
    }

    protected Favorite(Parcel in) {
        this.id = in.readInt();
        this.movie = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.dateRelease = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Favorite>CREATOR = new Parcelable.Creator<Favorite>() {

        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
