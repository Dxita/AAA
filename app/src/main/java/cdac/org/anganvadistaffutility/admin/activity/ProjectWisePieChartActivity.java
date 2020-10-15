package cdac.org.anganvadistaffutility.admin.activity;

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

import androidx.annotation.NonNull;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.ProjectWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;


public class ProjectWisePieChartActivity extends BaseActivity implements OnChartValueSelectedListener {

    private LinearLayout ll_bottom_sheet;
    private PieChart pieChart;
    private List<ProjectWiseEmployeeDetails> projectWiseEmployeeDetailsList;
    private String userType = "";
    private String cdpoMobile = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_project_wise_pie_chart);

        String projectData = getIntent().getStringExtra("project_data");
        userType = getIntent().getStringExtra("user_type");
        projectWiseEmployeeDetailsList = AppUtils.convertProjectToGet(projectData);

        TextView txt_title = findViewById(R.id.txt_title);
        if (userType.equalsIgnoreCase("registered_user")) {
            txt_title.setText(getResources().getString(R.string.project_wise_reg_users));
        } else {
            txt_title.setText(getResources().getString(R.string.project_wise_unreg_users));
        }
        /*txt_title.setText(getResources().getString(R.string.project_name) + projectName + "\n"
                + getResources().getString(R.string.project_code) + projectCode);*/

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
        setProjectData();

        setBottomSheet();
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
                /*if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }*/
                toggleBottomSheet(projectWiseEmployeeDetails.getProject_code(), projectWiseEmployeeDetails.getProject_name_english(),
                        projectWiseEmployeeDetails.getProject_head_name(), projectWiseEmployeeDetails.getProject_head_phone(),
                        projectWiseEmployeeDetails.getProject_head_email());
            }
        }
    }

    @Override
    public void onNothingSelected() {
    }

    private void setBottomSheet() {
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
    }

    private void toggleBottomSheet(String projectCode, String projectName, String name, String mobile, String email) {
        if (ll_bottom_sheet.getVisibility() == View.GONE) {
            ll_bottom_sheet.setVisibility(View.VISIBLE);
        }
       /* if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }*/

        cdpoMobile = mobile;
        TextView txt_cdpo_name = ll_bottom_sheet.findViewById(R.id.txt_cdpo_name);
        TextView txt_project_name = ll_bottom_sheet.findViewById(R.id.txt_project_name);
        TextView txt_project_code = ll_bottom_sheet.findViewById(R.id.txt_project_code);
        Button btn_call = ll_bottom_sheet.findViewById(R.id.btn_call);
        Button btn_email = ll_bottom_sheet.findViewById(R.id.btn_email);

        txt_cdpo_name.setText("CDPO Name: " + name);
        txt_project_name.setText("Project Name: " + projectName);
        txt_project_code.setText("Project Code: " + projectCode);

        btn_call.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AppUtils.hasPermissions(context, AppUtils.CALL_PERMISSIONS)) {
                    makePhoneCall(mobile);
                } else {
                    requestPermissions(AppUtils.CALL_PERMISSIONS, AppUtils.CALL_PERMISSION_REQUEST_CODE);
                }
            } else {
                makePhoneCall(mobile);
            }
        });

        btn_email.setOnClickListener(view -> {
            sendEmail(email);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtils.CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(cdpoMobile);
            }
        }
    }

    private void makePhoneCall(String mobile) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mobile));
        startActivity(callIntent);
    }

    private void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        //need this to prompts email client only
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }
}
