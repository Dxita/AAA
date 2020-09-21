package cdac.org.anganvadistaffutility.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.utils.AppUtils;
import retrofit2.Call;

public class ViewAaganwadiDataActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_aaganwadi_data);

        CardView view_aaganwadi_data = findViewById(R.id.view_aaganwadi_data);
        CardView view_payment_data = findViewById(R.id.view_payment_data);

        view_aaganwadi_data.setOnClickListener(this);
        view_payment_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_aaganwadi_data:
                startActivity(new Intent(context, ShowKPIActivity.class));
                break;
            case R.id.view_payment_data:
                if (AppUtils.isNetworkConnected(context)) {
                    getAaganwadiInfrastructureData();
                }
                break;
        }
    }

    private void getAaganwadiInfrastructureData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.INFRASTRUCTURE_BASE_URL);
        Call<AaganwadiInfraStructure> call = apiInterface.getInfrastructureData();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AaganwadiInfraStructure>() {
            @Override
            public void onSuccess(AaganwadiInfraStructure body) {
             //   AppUtils.showToast(context, body.getMessage());
                AppUtils.showToast(context, body.getData().getInfrastructureData().get(0).getTimInfrastructureNamee());
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }
}
