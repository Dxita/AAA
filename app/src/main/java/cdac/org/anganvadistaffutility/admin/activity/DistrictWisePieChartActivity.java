package cdac.org.anganvadistaffutility.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.adapter.DistrictWisePieChartAdapter;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.DistrictWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.admin.data.ProjectWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;

public class DistrictWisePieChartActivity extends BaseActivity implements DistrictWisePieChartAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private List<AdminUserData.Empdatum> empDatumList;
    private int districtWiseEmployees = 0;
    private int currentDistrictID = -1;
    private DistrictWiseEmployeeDetails districtWiseEmployeeDetails;
    private DistrictWisePieChartAdapter.ItemClickListener itemClickListener;
    private String userType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_district_wise_pie_chart);

        String emdData = getIntent().getStringExtra("emp_data");
        userType = getIntent().getStringExtra("user_type");
        empDatumList = AppUtils.convertUserToGet(emdData);

        TextView txt_title = findViewById(R.id.txt_title);
        if (userType.equalsIgnoreCase("registered_user")) {
            txt_title.setText(getResources().getString(R.string.district_wise_reg_users));
        } else {
            txt_title.setText(getResources().getString(R.string.district_wise_unreg_users));
        }

        itemClickListener = this;
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (userType != null) {
            setUserData(userType);
        }
    }

    private void setUserData(String userType) {
        int previousDistrictID;
        List<DistrictWiseEmployeeDetails> districtWiseEmployeeDetailsList = new ArrayList<>();
        if (userType.equalsIgnoreCase("registered_user")) {
            for (AdminUserData.Empdatum empDatum : empDatumList) {
                previousDistrictID = currentDistrictID;
                currentDistrictID = Integer.parseInt(empDatum.getTjdmDistrictId());

                // To make sum of all same district data and finally add to district list
                if (previousDistrictID != -1 && previousDistrictID != currentDistrictID) {
                    districtWiseEmployeeDetails.setDistrict_employees(formatEmployeeCount("" + districtWiseEmployees));
                    districtWiseEmployeeDetailsList.add(districtWiseEmployeeDetails);
                }

                if (previousDistrictID == currentDistrictID) {
                    districtWiseEmployees = districtWiseEmployees + Integer.parseInt(empDatum.getRegistered());
                } else {
                    districtWiseEmployeeDetails = new DistrictWiseEmployeeDetails();
                    districtWiseEmployeeDetails.setDistrict_id(empDatum.getTjdmDistrictId());
                    districtWiseEmployeeDetails.setDistrict_name_english(empDatum.getTjdmDistrictNameEnglish());
                    districtWiseEmployees = Integer.parseInt(empDatum.getRegistered());
                }
                districtWiseEmployeeDetails.setDistrict_employees("" + districtWiseEmployees);
            }
        } else {
            for (AdminUserData.Empdatum empDatum : empDatumList) {
                previousDistrictID = currentDistrictID;
                currentDistrictID = Integer.parseInt(empDatum.getTjdmDistrictId());

                // To make sum of all same district data and finally add to district list
                if (previousDistrictID != -1 && previousDistrictID != currentDistrictID) {
                    districtWiseEmployeeDetailsList.add(districtWiseEmployeeDetails);
                }

                if (previousDistrictID == currentDistrictID) {
                    districtWiseEmployees = districtWiseEmployees + Integer.parseInt(empDatum.getUnregistered());
                } else {
                    districtWiseEmployeeDetails = new DistrictWiseEmployeeDetails();
                    districtWiseEmployeeDetails.setDistrict_id(empDatum.getTjdmDistrictId());
                    districtWiseEmployeeDetails.setDistrict_name_english(empDatum.getTjdmDistrictNameEnglish());
                    districtWiseEmployees = Integer.parseInt(empDatum.getUnregistered());
                }
                districtWiseEmployeeDetails.setDistrict_employees("" + districtWiseEmployees);
            }
        }

        Collections.sort(districtWiseEmployeeDetailsList, Collections.reverseOrder());
        DistrictWisePieChartAdapter districtWisePieChartAdapter = new DistrictWisePieChartAdapter(this, districtWiseEmployeeDetailsList, itemClickListener);
        recyclerView.setAdapter(districtWisePieChartAdapter);
    }

    private String formatEmployeeCount(String employeeCount) {
        int ec = Integer.parseInt(employeeCount);
        if (ec < 9) {
            return "0" + employeeCount;
        } else {
            return "" + employeeCount;
        }
    }

    @Override
    public void onItemClick(Object item) {
        ArrayList<ProjectWiseEmployeeDetails> projectWiseEmployeeDetailsList = new ArrayList<>();
        for (AdminUserData.Empdatum empDatum : empDatumList) {
            if (empDatum.getTjdmDistrictId().equalsIgnoreCase(item.toString())) {
                ProjectWiseEmployeeDetails projectWiseEmployeeDetails = new ProjectWiseEmployeeDetails();
                projectWiseEmployeeDetails.setProject_code(empDatum.getTjpmProjectCode());
                projectWiseEmployeeDetails.setProject_name_english(empDatum.getTjpmProjectNameEnglish());
                projectWiseEmployeeDetails.setProject_head_name(empDatum.getTjpmProjectInchargeCdpo());
                projectWiseEmployeeDetails.setProject_head_email(empDatum.getTjpmCdpoEmail());
                projectWiseEmployeeDetails.setProject_head_phone(empDatum.getTjpmCdpoMobileNo());
                projectWiseEmployeeDetails.setProject_registered_users(empDatum.getRegistered());
                projectWiseEmployeeDetails.setProject_unregistered_users(empDatum.getUnregistered());
                projectWiseEmployeeDetailsList.add(projectWiseEmployeeDetails);
            }
        }

        startActivity(new Intent(context, ProjectWisePieChartActivity.class)
                .putExtra("user_type", userType)
                .putExtra("project_data", AppUtils.convertProjectToPut(projectWiseEmployeeDetailsList)));
    }
}
