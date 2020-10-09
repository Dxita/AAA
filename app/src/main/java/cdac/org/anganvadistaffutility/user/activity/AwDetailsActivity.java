package cdac.org.anganvadistaffutility.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.user.data.AWDetails;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;
import retrofit2.Call;

public class AwDetailsActivity extends BaseActivity implements View.OnClickListener {

    TextView name;
RadioButton  drinking_button, kitchen_button, toilet_button;
CheckBox mineral, regular, both,open_kitchen,non_open_kitchen,indian_toilet,western_toilet;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aw_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        drinking_button=findViewById(R.id.drinking_yes);
        kitchen_button=findViewById(R.id.kitchen_yes);
        toilet_button=findViewById(R.id.toilet_yes);
        name=findViewById(R.id.name);



        drinking_button.setOnClickListener(this);
        kitchen_button.setOnClickListener(this);
        toilet_button.setOnClickListener(this);

        relativeLayout = findViewById(R.id.relativeLayout);


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
        switch (view.getId())
        {
            case R.id.drinking_yes:
                show_dialog();
                Toast.makeText(context, "Drinking water", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kitchen_yes:
                show_kitchen_dialog();
                Toast.makeText(context, "Kitchen", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toilet_yes:
                show_toilet_type_dialog();
                Toast.makeText(context, "Toilet", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void show_toilet_type_dialog() {
        {

            final Dialog dialog=new Dialog(AwDetailsActivity.this);
            dialog.setContentView(R.layout.toilet_type);
            dialog.setCancelable(false);
            dialog.show();

            indian_toilet=dialog.findViewById(R.id.indian_toilet);
            western_toilet=dialog.findViewById(R.id.western_toilet);

            AppCompatTextView sumbit=dialog.findViewById(R.id.submit);
            AppCompatTextView cancel=dialog.findViewById(R.id.cancel);

            sumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }
    }

    private void show_kitchen_dialog() {

        {

            final Dialog dialog=new Dialog(AwDetailsActivity.this);
            dialog.setContentView(R.layout.kitcheen_type);
            dialog.setCancelable(false);
            dialog.show();

            open_kitchen=dialog.findViewById(R.id.open_kitchen);
            non_open_kitchen=dialog.findViewById(R.id.non_open_kitchen);


            AppCompatTextView sumbit=dialog.findViewById(R.id.submit);
            AppCompatTextView cancel=dialog.findViewById(R.id.cancel);

            sumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }

    }

    private void show_dialog() {
        {

            final Dialog dialog=new Dialog(AwDetailsActivity.this);
            dialog.setContentView(R.layout.drinking_water_type);
            dialog.setCancelable(false);
            dialog.show();

            mineral=dialog.findViewById(R.id.Mineral_water);
            regular=dialog.findViewById(R.id.normal_water);
            both=dialog.findViewById(R.id.both);

            AppCompatTextView sumbit=dialog.findViewById(R.id.submit);
            AppCompatTextView cancel=dialog.findViewById(R.id.cancel);

            sumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }

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
            case R.id.open_kitchen:
                str = checked ? "" : getResources().getString(R.string.kitchen);
                break;
            case R.id.non_open_kitchen:
                str = checked ? "" : getResources().getString(R.string.kitchen_faciltity);
                break;
            case R.id.indian_toilet:
                str = checked ? "" : getResources().getString(R.string.kitchen);
                break;
            case R.id.western_toilet:
                str = checked ? "" : getResources().getString(R.string.kitchen_faciltity);
                break;

        }

    }

}