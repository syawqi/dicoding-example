package xyz.msyawqi.syawqi.cataloguemovie.fragments;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.msyawqi.syawqi.cataloguemovie.BuildConfig;
import xyz.msyawqi.syawqi.cataloguemovie.MovieItem;
import xyz.msyawqi.syawqi.cataloguemovie.R;

public class MovieTaskLoaderFragments extends AsyncTaskLoader<ArrayList<MovieItem>> {
    private ArrayList<MovieItem> mData;
    private boolean mHasResult = false;

    private String mMovieList;
    String purpose;

    public MovieTaskLoaderFragments(final Context context, String movieList, String _purpose) {
        super(context);

        onContentChanged();
        this.mMovieList = movieList;

        this.purpose = _purpose;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItem> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = BuildConfig.API_KEY;

    // Format search kota url JAKARTA = 1642911 ,BANDUNG = 1650357, SEMARANG = 1627896
    // http://api.openweathermap.org/data/2.5/group?id=1642911,1650357,1627896&units=metric&appid=API_KEY

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> movieItemses = new ArrayList<>();
        String url = null;
        if(this.purpose == "search"){
            url = "https://api.themoviedb.org/3/search/movie?api_key="+ API_KEY + "&language="+ getContext().getResources().getString(R.string.leaguage) +"&query=" + mMovieList;
        }else if (this.purpose == "upcoming"){
            url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+ API_KEY + "&language="+ getContext().getResources().getString(R.string.leaguage);
        }else {
            url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ API_KEY + "&language="+ getContext().getResources().getString(R.string.leaguage);
        }
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(movie);
                        movieItemses.add(movieItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return movieItemses;
    }

    protected void onReleaseResources(ArrayList<MovieItem> data) {
        //nothing to do.
    }
}
