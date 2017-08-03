package com.mohdeva.learn.tasker;

/**
 * Created by ADMIN on 7/22/2017.
 */

public class Tasks {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String name,type,date;

    public int getIsdone() {
        return isdone;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }

    int isdone;
    public Tasks(String name, String type, String date,int isdone)
    {
        this.name=name;
        this.type=type;
        this.date=date;
        this.isdone=isdone;
    }

}
