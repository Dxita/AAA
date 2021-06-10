package cdac.org.anganvadistaffutility.user.pctsMapping.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;

import cdac.org.anganvadistaffutility.user.pctsMapping.data.SinglePageList;
import cdac.org.anganvadistaffutility.user.pctsMapping.data.VerifyStatusData;
import retrofit2.Call;

public class SinglePageListActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList personNames;
    BottomSheetBehavior bottomSheetBehavior;
    RecyclerView recyclerView;
    View view;
    RelativeLayout tbl_layout;
    /*  ExpandableListView expandableListView;
      ExpandableListAdapter expandableListAdapter;*/
    List<String> expandableListTitle;
    public HashMap<String, List<String>> expandableListDetail;
    private List<SinglePageList.IgmpyMotherDatum> iDatumList;
    LinearLayout status_layout;
    AppCompatTextView set_status;
    private AppCompatTextView mother_name_id, husband_father_name, mobile_no, aadhar_no;
    RadioGroup radioGroup;
    RadioButton rb_0, rb_1, rb_2;
    String mother_id, mother_type, verify_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_page_list);

        recyclerView = findViewById(R.id.recycler_view);
        tbl_layout = findViewById(R.id.tbl_layout);

        mother_name_id = findViewById(R.id.mother_name_id);
        husband_father_name = findViewById(R.id.husband_father_name);
        mobile_no = findViewById(R.id.mobile_no);
        aadhar_no = findViewById(R.id.aadhar_no);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        view = findViewById(R.id.time_chooser_bottom_sheet_id);
        set_status = findViewById(R.id.set_status);
        status_layout = findViewById(R.id.status_layout);
        radioGroup = findViewById(R.id.radioGroup);
        rb_0 = findViewById(R.id.rb_0);
        rb_1 = findViewById(R.id.rb_1);
        rb_2 = findViewById(R.id.rb_2);
        rb_0.setOnClickListener(this);
        rb_1.setOnClickListener(this);
        rb_2.setOnClickListener(this);
        set_status.setOnClickListener(this);

        String singlePageList = getIntent().getStringExtra("single_page_data_list");
        iDatumList = AppUtils.convertSinglePageListToGet(singlePageList);
        if (iDatumList != null) {

            tbl_layout.setVisibility(View.VISIBLE);
            SinglePageAdapter singlePageAdapter = new SinglePageAdapter(context, iDatumList);
            recyclerView.setAdapter(singlePageAdapter); // set the Adapter to RecyclerView

        }

        bottomSheetBehavior = BottomSheetBehavior.from(view);

        bottomSheetBehavior.setHideable(true);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY()))
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.set_status) {

            if (status_layout.getVisibility() == View.VISIBLE) {
                status_layout.setVisibility(View.GONE);
            } else {
                status_layout.setVisibility(View.VISIBLE);
            }

        } else if (v.getId() == R.id.rb_0) {
            verify_flag = "0";
            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, tbl_layout, View.VISIBLE);
                updateStatus(mother_id, mother_type, verify_flag);
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }
        } else if (v.getId() == R.id.rb_1) {
            verify_flag = "1";
            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, tbl_layout, View.VISIBLE);
                updateStatus(mother_id, mother_type, verify_flag);
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }
        } else if (v.getId() == R.id.rb_2) {
            verify_flag = "2";
            if (AppUtils.isNetworkConnected(context)) {
                AppUtils.setProgressBarVisibility(context, tbl_layout, View.VISIBLE);
                updateStatus(mother_id, mother_type, verify_flag);
            } else {
                AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
            }
        }
    }

    private void updateStatus(String mother_id, String mother_type, String verify_flag) {

        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<VerifyStatusData> call = apiInterface.updateVerifyFlag(mother_id, mother_type, verify_flag);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<VerifyStatusData>() {
            @Override
            public void onSuccess(VerifyStatusData body) {

                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.setProgressBarVisibility(context, tbl_layout, View.GONE);
                    Toast.makeText(SinglePageListActivity.this, "" + body.getMessage(), Toast.LENGTH_SHORT).show();

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    view.setVisibility(View.GONE);
                    finish();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, tbl_layout, View.GONE);
                AppUtils.showToast(context, context.getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }


    private class SinglePageAdapter extends RecyclerView.Adapter<SinglePageView> {
        private List<SinglePageList.IgmpyMotherDatum> iDatumList;
        Context context;

        public SinglePageAdapter(Context context, List<SinglePageList.IgmpyMotherDatum> iDatumList) {
            this.context = context;
            this.iDatumList = iDatumList;
        }

        @NonNull
        @Override
        public SinglePageView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_page_view, parent, false);
            return new SinglePageView(v);
        }

        @Override
        public void onBindViewHolder(@NonNull SinglePageView holder, int position) {
            holder.setData(context, position, iDatumList);
            mother_id = iDatumList.get(position).getTjmMotherId();
            mother_type = iDatumList.get(position).getTjmMotherType();
            verify_flag = iDatumList.get(position).getTjmVerifyflag();

            holder.more_img.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    view.setVisibility(View.VISIBLE);
                    mother_name_id.setText(iDatumList.get(position).getTjmName() + " " + "(" + iDatumList.get(position).getTjmMotherId() + ")");
                    husband_father_name.setText(getResources().getString(R.string.husband_name) + ":" + " " + iDatumList.get(position).getTjmHusbname() + " " + "|");
                    mobile_no.setText(getResources().getString(R.string.mobile_no) + ":" + " " + iDatumList.get(position).getTjmMobileno() + " " + "|" + " ");
                    aadhar_no.setText(getResources().getString(R.string.aadhar_no) + ":" + " " + iDatumList.get(position).getTbmAadharNo());
                }
            });
        }


        @Override
        public int getItemCount() {
            return iDatumList.size();
        }
    }

    private static class SinglePageView extends RecyclerView.ViewHolder {
        AppCompatImageView more_img;
        AppCompatTextView mother_name_id, pcts_anm_id, pcts_asha_id, verify_status;


        public SinglePageView(@NonNull View itemView) {
            super(itemView);
            more_img = itemView.findViewById(R.id.more_img);
            mother_name_id = itemView.findViewById(R.id.mother_name_id);
            pcts_anm_id = itemView.findViewById(R.id.pcts_anm_id);
            pcts_asha_id = itemView.findViewById(R.id.pcts_asha_id);
            verify_status = itemView.findViewById(R.id.verify_status);
        }

        @SuppressLint("SetTextI18n")
        public void setData(Context context, int position, List<SinglePageList.IgmpyMotherDatum> iDatumList) {

            mother_name_id.setText(iDatumList.get(position).getTjmName() + " " + "(" + iDatumList.get(position).getTjmMotherId() + ")");
            pcts_anm_id.setText(context.getResources().getString(R.string.pcts_anm_id) + "" + ":" + " " + iDatumList.get(position).getTjmAnmId());
            pcts_asha_id.setText(context.getResources().getString(R.string.pcts_asha_id) + "" + ":" + " " + iDatumList.get(position).getTjmAshaId());
            if (iDatumList.get(position).getTjmStatusflag().equals("0")) {
                verify_status.setText(context.getResources().getString(R.string.verify_status) + "" + ":" + " " + context.getResources().getString(R.string.available));
            } else if (iDatumList.get(position).getTjmStatusflag().equals("1")) {
                verify_status.setText(context.getResources().getString(R.string.verify_status) + "" + ":" + " " + context.getResources().getString(R.string.not_avail));
            } else if (iDatumList.get(position).getTjmStatusflag().equals("2")) {
                verify_status.setText(context.getResources().getString(R.string.verify_status) + "" + ":" + " " + context.getResources().getString(R.string.not_intrested));
            }

        }
    }

}