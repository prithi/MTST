package com.example.prithi.tvshowandmovietracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;


public class homeMovie extends ActionBarActivity {
    String imgUrl;
    String strFinalUrl;

    public static final String EXTRA_MESSAGE = "";
    public static final String EXTRA_QUERY = "";
    ListView mListView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_movie);

        editText = (EditText) findViewById(R.id.edit_message);

        Button watchlistButton = (Button) findViewById(R.id.myMovieList);
        watchlistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                startActivity(new Intent(getApplicationContext(), MyMovieShows.class));
            }
        });


        Button TopRatedMoviesButton = (Button) findViewById(R.id.topratedMovies);
        TopRatedMoviesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                startActivity(new Intent(getApplicationContext(), TopRatedMovies.class));
            }
        });

        Button NowPlayingButton = (Button) findViewById(R.id.nowPlayingMovies);
        NowPlayingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                startActivity(new Intent(getApplicationContext(), NowPlaying.class));
            }
        });

        Button UpcomingMovieButton = (Button) findViewById(R.id.upcomingMovies);
        UpcomingMovieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                startActivity(new Intent(getApplicationContext(), UpcomingMovies.class));
            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_movie, menu);
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

    public void queryTMDB(View view) {
        String query = editText.getText().toString();
        if(query.length()==0)
            Toast.makeText(this, "Please enter keywords to search movie", Toast.LENGTH_LONG).show();
        else{
            Intent intent = new Intent(this, SearchMovieActivity.class);

            intent.putExtra(EXTRA_QUERY, query);
            startActivity(intent);
        }
    }


}
