package nbu.f96226.project.jokes.Utils;

public class Constants {

    private Constants() {

    }

    public static final String JOKES_BASE_URL = "https://v2.jokeapi.dev/joke/Any?type=single";
    public static final Integer DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jokes.db";

    public static class SQL {
        public static final String CREATE_TABLE =
                "CREATE TABLE " + LikedJokesTable.TABLE_NAME + " (" +
                        LikedJokesTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                        LikedJokesTable.COLUMN_NAME_JOKE_ID + " INTEGER," +
                        LikedJokesTable.COLUMN_NAME_CATEGORY + " TEXT," +
                        LikedJokesTable.COLUMN_NAME_JOKE + " TEXT)";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXISTS " + LikedJokesTable.TABLE_NAME;

    }

    public static class LikedJokesTable {
        public static final String TABLE_NAME = "liked_jokes";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_JOKE_ID = "joke_id";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_JOKE = "joke";
    }

}
