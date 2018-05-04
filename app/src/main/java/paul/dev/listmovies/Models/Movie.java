package paul.dev.listmovies.Models;

import android.content.ContentValues;
import android.database.Cursor;

import paul.dev.listmovies.DataDB.MovieContract;

/**
 * Created by paulotrujillo on 5/1/18.
 */

public class Movie {

    private String id;
    private int vote_count;
    private Double vote_average;
    private Double popularity;
    private String original_language;
    private String title;
    private String overview;
    private String homepage;
    private String original_title;
    private String backdrop_path;
    private String poster_path;
    private String release_date;
    private int category;
    


    public  Movie(String id,String title, String overview,String original_title, int vote_count, Double vote_average,Double popularity, String original_language,String backdrop_path,String poster_path,String release_date,String homepage,int category){

        this.id = id;
        this.title = title;
        this.overview = overview;
        this.original_title = original_title;
        this.vote_count = vote_count;
        this.popularity = popularity;
        this.vote_average = vote_average;
        this.original_language = original_language;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.homepage = homepage;
        this.category = category;


    }


    public Movie(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.ID));
        title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.NAME));
        overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.DESCRIPCION));
        original_title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.ORIGINAL_TITLE));
        vote_count = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.VOTE_COUNT));
        popularity = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.POPULARITY));
        vote_average = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.VOTE_AVERAGE));
        original_language = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.LANGUAGES_ORIGINAL));
        backdrop_path = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.IMAGE));
        poster_path = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.IMAGE_POSTER));
        release_date = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.DATE_PREMIER));
        homepage = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.HOMEPAGE));
        category = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.CATEGORY));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.ID, id);
        values.put(MovieContract.MovieEntry.NAME, title);
        values.put(MovieContract.MovieEntry.DESCRIPCION, overview);
        values.put(MovieContract.MovieEntry.ORIGINAL_TITLE, original_title);
        values.put(MovieContract.MovieEntry.VOTE_COUNT, vote_count);
        values.put(MovieContract.MovieEntry.POPULARITY, popularity);
        values.put(MovieContract.MovieEntry.VOTE_AVERAGE, vote_average);
        values.put(MovieContract.MovieEntry.LANGUAGES_ORIGINAL, original_language);
        values.put(MovieContract.MovieEntry.IMAGE, backdrop_path);
        values.put(MovieContract.MovieEntry.IMAGE_POSTER, poster_path);
        values.put(MovieContract.MovieEntry.HOMEPAGE, homepage);
        values.put(MovieContract.MovieEntry.CATEGORY, category);
        return values;
    }


    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getoverview() {
        return overview;
    }

    public void setoverview(String overview) {
        this.overview = overview;
    }

    public String getoriginal_title() {
        return original_title;
    }

    public void setoriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public Double getVote_avergge() {
        return vote_average;
    }

    public void setVote_avergge(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getoriginal_language() {
        return original_language;
    }

    public void setoriginal_language(String original_language) {
        this.original_language = original_language;
    }


    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImage() {
        return backdrop_path;
    }

    public void setImage(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
