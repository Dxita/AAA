package cdac.org.anganvadistaffutility.admin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.adapter.DistrictWiseInfrastructuretAdapter;
import cdac.org.anganvadistaffutility.admin.adapter.ProjecttWiseInfrastructuretAdapter;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailProjectData;
import cdac.org.anganvadistaffutility.admin.data.InfraProjectWiseData;
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.admin.data.ProjectWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;


public class ProjectWisePieChartActivity extends BaseActivity implements ProjecttWiseInfrastructuretAdapter.ItemClickListener{

    private RecyclerView recyclerView;
    private InfraDetailProjectData.Data infraDetailsData;
    private int infraCount = 0;
    private int currentInfraDetailID = -1;
    private InfraProjectWiseData infraDetailProjectData;
    private String district_id;
    private ProjecttWiseInfrastructuretAdapter.ItemClickListener itemClickListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_project_wise_pie_chart);
        district_id = getIntent().getStringExtra("district_id");
        TextView txt_title = findViewById(R.id.txt_title);

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
        Call<InfraDetailProjectData> call = apiInterface.getInfrastructureProjectDetails("proj", appPreferences.getAdminInfraId(), district_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfraDetailProjectData>() {
            @Override
            public void onSuccess(InfraDetailProjectData body) {
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

    private void setUserData(InfraDetailProjectData.Data detailData) {
        int previousInfraDetailID;
        List<InfraProjectWiseData> infraDetailProjectDataList = new ArrayList<>();
        //  List<PieEntry> chartInfraCount = new ArrayList<>();

        for (InfraDetailProjectData.Infradatum infraDatum : detailData.getInfradata()) {
            previousInfraDetailID = currentInfraDetailID;
            currentInfraDetailID = Integer.parseInt(infraDatum.getProjectname());

            // To make sum of all same infra detail data and finally add to list
            // add same id data only once
            if (previousInfraDetailID == -1 || previousInfraDetailID == currentInfraDetailID) {
                if (!infraDetailProjectDataList.isEmpty()) {
                    infraDetailProjectDataList.remove(infraDetailProjectDataList.size() - 1);
                }
            }
            if (infraDetailProjectDataList != null) {
                infraDetailProjectDataList.add(infraDetailProjectData);
            }

            if (previousInfraDetailID == currentInfraDetailID) {
                //infraCount = infraCount + Integer.parseInt(infraDatum.getCount());
                infraCount = Integer.parseInt(infraDatum.getCount());
            } else {
                infraDetailProjectData = new InfraProjectWiseData();
                infraDetailProjectData.setProjectCode(infraDatum.getProjectcode());
                infraDetailProjectData.setProjectName(infraDatum.getProjectname());
                infraCount = Integer.parseInt(infraDatum.getCount());
            }
            infraDetailProjectData.setInfraCount("" + infraCount);
        }

        Collections.sort(infraDetailProjectDataList, Collections.reverseOrder());
        ProjecttWiseInfrastructuretAdapter projecttWiseInfrastructuretAdapter = new ProjecttWiseInfrastructuretAdapter(this, infraDetailProjectDataList, itemClickListener);
        recyclerView.setAdapter(projecttWiseInfrastructuretAdapter);
    }

    @Override
    public void onItemClick(Object item) {

    }
}
