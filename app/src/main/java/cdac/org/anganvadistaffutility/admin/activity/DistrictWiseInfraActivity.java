package cdac.org.anganvadistaffutility.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.adapter.DistrictWiseInfrastructuretAdapter;
import cdac.org.anganvadistaffutility.admin.adapter.DistrictWisePieChartAdapter;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.DistrictWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailProjectData;
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.admin.data.ProjectWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;

public class DistrictWiseInfraActivity extends BaseActivity implements DistrictWiseInfrastructuretAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private InfraStructureDetailData.Data infraDetailsData;
    private int infraCount = 0;
    private int currentInfraDetailID = -1;
    private InfraDetailData infraDetailData;
    private String infra_detail_id;
    private DistrictWiseInfrastructuretAdapter.ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_demo);
        TextView txt_title = findViewById(R.id.txt_title);

        //  itemClickListener = this;
        infra_detail_id = getIntent().getStringExtra("infra_detail_id");
        itemClickListener = this;
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (AppUtils.isNetworkConnected(context)) {

            getInfraDistrictData();
        }
    }

    private void getInfraDistrictData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<InfraStructureDetailData> call = apiInterface.getInfrastructureDetails("dist", appPreferences.getAdminInfraId(), "");
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfraStructureDetailData>() {
            @Override
            public void onSuccess(InfraStructureDetailData body) {
                //    AppUtils.showToast(context, body.getMessage());


                infraDetailsData = body.getData();

                if (infraDetailsData.getInfradata().size() > 0) {
                    setUserData(infraDetailsData);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void setUserData(InfraStructureDetailData.Data detailData) {
        int previousInfraDetailID;
        List<InfraDetailData> infraDetailDataList = new ArrayList<>();
        //  List<PieEntry> chartInfraCount = new ArrayList<>();

        for (InfraStructureDetailData.Infradatum infraDatum : detailData.getInfradata()) {
            previousInfraDetailID = currentInfraDetailID;
            currentInfraDetailID = Integer.parseInt(infraDatum.getDistid());

            // To make sum of all same infra detail data and finally add to list
            // add same id data only once
            if (previousInfraDetailID == -1 || previousInfraDetailID == currentInfraDetailID) {
                if (!infraDetailDataList.isEmpty()) {
                    infraDetailDataList.remove(infraDetailDataList.size() - 1);
                }
            }
            if (infraDetailData != null) {
                infraDetailDataList.add(infraDetailData);
            }

            if (previousInfraDetailID == currentInfraDetailID) {
                //infraCount = infraCount + Integer.parseInt(infraDatum.getCount());
                infraCount = Integer.parseInt(infraDatum.getCount());
            } else {
                infraDetailData = new InfraDetailData();
                infraDetailData.setDistrictID(infraDatum.getDistid());
                infraDetailData.setDistrict(infraDatum.getDistrict());
                infraCount = Integer.parseInt(infraDatum.getCount());
            }
            infraDetailData.setInfraCount("" + infraCount);
        }

        Collections.sort(infraDetailDataList, Collections.reverseOrder());
        DistrictWiseInfrastructuretAdapter districtWiseInfrastructuretAdapter = new DistrictWiseInfrastructuretAdapter(this, infraDetailDataList, itemClickListener);
        recyclerView.setAdapter(districtWiseInfrastructuretAdapter);
    }

    @Override
    public void onItemClick(Object item) {

            startActivity(new Intent(context,ProjectWisePieChartActivity.class).putExtra("district_id",infraDetailData.getDistrict()));
    }
}