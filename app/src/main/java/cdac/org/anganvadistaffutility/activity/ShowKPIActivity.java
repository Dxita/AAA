package cdac.org.anganvadistaffutility.activity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

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

public class ShowKPIActivity extends BaseActivity implements View.OnClickListener, OnChartValueSelectedListener {

    //private BarChart chart;
    private LineChart mChart;
    LineDataSet set1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_kpi);

        Button btn_show_kpi_chart = findViewById(R.id.btn_show_kpi_chart);
        //  chart = findViewById(R.id.barchart);
        mChart = findViewById(R.id.lineChart);
        btn_show_kpi_chart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_show_kpi_chart) {
            /*if (chart.getVisibility() == View.GONE) {
                chart.setVisibility(View.VISIBLE);
            }
            setData();*/

            if (mChart.getVisibility() == View.GONE) {
                mChart.setVisibility(View.VISIBLE);
            }
            setLineData();
        }
    }

    private void setLineData() {
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);

        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(1, 0));
        values.add(new Entry(2, 10));
        values.add(new Entry(3, 25));
        values.add(new Entry(4, 50));
        values.add(new Entry(5, 75));
        values.add(new Entry(6, 70));
        values.add(new Entry(7, 80));
        values.add(new Entry(8, 60));
        values.add(new Entry(9, 90));
        values.add(new Entry(10, 100));

        set1 = new LineDataSet(values, "Sample Data");
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
        LineData data = new LineData(dataSets);
        mChart.setData(data);
    }

    /*private void setData() {
        ArrayList<String> months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");

        ArrayList<BarEntry> barEntryArrayList = new ArrayList<>();
        barEntryArrayList.add(new BarEntry(2f, 0));
        barEntryArrayList.add(new BarEntry(4f, 1));
        barEntryArrayList.add(new BarEntry(6f, 2));
        barEntryArrayList.add(new BarEntry(8f, 3));
        barEntryArrayList.add(new BarEntry(7f, 4));
        barEntryArrayList.add(new BarEntry(3f, 5));
        barEntryArrayList.add(new BarEntry(9f, 6));
        barEntryArrayList.add(new BarEntry(5f, 7));
        barEntryArrayList.add(new BarEntry(10f, 8));


        BarDataSet dataSet = new BarDataSet(barEntryArrayList, "Projects");
        BarData data = new BarData(months, dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.animateY(2500);
        chart.setData(data);
    }*/


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
