package cdac.org.anganvadistaffutility.admin.activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.adapter.DistrictWisePieChartAdapter;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.DistrictWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;

public class DistrictWisePieChartActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<AdminUserData.Empdatum> empDatumList;
    private DistrictWisePieChartAdapter districtWisePieChartAdapter;

    private List<DistrictWiseEmployeeDetails> districtWiseEmployeeDetailsList;
    private int districtWiseEmployees = 0;
    private int currentDistrictID = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_district_wise_pie_chart);

        districtWiseEmployeeDetailsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        String emdData = getIntent().getStringExtra("emp_data");
        String user_type = getIntent().getStringExtra("data_type");
        empDatumList = AppUtils.convertUserToGet(emdData);

        if (user_type != null) {
            setUserData(user_type);
        }
    }

    private void setUserData(String userType) {
        int previousDistrictID;
        if (userType.equalsIgnoreCase("registered_user")) {
            for (AdminUserData.Empdatum empDatum : empDatumList) {
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
            for (AdminUserData.Empdatum empDatum : empDatumList) {
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

        districtWisePieChartAdapter = new DistrictWisePieChartAdapter(this, districtWiseEmployeeDetailsList);
        recyclerView.setAdapter(districtWisePieChartAdapter);
    }
}
