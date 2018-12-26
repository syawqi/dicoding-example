package xyz.msyawqi.syawqi.cataloguemovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

public class FavoriteHelper {
    private static final String TABLE_NOTE = DatabaseContract.TABLE_NOTE;
    private static String DATABASE_TABLE = TABLE_NOTE;
    private Context context;
    private DatabaseHelper dataBaseHelper;
    String _ID = "id";
    String MOVIEID = "movie";
    String TITLE = "title";

    String DESCRIPTION = "description";

    String DATE = "daterelease";

    String IMAGE = "image";

    private SQLiteDatabase database;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Favorite> query(){
        ArrayList<Favorite> arrayList = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,null,null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount()>0) {
            do {

                favorite = new Favorite();
                favorite.setMovie(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIEID)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favorite.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                favorite.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                favorite.setImage(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));

                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        Log.d("TAG", "query: " + arrayList);
        return arrayList;
    }

    public long insert(Favorite favorite){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(MOVIEID, favorite.getMovie());
        initialValues.put(TITLE, favorite.getTitle());
        initialValues.put(DESCRIPTION, favorite.getDesc());
        initialValues.put(DATE, favorite.getDate());
        initialValues.put(IMAGE, favorite.getImage());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Favorite favorite){
        ContentValues args = new ContentValues();
        args.put(_ID, favorite.getMovie());
        args.put(TITLE, favorite.getTitle());
        args.put(DESCRIPTION, favorite.getDesc());
        args.put(DATE, favorite.getDate());
        args.put(IMAGE, favorite.getImage());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + favorite.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLE_NOTE, MOVIEID + " = '"+id+"'", null);
    }
}
