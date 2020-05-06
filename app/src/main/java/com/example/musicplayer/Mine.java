package com.example.musicplayer;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Mine  {

    public String Name;
    public String image;
    public ArrayList<String> Singers=new ArrayList<String>();
    public Mine(String Name)
    {
       this.Name=Name ;
    }

    @NonNull
    @Override
    public String toString() {

        return Name;
    }
}

