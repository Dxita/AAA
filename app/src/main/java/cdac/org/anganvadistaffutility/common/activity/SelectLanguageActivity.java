package cdac.org.anganvadistaffutility.common.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import uk.co.senab.photoview.PhotoViewAttacher;

public class SelectLanguageActivity extends BaseActivity implements View.OnClickListener {

    private TextView hindi_text, english_text;
     RelativeLayout select_language_activity;
     LinearLayout continue_button_layout;
    private Button continue_button;
    private boolean isContinue = false;
     PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_language);

       // showImage();

        english_text = findViewById(R.id.english_text);
        hindi_text = findViewById(R.id.hindi_text);
        continue_button = findViewById(R.id.continue_button);
        select_language_activity = findViewById(R.id.select_language_layout);
        continue_button_layout = findViewById(R.id.button_language_activity);

        if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
           /* english_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_unselected));
            hindi_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_selected));
            //hindi_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_circle_24, 0);*/
           english_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            hindi_text.setEnabled(true);

        } else {
            english_text.setEnabled(true);
            hindi_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


        }

        english_text.setOnClickListener(this);
        hindi_text.setOnClickListener(this);
       // continue_button.setOnClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
       /*     case R.id.continue_button:
                isContinue = true;
                startActivity(new Intent(context, UserTypeActivity.class));
                finish();
                break;
*/
            case R.id.english_text:
                english_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_selected));
                hindi_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_unselected));
                isContinue = true;
                english_text.setClickable(false);
                changeAppLocale((AppCompatActivity) context, LocaleManager.ENGLISH);
                startActivity(new Intent(context, UserTypeActivity.class));
                finishAffinity();
                break;
            case R.id.hindi_text:
                english_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_unselected));
                hindi_text.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_lan_selected));
                isContinue = true;
                hindi_text.setClickable(false);
                changeAppLocale((AppCompatActivity) context, LocaleManager.HINDI);
                startActivity(new Intent(context, UserTypeActivity.class));
                finishAffinity();
                break;
        }
    }

    @Override
    protected void onDestroy() {

        if (!appPreferences.isUserLoggedIn() && LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.ENGLISH) && !isContinue) {
            clearUserData();
        }
        super.onDestroy();
    }

   /* public void showImage() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_fullimage_dialoge,
                (ViewGroup) findViewById(R.id.layout_root));

        ImageView image = layout.findViewById(R.id.fullimage);
        ImageView close = layout.findViewById(R.id.close);
        image.setImageResource(R.drawable.map);
        mAttacher = new PhotoViewAttacher(image);
        image.requestLayout();
        dialog.setContentView(layout);
        dialog.show();

        dialog.setCanceledOnTouchOutside(true);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }*/
}
