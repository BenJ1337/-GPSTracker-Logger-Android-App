package de.hackersolutions.android.activites;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hackersolutions.android.R;
import de.hackersolutions.android.data.AppDatabase;
import de.hackersolutions.android.data.domain.Location;
import de.hackersolutions.android.data.domain.dao.LocationDao;
import java.lang.Math.*;
public class MyLocationsActivity extends AppCompatActivity {
    private String tag = MyLocationsActivity.class.getName();

    private LinearLayout linearLayout;
    private LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);

    private AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "db-location")
            .allowMainThreadQueries()
            .build();
    private LocationDao dao = database.getLocationDao();
    private String locationCSV = "longitude,latitude,description\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_locations);

        linearLayout = (LinearLayout) this.findViewById(R.id.locationList);

        List<Location> locationList = dao.getLocations();

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        for(Location loc : locationList) {
            Log.i(tag, loc.getTimestamp().toString());
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

            TextView tv1 = new TextView(this);
            tv1.setLayoutParams(layoutParams);
            tv1.setTextSize(12);
            tv1.setGravity(Gravity.CENTER);
            tv1.setPadding(20, 20, 0, 0);
            String longi = String.valueOf(loc.getLongitude());
            locationCSV += longi + ",";
            if(longi.length() > 10) {
                longi = longi.substring(0, 10);
            }
            tv1.setText(longi);
            layout.addView(tv1);

            TextView tv2 = new TextView(this);
            tv2.setLayoutParams(layoutParams);
            tv2.setTextSize(12);
            tv2.setGravity(Gravity.CENTER);
            String lati = String.valueOf(loc.getLatitude());
            locationCSV += lati + ",";
            if(lati.length() > 10) {
                lati = lati.substring(0, 10);
            }
            tv2.setText(lati);
            layout.addView(tv2);

            TextView tv3 = new TextView(this);
            tv3.setLayoutParams(layoutParams);
            tv3.setTextSize(12);
            tv3.setGravity(Gravity.CENTER);
            locationCSV += df.format(loc.getTimestamp()) + "\n";
            tv3.setText(df.format(loc.getTimestamp()));
            layout.addView(tv3);
            this.linearLayout.addView(layout);
        }

        final Button btnSettings = findViewById(R.id.btnExport);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isExternalStorageWritable()) {
                    Toast.makeText(getApplicationContext(), "Datei wird erstellt", Toast.LENGTH_SHORT).show();

                    File dir = getPublicAlbumStorageDir("GPS-Tracker");
                    File file = new File(dir, "locations.csv");

                    try {
                        FileOutputStream f = new FileOutputStream(file);
                        PrintWriter pw = new PrintWriter(f);
                        pw.println(locationCSV);
                        pw.flush();
                        pw.close();
                        f.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.i(tag, "******* File not found. Did you" +
                                " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Datei fertig", Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(getApplicationContext(), "Keine Schreibrechte",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(tag, "Directory not created");
        }
        return file;
    }


}
