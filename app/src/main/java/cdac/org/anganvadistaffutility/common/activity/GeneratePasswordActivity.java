package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;
import cdac.org.anganvadistaffutility.user.data.SetUserPassword;
import retrofit2.Call;

import static cdac.org.anganvadistaffutility.common.utils.AppUtils.isValidPassword;

public class GeneratePasswordActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;
    private AppCompatEditText pwd, c_pwd;
    private String userMobileNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_generate_pasword);

        relativeLayout = findViewById(R.id.relativeLayout);
        pwd = findViewById(R.id.generate_pswd);
        c_pwd = findViewById(R.id.confirm_pswd);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userMobileNumber = bundle.getString("mobile_number");
        }

        AppCompatButton continue_button = findViewById(R.id.continue_button);
        continue_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.continue_button) {
            if (Objects.requireNonNull(pwd.getText()).toString().isEmpty() || pwd.getText().toString().length() < 8 && isValidPassword(pwd.getText().toString())) {
                pwd.requestFocus();
                Toast.makeText(context, "" + getResources().getString(R.string.follow_password_guidlines), Toast.LENGTH_SHORT).show();
            } else if (Objects.requireNonNull(c_pwd.getText()).toString().length() < 8 && isValidPassword(c_pwd.getText().toString())) {
                c_pwd.requestFocus();
                Toast.makeText(context, "" + getResources().getString(R.string.follow_password_guidlines), Toast.LENGTH_SHORT).show();
            } else if (!(c_pwd.getText().toString().equals(pwd.getText().toString()))) {
                c_pwd.requestFocus();
                Toast.makeText(context, "" + getResources().getString(R.string.password_doesnt_match), Toast.LENGTH_SHORT).show();
            } else {
                if (AppUtils.isNetworkConnected(context)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    setUserPassword();
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                }
            }
        }
    }

    private void setUserPassword() {
        apiInterface = ApiUtils.getApiInterface(ApiUtils.SET_USER_PASSWORD);
        Call<SetUserPassword> call = apiInterface.setUserPassword(appPreferences.getEmployeeId(), userMobileNumber,
                Objects.requireNonNull(pwd.getText()).toString());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<SetUserPassword>() {
            @Override
            public void onSuccess(SetUserPassword body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.showToast(context, body.getMessage());
                    startActivity(new Intent(context, UserSectionActivity.class));
                    finishAffinity();
                } else {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                    AppUtils.showToast(context, body.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
            }
        }));
    }

}