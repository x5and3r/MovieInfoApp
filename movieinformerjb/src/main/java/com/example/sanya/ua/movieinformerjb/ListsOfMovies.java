package com.example.sanya.ua.movieinformerjb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ListsOfMovies extends Activity {
    private static final String DEBUG_TAG = "TMDBQueryManager";
    public final String TMDB_API_KEY = "58a82992b566f589ca43c37a73627799";
    ThreadPolicy tp = ThreadPolicy.LAX;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_progress);

        // Get the intent to get the query.
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);
        // Check if the NetworkConnection is active and connected.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new TMDBQueryManager().execute(query);
        } else {
            TextView textView = new TextView(this);
            textView.setText("No network connection.");
            setContentView(textView);
        }
    }

    public void updateViewWithResults(final ArrayList<MovieResult> result) {
        ListView listView = new ListView(this);
        Log.d("updateViewWithResults", result.toString());
        // Add results to listView.
        MovieAdapter adapter = new MovieAdapter(this, R.layout.imagetext_list, result);
        listView.setAdapter(adapter);
        if (adapter.isEmpty()) {
            TextView textView = new TextView(this);
            textView.setText("No movies found");
            setContentView(textView);
        }
        else
        // Update Activity to show listView
        setContentView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String queryID = "http://api.themoviedb.org/3/movie/" + result.get(pos).getId()+"?api_key=" +
                        TMDB_API_KEY;
                try {
                    StrictMode.setThreadPolicy(tp);
                    URL url = new URL(queryID);
                    InputStream stream = null;
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.addRequestProperty("Accept", "application/json");
                    conn.setDoInput(true);
                    conn.connect();
                    stream = conn.getInputStream();

                    JSONObject jsonObject = new JSONObject(stringify(stream));
                    String array =  jsonObject.getString("overview");
                    /*Intent for MovieTotalInfo.class which shows detailed information
                    about concrete movie from list */
                    Intent in = new Intent(getApplication(), MovieTotalInfo.class);
                    in.putExtra("title", result.get(pos).getTitle());
                    in.putExtra("date", result.get(pos).getReleaseDate());
                    in.putExtra("vote", result.get(pos).getVoteAverage());
                    in.putExtra("poster", result.get(pos).getPosterPath());
                    in.putExtra("popularity", result.get(pos).getPopularity());
                    in.putExtra("vcount", result.get(pos).getVoteCount());
                    in.putExtra("overview", array);
                    startActivity(in);
                }

                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (ProtocolException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private class TMDBQueryManager extends AsyncTask {

        @Override
        protected ArrayList<MovieResult> doInBackground(Object... params) {
            try {
                return searchTMDB((String) params[0]);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            updateViewWithResults((ArrayList<MovieResult>) result);
        };
    }

    public ArrayList<MovieResult> searchTMDB(String query) throws IOException {
        // Build URL
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(query);
        stringBuilder.append("api_key=" + TMDB_API_KEY);
        URL url = new URL(stringBuilder.toString());
        InputStream stream = null;
        try {
            // Establish a connection
            StrictMode.setThreadPolicy(tp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Accept", "application/json"); // Required to get TMDB to play nicely.
            conn.setDoInput(true);
            conn.connect();

            int responseCode = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response code is: " + responseCode + " " + conn.getResponseMessage());

            stream = conn.getInputStream();
            return parseResult(stringify(stream));
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
    //Function for parsing results from queries: Popular/Upcoming/Top_rated/Search
    private ArrayList<MovieResult> parseResult(String result) {
        String streamAsString = result;
        ArrayList<MovieResult> results = new ArrayList<MovieResult>();
        try {
            JSONObject jsonObject = new JSONObject(streamAsString);
            JSONArray array = (JSONArray) jsonObject.get("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonMovieObject = array.getJSONObject(i);
                MovieResult mBuilder = new MovieResult();
                mBuilder.setId(Integer.parseInt(jsonMovieObject.getString("id")));
                mBuilder.setTitle(jsonMovieObject.getString("title"));
                mBuilder.setBackdropPath(jsonMovieObject.getString("backdrop_path"));
                mBuilder.setOriginalTitle(jsonMovieObject.getString("original_title"));
                mBuilder.setPopularity(jsonMovieObject.getDouble("popularity"));
                mBuilder.setVoteAverage(jsonMovieObject.getDouble("vote_average"));
                mBuilder.setPosterPath(jsonMovieObject.getString("poster_path"));
                mBuilder.setReleaseDate(jsonMovieObject.getString("release_date"));
                mBuilder.setVoteCount(jsonMovieObject.getInt("vote_count"));
                results.add(mBuilder);
            }
        } catch (JSONException e) {
            System.err.println(e);
            Log.d(DEBUG_TAG, "Error parsing JSON. String was: " + streamAsString);
        }
        return results;
    }

    public String stringify(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        return bufferedReader.readLine();
    }

}
