package de.hackersolutions.android.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import de.hackersolutions.android.data.converter.DateTypeConverter;
import de.hackersolutions.android.data.domain.Location;
import de.hackersolutions.android.data.domain.dao.LocationDao;

@Database(entities = {Location.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao getLocationDao();
}
