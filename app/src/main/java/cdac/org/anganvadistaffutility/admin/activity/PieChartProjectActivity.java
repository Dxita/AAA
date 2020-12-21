package cdac.org.anganvadistaffutility.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;

public class PieChartProjectActivity extends BaseActivity implements OnChartValueSelectedListener {
    private RelativeLayout relativeLayout;
    private PieChart pieChart;
    private AdminUserData.Data empData;
    private int totalRegisteredEmployees = 0;
    private int totalUnRegisteredEmployees = 0;
    private String infraID = "";

    private InfraStructureDetailData.Data infraDetailsData;
    private InfraDetailData infraDetailData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pie_chart_project);

        relativeLayout = findViewById(R.id.relativeLayout);
        pieChart = findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setExtraOffsets(16, 16, 16, 16);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(0);

        pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setDrawCenterText(false);
        pieChart.setRotationAngle(270);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        //  pieChart.animateY(1400, Easing.EaseInOutQuad);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(14f);
        pieChart.setOnChartValueSelectedListener(this);

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
        //    getAdminUserData();
        }
    }

  /*  private void getAdminUserData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.UPDATE_INFRASTRUCTURE);
        Call<InfraStructureDetailData> call = apiInterface.getInfrastructureDetails("dist", infraID);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfraStructureDetailData>() {
            @Override
            public void onSuccess(InfraStructureDetailData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //    AppUtils.showToast(context, body.getMessage());

                infraDetailsData = body.getData();
                if (infraDetailsData.getInfradata().size() > 0) {
                    if (pieChart.getVisibility() == View.GONE) {
                        pieChart.setVisibility(View.VISIBLE);
                    }
                    setUserData(infraDetailsData);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }*/

    private void setUserData(InfraStructureDetailData.Data detailData) {
        List<InfraDetailData> infraDetailDataList = new ArrayList<>();
        List<PieEntry> chartInfraCount = new ArrayList<>();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}