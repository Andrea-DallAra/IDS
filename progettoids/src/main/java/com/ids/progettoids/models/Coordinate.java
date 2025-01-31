package com.ids.progettoids.models;

public class Coordinate {
    
    private double latitudine;
    private double longitudine;

    public Coordinate(double latitudine, double longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }
    public double getLongitudine() {
        return longitudine;
    }
    @Override
    public String toString() {
        return latitudine + "," + longitudine;
    }
}
