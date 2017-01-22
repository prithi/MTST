package com.example.prithi.tvshowandmovietracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyMovieShows extends ActionBarActivity {


    private final String KEY_TITLE = "title";
    private final String KEY_YEAR = "Year";
    private final String KEY_RATING = "Rating";
    private final String KEY_THUMB_URL = "thumb_url";
    private final String KEY_ID = "id";
    private final String KEY_OVERVIEW = "overview";


    String id;
    SecondaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_movie_shows);

        update(listMovie());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_movie_shows, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<HashMap<String, String>> listMovie(){
        List<MyMovie> list;
        ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
        ParseQuery<MyMovie> query = ParseQuery.getQuery(MyMovie.class);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        try {
            list = query.find();


            for (MyMovie d : list) {
                HashMap<String, String> map = new HashMap<String, String>();
                // double rating = Double.parseDouble(d.getTVRating());
                String year = d.getMovieYear();
                String title = d.getMovieTitle();
                String rating = d.getMovieRating();
                String imgUrl = d.getMovieIMGURL();
                String synopsis = d.getMovieSynopsis();
                int id = d.getMovieID();
                String ids = String.valueOf(id);

                map.put(KEY_TITLE, title);
                map.put(KEY_YEAR, year);
                map.put(KEY_OVERVIEW, synopsis);
                map.put(KEY_RATING, rating);
                map.put(KEY_ID, ids);
                map.put(KEY_THUMB_URL, imgUrl);

                results.add(map);

            }
        }


        catch (ParseException e){
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            Toast.makeText(MyMovieShows.this, "Error : "
                    + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        return results;

    }


    public void update(ArrayList<HashMap<String, String>> result) {

        ListView listView = new ListView(this);

        // Add results to listView.
        adapter = new SecondaryAdapter(this, result);
        listView.setAdapter(adapter);
        // Update Activity to show listView
        setContentView(listView);

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
                vi = inflater.inflate(R.layout.activity_my_tvshows, null);

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

                    Intent intent = new Intent(MyMovieShows.this,
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

