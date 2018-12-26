package xyz.msyawqi.syawqi.cataloguemovie.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.msyawqi.syawqi.cataloguemovie.MovieCardAdapter;
import xyz.msyawqi.syawqi.cataloguemovie.MovieItem;
import xyz.msyawqi.syawqi.cataloguemovie.R;

public class UpCommingFaragment extends Fragment
        implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {
    RecyclerView listView;
    MovieCardAdapter movieAdapter;
    private List<MovieItem> movieList;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    public UpCommingFaragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_now_playing, container, false);

        movieAdapter = new MovieCardAdapter(getActivity()) {
        };
        listView = rootview.findViewById(R.id.nowplaylistView);
        movieList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

        String title = "Captain";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE,title);
        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(0, bundle, this);

        return rootview;

    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, Bundle bundle) {
        String movieList = "";
        String type = "upcoming";

        if (bundle != null){
            movieList = bundle.getString(EXTRAS_MOVIE);
        }
//        Log.d("TAG", "onCreateLoader: " + movieList);
        return new MovieTaskLoaderFragments(getContext(),movieList, type);

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

}
