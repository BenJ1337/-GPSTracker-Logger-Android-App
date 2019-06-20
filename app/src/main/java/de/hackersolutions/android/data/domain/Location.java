package de.hackersolutions.android.data.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


import java.util.Date;

@Entity(tableName = "location")
public class Location {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @NonNull
    private double longitude;
    @NonNull
    private double latitude;
    @NonNull
    private Date timestamp;

    public Location(double longitude, double latitude, @NonNull Date timestamp) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull Date timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "Location(" + this.id + ", " + this.longitude + ", " + this.latitude + ", " + this.timestamp.toString() + ")";
    }
}
