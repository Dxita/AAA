package cdac.org.anganvadistaffutility.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.UserLoginActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.user.data.VerifyUser;
import retrofit2.Call;


public class VerifyUserActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;
    private EditText edt_user_mobile_no;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_user);

        relativeLayout = findViewById(R.id.relativeLayout);
        edt_user_mobile_no = findViewById(R.id.edt_user_mobile_no);
        AppCompatButton btn_verify_user = findViewById(R.id.btn_verify_user);
        btn_verify_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_verify_user) {
            String mobile = edt_user_mobile_no.getText().toString();
            if (mobile.isEmpty()) {
                edt_user_mobile_no.requestFocus();
                edt_user_mobile_no.setError(getResources().getString(R.string.required_field));
            } else {
                if (AppUtils.isNetworkConnected(context)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                    verifyUser(mobile);
                } else {
                    AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                }
            }
        }
    }

    private void verifyUser(String userMobileNumber) {
        apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<VerifyUser> call = apiInterface.verifyUser(userMobileNumber);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifyUser>() {
            @Override
            public void onSuccess(VerifyUser body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.showToast(context, body.getMessage());
                    appPreferences.setEmployeeId(body.getData().getEmpdata().getEmpid());
                    if (body.getData().getEmpdata().getPasswordset()) {
                        AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                        startActivity(new Intent(context, UserLoginActivity.class));
                    } else {
                        sendOtpToServer(relativeLayout, userMobileNumber, AppUtils.getRandomNumberString());
                    }
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
