package cdac.org.anganvadistaffutility.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import cdac.org.anganvadistaffutility.R;

import static cdac.org.anganvadistaffutility.R.drawable.button_stroke;
import static cdac.org.anganvadistaffutility.R.drawable.white_stroke;

public class SelectLanguageActivity extends BaseActivity  {

    TextView english_text, hindi_text;
    Button continue_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_language);

        english_text=findViewById(R.id.english_text);
        hindi_text=findViewById(R.id.hindi_text);

        continue_button=findViewById(R.id.continue_button);


       continue_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent=new Intent(getApplicationContext(), UserTypeActivity.class);
               startActivity(intent);
               finish();
           }
       });

       hindi_text.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               TextView textView = (TextView) view;
               if (textView.isSelected()) {
                   textView.setTextColor(Color.WHITE);
                  textView.setBackgroundColor(Color.GRAY);
                   view.setSelected(false);
               } else {
                   textView.setTextColor(Color.BLACK);
                   textView.setBackgroundResource(button_stroke);
                   view.setSelected(true);
               }
           }
       });

    }


}