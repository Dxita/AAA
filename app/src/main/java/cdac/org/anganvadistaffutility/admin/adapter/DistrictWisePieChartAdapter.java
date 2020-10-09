package cdac.org.anganvadistaffutility.admin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.DistrictWiseEmployeeDetails;

public class DistrictWisePieChartAdapter extends RecyclerView.Adapter<DistrictWisePieChartAdapter.ViewHolder> {

    private Context mContext;
    private List<DistrictWiseEmployeeDetails> districtWiseEmployeeDetailsList;
    protected ItemClickListener mListener;
    private int fixListSize = 7;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected PieChart pieChart;
        protected View layout;
        protected List<DistrictWiseEmployeeDetails> districtWiseEmployeeDetails;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            pieChart = v.findViewById(R.id.pieChart);
            layout.setOnClickListener(this);

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
        }

        public void setData(int position, List<DistrictWiseEmployeeDetails> districtWiseEmployeeDetails) {
            this.districtWiseEmployeeDetails = districtWiseEmployeeDetails;

            List<PieEntry> noOfEmp1 = new ArrayList<>();
            List<PieEntry> noOfEmp2 = new ArrayList<>();
            List<PieEntry> noOfEmp3 = new ArrayList<>();
            List<PieEntry> noOfEmp4 = new ArrayList<>();
            List<PieEntry> noOfEmp5 = new ArrayList<>();

            PieDataSet dataSet = null;
            PieData pieData = null;

            if (districtWiseEmployeeDetails.size() >= fixListSize) {
                for (int j = 0; j < fixListSize; j++) {
                    noOfEmp1.add(new PieEntry(Integer.parseInt(districtWiseEmployeeDetails.get(j).getDistrict_employees()),
                            districtWiseEmployeeDetails.get(j).getDistrict_name_english() + " (" + districtWiseEmployeeDetails.get(j).getDistrict_employees() + ")"));
                }
            }

            if (districtWiseEmployeeDetails.size() >= 2 * fixListSize) {
                for (int j = fixListSize; j < 2 * fixListSize; j++) {
                    noOfEmp2.add(new PieEntry(Integer.parseInt(districtWiseEmployeeDetails.get(j).getDistrict_employees()),
                            districtWiseEmployeeDetails.get(j).getDistrict_name_english() + " (" + districtWiseEmployeeDetails.get(j).getDistrict_employees() + ")"));
                }
            }

            if (districtWiseEmployeeDetails.size() >= 3 * fixListSize) {
                for (int j = 2 * fixListSize; j < 3 * fixListSize; j++) {
                    noOfEmp3.add(new PieEntry(Integer.parseInt(districtWiseEmployeeDetails.get(j).getDistrict_employees()),
                            districtWiseEmployeeDetails.get(j).getDistrict_name_english() + " (" + districtWiseEmployeeDetails.get(j).getDistrict_employees() + ")"));
                }
            }

            if (districtWiseEmployeeDetails.size() >= 4 * fixListSize) {
                for (int j = 3 * fixListSize; j < 4 * fixListSize; j++) {
                    noOfEmp4.add(new PieEntry(Integer.parseInt(districtWiseEmployeeDetails.get(j).getDistrict_employees()),
                            districtWiseEmployeeDetails.get(j).getDistrict_name_english() + " (" + districtWiseEmployeeDetails.get(j).getDistrict_employees() + ")"));
                }
            }

            if (districtWiseEmployeeDetails.size() <= 5 * fixListSize) {
                for (int j = 4 * fixListSize; j < 31; j++) {
                    noOfEmp5.add(new PieEntry(Integer.parseInt(districtWiseEmployeeDetails.get(j).getDistrict_employees()),
                            districtWiseEmployeeDetails.get(j).getDistrict_name_english() + " (" + districtWiseEmployeeDetails.get(j).getDistrict_employees() + ")"));
                }
            }

            if (position == 0) {
                dataSet = new PieDataSet(noOfEmp1, "");
                pieData = new PieData(dataSet);
            } else if (position == 1) {
                dataSet = new PieDataSet(noOfEmp2, "");
                pieData = new PieData(dataSet);
            } else if (position == 2) {
                dataSet = new PieDataSet(noOfEmp3, "");
                pieData = new PieData(dataSet);
            } else if (position == 3) {
                dataSet = new PieDataSet(noOfEmp4, "");
                pieData = new PieData(dataSet);
            } else if (position == 4) {
                dataSet = new PieDataSet(noOfEmp5, "");
                pieData = new PieData(dataSet);
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
        public void onClick(View view) {
            if (mListener != null) {
                // mListener.onItemClick(districtWiseEmployeeDetails);
            }
        }
    }

    public DistrictWisePieChartAdapter(Context context, List<DistrictWiseEmployeeDetails> districtWiseEmployeeDetails) {
        this.mContext = context;
        this.districtWiseEmployeeDetailsList = districtWiseEmployeeDetails;
    }

    @NonNull
    @Override
    public DistrictWisePieChartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycle_item_district_wise_details, parent, false);
        return new DistrictWisePieChartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull DistrictWisePieChartAdapter.ViewHolder holder, final int position) {
        holder.setData(position, districtWiseEmployeeDetailsList);
    }

    @Override

    public int getItemCount() {
        if (districtWiseEmployeeDetailsList.size() % fixListSize == 0) {
            return districtWiseEmployeeDetailsList.size() / fixListSize;
        } else {
            return districtWiseEmployeeDetailsList.size() / fixListSize + 1;
        }
    }

    public interface ItemClickListener {
        void onItemClick(DistrictWiseEmployeeDetails item);
    }

}
