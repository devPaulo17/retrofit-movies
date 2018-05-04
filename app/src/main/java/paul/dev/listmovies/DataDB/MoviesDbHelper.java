package paul.dev.listmovies.DataDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import paul.dev.listmovies.Models.Movie;

/**
 * Created by paulotrujillo on 5/1/18.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        db.execSQL("CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " ("

                + MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MovieContract.MovieEntry.ID + " TEXT NOT NULL,"
                + MovieContract.MovieEntry.NAME + " TEXT NOT NULL,"
                + MovieContract.MovieEntry.IMAGE + " TEXT NOT NULL,"
                + MovieContract.MovieEntry.ORIGINAL_TITLE + " TEXT NOT NULL,"
                + MovieContract.MovieEntry.DESCRIPCION + " TEXT NOT NULL,"
                + MovieContract.MovieEntry.VOTE_COUNT + " INT NOT NULL,"
                + MovieContract.MovieEntry.VOTE_AVERAGE + " REAL NOT NULL,"
                + MovieContract.MovieEntry.POPULARITY + " REAL NOT NULL,"
                + MovieContract.MovieEntry.IMAGE_POSTER + " REAL NOT NULL,"
                + MovieContract.MovieEntry.DATE_PREMIER + " TEXT ,"
                + MovieContract.MovieEntry.HOMEPAGE + " TEXT,"
                + MovieContract.MovieEntry.CATEGORY + " INT,"
                + MovieContract.MovieEntry.LANGUAGES_ORIGINAL + " TEXT)");








    }



    public long saveMovies( Movie movie) {

        SQLiteDatabase db = getWritableDatabase();

        return db.insert(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                movie.toContentValues());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MoviesDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);

        onCreate(db);
    }



    public Cursor getAll(int i) {
        return getReadableDatabase()
                .query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        null,
                        MovieContract.MovieEntry.CATEGORY + " LIKE ?",
                            new String []{String.valueOf(i)},
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getMovieById(String movieId) {



        Cursor c = getReadableDatabase().query(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                MovieContract.MovieEntry.ID + " LIKE ?",
                new String[]{movieId},
                null,
                null,
                null);
        return c;
    }

}
