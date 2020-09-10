package cdac.org.anganvadistaffutility.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.VerifyUser;

public class UserTypeActivity extends BaseActivity {
    CardView c1, c2, c3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);


        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(NewSplashActivity.this, "Working on this", Toast.LENGTH_SHORT).show();
              /*  Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);*/
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       Toast.makeText(NewSplashActivity.this, "Working on this", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), VerifyUserActivity.class);
                startActivity(intent);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);*/


            }
        });


    }
}
