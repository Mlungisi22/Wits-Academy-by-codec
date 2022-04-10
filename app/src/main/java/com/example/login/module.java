package com.example.login;

public class module {
    String modName, modCode, modTeacher;

    module (){

    }

    module(String modCode, String modName, String modTeacher){
        this.modCode = modCode;
        this.modName = modName;
        this.modTeacher = modTeacher;
    }

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
}
