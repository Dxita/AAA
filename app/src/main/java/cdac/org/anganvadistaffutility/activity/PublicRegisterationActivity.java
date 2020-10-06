package cdac.org.anganvadistaffutility.activity;

import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import cdac.org.anganvadistaffutility.R;

public class PublicRegisterationActivity extends BaseActivity implements View.OnClickListener {
AppCompatTextView login_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_public_registeration);
        login_txt=findViewById(R.id.login_txt);
        login_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_txt:
                startActivity(new Intent(context, PublicLoginActivity.class));
                break;
        }

    }
}