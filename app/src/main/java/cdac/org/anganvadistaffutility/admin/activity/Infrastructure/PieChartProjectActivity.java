package cdac.org.anganvadistaffutility.admin.activity.Infrastructure;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
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


public class PieChartProjectActivity extends BaseActivity implements ProjecttWiseInfrastructuretAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private InfraDetailProjectData.Data infraDetailsData;
    private int infraCount = 0;
    private int currentInfraDetailID = -1;
    private InfraProjectWiseData infraDetailProjectData;
    private String district_id;
    private ProjecttWiseInfrastructuretAdapter.ItemClickListener itemClickListener;
    protected List<InfraProjectWiseData> infraDetailData;
    private RelativeLayout relativeLayout;
    private String cdpoMobile = "";
    private PieChart pieChart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pie_chart_project);


        relativeLayout = findViewById(R.id.relativeLayout);
        district_id = getIntent().getStringExtra("district_id");
        TextView txt_title = findViewById(R.id.txt_title);

        itemClickListener = this;
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getInfraDistrictData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

//        setBottomSheet();
    }

  /*  private void setBottomSheet() {
        ll_bottom_sheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(ll_bottom_sheet);
        bottomSheetBehavior.setHideable(false);
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        // btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        // btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        };
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback);
    }*/

    private void getInfraDistrictData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<InfraDetailProjectData> call = apiInterface.getInfrastructureProjectDetails("proj", appPreferences.getAdminInfraId(), district_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfraDetailProjectData>() {
            @Override
            public void onSuccess(InfraDetailProjectData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //  AppUtils.showToast(context, body.getMessage());
                //  infraDetailsData = body.getData();
               /* if (infraDetailsData.getInfradata().size() > 0) {
                    if (pieChart.getVisibility() == View.GONE) {
                        pieChart.setVisibility(View.VISIBLE);
                    }
                    setUserData(infraDetailsData);
                }*/

                infraDetailsData = body.getData();

                if (infraDetailsData.getInfradata().size() > 0) {
                    setUserData(infraDetailsData);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void setUserData(InfraDetailProjectData.Data detailData) {
        int previousInfraDetailID;
        List<InfraProjectWiseData> infraDetailDataList = new ArrayList<>();
        //  List<PieEntry> chartInfraCount = new ArrayList<>();

        for (InfraDetailProjectData.Infradatum infraDatum : detailData.getInfradata()) {
            previousInfraDetailID = currentInfraDetailID;
            currentInfraDetailID = Integer.parseInt(infraDatum.getProjectcode());

            // To make sum of all same infra detail data and finally add to list
            // add same id data only once
            if (previousInfraDetailID == -1 || previousInfraDetailID == currentInfraDetailID) {
                if (!infraDetailDataList.isEmpty()) {
                    infraDetailDataList.remove(infraDetailDataList.size() - 1);
                }
            }
            if (infraDetailProjectData != null) {
                infraDetailDataList.add(infraDetailProjectData);
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
        //Collections.sort(infraDetailDataList, Collections.reverseOrder());
        ProjecttWiseInfrastructuretAdapter projecttWiseInfrastructuretAdapter = new ProjecttWiseInfrastructuretAdapter(this, infraDetailDataList, itemClickListener);
        recyclerView.setAdapter(projecttWiseInfrastructuretAdapter);
    }

    @Override
    public void onItemClick(Object e) {
        for (InfraDetailProjectData.Infradatum infraProjectWiseData: infraDetailsData.getInfradata()) {
            Toast.makeText(context, ""+infraProjectWiseData.getProjectname(), Toast.LENGTH_SHORT).show();

            if (infraProjectWiseData.getCount().equalsIgnoreCase(e.toString())) {
                /*if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }*/

            }
        }
    }
}
