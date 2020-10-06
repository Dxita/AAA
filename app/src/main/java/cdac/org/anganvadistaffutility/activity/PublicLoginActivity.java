package cdac.org.anganvadistaffutility.activity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import cdac.org.anganvadistaffutility.R;

public class PublicLoginActivity extends BaseActivity implements View.OnClickListener {
    AppCompatTextView register_txt;
    AppCompatButton login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_public_login);
        login_btn=findViewById(R.id.login_btn);
        register_txt=findViewById(R.id.register_txt);
        register_txt.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_txt:
                startActivity(new Intent(context, PublicRegisterationActivity.class));
                break;
            case R.id.login_btn:
                startActivity(new Intent(context, ViewInfraStructureActivity.class));
                break;

        }
    }
}