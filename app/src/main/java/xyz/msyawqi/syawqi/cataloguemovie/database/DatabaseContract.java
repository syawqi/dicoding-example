package xyz.msyawqi.syawqi.cataloguemovie.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NOTE = "favorite";
    static final class FavoriteColumns implements BaseColumns {

        static String MOVIEID = "movie";

        static String TITLE = "title";

        static String DESCRIPTION = "description";

        static String DATE = "daterelease";

        static String IMAGE = "image";

    }

}
