package cdac.org.anganvadistaffutility.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.user.activity.UserSectionActivity;
import cdac.org.anganvadistaffutility.user.data.SetUserLogin;
import retrofit2.Call;

public class UserLoginActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;
    private AppCompatEditText et_user_password;
    private AppCompatTextView forgot_password, change_password;
    private String userMobileNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_login);

        userMobileNumber = appPreferences.getMobileNumber();
        relativeLayout = findViewById(R.id.relativeLayout);
        et_user_password = findViewById(R.id.et_user_password);
        forgot_password = findViewById(R.id.forgot_password);
        change_password = findViewById(R.id.change_your_password);

        AppCompatButton btn_login_user = findViewById(R.id.btn_login_user);
        btn_login_user.setOnClickListener(this);
        forgot_password.setOnClickListener(this);
        change_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login_user) {
            String password = Objects.requireNonNull(et_user_password.getText()).toString().trim();

            if (password.isEmpty()) {
                et_user_password.requestFocus();
                et_user_password.setError(getResources().getString(R.string.required_field));
            } else {
                if (AppUtils.isNetworkConnected(context)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    loginUser(appPreferences.getEmployeeId(), password);
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                }
            }
        } else if (view.getId() == R.id.forgot_password) {

            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                sendOtpToServerPublic(relativeLayout, userMobileNumber, AppUtils.getRandomNumberString());
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }

            startActivity(new Intent(context, ForgetPwdOTPActivity.class));
        }
    }

    private void loginUser(String employeeID, String password) {
        apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<SetUserLogin> call = apiInterface.setUserLogin(employeeID, password);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<SetUserLogin>() {
            @Override
            public void onSuccess(SetUserLogin body) {
                if (body.getStatus().getStatusCode().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                    appPreferences.setUserLoggedIn(true);
                    appPreferences.setTypeUser(true);
                    startActivity(new Intent(context, UserSectionActivity.class));
                    finishAffinity();
                } else {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                    AppUtils.showToast(context, body.getStatus().getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
            }
        }));
    }
}