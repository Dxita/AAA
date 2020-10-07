package cdac.org.anganvadistaffutility.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cdac.org.anganvadistaffutility.R;

public class PublicInfraActivity extends BaseActivity implements View.OnClickListener {
CardView building_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_infra);

        building_details=findViewById(R.id.building_details_card);
        building_details.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.building_details_card:
                startActivity(new Intent(context,BuildingDetailsActivity.class));
                break;
        }
    }
}