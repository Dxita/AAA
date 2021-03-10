package cdac.org.anganvadistaffutility.admin.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.ProjectEmployeeData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import retrofit2.Call;

public class ProjectWiseTableActivity extends BaseActivity {
    private LinearLayout relativeLayout, tbl_layout, ll_bottom_sheet;
    private RecyclerView recyclerView;
    private List<ProjectEmployeeData.Empdatum> empData;
    private static String userType = "";
    private  String distid, count;
    private String cdpoMobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_project_wise_table);

        distid = getIntent().getStringExtra("dist_id");
        userType = getIntent().getStringExtra("user_type");

        relativeLayout = findViewById(R.id.relativeLayout);
        tbl_layout = findViewById(R.id.tbl_layout);

        TextView txt_title = findViewById(R.id.txt_title);
        if (userType.equalsIgnoreCase("registered_user")) {
            txt_title.setText(getResources().getString(R.string.project_wise_reg_users));
        } else {
            txt_title.setText(getResources().getString(R.string.project_wise_unreg_users));
        }
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getAdminUserData();
        }

        setBottomSheet();

    }

    private void getAdminUserData() {

        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<ProjectEmployeeData> call = apiInterface.getProjectEmployeeData(distid, "2");
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<ProjectEmployeeData>() {
            @Override
            public void onSuccess(ProjectEmployeeData body) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //   AppUtils.showToast(context, body.getMessage());
                empData = new ArrayList<>();
                empData = body.getData().getEmpdata();
                if (empData.size() > 0) {
                    if (tbl_layout.getVisibility() == View.GONE) {
                        tbl_layout.setVisibility(View.VISIBLE);
                    }
                    ProjecttWiseTableAdapter projecttWiseTableAdapter = new ProjecttWiseTableAdapter(context, empData);
                    recyclerView.setAdapter(projecttWiseTableAdapter);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }


    private class ProjecttWiseTableAdapter extends RecyclerView.Adapter<MyHolder> {
        private final Context mContext;
        protected List<ProjectEmployeeData.Empdatum> empdata;
        String countE;


        public ProjecttWiseTableAdapter(Context context, List<ProjectEmployeeData.Empdatum> empData) {
            this.mContext = context;
            this.empdata = empData;

        }


        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.admin_district_rv_items, parent, false);
            return new MyHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.setData(position, empdata);
            holder.view_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleBottomSheet(empData.get(position).getTjpmProjectCode(), empData.get(position).getTjpmProjectNameEnglish(),
                            empData.get(position).getTjpmProjectNameHindi(),
                            empData.get(position).getTjpmProjectInchargeCdpo(),
                            empData.get(position).getTjpmCdpoMobileNo(),
                            empData.get(position).getTjpmCdpoEmail());
                }
            });
        }

        @Override
        public int getItemCount() {
            return empdata.size();
        }
    }

    @SuppressLint("SetTextI18n")
    private void toggleBottomSheet(String tjpmProjectCode, String projectNameEnglish, String projectNameHindi,
                                   String tjpmProjectInchargeCdpo, String tjpmCdpoMobileNo, String tjpmCdpoEmail) {
        if (ll_bottom_sheet.getVisibility() == View.GONE) {
            ll_bottom_sheet.setVisibility(View.VISIBLE);
        }

        cdpoMobile = tjpmCdpoMobileNo;
        TextView txt_cdpo_name = ll_bottom_sheet.findViewById(R.id.txt_cdpo_name);
        TextView txt_project_name = ll_bottom_sheet.findViewById(R.id.txt_project_name);
        TextView txt_project_code = ll_bottom_sheet.findViewById(R.id.txt_project_code);
        Button btn_call = ll_bottom_sheet.findViewById(R.id.btn_call);
        Button btn_email = ll_bottom_sheet.findViewById(R.id.btn_email);

        txt_cdpo_name.setText(getResources().getString(R.string.cdpo_name) + " " + tjpmProjectInchargeCdpo);

        if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
            txt_project_name.setText(getResources().getString(R.string.proj_name) + " " + projectNameHindi);

        } else {
            txt_project_name.setText(getResources().getString(R.string.proj_name) + " " + projectNameEnglish);
        }

        txt_project_code.setText(getResources().getString(R.string.proj_code) + " " + tjpmProjectCode);

        btn_call.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AppUtils.hasPermissions(context, AppUtils.CALL_PERMISSIONS)) {
                    makePhoneCall(tjpmCdpoMobileNo);
                } else {
                    requestPermissions(AppUtils.CALL_PERMISSIONS, AppUtils.CALL_PERMISSION_REQUEST_CODE);
                }
            } else {
                makePhoneCall(tjpmCdpoMobileNo);
            }
        });

        btn_email.setOnClickListener(view -> {
            sendEmail(tjpmCdpoEmail);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtils.CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(cdpoMobile);
            }
        }
    }

    private void makePhoneCall(String mobile) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mobile));
        startActivity(callIntent);
    }

    private void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        //need this to prompts email client only
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        TextView dist_name, dist_count, sr_no, view_data;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            dist_name = itemView.findViewById(R.id.name);
            dist_count = itemView.findViewById(R.id.count);
            sr_no = itemView.findViewById(R.id.sr_no);
            view_data = itemView.findViewById(R.id.view_registered_emp);

        }

        @SuppressLint("SetTextI18n")
        public void setData(int position, List<ProjectEmployeeData.Empdatum> empdata) {
            sr_no.setText("" + (position + 1));

            if (userType.equalsIgnoreCase("registered_user")) {

                if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                    dist_name.setText(empdata.get(position).getTjpmProjectNameHindi());
                    dist_count.setText(empdata.get(position).getRegistered());

                } else {
                    dist_name.setText(empdata.get(position).getTjpmProjectNameEnglish());
                    dist_count.setText(empdata.get(position).getRegistered());
                }
            } else {

                if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                    dist_name.setText(empdata.get(position).getTjpmProjectNameHindi());
                    dist_count.setText(empdata.get(position).getUnregistered());
                } else {
                    dist_name.setText(empdata.get(position).getTjpmProjectNameEnglish());
                    dist_count.setText(empdata.get(position).getUnregistered());
                }

            }
        }

    }

    private void setBottomSheet() {
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
    }
}
