package cdac.org.anganvadistaffutility.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.activity.BaseActivity;
import cdac.org.anganvadistaffutility.data.RegisteredUserKPI;
import cdac.org.anganvadistaffutility.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.utils.AppUtils;
import retrofit2.Call;

public class UsersGraphActivity extends BaseActivity {

    private RelativeLayout relativeLayout;
    private LineChart mChart;
    private ArrayList<String> x;
    private ArrayList<Entry> y;
    private ArrayList<Entry> z;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_users_graph);

        relativeLayout = findViewById(R.id.relativeLayout);
        mChart = findViewById(R.id.lineChart);

        x = new ArrayList<>();
        y = new ArrayList<>();
        z = new ArrayList<>();

        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.getXAxis().setTextSize(14f);
        mChart.getAxisLeft().setTextSize(14f);
        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(false);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setForm(Legend.LegendForm.CIRCLE);

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getRegisteredUserKPI();
        }
    }

    private void getRegisteredUserKPI() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.SHOW_USER_KPI_BASE_URL);
        Call<RegisteredUserKPI> call = apiInterface.getRegisteredUserKPI();
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<RegisteredUserKPI>() {
            @Override
            public void onSuccess(RegisteredUserKPI body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                // AppUtils.showToast(context, body.getMessage());
                if (mChart.getVisibility() == View.GONE) {
                    mChart.setVisibility(View.VISIBLE);
                }
                setLineData(body.getData());
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void setLineData(RegisteredUserKPI.Data data) {

        for (int i = 0; i < data.getEmpdata().size(); i++) {
            RegisteredUserKPI.Empdatum empdatum = data.getEmpdata().get(i);

            y.add(new Entry(Integer.parseInt(empdatum.getTotalEmployees()), i));
            z.add(new Entry(Integer.parseInt(empdatum.getRegisterredEmployee()), i));
            x.add(empdatum.getMonthyear());
        }

        LineDataSet set1 = new LineDataSet(y, getResources().getString(R.string.total_employees));
        set1.setColor(Color.RED);
        set1.setLineWidth(5f);
        set1.setCircleRadius(2f);

        LineDataSet set2 = new LineDataSet(z, getResources().getString(R.string.registered_employees));
        set2.setColor(Color.GREEN);
        set2.setLineWidth(3f);
        set2.setCircleRadius(2f);

        LineData datas = new LineData(x);
        datas.addDataSet(set1);
        datas.addDataSet(set2);
        mChart.setData(datas);
        mChart.invalidate();
    }
}

