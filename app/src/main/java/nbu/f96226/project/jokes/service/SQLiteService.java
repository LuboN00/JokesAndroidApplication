package nbu.f96226.project.jokes.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import nbu.f96226.project.jokes.Utils.Constants;
import nbu.f96226.project.jokes.Utils.ContentValueBuilder;
import nbu.f96226.project.jokes.model.JokesApiResponse;

public class SQLiteService extends SQLiteOpenHelper {

    private static SQLiteService singleInstance = null;

    public static SQLiteService getSQLiteServiceInstance(Context context) {
        if (singleInstance == null) {
            singleInstance = new SQLiteService(context);
        }

        return singleInstance;
    }

    private final SQLiteDatabase database = getReadableDatabase();

    private SQLiteService(Context context) {
        super(context, Constants.DATABASE_NAME, null ,Constants.DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.SQL.CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.SQL.DELETE_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertNewJoke(int jokeId, String joke, String category) {
        ContentValues values = ContentValueBuilder.createBuilder()
                .withValue(Constants.LikedJokesTable.COLUMN_NAME_JOKE_ID, jokeId)
                .withValue(Constants.LikedJokesTable.COLUMN_NAME_JOKE, joke)
                .withValue(Constants.LikedJokesTable.COLUMN_NAME_CATEGORY, category)
                .build();

        database.insert(
                Constants.LikedJokesTable.TABLE_NAME,
                null,
                values
        );
    }

    public void unlikeJoke(int jokeId) {
        database.delete(
                Constants.LikedJokesTable.TABLE_NAME,
                Constants.LikedJokesTable.COLUMN_NAME_JOKE_ID + " = ?",
                new String[] {String.valueOf(jokeId)}
        );
    }

    public boolean isJokeLiked(int jokeId) {
        Cursor cursor = database.query(
                Constants.LikedJokesTable.TABLE_NAME,
                null,
                Constants.LikedJokesTable.COLUMN_NAME_JOKE_ID + " = ?",
                new String[] { String.valueOf(jokeId) },
                null,
                null,
                null
        );

        return cursor.getCount() != 0;
    }

    public List<JokesApiResponse> getAllLikedJokes() {
        String[] projections = {
            Constants.LikedJokesTable.COLUMN_NAME_JOKE_ID,
            Constants.LikedJokesTable.COLUMN_NAME_JOKE,
            Constants.LikedJokesTable.COLUMN_NAME_CATEGORY
        };

        Cursor cursor = database.query(
                Constants.LikedJokesTable.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null
        );

        return getJokesFromQuery(cursor);
    }

    private static List<JokesApiResponse> getJokesFromQuery(Cursor cursor) {
        List<JokesApiResponse> responses = new ArrayList<>();

        while (cursor.moveToNext()) {
            int jokeId = cursor.getInt(cursor.getColumnIndexOrThrow(Constants.LikedJokesTable.COLUMN_NAME_JOKE_ID));
            String joke = cursor.getString(cursor.getColumnIndexOrThrow(Constants.LikedJokesTable.COLUMN_NAME_JOKE));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(Constants.LikedJokesTable.COLUMN_NAME_CATEGORY));

            JokesApiResponse response = new JokesApiResponse(jokeId, joke, category);
            responses.add(response);
        }

        return  responses;
    }
}
