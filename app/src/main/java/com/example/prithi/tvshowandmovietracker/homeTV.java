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


public class homeTV extends ActionBarActivity {
    public static final String EXTRA_MESSAGE = "";
    public static final String EXTRA_QUERY = "";
    ListView mListView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tv);

        editText = (EditText) findViewById(R.id.edit_message_tv);


        Button watchlistButton = (Button) findViewById(R.id.myTVList);
        watchlistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                startActivity(new Intent(getApplicationContext(), myTVShows.class));
            }
        });

        Button OnTheAirTVButton = (Button) findViewById(R.id.onTheAir);
        OnTheAirTVButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                startActivity(new Intent(getApplicationContext(), onTheAirTV.class));
            }
        });

        Button AiringTodayButton = (Button) findViewById(R.id.airingToday);
        AiringTodayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                startActivity(new Intent(getApplicationContext(), AiringToday.class));
            }
        });

        Button TopRatedTVButton = (Button) findViewById(R.id.topratedTV);
        TopRatedTVButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                startActivity(new Intent(getApplicationContext(), TopRatedTV.class));
            }
        });

        Button PopularTVButton = (Button) findViewById(R.id.popluarTV);
        PopularTVButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText.setText("");
                startActivity(new Intent(getApplicationContext(), PopularTV.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_tv, menu);
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
            Toast.makeText(this, "Please enter keywords to search TV Show", Toast.LENGTH_LONG).show();
        else{
            Intent intent = new Intent(this, SearchTVActivity.class);

            intent.putExtra(EXTRA_QUERY, query);
            startActivity(intent);
        }
    }

}
