package xyz.msyawqi.syawqi.cataloguemovie.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_NOTE = "favorite";
    private DatabaseContract(){}
    public static final class FavoriteColumns implements BaseColumns {

        public static String MOVIEID = "movie";

        public static String TITLE = "title";

        public static String DESCRIPTION = "description";

        public static String DATE = "daterelease";

        public static String IMAGE = "image";

        public static final String AUTHORITY = "xyz.msyawqi.syawqi.cataloguemovie";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_NOTE)
                .build();
    }


    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

}
