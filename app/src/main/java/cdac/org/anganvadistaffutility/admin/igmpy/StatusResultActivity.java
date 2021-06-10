package cdac.org.anganvadistaffutility.admin.igmpy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.igmpy.data.SearchResultData;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;

public class StatusResultActivity extends BaseActivity implements View.OnClickListener  {
    private ArrayList personNames;
    BottomSheetBehavior bottomSheetBehavior;
    RecyclerView recyclerView;
    View view;
    RelativeLayout tbl_layout;

    List<String> expandableListTitle;
    public HashMap<String, List<String>> expandableListDetail;
    private List<SearchResultData.IgmpyStatusDatum> iDatumList;
    LinearLayout status_layout;
    AppCompatTextView set_status;
    private AppCompatTextView mother_name_id,husband_father_name,mobile_no,aadhar_no;
    RadioGroup radioGroup;
    RadioButton rb_0, rb_1, rb_2;
    String mother_id,mother_type,verify_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_status_result);
        recyclerView = findViewById(R.id.recycler_view);
        tbl_layout=findViewById(R.id.relativeLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        String singlePageList = getIntent().getStringExtra("single_page_data_list");
        iDatumList = AppUtils.convertSearchResultDataToGet(singlePageList);
        if (iDatumList != null) {

            tbl_layout.setVisibility(View.VISIBLE);
            SearchResultAdapter searchResultAdapter = new SearchResultAdapter(context, iDatumList);
            recyclerView.setAdapter(searchResultAdapter); // set the Adapter to RecyclerView

        }

    }

    @Override
    public void onClick(View v) {

    }

    private static class SearchResultAdapter extends RecyclerView.Adapter<SearchResultHolder> {
        private List<SearchResultData.IgmpyStatusDatum> iDatumList;
        Context context;

        public SearchResultAdapter(Context context, List<SearchResultData.IgmpyStatusDatum> iDatumList) {
            this.context = context;
            this.iDatumList = iDatumList;
        }

        @NonNull
        @Override
        public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_list, parent, false);
            return new SearchResultHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {
            holder.setData(context, position, iDatumList);
        }


        @Override
        public int getItemCount() {
            return iDatumList.size();
        }
    }

    private static class SearchResultHolder extends RecyclerView.ViewHolder {
        AppCompatTextView mother_name_id,husband_name,mobile_no,aadhar_no,janadhar_no,reg_date,lmp_date,status;

        public SearchResultHolder(@NonNull View itemView) {
            super(itemView);

            mother_name_id=itemView.findViewById(R.id.mother_name_id);
            husband_name=itemView.findViewById(R.id.husband_father_name);
            mobile_no=itemView.findViewById(R.id.mobile_no);
            aadhar_no=itemView.findViewById(R.id.aadhar_no);
            janadhar_no=itemView.findViewById(R.id.janaadhar_no);
            reg_date=itemView.findViewById(R.id.registration_date);
            lmp_date=itemView.findViewById(R.id.lmp_date);
            status=itemView.findViewById(R.id.status);

        }

        @SuppressLint("SetTextI18n")
        public void setData(Context context, int position, List<SearchResultData.IgmpyStatusDatum> iDatumList) {

            mother_name_id.setText(iDatumList.get(position).getMotherName()+" "+"("+iDatumList.get(position).getMotherId()+")");
            husband_name.setText(iDatumList.get(position).getHusbandName());
            aadhar_no.setText(iDatumList.get(position).getAadhar());
            janadhar_no.setText(iDatumList.get(position).getJanAadhar());
            reg_date.setText(iDatumList.get(position).getREGDate());
            lmp_date.setText(iDatumList.get(position).getLMPDate());

        }
    }
}