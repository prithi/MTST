package com.example.prithi.tvshowandmovietracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends ActionBarActivity {

    RatingBar ratingBar;
    TextView txtRatingValue;
    TextView txtVotingValue;


    String id;
    int ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);


        Intent i = getIntent();
        id = i.getStringExtra("id");
        ids = Integer.parseInt(id);
       // Parse.initialize(this, "aDDqrB7WPgyAlGcxUKXpjTbS3EZBpflZBRJHaKOc", "xESrAsBAipLBIIEHj4TSsnmjcZhuePjf1N7yoUTm");
        //ParseAnalytics.trackAppOpened(getIntent());
        //ParseObject.registerSubclass(Rating.class);



        addListenerOnRatingBar();
        getTotalRating();


    }

    public void getTotalRating(){
        List<Rating> ob;
        ParseQuery<Rating> query = ParseQuery.getQuery(Rating.class);
        query.whereEqualTo("movieid", ids);
        //query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        ArrayList<Double> scores = new ArrayList<Double>();

        try{
            ob = query.find();

            for (Rating  d : ob){
                double rating = d.getRating();
                if (rating > 0){
                    scores.add(rating);
                }
            }
            int totalVote = scores.size();
            String total = Integer.toString(totalVote);
            double sum = 0.0;
            for (int i=0; i <scores.size(); i++){
                sum += scores.get(i);
            }

            String averageRating = String.format("%.2f", sum / scores.size());
            txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);
            txtRatingValue.setText(averageRating);
            txtVotingValue = (TextView) findViewById(R.id.txtVotingValue);
            txtVotingValue.setText(total);
            }
        catch (ParseException e){
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            Toast.makeText(RatingActivity.this, "Error : "
                    + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

      /*  query.findInBackground(new FindCallback<Rating>() {
            @Override
            public void done(List<Rating> ratingList, ParseException e) {
                if (e == null) {
                    ArrayList<Double> scores = new ArrayList<Double>();
                   for(Rating d: ratingList){
                      double rating = d.getDouble("rating");
                       if (rating > 0){
                            scores.add(rating);
                        }
                    }
                    int totalVote = scores.size();

                    double sum = 0.0;
                    for (int i=0; i <scores.size(); i++){
                      sum += scores.get(i);
                    }

                   String averageRating = String.format("%.2f", sum / scores.size());
                    txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);
                    txtRatingValue.setText(averageRating);

                    txtVotingValue = (TextView) findViewById(R.id.txtVotingValue);
                    txtVotingValue.setText(totalVote);
                }else {
                    Toast.makeText(RatingActivity.this, "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });*/
    }




    public void addListenerOnRatingBar(){
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // txtRatingValue.setText(String.valueOf(rating));
                Rating rt = new Rating();

                rt.setUser(ParseUser.getCurrentUser());
                rt.setMovieID(ids);
                rt.setRating(rating);
                rt.saveInBackground();
                getTotalRating();
                Toast.makeText(RatingActivity.this, "Your Selected Ratings : "
                        + String.valueOf(rating), Toast.LENGTH_SHORT).show();


            }
        });
        getTotalRating();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rating, menu);
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
}
