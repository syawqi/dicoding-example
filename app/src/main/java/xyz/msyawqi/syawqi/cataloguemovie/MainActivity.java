package xyz.msyawqi.syawqi.cataloguemovie;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    ListView listView;
    MovieAdapter movieAdapter;
    EditText editTitle;
    Button buttonSearch;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieAdapter = new MovieAdapter(this);
        movieAdapter.notifyDataSetChanged();
        listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(movieAdapter);
        editTitle = (EditText)findViewById(R.id.edit_title);
        buttonSearch = (Button)findViewById(R.id.btn_search);

        buttonSearch.setOnClickListener(myListener);

        String title = "Captain";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE,title);

        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, Bundle bundle) {
        String movieList = "";
        if (bundle != null){
            movieList = bundle.getString(EXTRAS_MOVIE);
        }

        return new MovieAsyncTaskLoader(this,movieList);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> movieItems) {
        movieAdapter.setData(movieItems);
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
