package cdac.org.anganvadistaffutility.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.activity.BaseActivity;
import cdac.org.anganvadistaffutility.data.AdminUserData;
import cdac.org.anganvadistaffutility.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.utils.AppUtils;
import retrofit2.Call;

public class UsersPieChartActivity extends BaseActivity {

    private RelativeLayout relativeLayout;
    private PieChart pieChart;

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

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        l.setForm(Legend.LegendForm.CIRCLE);

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
                setUserData(body.getData());
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

        Log.e(TAG, "Registered: " + totalRegisteredEmployees);
        Log.e(TAG, "UnRegistered: " + totalUnRegisteredEmployees);

        List<Entry> NoOfEmp = new ArrayList<>();

        NoOfEmp.add(new Entry(totalRegisteredEmployees, 0));
        NoOfEmp.add(new Entry(totalUnRegisteredEmployees, 1));
        /*NoOfEmp.add(new Entry(1133f, 2));
        NoOfEmp.add(new Entry(1240f, 3));
        NoOfEmp.add(new Entry(1369f, 4));
        NoOfEmp.add(new Entry(1487f, 5));
        NoOfEmp.add(new Entry(1501f, 6));
        NoOfEmp.add(new Entry(1645f, 7));

        NoOfEmp.add(new Entry(1578f, 8));
        NoOfEmp.add(new Entry(1695f, 9));*/
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");

        List<String> year = new ArrayList<>();

        year.add("2008");
        year.add("2009");
        /*year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");*/

        PieData pieData = new PieData(year, dataSet);
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(2000, 2000);
    }
}
