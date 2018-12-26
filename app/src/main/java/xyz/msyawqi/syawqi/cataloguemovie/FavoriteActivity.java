package xyz.msyawqi.syawqi.cataloguemovie;

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

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;

    private ArrayList<Favorite> list;
    private FavoriteCardAdapter adapter;
    private FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getSupportActionBar().setTitle("Notes");

        recyclerView = findViewById(R.id.listFavorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        favoriteHelper = new FavoriteHelper(this);
        try {
            favoriteHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        list = new ArrayList<>();

        adapter = new FavoriteCardAdapter(this);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);

//        new LoadNoteAsync().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadNoteAsync().execute();
    }


    private class LoadNoteAsync extends AsyncTask<Void, Void, ArrayList<Favorite>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            if (list.size() > 0){
                list.clear();
            }
        }

        @Override
        protected ArrayList<Favorite> doInBackground(Void... voids) {
            return favoriteHelper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<Favorite> favorite) {
            super.onPostExecute(favorite);
            progressBar.setVisibility(View.GONE);

            Log.d("TAG", "onPostExecute: " + favorite);
            list.addAll(favorite);
            adapter.setData(list);
            adapter.notifyDataSetChanged();

            if (list.size() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
