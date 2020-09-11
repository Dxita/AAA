package cdac.org.anganvadistaffutility.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.cardview.widget.CardView;

import cdac.org.anganvadistaffutility.R;

public class UserTypeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_type);

        CardView user_type_admin = findViewById(R.id.user_type_admin);
        CardView user_type_employee = findViewById(R.id.user_type_employee);
        CardView user_type_public = findViewById(R.id.user_type_public);

        user_type_admin.setOnClickListener(this);
        user_type_employee.setOnClickListener(this);
        user_type_public.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_type_admin:
                break;
            case R.id.user_type_employee:
                startActivity(new Intent(context, VerifyUserActivity.class));
                break;
            case R.id.user_type_public:
                break;
        }
    }
}
