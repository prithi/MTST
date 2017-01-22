package com.example.prithi.tvshowandmovietracker;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

public class DetailedViewMovie extends ActionBarActivity {

    String id;
    String year;
    String title;
    String rating;
    String imgUrl;
    String synopsis;
    SecondaryAdapter adapter;
    ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_watchlist_movie_detail_view);

        Intent i = getIntent();

        title = i.getStringExtra("title");

        year = i.getStringExtra("Year");
        rating = i.getStringExtra("Rating");
        imgUrl = i.getStringExtra("thumb_url");
        id = i.getStringExtra("id");
        imageLoader = new ImageLoader(getApplicationContext());

        TextView textname = (TextView) findViewById(R.id.name_of_movie);
        TextView textrating = (TextView)findViewById(R.id.rating);
        ImageView bigger_image = (ImageView) findViewById(R.id.image_of_movie);


        textname.setText(title);
        textrating.setText(rating);


        imageLoader.DisplayImage(imgUrl, bigger_image);

        new TMDBCast().execute();
        new TMDBSynopsis().execute();

        Button commentButton = (Button) findViewById(R.id.buttonComment);
        commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DetailedViewMovie.this,
                        CommentActivity.class);

                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button ratingButton = (Button) findViewById(R.id.buttonRating);
        ratingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DetailedViewMovie.this,
                        RatingActivity.class);

                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button addMovieButton = (Button) findViewById(R.id.buttonAddtoWatchlist);
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyMovie movie = new MyMovie();
                int ids = (Integer.valueOf(id));
                movie.setUser(ParseUser.getCurrentUser());
                movie.setUsername(ParseUser.getCurrentUser().getUsername());
                movie.setMovieID(ids);
                movie.setMovieTitle(title);
                movie.setMovieYear(year);
                movie.setMovieRating(rating);
                movie.setMovieSynopsis(synopsis);
                movie.setMovieIMGURL(imgUrl);
                movie.saveEventually();
                Toast.makeText(DetailedViewMovie.this, "Added : "
                        + title + " in your watchlist", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_view, menu);
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
                return true;

            case R.id.action_add_to_movie:
                MyMovie movie = new MyMovie();
                int ids = (Integer.valueOf(id));
                movie.setUser(ParseUser.getCurrentUser());
                movie.setUsername(ParseUser.getCurrentUser().getUsername());
                movie.setMovieID(ids);
                movie.setMovieTitle(title);
                movie.setMovieYear(year);
                movie.setMovieRating(rating);
                movie.setMovieSynopsis(synopsis);
                movie.setMovieIMGURL(imgUrl);
                movie.saveEventually();
                Toast.makeText(DetailedViewMovie.this, "Added : "
                        + title + " in your watchlist", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        /*switch (item.getItemId()) {
            case R.id.action_logout:
                ParseUser.logOut();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_add_tvshow:
                MyTV tv = new MyTV();
                tv.setTVID(Integer.parseInt(id));
                tv.setTitle(title);
                tv.setYear(year);
                tv.setRating(rating);
                tv.setSynopsis(synopsis);
                tv.setIMGURL(imgUrl);
                Intent intent1 = new Intent(this, DetailedViewTV.class);
                startActivity(intent1);
                finish();




        }
        finish();
        return true;*/
    }



    // displays cast
    public void updateListOfCast(ArrayList<String> result) {
        Log.d("this", this.toString());
        Log.d("results", result.toString());

        ListView listView = (ListView) findViewById(R.id.cast_details);
        Log.d("updateViewWithResults", result.toString());
        // Add results to listView.
        ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<ArrayList<String>>(
                this, android.R.layout.simple_list_item_1, (List) result);
        listView.setAdapter(adapter);

    }

    public void updateSynopsis(ArrayList<String> result) {
        TextView txtSynopsis = (TextView) findViewById(R.id.synopsis);
        txtSynopsis.setMovementMethod(new ScrollingMovementMethod());
        txtSynopsis.setText(result.get(0));
        synopsis = result.get(0);


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
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.activity_watchlist_movie_detail_view, null);

            TextView castname = (TextView) vi.findViewById(R.id.cast_details);

            movie = data.get(position);

            castname.setText(movie.get("cast"));

            return vi;
        }
    }

    private class TMDBCast extends AsyncTask<Object, Void, ArrayList<String>> {

        private final String TMDB_API_KEY = "474b56914cbb221a1f9b28e7f778c6a3";
        private static final String DEBUG_TAG = "TMDBQueryManager";

        @Override
        protected ArrayList<String> doInBackground(Object... params) {
            try {
                return getCast();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> results_Cast) {
            updateListOfCast(results_Cast);

        };

        public ArrayList<String> getCast() throws IOException {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://api.themoviedb.org/3/movie/" + id + "/credits");
            stringBuilder.append("?api_key=" + TMDB_API_KEY);
            URL url = new URL(stringBuilder.toString());


            InputStream stream = null;
            try {

                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.connect();

                int responseCode = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response code is: " + responseCode + " " + conn.getResponseMessage());

                stream = conn.getInputStream();
                return parseCast(stringify(stream));
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        private ArrayList<String> parseCast(String result) {
            String streamAsString = result;

            ArrayList<String> results_Cast = new ArrayList<String>();
            try {
                JSONObject jsonObject = new JSONObject(streamAsString);
                JSONArray array = (JSONArray) jsonObject.get("cast");
                Log.d("array view", array.toString());
                for (int i = 0; i < array.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonMovieObject = array.getJSONObject(i);
                    results_Cast.add(jsonMovieObject.getString("name"));
                }
            } catch (JSONException e) {
                Log.d("e", e.toString());
                Log.d(DEBUG_TAG, "Error parsing JSON. String was: "
                        + streamAsString);
            }

            return results_Cast;
        }

        public String stringify(InputStream stream) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.readLine();
        }
    }

    private class TMDBSynopsis extends
            AsyncTask<Object, Void, ArrayList<String>> {

        private final String TMDB_API_KEY = "474b56914cbb221a1f9b28e7f778c6a3";
        private static final String DEBUG_TAG = "TMDBQueryManager";

        @Override
        protected ArrayList<String> doInBackground(Object... params) {
            try {
                return getSynopsis();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> results_Cast) {
            updateSynopsis(results_Cast);

        };

        public ArrayList<String> getSynopsis() throws IOException {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://api.themoviedb.org/3/movie/" + id);
            stringBuilder.append("?api_key=" + TMDB_API_KEY);
            URL url = new URL(stringBuilder.toString());


            InputStream stream = null;
            try {
                // Establish a connection
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.connect();

                int responseCode = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response code is: " + responseCode + " "
                        + conn.getResponseMessage());

                stream = conn.getInputStream();
                return parseSynopsis(stringify(stream));
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        private ArrayList<String> parseSynopsis(String result) {
            String streamAsString = result;

            ArrayList<String> results_Cast = new ArrayList<String>();
            try {
                JSONObject jsonObject = new JSONObject(streamAsString);
                Log.d("overview","synopsis : "+jsonObject.getString("overview"));

                results_Cast.add(jsonObject.getString("overview"));


            } catch (JSONException e) {
                Log.d("e", e.toString());
                Log.d(DEBUG_TAG, "Error parsing JSON. String was: "
                        + streamAsString);
            }

            return results_Cast;
        }

        public String stringify(InputStream stream) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.readLine();
        }
    }

}
