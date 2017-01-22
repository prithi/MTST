package com.example.prithi.tvshowandmovietracker;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("MyMovie")
public class MyMovie extends ParseObject {
    public MyMovie(){

    }

   public int getMovieID(){

       return getInt("movieid");
    }

    public void setMovieID(int id){

        put("movieid", id);
    }

    public String  getMovieYear(){
        return getString("year");
    }

    public void setMovieYear(String year){
        put("year", year);
    }

    public String getMovieTitle(){
        return getString("title");
    }

    public void setMovieTitle(String title){
        put("title", title);
    }

    public String getMovieRating(){
        return getString("rating");
    }

    public void setMovieRating(String rating){
        put("rating", rating);
    }

    public String getMovieIMGURL(){
        return getString("imgurl");
    }

    public void setMovieIMGURL(String imgurl){
        put("imgurl", imgurl);
    }

    public String getMovieSynopsis(){
        return getString("synopsis");
    }

    public void setMovieSynopsis(String synopsis){
        put("synopsis", synopsis);
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
