package xyz.msyawqi.syawqi.cataloguemovie;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import xyz.msyawqi.syawqi.cataloguemovie.database.Favorite;
import xyz.msyawqi.syawqi.cataloguemovie.database.FavoriteHelper;

import static xyz.msyawqi.syawqi.cataloguemovie.database.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;

    private Cursor list;
    private FavoriteCardAdapter adapter;
//    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getSupportActionBar().setTitle("Notes");

        recyclerView = findViewById(R.id.listFavorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        progressBar = (ProgressBar)findViewById(R.id.progressbar);

//        favoriteHelper = new FavoriteHelper(this);
//        try {
//            favoriteHelper.open();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        adapter = new FavoriteCardAdapter(this);
        adapter.setListNotes(list);
        recyclerView.setAdapter(adapter);

//        new LoadNoteAsync().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadNoteAsync().execute();
    }


    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.e("TAG", "doInBackground: " + CONTENT_URI);
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);

            list = notes;
            adapter.setListNotes(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
