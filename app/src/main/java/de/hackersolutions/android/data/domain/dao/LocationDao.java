package de.hackersolutions.android.data.domain.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import de.hackersolutions.android.data.domain.Location;

@Dao
public interface LocationDao {

    @Insert
    void insert(Location... locations);

    @Update
    void update(Location... locations);

    @Delete
    void delete(Location... locations);

    @Query("SELECT * FROM location")
    List<Location> getLocations();

    @Query("SELECT * FROM location WHERE timestamp > :timestamp")
    public Location getLocationsAfter(Long timestamp);
}
