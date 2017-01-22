package com.example.prithi.tvshowandmovietracker;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Rating")
public class Rating extends ParseObject {

    public Rating(){

    }

    public int getMovieID(){
        return getInt("movieid");
    }

    public void setMovieID(int id){

        put("movieid", id);
    }

    public double getRating(){

        return getDouble("rating");
    }

    public void setRating(double rating){
        put("rating", rating);
    }

    public void setUser(ParseUser currentUser){
        put("user", currentUser);
    }



}
