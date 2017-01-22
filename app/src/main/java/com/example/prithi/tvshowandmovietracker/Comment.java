package com.example.prithi.tvshowandmovietracker;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject{
    public Comment(){

    }

    public int getMovieID(){

        return getInt("movieid");
    }

    public void setMovieID(int id){

        put("movieid", id);
    }



    public String getComment(){

        return getString("comment");
    }
    public void setComment(String comment){
        put("comment", comment);
    }

    public String getUsername(){
        return getString("username");
    }

    public void setUsername(String currentUser){
        put("username", currentUser);
    }

    public void setUser(ParseUser currentUser){
        put("user", currentUser);
    }
}
