package cdac.org.anganvadistaffutility.public_u.activity.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;

public class PublicInfraActivity extends BaseActivity implements View.OnClickListener {

    CardView building_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_infra);

        building_details = findViewById(R.id.building_details_card);
        building_details.setOnClickListener(this);


        if (ActivityCompat.checkSelfPermission(PublicInfraActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PublicInfraActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PublicInfraActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            GPSTracker gpsTracker = new GPSTracker(this);
            if (gpsTracker.isGPSEnabled) {
                String stringLatitude = String.valueOf(gpsTracker.latitude);

                String stringLongitude = String.valueOf(gpsTracker.longitude);

                Log.d("curentlocation", "" + stringLatitude + " " + stringLongitude);

                Toast.makeText(context, "" + stringLatitude + " " + stringLongitude, Toast.LENGTH_SHORT).show();

            } else {

                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gpsTracker.showSettingsAlert();

            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.building_details_card:
                startActivity(new Intent(context, BuildingDetailsActivity.class));
                break;
        }
    }
}