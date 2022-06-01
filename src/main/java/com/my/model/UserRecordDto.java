package com.my.model;

import java.time.LocalDateTime;

public class UserRecordDto {

    private int num;
    private String lat;
    private String lnt;
    private LocalDateTime reg_date;


    public UserRecordDto() {
    }

    public UserRecordDto(int num, String lat, String lnt, LocalDateTime reg_date) {
        this.num = num;
        this.lat = lat;
        this.lnt = lnt;
        this.reg_date = reg_date;
    }

    public UserRecordDto(String lat, String lnt) {
        this.lat = lat;
        this.lnt = lnt;
    }

    public int getNum() {
        return num;
    }

    public String getLat() {
        return lat;
    }

    public String getLnt() {
        return lnt;
    }

    public LocalDateTime getReg_date() {
        return reg_date;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLnt(String lnt) {
        this.lnt = lnt;
    }

    public void setReg_date(LocalDateTime reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {
        return "UserRecordDto{" +
                "num=" + num +
                ", lat='" + lat + '\'' +
                ", lnt='" + lnt + '\'' +
                ", reg_date=" + reg_date +
                '}';
    }
}
