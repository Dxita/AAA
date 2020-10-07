package cdac.org.anganvadistaffutility.activity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import cdac.org.anganvadistaffutility.R;

public class PublicRegisterationActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
AppCompatTextView login_txt;
AppCompatButton register_btn;
    String[] district = {"Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bhilwara", "Dausa"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_public_registeration);
        login_txt=findViewById(R.id.login_txt);
        register_btn=findViewById(R.id.register_btn);
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spin3 = (Spinner) findViewById(R.id.spinner3);

        spin.setOnItemSelectedListener(this);
        login_txt.setOnClickListener(this);
        register_btn.setOnClickListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, district);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        spin2.setAdapter(aa);
        spin3.setAdapter(aa);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_txt:
                startActivity(new Intent(context, PublicLoginActivity.class));
                break;
            case R.id.register_btn:
                startActivity(new Intent(context, PublicInfraActivity.class));
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), district[i], Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}