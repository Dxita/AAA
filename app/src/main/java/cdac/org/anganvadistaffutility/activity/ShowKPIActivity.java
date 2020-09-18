package cdac.org.anganvadistaffutility.activity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.RegisteredUserKPI;
import cdac.org.anganvadistaffutility.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.utils.AppUtils;
import retrofit2.Call;

public class ShowKPIActivity extends BaseActivity implements View.OnClickListener, OnChartValueSelectedListener {

    private RelativeLayout relativeLayout;
    private LineChart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_kpi);

        relativeLayout = findViewById(R.id.relativeLayout);
        mChart = findViewById(R.id.lineChart);

        Button btn_show_kpi_chart = findViewById(R.id.btn_show_kpi_chart);
        btn_show_kpi_chart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_show_kpi_chart) {
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
                AppUtils.showToast(context, body.getMessage());
                if (mChart.getVisibility() == View.GONE) {
                    mChart.setVisibility(View.VISIBLE);
                }

                setLineData(body.getData());
            }

            @Override
            public void onFailure(Throwable t) {
                //
            }
        }));
    }

    private void setLineData(RegisteredUserKPI.Data data) {
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);

        ArrayList<Entry> values = new ArrayList<>();
        for (RegisteredUserKPI.Empdatum empdatum: data.getEmpdata()) {
            values.add(new Entry(1, Float.parseFloat(empdatum.getAMonth() + empdatum.getAYear())));
        }

        LineDataSet set1 = new LineDataSet(values, "Total Employees");
        set1.setDrawIcons(false);
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.DKGRAY);
        set1.setCircleColor(Color.DKGRAY);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);
        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.DKGRAY);
        }
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData lineData = new LineData(dataSets);
        mChart.setData(lineData);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }

    @Override
    public void onNothingSelected() {
    }
}
