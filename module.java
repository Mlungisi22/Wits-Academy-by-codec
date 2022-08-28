package com.example.login;

import android.widget.RatingBar;

public class module {
    private String modName, modCode, modTeacher;

    private float rating;

    module (){


    }

    module(String modCode, String modName, String modTeacher, float rating){
        setModName(modName);
        setModCode(modCode);
        setModTeacher(modTeacher);
        setRatingNum(rating);
    }

    public float getRatingNum(){return rating;}

    public String getModName() {
        return modName;
    }

    public String getModCode() {
        return modCode;
    }

    public String getModTeacher() {
        return modTeacher;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public void setModCode(String modCode) {
        this.modCode = modCode;
    }

    public void setModTeacher(String modTeacher) {
        this.modTeacher = modTeacher;
    }

    public void setRatingNum(float num){
        this.rating = num;
    }

}
