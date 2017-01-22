package com.example.prithi.tvshowandmovietracker;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("MyTV")
public class MyTV extends ParseObject {
    public MyTV(){

    }

    public int getTVID(){

        return getInt("tvid");
    }

    public void setTVID(int id){

        put("tvid", id);
    }

    public String  getTVYear(){
        return getString("year");
    }

    public void setTVYear(String year){
        put("year", year);
    }

    public String getTVTitle(){
        return getString("title");
    }

    public void setTVTitle(String title){
        put("title", title);
    }

    public String getTVRating(){
        return getString("rating");
    }

    public void setTVRating(String rating){
        put("rating", rating);
    }

    public String getTVIMGURL(){
        return getString("imgurl");
    }

    public void setTVIMGURL(String imgurl){
        put("imgurl", imgurl);
    }

    public String getTVSynopsis(){
        return getString("synopsis");
    }

    public void setTVSynopsis(String synopsis){
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
