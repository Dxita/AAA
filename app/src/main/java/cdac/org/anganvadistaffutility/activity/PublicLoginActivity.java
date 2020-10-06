package cdac.org.anganvadistaffutility.activity;

import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cdac.org.anganvadistaffutility.R;

public class PublicLoginActivity extends BaseActivity implements View.OnClickListener {
    AppCompatTextView register_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_login);
        register_txt=findViewById(R.id.register_txt);
        register_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_txt:
                startActivity(new Intent(context, PublicRegisterationActivity.class));
                break;
        }
    }
}