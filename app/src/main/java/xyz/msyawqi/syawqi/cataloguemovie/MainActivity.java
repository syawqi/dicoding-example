package xyz.msyawqi.syawqi.cataloguemovie;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {
    RecyclerView listView;
    MovieCardAdapter movieAdapter;
    EditText editTitle;
    Button buttonSearch;
    private List<MovieItem> movieList;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieAdapter = new MovieCardAdapter(this) {
        };
        listView = (RecyclerView)findViewById(R.id.listView);
        movieList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

        editTitle = (EditText)findViewById(R.id.edit_title);
        buttonSearch = (Button)findViewById(R.id.btn_search);

        String buttonText = String.format(getResources().getString(R.string.btn_cari));
        String editHint = String.format(getResources().getString(R.string.search_place_holder));

        editTitle.setHint(editHint);
        buttonSearch.setText(buttonText);
        buttonSearch.setOnClickListener(myListener);

        String title = "Captain";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE,title);

        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, Bundle bundle) {
        String movieList = "";
        String type = "nowplay";
        String title = editTitle.getText().toString();

        Log.d("TAG", "onCreateLoader: " + editTitle.getText());
        if (!title.isEmpty()){
            type = "search";
        }

        if (bundle != null){
            movieList = bundle.getString(EXTRAS_MOVIE);
        }
//        Log.d("TAG", "onCreateLoader: " + movieList);
        return new MovieAsyncTaskLoader(this,movieList, type);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> movieItems) {
        movieAdapter.setData(movieItems);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        movieAdapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = editTitle.getText().toString();

            if (TextUtils.isEmpty(title))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE,title);
            getLoaderManager().restartLoader(0,bundle,MainActivity.this);
        }
    };
}
