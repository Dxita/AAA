package cdac.org.anganvadistaffutility.admin.activity.Infrastructure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AdminUserData;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailData;
import cdac.org.anganvadistaffutility.admin.data.InfraDetailProjectData;
import cdac.org.anganvadistaffutility.admin.data.InfraProjectWiseData;
import cdac.org.anganvadistaffutility.admin.data.InfraStructureDetailData;
import cdac.org.anganvadistaffutility.admin.data.ProjectWiseEmployeeDetails;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import retrofit2.Call;


public class PieChartProjectActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private InfraDetailProjectData.Data infraDetailsData;
    private int infraCount = 0;
    private int currentInfraDetailID = -1;
    private InfraProjectWiseData infraDetailProjectData;
    private String district_id, infra_id;
    protected List<InfraProjectWiseData> infraDetailData;
    //private RelativeLayout relativeLayout;
    private String cdpoMobile = "";
    private PieChart pieChart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pie_chart_project);


        //relativeLayout = findViewById(R.id.relativeLayout);
        infra_id = getIntent().getStringExtra("infra_id");
        district_id = getIntent().getStringExtra("district_id");
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

//        setBottomSheet();
    }

  /*  private void setBottomSheet() {
        ll_bottom_sheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(ll_bottom_sheet);
        bottomSheetBehavior.setHideable(false);
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        // btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        // btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        };
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback);
    }*/

    private void getInfraDistrictData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<InfraDetailProjectData> call = apiInterface.getInfrastructureProjectDetails("proj", infra_id, district_id);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<InfraDetailProjectData>() {
            @Override
            public void onSuccess(InfraDetailProjectData body) {
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

    private void setUserData(InfraDetailProjectData.Data detailData) {
        int previousInfraDetailID;
        List<InfraProjectWiseData> infraDetailDataList = new ArrayList<>();
        //  List<PieEntry> chartInfraCount = new ArrayList<>();

        for (InfraDetailProjectData.Infradatum infraDatum : detailData.getInfradata()) {
            previousInfraDetailID = currentInfraDetailID;
            currentInfraDetailID = Integer.parseInt(infraDatum.getProjectcode());

            // To make sum of all same infra detail data and finally add to list
            // add same id data only once
            if (previousInfraDetailID == -1 || previousInfraDetailID == currentInfraDetailID) {
                if (!infraDetailDataList.isEmpty()) {
                    infraDetailDataList.remove(infraDetailDataList.size() - 1);
                }
            }
            if (infraDetailProjectData != null) {
                infraDetailDataList.add(infraDetailProjectData);
            }

            if (previousInfraDetailID == currentInfraDetailID) {
                //infraCount = infraCount + Integer.parseInt(infraDatum.getCount());
                infraCount = Integer.parseInt(infraDatum.getCount());
            } else {
                infraDetailProjectData = new InfraProjectWiseData();
                infraDetailProjectData.setProjectCode(infraDatum.getProjectcode());
                infraDetailProjectData.setProjectName(infraDatum.getProjectname());
                infraDetailProjectData.setCdpoName(infraDatum.getCdpoName());
                infraDetailProjectData.setCdpoEmail(infraDatum.getCdpoEmail());
                infraDetailProjectData.setCdpoNumber(infraDatum.getCdpoMobileno());
                infraCount = Integer.parseInt(infraDatum.getCount());
            }
            infraDetailProjectData.setInfraCount("" + infraCount);
        }
        //Collections.sort(infraDetailDataList, Collections.reverseOrder());
        ProjecttWiseInfrastructuretAdapter projecttWiseInfrastructuretAdapter = new ProjecttWiseInfrastructuretAdapter(this, infraDetailDataList);
        recyclerView.setAdapter(projecttWiseInfrastructuretAdapter);
    }


    public class ProjecttWiseInfrastructuretAdapter extends RecyclerView.Adapter<ProjecttWiseInfrastructuretAdapter.ViewHolder> {

        private Context mContext;
        protected List<InfraProjectWiseData> infraDetailData;
        protected int fixListSize = 7;
        private int totalListSize = 0;

        public ProjecttWiseInfrastructuretAdapter(PieChartProjectActivity mContext,
                                                  List<InfraProjectWiseData> infraDetailProjectDataList) {
            this.mContext = mContext;
            this.infraDetailData = infraDetailProjectDataList;
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
                int pos = (int) h.getX();

                startActivity(new Intent(context, AanganwadiListActivity.class)
                        .putExtra("infra_id", infra_id)
                        .putExtra("project_id", infraDetailData.get(pos).getProjectcode())
                        .putExtra("project_name", infraDetailData.get(pos).getProjectname())
                        .putExtra("cdpo_number",infraDetailData.get(pos).getCdpoNumber())
                        .putExtra("cdpo_email",infraDetailData.get(pos).getCdpoEmail())
                        .putExtra("cdpo_name",infraDetailData.get(pos).getCdpoName()));


            }

            @Override
            public void onNothingSelected() {

            }
        }
    }

}
