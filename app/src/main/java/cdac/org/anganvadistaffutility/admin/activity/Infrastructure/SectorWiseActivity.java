package cdac.org.anganvadistaffutility.admin.activity.Infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import cdac.org.anganvadistaffutility.admin.data.InfraDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailProjectData;
import cdac.org.anganvadistaffutility.admin.data.InfraProjectWiseData;
import cdac.org.anganvadistaffutility.admin.data.InfraSectorWiseData;
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfrasDetailSectorData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;

public class SectorWiseActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private InfrasDetailSectorData.Data infraDetailsData;
    List<InfrasDetailSectorData.Infradatum> getTjpmProjectInchargeCdpo;
    private int infraCount = 0;
    RelativeLayout relativeLayout;
    private int currentInfraDetailID = -1;
    private InfraSectorWiseData infraDetailData;
    private String project_id, infra_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sector_wise);

        infra_id = getIntent().getStringExtra("infra_id");
        project_id = getIntent().getStringExtra("project_id");
        TextView txt_title = findViewById(R.id.txt_title);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        if (AppUtils.isNetworkConnected(context)) {
            // AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getInfraDistrictData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

    }

    private void getInfraDistrictData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<InfrasDetailSectorData> call = apiInterface.getInfraDetailSectordata("sec", infra_id, project_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfrasDetailSectorData>() {
            @Override
            public void onSuccess(InfrasDetailSectorData body) {
                //      AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //  AppUtils.showToast(context, body.getMessage());
                //  infraDetailsData = body.getData();
               /* if (infraDetailsData.getInfradata().size() > 0) {
                    if (pieChart.getVisibility() == View.GONE) {
                        pieChart.setVisibility(View.VISIBLE);
                    }
                    setUserData(infraDetailsData);
                }*/

                infraDetailsData = body.getData();

                if (infraDetailsData.getInfradata().size() > 0) {
                    setUserData(infraDetailsData);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //     AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    private void setUserData(InfrasDetailSectorData.Data detailData) {
        int previousInfraDetailID;
        List<InfraSectorWiseData> infraDetailDataList = new ArrayList<>();
        //  List<PieEntry> chartInfraCount = new ArrayList<>();

        for (InfrasDetailSectorData.Infradatum infraDatum : detailData.getInfradata()) {
            previousInfraDetailID = currentInfraDetailID;
            currentInfraDetailID = Integer.parseInt(infraDatum.getSectorid());

            // To make sum of all same infra detail data and finally add to list
            // add same id data only once
            if (previousInfraDetailID == -1 || previousInfraDetailID == currentInfraDetailID) {
                if (!infraDetailDataList.isEmpty()) {
                    infraDetailDataList.remove(infraDetailDataList.size() - 1);
                }
            }
            if (infraDetailData != null) {
                infraDetailDataList.add(infraDetailData);
            }

            if (previousInfraDetailID == currentInfraDetailID) {
                //infraCount = infraCount + Integer.parseInt(infraDatum.getCount());
                infraCount = Integer.parseInt(infraDatum.getCount());
            } else {
                infraDetailData = new InfraSectorWiseData();
                infraDetailData.setSectorid(infraDatum.getSectorid());
                infraDetailData.setSecnamee(infraDatum.getSecnamee());
                infraCount = Integer.parseInt(infraDatum.getCount());
            }
            infraDetailData.setInfraCount("" + infraCount);
        }

        //    Collections.sort(infraDetailDataList, Collections.reverseOrder());
        DistrictWiseInfrastructuretAdapter districtWiseInfrastructuretAdapter = new DistrictWiseInfrastructuretAdapter(this, infraDetailDataList);
        recyclerView.setAdapter(districtWiseInfrastructuretAdapter);
    }

    public static class DistrictWiseInfrastructuretAdapter extends RecyclerView.Adapter<DistrictWiseInfrastructuretAdapter.ViewHolder> {
        private final Context mContext;
        protected List<InfraSectorWiseData> infraDetailData;
        //  protected ItemClickListener mListener;
        protected int fixListSize = 7;
        private int totalListSize = 0;


        public DistrictWiseInfrastructuretAdapter(Context mContext, List<InfraSectorWiseData> infraDetailDataList) {

            this.mContext = mContext;
            this.infraDetailData = infraDetailDataList;
            // this.mListener = itemClickListener;

        }

        @NonNull
        @Override
        public DistrictWiseInfrastructuretAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.recycle_item_district_wise_details, parent, false);
            return new DistrictWiseInfrastructuretAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull DistrictWiseInfrastructuretAdapter.ViewHolder holder, int position) {
            holder.setData(position, infraDetailData);

        }


        public int getItemCount() {
            if (infraDetailData.size() % fixListSize == 0) {
                totalListSize = infraDetailData.size() / fixListSize;
            } else {
                totalListSize = infraDetailData.size() / fixListSize + 1;
            }
            return totalListSize;
        }
/*

    public interface ItemClickListener {
        void onItemClick(Object item);
    }
*/

        public class ViewHolder extends RecyclerView.ViewHolder implements OnChartValueSelectedListener {
            private PieChart pieChart;

            protected TextView txt_page_count;
            protected View layout;
            protected List<InfraSectorWiseData> infraDetailData;

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

            }


            public void setData(int position, List<InfraSectorWiseData> infraDetailData) {
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


                PieDataSet dataSet = null;
                PieData pieData = null;

                for (int j = 0; j < infraDetailData.size(); j++) {
                    if (position == 0 && j < fixListSize) {

                        noOfEmp1.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                  /*  noOfEmp1.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getDistrict()),
                            infraDetailData.get(j).getDistrict() + " (" + infraDetailData.get(j).getInfraCount() + ")"
                            , infraDetailData.get(j).getDistrictID()));
                    */
                        dataSet = new PieDataSet(noOfEmp1, "");
                        pieData = new PieData(dataSet);

                    } else if (position == 1 && j >= fixListSize && j < 2 * fixListSize) {
                        noOfEmp2.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                        dataSet = new PieDataSet(noOfEmp2, "");
                        pieData = new PieData(dataSet);

                    } else if (position == 2 && j >= 2 * fixListSize && j < 3 * fixListSize) {
                        noOfEmp3.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                        dataSet = new PieDataSet(noOfEmp3, "");
                        pieData = new PieData(dataSet);

                    } else if (position == 3 && j >= 3 * fixListSize && j < 4 * fixListSize) {
                        noOfEmp4.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                        dataSet = new PieDataSet(noOfEmp4, "");
                        pieData = new PieData(dataSet);

                    } else if (position == 4 && j >= 4 * fixListSize && j < 5 * fixListSize) {
                        noOfEmp5.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                        dataSet = new PieDataSet(noOfEmp5, "");
                        pieData = new PieData(dataSet);
                    } else if (position == 5 && j >= 5 * fixListSize && j < 6 * fixListSize) {
                        noOfEmp6.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                        dataSet = new PieDataSet(noOfEmp6, "");
                        pieData = new PieData(dataSet);
                    } else if (position == 6 && j >= 6 * fixListSize && j < 7 * fixListSize) {
                        noOfEmp7.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                        dataSet = new PieDataSet(noOfEmp7, "");
                        pieData = new PieData(dataSet);

                    } else if (position == 7 && j >= 7 * fixListSize && j < 8 * fixListSize) {
                        noOfEmp8.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                        dataSet = new PieDataSet(noOfEmp8, "");
                        pieData = new PieData(dataSet);
                    } else if (position == 8 && j >= 8 * fixListSize && j < 9 * fixListSize) {
                        noOfEmp9.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                        dataSet = new PieDataSet(noOfEmp9, "");
                        pieData = new PieData(dataSet);
                    } else if (position == 9 && j >= 9 * fixListSize && j < 10 * fixListSize) {
                        noOfEmp10.add(new PieEntry(Integer.parseInt(infraDetailData.get(j).getInfraCount()),
                                infraDetailData.get(j).getSecnamee() + "(" + infraDetailData.get(j).getInfraCount() + ")"));
                        dataSet = new PieDataSet(noOfEmp10, "");
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
                pieChart.setOnChartValueSelectedListener(this);

            }

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos = (int) h.getX();
            /*    Toast.makeText(mContext, ""
                        + infraDetailData.get(pos).getDistrictID(), Toast.LENGTH_SHORT).show();
*/
              //  startActivity(new Intent(context, PieChartProjectActivity.class).putExtra("infra_id", infra_id).putExtra("district_id", infraDetailData.get(pos).getDistrictID()));

          /*  if (mListener != null) {
                mListener.onItemClick(e.getData());
            }*/
            }

            @Override
            public void onNothingSelected() {

            }
        }
    }

}