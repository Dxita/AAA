package cdac.org.anganvadistaffutility.admin.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

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

public class ViewInfraStructureDetailActivity extends BaseActivity implements OnChartValueSelectedListener {

    private RelativeLayout relativeLayout;
    private String infraID = "";

    private InfraStructureDetailData.Data infraDetailsData;
    private InfraDetailData infraDetailData;
    private int infraCount = 0;
    private int previousInfraDetailID = -1;
    private int currentInfraDetailID = -1;
    private PieChart pieChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_infra_detail);

        relativeLayout = findViewById(R.id.relativeLayout);
        pieChart = findViewById(R.id.pieChart);

       /* infraID = getIntent().getStringExtra("infra_id");*/
        appPreferences.setAdminInfraId(getIntent().getStringExtra("infra_id"));

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
        pieChart.animateY(1400, Easing.EaseInOutQuad);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(14f);
        pieChart.setOnChartValueSelectedListener(this);

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getInfraDetails();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void getInfraDetails() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<InfraStructureDetailData> call = apiInterface.getInfrastructureDetails("dist", appPreferences.getAdminInfraId(),"");
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
                    setInfraDetailData(infraDetailsData);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void setInfraDetailData(InfraStructureDetailData.Data detailData) {
        List<InfraDetailData> infraDetailDataList = new ArrayList<>();
        List<PieEntry> chartInfraCount = new ArrayList<>();

        for (InfraStructureDetailData.Infradatum infraDatum : detailData.getInfradata()) {
            previousInfraDetailID = currentInfraDetailID;
            currentInfraDetailID = Integer.parseInt(infraDatum.getTidInfraDetailId());

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
                infraCount = infraCount + Integer.parseInt(infraDatum.getCount());
            } else {
                infraDetailData = new InfraDetailData();
                infraDetailData.setInfraDetailID(infraDatum.getTidInfraDetailId());
                infraDetailData.setInfraName(infraDatum.getTidInfraNamee());
                infraCount = Integer.parseInt(infraDatum.getCount());
            }
            infraDetailData.setInfraCount("" + infraCount);
        }

        for (InfraDetailData infraDetailData : infraDetailDataList) {
            chartInfraCount.add(new PieEntry(Integer.parseInt(infraDetailData.getInfraCount()),
                    infraDetailData.getInfraName() + "(" + infraDetailData.getInfraCount() + ")"));
        }

        PieDataSet dataSet = new PieDataSet(chartInfraCount, "");
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(1.2f);
        dataSet.setValueLineVariableLength(true);

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(4f);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);
        pieChart.setData(pieData);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        /*List<InfraStructureDetailData.Infradatum> infradata = infraDetailsData.getInfradata();
        ArrayList<InfraStructureDetailData.Infradatum> infraDatumArrayList = new ArrayList<>(infradata);
*/

            startActivity(new Intent(context, DistrictWiseInfraActivity.class).putExtra("infra_detail_id",infraDetailData.getInfraDetailID()));



    }

    @Override
    public void onNothingSelected() {
        // do nothing
    }
}
