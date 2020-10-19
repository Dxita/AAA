package cdac.org.anganvadistaffutility.public_u.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.public_u.activity.activity.PublicInfraActivity;

public class PublicRegistrationActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    AppCompatTextView login_txt;
    AppCompatButton register_btn;
    String[] district = {"", "Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bhilwara", "Dausa"};
    String[] project = {"", "Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bhilwara", "Dausa"};
    String[] sector = {"", "S01", "S02", "S03", "S04", "S05", "S06", "S07"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_public_registeration);
        register_btn = findViewById(R.id.register_btn);
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spin3 = (Spinner) findViewById(R.id.spinner3);

        spin.setOnItemSelectedListener(this);
        register_btn.setOnClickListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, district);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        ArrayAdapter projet_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, project);
        projet_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(projet_adapter);

        ArrayAdapter sector_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sector);
        sector_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(sector_adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                startActivity(new Intent(context, PublicInfraActivity.class));
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}