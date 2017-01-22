package com.example.prithi.tvshowandmovietracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Parse.initialize(this, "aDDqrB7WPgyAlGcxUKXpjTbS3EZBpflZBRJHaKOc", "xESrAsBAipLBIIEHj4TSsnmjcZhuePjf1N7yoUTm");




        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void homeTV (View view){
        Intent intent1 = new Intent(MainActivity.this, homeTV.class);
        startActivity(intent1);
    }

    public void homeMovie(View view){
        Intent intent2 = new Intent(MainActivity.this, homeMovie.class);
        startActivity(intent2);
    }
}


