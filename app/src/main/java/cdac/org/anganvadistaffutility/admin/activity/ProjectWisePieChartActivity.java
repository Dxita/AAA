package cdac.org.anganvadistaffutility.admin.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

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
import cdac.org.anganvadistaffutility.admin.data.ProjectWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;


public class ProjectWisePieChartActivity extends BaseActivity implements OnChartValueSelectedListener {

    private PieChart pieChart;
    private List<ProjectWiseEmployeeDetails> projectWiseEmployeeDetailsList;
    private String userType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_project_wise_pie_chart);

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

        String projectData = getIntent().getStringExtra("project_data");
        userType = getIntent().getStringExtra("user_type");
        projectWiseEmployeeDetailsList = AppUtils.convertProjectToGet(projectData);

        setProjectData();
    }

    private void setProjectData() {
        List<PieEntry> projectDetails = new ArrayList<>();
        if (userType.equalsIgnoreCase("registered_user")) {
            for (ProjectWiseEmployeeDetails projectWiseEmployeeDetail : projectWiseEmployeeDetailsList) {
                projectDetails.add(new PieEntry(Integer.parseInt(projectWiseEmployeeDetail.getProject_registered_users()),
                        projectWiseEmployeeDetail.getProject_name_english() + " (" + projectWiseEmployeeDetail.getProject_registered_users() + ")"
                        , projectWiseEmployeeDetail.getProject_code()));
            }
        } else {
            for (ProjectWiseEmployeeDetails projectWiseEmployeeDetail : projectWiseEmployeeDetailsList) {
                projectDetails.add(new PieEntry(Integer.parseInt(projectWiseEmployeeDetail.getProject_unregistered_users()),
                        projectWiseEmployeeDetail.getProject_name_english() + " (" + projectWiseEmployeeDetail.getProject_unregistered_users() + ")"
                        , projectWiseEmployeeDetail.getProject_code()));
            }
        }

        PieDataSet dataSet = new PieDataSet(projectDetails, "");
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(1.2f);
        dataSet.setValueLineVariableLength(true);

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        //   dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(4f);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLACK);
        pieChart.setData(pieData);
        //    pieChart.setDrawEntryLabels(true);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        for (ProjectWiseEmployeeDetails projectWiseEmployeeDetails : projectWiseEmployeeDetailsList) {
            if (projectWiseEmployeeDetails.getProject_code().equalsIgnoreCase(e.getData().toString())) {
                Toast.makeText(context, "" + projectWiseEmployeeDetails.getProject_head_name() + "\n" + projectWiseEmployeeDetails.getProject_head_phone()
                        + "\n" + projectWiseEmployeeDetails.getProject_head_email(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNothingSelected() {
    }
}
