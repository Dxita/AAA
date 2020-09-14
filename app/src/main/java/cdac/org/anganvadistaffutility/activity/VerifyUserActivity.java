package cdac.org.anganvadistaffutility.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.VerifyUser;
import cdac.org.anganvadistaffutility.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.utils.AppUtils;
import retrofit2.Call;

public class VerifyUserActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_user_mobile_no;
    String mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_user);

        edt_user_mobile_no = findViewById(R.id.edt_user_mobile_no);
        AppCompatButton btn_verify_user = findViewById(R.id.btn_verify_user);
        btn_verify_user.setOnClickListener(this);
    }

    private void verifyUser(String userMobileNumber) {
        apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        //  Call<VerifyUser> call = apiInterface.verifyUser(userMobileNumber);
        Call<VerifyUser> call = apiInterface.verifyUser(AppUtils.empMobileNumber);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifyUser>() {
            @Override
            public void onSuccess(VerifyUser body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.showToast(context, body.getMessage());
                    startActivity(new Intent(VerifyUserActivity.this, VerifyOTPActivity.class).putExtra("mobile_number",mobile));
                } else {
                    AppUtils.showToast(context, body.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //
            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_verify_user) {
             mobile = edt_user_mobile_no.getText().toString();
            if (mobile.isEmpty()) {
                edt_user_mobile_no.requestFocus();
                edt_user_mobile_no.setError(getResources().getString(R.string.required_field));
            } else {
                if (AppUtils.isNetworkConnected(context)) {
                    verifyUser(mobile);
                }
            }
        }
    }
}
