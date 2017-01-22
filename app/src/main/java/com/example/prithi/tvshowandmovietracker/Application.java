package com.example.prithi.tvshowandmovietracker;

import com.parse.Parse;
import com.parse.ParseObject;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this,
                getResources().getString(R.string.parse_application_id),
                getResources().getString(R.string.parse_client_key));

        ParseObject.registerSubclass(Rating.class);
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(MyTV.class);
        ParseObject.registerSubclass(MyMovie.class);
    }
}