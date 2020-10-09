package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.user.activity.HomeActivity;
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;

public class GeneratePasswordActivity extends BaseActivity implements View.OnClickListener {
    AppCompatEditText pwd, c_pwd;
    AppCompatButton continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_pasword);

        continue_button = findViewById(R.id.continue_button);
        continue_button.setOnClickListener(this);

        pwd = findViewById(R.id.generate_pswd);
        c_pwd = findViewById(R.id.confirm_pswd);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.continue_button) {
            if (pwd.getText().toString().isEmpty() || pwd.getText().toString().length() < 8 && !isValidPassword(pwd.getText().toString())) {
                pwd.requestFocus();
                Toast.makeText(context, "" + getResources().getString(R.string.follow_password_guidlines), Toast.LENGTH_SHORT).show();

            } else if (c_pwd.getText().toString().length() < 8 && !isValidPassword(c_pwd.getText().toString())) {
                c_pwd.requestFocus();
                Toast.makeText(context, "" + getResources().getString(R.string.follow_password_guidlines), Toast.LENGTH_SHORT).show();
            } else if (!(c_pwd.getText().toString().equals(pwd.getText().toString()))) {
                c_pwd.requestFocus();
                Toast.makeText(context, "" + getResources().getString(R.string.password_doesnt_match), Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(context, UserSectionActivity.class));
            }

        }

    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-8])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}