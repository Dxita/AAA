package cdac.org.anganvadistaffutility.admin.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;

public class ViewInfraStructureDetailActivity extends BaseActivity {

    private RelativeLayout relativeLayout;
    private String infraID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_infra_detail);

        relativeLayout = findViewById(R.id.relativeLayout);
        infraID = getIntent().getStringExtra("infra_id");

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getInfraDetails();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void getInfraDetails() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.ADMIN_INFRA_DETAILS_BASE_URL);
        Call<InfraStructureDetailData> call = apiInterface.getInfrastructureDetails("dist", infraID);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfraStructureDetailData>() {
            @Override
            public void onSuccess(InfraStructureDetailData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //    AppUtils.showToast(context, body.getMessage());

                List<InfraDetailData> infraDetailDataList = new ArrayList<>();
                List<String> infraDetailList = new ArrayList<>();

                for (InfraStructureDetailData.Infradatum infraDatum : body.getData().getInfradata()) {
                    InfraDetailData infraDetailData = new InfraDetailData();
                    infraDetailData.setInfraName(infraDatum.getTidInfraNamee());
                    infraDetailData.setInfraDetailID(infraDatum.getTidInfraDetailId());
                    infraDetailData.setInfraCount(infraDatum.getCount());

                    if (infraDetailList.isEmpty()) {
                        infraDetailList.add(infraDatum.getTidInfraDetailId());
                    } else {
                        if (infraDetailList.contains(infraDatum.getTidInfraDetailId())) {
                            infraDetailData.setInfraCount("" + Integer.parseInt(infraDetailData.getInfraCount() + infraDatum.getCount()));
                        }
                    }
                    infraDetailDataList.add(infraDetailData);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    /*if (!infraDetailDataList.isEmpty()) {
            for (InfraDetailData id : infraDetailDataList) {
            if (id.getInfraDetailID().contains(infraDatum.getTidInfraDetailId())) {
            int a = Integer.parseInt(id.getInfraCount());
            int b = Integer.parseInt(infraDatum.getCount());
            int c = a + b;
            id.setInfraName(infraDatum.getTidInfraNamee());
            id.setInfraDetailID(infraDatum.getTidInfraDetailId());
            id.setInfraCount("" + c);
            } else {
            id.setInfraName(infraDatum.getTidInfraNamee());
            id.setInfraDetailID(infraDatum.getTidInfraDetailId());
            id.setInfraCount(infraDatum.getCount());
            }
            infraDetailDataList.add(id);
            }*/
}
