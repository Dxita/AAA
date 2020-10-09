package cdac.org.anganvadistaffutility.admin.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.DistrictWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;


public class UsersPieChartActivity extends BaseActivity implements OnChartValueSelectedListener {

    private RelativeLayout relativeLayout;
    private PieChart pieChart;

    private List<DistrictWiseEmployeeDetails> districtWiseEmployeeDetailsList;
    private AdminUserData.Data empData;
    private int totalRegisteredEmployees = 0;
    private int totalUnRegisteredEmployees = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_users_pie_chart);

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
        pieChart.animateY(1400, Easing.EaseInOutQuad);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(14f);

        pieChart.setOnChartValueSelectedListener(this);

      /*  List<LegendEntry> legendEntries = new ArrayList<>();
        legendEntries.add(new LegendEntry("Registered Employees", Legend.LegendForm.SQUARE, 11f, 11f,
                null, ContextCompat.getColor(context, R.color.green)));

        legendEntries.add(new LegendEntry("UnRegistered Employees", Legend.LegendForm.SQUARE, 11f, 11f,
                null, ContextCompat.getColor(context, R.color.red)));*/

       /* Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setCustom(legendEntries);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);*/

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAdminUserData();
        }
    }

    private void getAdminUserData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.ADMIN_USER_DATA_BASE_URL);
        Call<AdminUserData> call = apiInterface.getAdminUserData();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<AdminUserData>() {
            @Override
            public void onSuccess(AdminUserData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, body.getMessage());
                if (pieChart.getVisibility() == View.GONE) {
                    pieChart.setVisibility(View.VISIBLE);
                }
                empData = body.getData();
                setUserData(empData);
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void setUserData(AdminUserData.Data data) {
        for (AdminUserData.Empdatum empDatum : data.getEmpdata()) {
            totalRegisteredEmployees = totalRegisteredEmployees + Integer.parseInt(empDatum.getRegistered());
            totalUnRegisteredEmployees = totalUnRegisteredEmployees + Integer.parseInt(empDatum.getUnregistered());
        }

        List<PieEntry> NoOfEmp = new ArrayList<>();
        NoOfEmp.add(new PieEntry(totalRegisteredEmployees, "Registered Employees " + "(" + totalRegisteredEmployees + ")"));
        NoOfEmp.add(new PieEntry(totalUnRegisteredEmployees, "UnRegistered Employees " + "(" + totalUnRegisteredEmployees + ")"));

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "");
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(1.2f);
        dataSet.setValueLineVariableLength(true);

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        //   dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(4f);

        List<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(context, R.color.green));
        colors.add(ContextCompat.getColor(context, R.color.red));

        PieData pieData = new PieData(dataSet);
        ValueFormatter vf = new ValueFormatter() { //value format here, here is the overridden method
            @Override
            public String getFormattedValue(float value) {
                return "" + (int) value;
            }
        };

        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);
        pieChart.setData(pieData);
        //    pieChart.setDrawEntryLabels(true);
        dataSet.setColors(colors);

        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        List<AdminUserData.Empdatum> empDatumList = empData.getEmpdata();
        ArrayList<AdminUserData.Empdatum> empDatumArrayList = new ArrayList<>(empDatumList);

        if (h.getX() == 0.0) {
            startActivity(new Intent(context, DistrictWisePieChartActivity.class).putExtra("data_type", "registered_user")
                    .putExtra("emp_data", AppUtils.convertUserToPut(empDatumArrayList)));
        } else {
            startActivity(new Intent(context, DistrictWisePieChartActivity.class).putExtra("data_type", "unregistered_user")
                    .putExtra("emp_data", AppUtils.convertUserToPut(empDatumArrayList)));
        }

      /*  int districtWiseEmployees = 0;
        int previousDistrictID;
        int currentDistrictID = -1;

        districtWiseEmployeeDetailsList = new ArrayList<>();
        if (h.getX() == 0.0) {
            for (AdminUserData.Empdatum empDatum : empData.getEmpdata()) {
                previousDistrictID = currentDistrictID;
                currentDistrictID = Integer.parseInt(empDatum.getTjdmDistrictId());

                if (previousDistrictID == currentDistrictID) {
                    districtWiseEmployees = districtWiseEmployees + Integer.parseInt(empDatum.getRegistered());
                } else {
                    if (previousDistrictID != -1) {
                        DistrictWiseEmployeeDetails districtWiseEmployeeDetails = new DistrictWiseEmployeeDetails();
                        districtWiseEmployeeDetails.setDistrict_name_english(empDatum.getTjdmDistrictNameEnglish());
                        districtWiseEmployeeDetails.setDistrict_employees("" + districtWiseEmployees);
                        districtWiseEmployeeDetails.setDistrict_id("" + previousDistrictID);
                        districtWiseEmployeeDetailsList.add(districtWiseEmployeeDetails);
                    }
                    districtWiseEmployees = Integer.parseInt(empDatum.getRegistered());
                }
            }
        } else {
            for (AdminUserData.Empdatum empDatum : empData.getEmpdata()) {
                previousDistrictID = currentDistrictID;
                currentDistrictID = Integer.parseInt(empDatum.getTjdmDistrictId());

                if (previousDistrictID == currentDistrictID) {
                    districtWiseEmployees = districtWiseEmployees + Integer.parseInt(empDatum.getUnregistered());
                } else {
                    if (previousDistrictID != -1) {
                        DistrictWiseEmployeeDetails districtWiseEmployeeDetails = new DistrictWiseEmployeeDetails();
                        districtWiseEmployeeDetails.setDistrict_name_english(empDatum.getTjdmDistrictNameEnglish());
                        districtWiseEmployeeDetails.setDistrict_employees("" + districtWiseEmployees);
                        districtWiseEmployeeDetails.setDistrict_id("" + previousDistrictID);
                        districtWiseEmployeeDetailsList.add(districtWiseEmployeeDetails);
                    }
                    districtWiseEmployees = Integer.parseInt(empDatum.getUnregistered());
                }
            }
        }
        setEmployeeData(districtWiseEmployeeDetailsList);*/
    }

    @Override
    public void onNothingSelected() {
        // do nothing
    }

    private void setEmployeeData(List<DistrictWiseEmployeeDetails> districtWiseEmployeeDetails) {
        List<PieEntry> NoOfEmp = new ArrayList<>();
        for (int j = 0; j < 7; j++) {
            // NoOfEmp.add(new PieEntry(districtWiseEmployeeDetails.get(j).getDistrict_name_english(), j));
            NoOfEmp.add(new PieEntry(Integer.parseInt(districtWiseEmployeeDetails.get(j).getDistrict_employees()),
                    districtWiseEmployeeDetails.get(j).getDistrict_name_english() + " (" + districtWiseEmployeeDetails.get(j).getDistrict_employees() + ")"));
        }

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "");
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(1.2f);
        dataSet.setValueLineVariableLength(true);

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(4f);

        PieData pieData = new PieData(dataSet);
        ValueFormatter vf = new ValueFormatter() { //value format here, here is the overridden method
            @Override
            public String getFormattedValue(float value) {
                return "" + (int) value;
            }
        };

        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);
        pieChart.setData(pieData);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }
}
