package com.example.prithi.tvshowandmovietracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class TopRatedMovies extends ActionBarActivity {
    private final String KEY_TITLE = "title";
    private  final String KEY_YEAR = "Year";
    private  final String KEY_RATING = "Rating";
    private  final String KEY_THUMB_URL = "thumb_url";
    private  final String KEY_ID = "id";
    private final String KEY_OVERVIEW = "overview";


    String id;
    SecondaryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_movies);

        new TMDBUpComing().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_rated_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_logout:
                ParseUser.logOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();

        }
        finish();
        return true;
    }



    public void update(ArrayList<HashMap<String, String>> result) {

        ListView listView = new ListView(this);

        // Add results to listView.
        adapter = new SecondaryAdapter(this, result);
        listView.setAdapter(adapter);
        // Update Activity to show listView
        setContentView(listView);

    }

    public class TMDBUpComing extends AsyncTask {

        private final String TMDB_API_KEY = "474b56914cbb221a1f9b28e7f778c6a3";
        private static final String DEBUG_TAG = "TMDBQueryManager";

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Object... params) {
            try {
                return displayUpComingMovies();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            update((ArrayList<HashMap<String, String>>) result);
        };


        public ArrayList<HashMap<String, String>> displayUpComingMovies()
                throws IOException {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://api.themoviedb.org/3/movie/top_rated");
            stringBuilder.append("?api_key=" + TMDB_API_KEY);

            URL url = new URL(stringBuilder.toString());

            InputStream stream = null;
            try {
                // Establish a connection
                HttpURLConnection conn = (HttpURLConnection) url .openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.connect();

                int responseCode = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response code is: " + responseCode + " " + conn.getResponseMessage());

                stream = conn.getInputStream();
                return parseUpComingMovies(convertInputStreamToString(stream));
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        private ArrayList<HashMap<String, String>> parseUpComingMovies(String result) {
            String streamAsString = result;
            ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
            try {
                JSONObject jsonObject = new JSONObject(streamAsString);
                JSONArray array = (JSONArray) jsonObject.get("results");
                for (int i = 0; i < array.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonMovieObject = array.getJSONObject(i);
                    map.put(KEY_TITLE, jsonMovieObject.getString("original_title"));
                    map.put(KEY_YEAR, jsonMovieObject.getString("release_date"));
                    map.put(KEY_OVERVIEW, jsonMovieObject.getString("overview"));
                    map.put(KEY_RATING,jsonMovieObject.getString("vote_average"));
                    map.put(KEY_ID, jsonMovieObject.getString("id"));
                    map.put(KEY_THUMB_URL, "http://image.tmdb.org/t/p/w500" + jsonMovieObject.getString("poster_path"));

                    results.add(map);
                }
            } catch (JSONException e) {
                System.err.println(e);
                Log.d(DEBUG_TAG, "Error parsing JSON. String was: "
                        + streamAsString);
            }
            return results;
        }

        public String convertInputStreamToString(InputStream stream) throws IOException {
            Reader reader;
            reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.readLine();
        }
    }

    public class SecondaryAdapter extends BaseAdapter {
        HashMap<String, String> movie = new HashMap<String, String>();

        private Activity activity;
        private ArrayList<HashMap<String, String>> data;
        private LayoutInflater inflater = null;
        public ImageLoader imageLoader;

        public SecondaryAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
            activity = a;
            data = d;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            imageLoader = new ImageLoader(activity.getApplicationContext());
        }



        public int getCount() {
            return data.size();
        }

        public long getItemId(int position) {
            return position;
        }

        public Object getItem(int arg0) {

            return null;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.activity_top_rated_movies, null);

            TextView title = (TextView) vi.findViewById(R.id.title);
            TextView year = (TextView) vi.findViewById(R.id.year);
            TextView overview = (TextView) vi.findViewById(R.id.overview);
            TextView rating = (TextView) vi.findViewById(R.id.rating);

            ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image);

            movie = data.get(position);

            title.setText(movie.get("title"));
            year.setText(movie.get("Year"));
            overview.setText(movie.get("overview"));
            rating.setText(movie.get("Rating"));
            imageLoader.DisplayImage(movie.get("thumb_url"), thumb_image);

            vi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    movie = data.get(position);

                    Intent intent = new Intent(TopRatedMovies.this,
                            DetailedViewMovie.class);

                    // show data to detail_view
                    intent.putExtra("title", movie.get("title"));
                    intent.putExtra("Year", movie.get("Year"));
                    intent.putExtra("Rating", movie.get("Rating"));
                    intent.putExtra("thumb_url", movie.get("thumb_url"));
                    intent.putExtra("id", movie.get("id"));
                    id = intent.getStringExtra("id");
                    startActivity(intent);

                }
            });


            return vi;
        }

    }

}