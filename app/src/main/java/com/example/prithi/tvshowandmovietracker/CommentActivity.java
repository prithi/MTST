package com.example.prithi.tvshowandmovietracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends ActionBarActivity {


    //AppID : aDDqrB7WPgyAlGcxUKXpjTbS3EZBpflZBRJHaKOc
    //ClientID: xESrAsBAipLBIIEHj4TSsnmjcZhuePjf1N7yoUTm
    String id;
    int ids;
    EditText mCommentInput;
    ListView mListView;
    CommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        ids = Integer.parseInt(id);
       // Parse.initialize(this, "aDDqrB7WPgyAlGcxUKXpjTbS3EZBpflZBRJHaKOc", "xESrAsBAipLBIIEHj4TSsnmjcZhuePjf1N7yoUTm");
        //ParseAnalytics.trackAppOpened(getIntent());
        //ParseObject.registerSubclass(Comment.class);



        mAdapter = new CommentAdapter(this, new ArrayList<Comment>());
        mCommentInput= (EditText) findViewById(R.id.comment_input);
        mListView = (ListView) findViewById(R.id.comment_list);
        mListView.setAdapter(mAdapter);
        updateData();


    }

    public void createComment(View v){
        if (mCommentInput.getText().length() > 0){
            Comment c = new Comment();

            c.setUser(ParseUser.getCurrentUser());
            c.setUsername(ParseUser.getCurrentUser().getUsername());
            c.setMovieID(ids);
            c.setComment(mCommentInput.getText().toString());
            c.saveEventually();
            mAdapter.insert(c, 0);
            mCommentInput.setText("");



        }
    }

    public void updateData(){
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
       query.whereEqualTo("movieid", ids);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException error) {
                if (comments != null) {
                    mAdapter.clear();
                    for (int i = 0; i < comments.size(); i++) {
                        mAdapter.add(comments.get(i));
                    }
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
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
