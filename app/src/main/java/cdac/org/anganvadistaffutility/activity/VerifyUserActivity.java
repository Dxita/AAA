package cdac.org.anganvadistaffutility.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
EditText edtx;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);
        edtx=findViewById(R.id.edtxt);

        AppCompatButton btn_verify_user = findViewById(R.id.btn_verify_user);
        btn_verify_user.setOnClickListener(this);




    }

    private void verifyUser() {
        apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<VerifyUser> call = apiInterface.verifyUser(AppUtils.empMobileNumber);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifyUser>() {
            @Override
            public void onSuccess(VerifyUser body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.showToast(context, body.getMessage());
                    startActivity(new Intent(VerifyUserActivity.this, VerifyOTPActivity.class));
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

            String edit_text=edtx.getText().toString();
          if (edit_text.isEmpty())
          {
              edtx.requestFocus();
              edtx.setError("This field is required");
          }
          else if (AppUtils.isNetworkConnected(context)) {
                verifyUser();
            }
        }
    }
}
