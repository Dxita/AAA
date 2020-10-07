package cdac.org.anganvadistaffutility.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import cdac.org.anganvadistaffutility.R;

public class BuildingDetailsActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    String[] district = {"Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bhilwara", "Dausa"};
    CheckBox chk1, chk2, chk3, chk4, chk5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_details);
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spin3 = (Spinner) findViewById(R.id.spinner3);
        chk1 = (CheckBox)findViewById(R.id.chk1);
        chk2 = (CheckBox)findViewById(R.id.chk2);
        chk3 = (CheckBox)findViewById(R.id.chk3);
        chk4 = (CheckBox)findViewById(R.id.chk4);
        chk5 = (CheckBox)findViewById(R.id.chk5);

        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, district);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        spin2.setAdapter(aa);
        spin3.setAdapter(aa);


    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.chk1:
                str = checked?"":getResources().getString(R.string.drinking_water);
                break;
            case R.id.chk2:
                str = checked?"":getResources().getString(R.string.electricity);
                break;
            case R.id.chk3:
                str = checked?"":getResources().getString(R.string.kitchen);
                break;
            case R.id.chk4:
                str = checked?"":getResources().getString(R.string.open_area);
                break;
            case R.id.chk5:
                str = checked?"":getResources().getString(R.string.toilet);
                break;
        }

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), district[i], Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}