package cdac.org.anganvadistaffutility.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.user.data.ChangePasswordData;
import cdac.org.anganvadistaffutility.user.data.SetUserLogin;
import retrofit2.Call;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;
    private AppCompatEditText old_password,new_password, confirm_password;
    private String mobile_number="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_user);
        setContentView(R.layout.activity_change_password);
        relativeLayout=findViewById(R.id.relativeLayout);
        old_password=findViewById(R.id.old_pswd);
        new_password=findViewById(R.id.new_pswd);
        confirm_password=findViewById(R.id.confirm_pswd);

        mobile_number=appPreferences.getMobileNumber();
        AppCompatButton continue_button = findViewById(R.id.continue_button);
        continue_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.continue_button) {
            String oldPassword = Objects.requireNonNull(old_password.getText()).toString().trim();
            String newPassword = Objects.requireNonNull(new_password.getText()).toString().trim();
            String confirmPassword = Objects.requireNonNull(confirm_password.getText()).toString().trim();

           if (oldPassword.isEmpty()) {
                old_password.requestFocus();
                old_password.setError(getResources().getString(R.string.required_field));
            }
            else if (newPassword.isEmpty()) {
                new_password.requestFocus();
                new_password.setError(getResources().getString(R.string.required_field));
            }
           else if (confirmPassword.isEmpty()) {
               confirm_password.requestFocus();
               confirm_password.setError(getResources().getString(R.string.required_field));
           }

           else if (!confirmPassword.equals(newPassword)){
               confirm_password.requestFocus();
               confirm_password.setError(getResources().getString(R.string.password_doesnt_match));
           }
           else if (!newPassword.equals(confirmPassword)){
               new_password.requestFocus();
               new_password.setError(getResources().getString(R.string.password_doesnt_match));
           }
            else {
                if (AppUtils.isNetworkConnected(context)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    changePasswordApi(appPreferences.getMobileNumber(),oldPassword,newPassword);
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                }
            }
        }

    }

    private void changePasswordApi(String mobileNumber, String oldPassword, String newPassword) {
        apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<ChangePasswordData> call = apiInterface.changePassword(mobileNumber, oldPassword,newPassword);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<ChangePasswordData>() {
            @Override
            public void onSuccess(ChangePasswordData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, body.getMessage());

                loginagain();

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
            }
        }));
    }

    private void loginagain() {
            AppUtils.showToast(context, getResources().getString(R.string.logout_success));

            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
            }


    }
}