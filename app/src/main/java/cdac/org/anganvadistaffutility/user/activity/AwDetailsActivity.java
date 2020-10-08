package cdac.org.anganvadistaffutility.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;

public class AwDetailsActivity extends BaseActivity implements View.OnClickListener {
RadioButton drinking_button, kitchen_button, toilet_button;
CheckBox mineral, regular, both;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aw_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

     /*   drinking_button=findViewById(R.id.drinking_yes);
        kitchen_button=findViewById(R.id.kitchen_yes);
        toilet_button=findViewById(R.id.toilet_yes);

        drinking_button.setOnClickListener(this);
        kitchen_button.setOnClickListener(this);
        toilet_button.setOnClickListener(this);
*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
       /* switch (view.getId())
        {
            case R.id.drinking_yes:
               drinking_water_type();
                break;
            case R.id.kitchen_yes:
                Toast.makeText(context, "Kitchen", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toilet_yes:
                Toast.makeText(context, "Toilet", Toast.LENGTH_SHORT).show();
                break;
        }*/
    }


   /* private void drinking_water_type() {
        View view = getLayoutInflater().inflate(R.layout.drinking_water_type, null);
        //Button pay_now_btn = view.findViewById(R.id.pay_now_btn);
         mineral = view.findViewById(R.id.Mineral_water);
         regular = view.findViewById(R.id.normal_water);
         both = view.findViewById(R.id.both);
        TextView submit = view.findViewById(R.id.submit);
        TextView cancel = view.findViewById(R.id.cancel);


        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(view);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str = "";
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.Mineral_water:
                str = checked ? "" : getResources().getString(R.string.drinking_water);
                break;
            case R.id.normal_water:
                str = checked ? "" : getResources().getString(R.string.electricity);
                break;
            case R.id.both:
                str = checked ? "" : getResources().getString(R.string.kitchen);
                break;
        }

    }*/
}