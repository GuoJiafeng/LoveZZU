package com.gjf.lovezzu.entity;

/**
 * Created by Leon on 2017/7/24.
 */

public class Topic2 {
    private  String name;
    private  String content;
    private int floor;
    private int time;
    private  int zan;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public Topic2(String name, String content, int floor, int time, int zan) {
        this.name = name;
        this.content = content;
        this.floor = floor;
        this.time = time;
        this.zan = zan;
    }
}
