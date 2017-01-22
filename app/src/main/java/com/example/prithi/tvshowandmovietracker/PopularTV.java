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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class PopularTV extends ActionBarActivity {

    private final String KEY_TITLE = "title";
    private final String KEY_YEAR = "Year";
    private final String KEY_RATING = "Rating";
    private final String KEY_OVERVIEW = "overview";
    private final String KEY_THUMB_URL = "thumb_url";
    private final String KEY_ID = "id";


    String id;



    ListView list;
    SecondaryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_the_air_tv);




        new TMDBOnTheAir().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_tv, menu);
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

    /**
     * Updates the View with the results. This is called asynchronously when the
     * results are ready.
     */

    public void update(ArrayList<HashMap<String, String>> result) {

        ListView listView = new ListView(this);

        // Add results to listView.
        adapter = new SecondaryAdapter(this, result);
        listView.setAdapter(adapter);
        // Update Activity to show listView
        setContentView(listView);

    }

    public class TMDBOnTheAir extends AsyncTask {

        private final String TMDB_API_KEY = "474b56914cbb221a1f9b28e7f778c6a3";
        private static final String DEBUG_TAG = "TMDBQueryManager";

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(
                Object... params) {
            try {
                return displayOnTheAirShows();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            update((ArrayList<HashMap<String, String>>) result);
        }



        //Searches TMDBs API for the given query

        public ArrayList<HashMap<String, String>> displayOnTheAirShows()
                throws IOException {
            // Build URL

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append("https://api.themoviedb.org/3/tv/popular");
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
                return parseOnTheAirShows(convertInputStramToString(stream));
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }

        private ArrayList<HashMap<String, String>> parseOnTheAirShows(String result) {
            String streamAsString = result;
            ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
            try {
                JSONObject jsonObject = new JSONObject(streamAsString);
                JSONArray array = (JSONArray) jsonObject.get("results");
                for (int i = 0; i < array.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonTVObject = array.getJSONObject(i);
                    map.put(KEY_TITLE,
                            jsonTVObject.getString("original_name"));
                    map.put(KEY_YEAR, jsonTVObject.getString("first_air_date"));
                    map.put(KEY_OVERVIEW, jsonTVObject.getString("overview"));
                    map.put(KEY_RATING, jsonTVObject.getString("vote_average"));
                    map.put(KEY_ID, jsonTVObject.getString("id"));
                    map.put(KEY_THUMB_URL, "http://image.tmdb.org/t/p/w500" + jsonTVObject.getString("poster_path"));

                    results.add(map);
                }
            } catch (JSONException e) {
                System.err.println(e);
                Log.d(DEBUG_TAG, "Error parsing JSON. String was: "
                        + streamAsString);
            }
            return results;
        }

        public String convertInputStramToString(InputStream stream) throws IOException,
                UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.readLine();
        }
    }

    public class SecondaryAdapter extends BaseAdapter {
        HashMap<String, String> tv = new HashMap<String, String>();

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
            // TODO Auto-generated method stub
            return null;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.activity_popular_tv, null);

            TextView title = (TextView) vi.findViewById(R.id.title);

            TextView year = (TextView) vi.findViewById(R.id.year);
            TextView overview = (TextView) vi.findViewById(R.id.overview);
            TextView rating = (TextView) vi.findViewById(R.id.rating);
            // name
            ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image);

            tv = data.get(position);


            title.setText(tv.get("title"));
            year.setText(tv.get("Year"));
            overview.setText(tv.get("overview"));
            rating.setText(tv.get("Rating"));

            imageLoader.DisplayImage(tv.get("thumb_url"), thumb_image);

            vi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // Get the position
                    tv = data.get(position);
                    // new TMDBDetail().execute();

                    Intent intent = new Intent(PopularTV.this,
                            DetailedViewTV.class);

                    // pass required data to detail_view
                    intent.putExtra("title", tv.get("title"));
                    intent.putExtra("Year", tv.get("Year"));
                    intent.putExtra("Rating", tv.get("Rating"));
                    intent.putExtra("thumb_url", tv.get("thumb_url"));
                    intent.putExtra("id", tv.get("id"));
                    id = intent.getStringExtra("id");
                    startActivity(intent);

                }
            });


            return vi;
        }

    }
}
