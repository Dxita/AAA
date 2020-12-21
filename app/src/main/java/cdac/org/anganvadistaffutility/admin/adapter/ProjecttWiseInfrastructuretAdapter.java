package cdac.org.anganvadistaffutility.admin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import cdac.org.anganvadistaffutility.admin.activity.ProjectWisePieChartActivity;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfraProjectWiseData;

public class ProjecttWiseInfrastructuretAdapter extends RecyclerView.Adapter<ProjecttWiseInfrastructuretAdapter.ViewHolder> {

    private Context mContext;
    protected List<InfraProjectWiseData> infraDetailData;
    protected ProjecttWiseInfrastructuretAdapter.ItemClickListener mListener;
    protected int fixListSize = 7;
    private static int totalListSize = 0;

    public ProjecttWiseInfrastructuretAdapter(ProjectWisePieChartActivity mContext,
                                              List<InfraProjectWiseData> infraDetailProjectDataList, ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.infraDetailData = infraDetailProjectDataList;
        this.mListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycle_item_district_wise_details, parent, false);
        return new ProjecttWiseInfrastructuretAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position, infraDetailData);
    }


    @Override
    public int getItemCount() {
        if (infraDetailData.size() % fixListSize == 0) {
            totalListSize = infraDetailData.size() / fixListSize;
        } else {
            totalListSize = infraDetailData.size() / fixListSize + 1;
        }
        return totalListSize;
    }

    public interface ItemClickListener {
        void onItemClick(Object item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnChartValueSelectedListener {
        protected PieChart pieChart;
        protected TextView txt_page_count;
        protected View layout;
        protected List<InfraProjectWiseData> infraDetailData;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            pieChart = v.findViewById(R.id.pieChart);
            txt_page_count = v.findViewById(R.id.txt_page_count);

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

        }


        @SuppressLint("SetTextI18n")
        public void setData(int position, List<InfraProjectWiseData> infraDetailData) {
            this.infraDetailData = infraDetailData;

            int pagePosition = position + 1;
            txt_page_count.setText("(" + pagePosition + "/" + totalListSize + ")");
            List<PieEntry> noOfEmp1 = new ArrayList<>();
            List<PieEntry> noOfEmp2 = new ArrayList<>();
            List<PieEntry> noOfEmp3 = new ArrayList<>();
            List<PieEntry> noOfEmp4 = new ArrayList<>();
            List<PieEntry> noOfEmp5 = new ArrayList<>();
            List<PieEntry> noOfEmp6 = new ArrayList<>();
            List<PieEntry> noOfEmp7 = new ArrayList<>();
            List<PieEntry> noOfEmp8 = new ArrayList<>();
            List<PieEntry> noOfEmp9 = new ArrayList<>();
            List<PieEntry> noOfEmp10 = new ArrayList<>();
            List<PieEntry> noOfEmp11 = new ArrayList<>();
            List<PieEntry> noOfEmp12 = new ArrayList<>();
            List<PieEntry> noOfEmp13 = new ArrayList<>();
            List<PieEntry> noOfEmp14 = new ArrayList<>();
            List<PieEntry> noOfEmp15 = new ArrayList<>();
            List<PieEntry> noOfEmp16 = new ArrayList<>(); List<PieEntry> noOfEmp17 = new ArrayList<>(); List<PieEntry> noOfEmp18 = new ArrayList<>();
            List<PieEntry> noOfEmp19 = new ArrayList<>();
            List<PieEntry> noOfEmp20 = new ArrayList<>();



            PieDataSet dataSet = null;
            PieData pieData = null;

            for (int j = 0; j < infraDetailData.size(); j++) {
                if (position == 0 && j < fixListSize) {

                    noOfEmp1.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                  /*  noOfEmp1.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getDistrict()),
                            infraDetailData.get(j).getDistrict() + " (" + infraDetailData.get(j).getInfraCount() + ")"
                            , infraDetailData.get(j).getDistrictID()));
                    */
                    dataSet = new PieDataSet(noOfEmp1, "");
                    pieData = new PieData(dataSet);

                } else if (position == 1 && j >= fixListSize && j < 2 * fixListSize) {
                    noOfEmp2.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp2, "");
                    pieData = new PieData(dataSet);

                } else if (position == 2 && j >= 2 * fixListSize && j < 3 * fixListSize) {
                    noOfEmp3.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp3, "");
                    pieData = new PieData(dataSet);

                } else if (position == 3 && j >= 3 * fixListSize && j < 4 * fixListSize) {
                    noOfEmp4.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp4, "");
                    pieData = new PieData(dataSet);

                } else if (position == 4 && j >= 4 * fixListSize && j < 5 * fixListSize) {
                    noOfEmp5.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp5, "");
                    pieData = new PieData(dataSet);
                } else if (position == 5 && j >= 5 * fixListSize && j < 6 * fixListSize) {
                    noOfEmp6.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp6, "");
                    pieData = new PieData(dataSet);
                } else if (position == 6 && j >= 6 * fixListSize && j < 7 * fixListSize) {
                    noOfEmp7.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp7, "");
                    pieData = new PieData(dataSet);

                } else if (position == 7 && j >= 7 * fixListSize && j < 8 * fixListSize) {
                    noOfEmp8.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp8, "");
                    pieData = new PieData(dataSet);
                } else if (position == 8 && j >= 8 * fixListSize && j < 9 * fixListSize) {
                    noOfEmp9.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp9, "");
                    pieData = new PieData(dataSet);
                } else if (position == 9 && j >= 9 * fixListSize && j < 10 * fixListSize) {
                    noOfEmp10.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp10, "");
                    pieData = new PieData(dataSet);
                } else if (position == 10 && j >= 10 * fixListSize && j < 11 * fixListSize) {
                    noOfEmp11.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp11, "");
                    pieData = new PieData(dataSet);
                } else if (position == 11 && j >= 11 * fixListSize && j < 12 * fixListSize) {
                    noOfEmp12.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp12, "");
                    pieData = new PieData(dataSet);
                } else if (position == 12 && j >= 12 * fixListSize && j < 13 * fixListSize) {
                    noOfEmp13.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp13, "");
                    pieData = new PieData(dataSet);
                } else if (position == 13 && j >= 13 * fixListSize && j < 14 * fixListSize) {
                    noOfEmp14.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp14, "");
                    pieData = new PieData(dataSet);
                } else if (position == 14 && j >= 14 * fixListSize && j < 15 * fixListSize) {
                    noOfEmp15.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp15, "");
                    pieData = new PieData(dataSet);
                } else if (position == 15 && j >= 15 * fixListSize && j < 16 * fixListSize) {
                    noOfEmp16.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp16, "");
                    pieData = new PieData(dataSet);
                }
                else if (position == 16 && j >= 16 * fixListSize && j < 17 * fixListSize) {
                    noOfEmp17.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp17, "");
                    pieData = new PieData(dataSet);
                }
                else if (position == 17 && j >= 17 * fixListSize && j < 18 * fixListSize) {
                    noOfEmp18.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp18, "");
                    pieData = new PieData(dataSet);
                }
                else if (position == 18 && j >= 18 * fixListSize && j < 19 * fixListSize) {
                    noOfEmp19.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp19, "");
                    pieData = new PieData(dataSet);
                }

                else if (position == 19 && j >= 19 * fixListSize && j < 20 * fixListSize) {
                    noOfEmp20.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                            infraDetailData.get(j).getProjectname() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                    dataSet = new PieDataSet(noOfEmp20, "");
                    pieData = new PieData(dataSet);
                }
            }
            assert dataSet != null;
            dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

            dataSet.setValueLinePart1Length(0.5f);
            dataSet.setValueLinePart2Length(1.2f);
            dataSet.setValueLineVariableLength(true);

            dataSet.setDrawIcons(false);
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(4f);

            pieData.setValueFormatter(new PercentFormatter());
            pieData.setValueTextSize(12f);
            pieData.setValueTextColor(Color.BLACK);
            pieChart.setData(pieData);
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            pieChart.highlightValues(null);
            pieChart.invalidate();
        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            if (mListener != null) {
                mListener.onItemClick(e.getData());
            }
        }

        @Override
        public void onNothingSelected() {

        }
    }
}
