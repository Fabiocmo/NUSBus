package me.zhuangweiming.nusbus.model;

/**
 * Created by weiming on 26/10/16.
 */

public class Shuttle {
    String arrivalTime;
    String nextArrivalTime;
    String name;

    public Shuttle(String arrivalTime, String nextArrivalTime, String name) {
        this.arrivalTime = arrivalTime;
        this.nextArrivalTime = nextArrivalTime;
        this.name = name;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getNextArrivalTime() {
        return nextArrivalTime;
    }

    public void setNextArrivalTime(String nextArrivalTime) {
        this.nextArrivalTime = nextArrivalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
