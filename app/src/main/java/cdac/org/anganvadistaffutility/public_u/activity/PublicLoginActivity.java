package cdac.org.anganvadistaffutility.public_u.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.UserLoginActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.public_u.activity.data.VerifyPublicData;
import cdac.org.anganvadistaffutility.user.data.VerifyUser;
import retrofit2.Call;

public class PublicLoginActivity extends BaseActivity implements View.OnClickListener {
    AppCompatTextView register_txt;
    AppCompatButton login_btn;
    private EditText edt_user_mobile_no;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_public_login);
        relativeLayout = findViewById(R.id.relativeLayout);

        edt_user_mobile_no = findViewById(R.id.edt_user_mobile_no);
        login_btn = findViewById(R.id.btn_verify_user);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_verify_user) {
            String mobile = login_btn.getText().toString();
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

    private void verifyUser(String mobile) {

        apiInterface = ApiUtils.getApiInterface(ApiUtils.UPDATE_INFRASTRUCTURE);
        Call<VerifyPublicData> call = apiInterface.verifyPublic(mobile);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifyPublicData>() {
            @Override
            public void onSuccess(VerifyPublicData body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                         AppUtils.showToast(context, body.getMessage());

                       /* if (AppUtils.isNetworkConnected(context)) {
                            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
                            sendOtpToServerPublic(relativeLayout, mobile, AppUtils.getRandomNumberString());
                        } else {
                            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
                        }*/

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