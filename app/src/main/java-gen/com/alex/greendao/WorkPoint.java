package com.alex.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "WORK_POINT".
 */
public class WorkPoint {

    public double lat;
    public double lon;
    public double alt;
    public float speed;
    public float power;
    public int temp;
    public int status;
    public long time;

    public WorkPoint() {
    }

    public WorkPoint(double lat, double lon, double alt, float speed, float power, int temp, int status, long time) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.speed = speed;
        this.power = power;
        this.temp = temp;
        this.status = status;
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
