package cdac.org.anganvadistaffutility.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.adapter.DistrictWiseInfrastructuretAdapter;
import cdac.org.anganvadistaffutility.admin.adapter.DistrictWisePieChartAdapter;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.DistrictWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.admin.data.DistrictWiseInfraData;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.user.InfraDetailsActivity;

public class DistrictWiseInfraActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<InfraStructureDetailData.Infradatum> infraDetailsData;
    private int districtWiseEmployees = 0;
    private int currentDistrictID = -1;
    private DistrictWiseInfraData districtWiseInfraData;
    private DistrictWiseInfraActivity itemClickListener;
    private String filter_by = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_district_wise_pie_chart);
        setContentView(R.layout.activity_district_wise_infra);

        String infraData = getIntent().getStringExtra("infra_data");
        String filter_by = getIntent().getStringExtra("filter_by");
        infraDetailsData = AppUtils.convertInfradataToGet(infraData);

        TextView txt_title = findViewById(R.id.txt_title);
        txt_title.setText(infraDetailsData.get(0).getTidInfraNamee());

        itemClickListener = this;
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (filter_by != null) {
            setUserData(filter_by);
        }
    }

    private void setUserData(String filter_by) {
        int previousDistrictID;
        List<DistrictWiseInfraData> districtWiseInfraDataList = new ArrayList<>();
        if (filter_by.equalsIgnoreCase("filter_by")) {
            for (InfraStructureDetailData.Infradatum infradatum : infraDetailsData) {
                previousDistrictID = currentDistrictID;
                currentDistrictID = Integer.parseInt(infraDetailsData.get(0).getDistid());
                // To make sum of all same district data and finally add to district list

                if (previousDistrictID != -1 && previousDistrictID != currentDistrictID) {
                    districtWiseInfraDataList.add(districtWiseInfraData);
                }
                if (previousDistrictID == currentDistrictID) {

                    districtWiseEmployees = districtWiseEmployees + Integer.parseInt(infraDetailsData.get(0).getTidInfraNamee());


                } else {
                    districtWiseInfraData = new DistrictWiseInfraData();
                    districtWiseInfraData.setDistid(infraDetailsData.get(0).getDistid());
                    districtWiseInfraData.setTidInfraNamee(infraDetailsData.get(0).getTidInfraNamee());
                    districtWiseEmployees = Integer.parseInt(infraDetailsData.get(0).getTidInfraNamee());
                       /* if ((!empDatum.getRegistered().equalsIgnoreCase("0")))
                        {
                            districtWiseEmployees = Integer.parseInt(empDatum.getRegistered());
                        }*/
                }

                districtWiseInfraData.setTidInfraNamee(formatEmployeeCount("" + districtWiseEmployees));

              /*  Collections.sort(districtWiseEmployeeDetailsList, Collections.reverseOrder());
                DistrictWisePieChartAdapter districtWisePieChartAdapter = new DistrictWisePieChartAdapter(this, districtWiseEmployeeDetailsList, itemClickListener);
                recyclerView.setAdapter(districtWisePieChartAdapter);*/
            }


        }
}

    private String formatEmployeeCount(String employeeCount) {
        int ec = Integer.parseInt(employeeCount);
        if (ec < 9) {
            return "0" + employeeCount;
        } else {
            return "" + employeeCount;
        }
    }
    }