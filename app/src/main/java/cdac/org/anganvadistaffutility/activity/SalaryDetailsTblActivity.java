package cdac.org.anganvadistaffutility.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.PaymentDetails;
import cdac.org.anganvadistaffutility.utils.AppUtils;

public class SalaryDetailsTblActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    PaymentAdapter adapter;
    List<PaymentDetails.Empdatum> empdatumList;
    String emp_id,emp_name,emonth,payslab_amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_details_tbl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Download Salary Slip");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        String emdData = getIntent().getStringExtra("salary_data");
        empdatumList = AppUtils.convertToGet(emdData);
        for (PaymentDetails.Empdatum empdatum : empdatumList) {

            emp_id= empdatum.getEmployeeId();
            emonth=empdatum.getMonth();
            emp_name=empdatum.getEmployeeNameEnglish();
            payslab_amt=empdatum.getSalary();
        }

        recycler_view = findViewById(R.id.recycler_view);

        setRecyclerView();




    }

    private void setRecyclerView() {

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentAdapter(this, empdatumList);
        recycler_view.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private static class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

        Context context;
        List<PaymentDetails.Empdatum> payment_list;


        public PaymentAdapter(SalaryDetailsTblActivity context, List<PaymentDetails.Empdatum> payment_list) {
            this.context = context;
            this.payment_list = payment_list;
        }

        @NonNull
        @Override
        public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            if (payment_list != null && payment_list.size() > 0){
                PaymentDetails.Empdatum empdatum = payment_list.get(position);
                holder.id_tv.setText(empdatum.getEmployeeId());
                holder.name_tv.setText(empdatum.getMonth());
                holder.payment_tv.setText(empdatum.getSalary());
            } else {
                return;
            }
        }

        @Override
        public int getItemCount() {
            return payment_list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView id_tv,name_tv,payment_tv;

            public ViewHolder(View itemView) {
                super(itemView);

                id_tv = itemView.findViewById(R.id.id_tv);
                name_tv = itemView.findViewById(R.id.name_tv);
                payment_tv = itemView.findViewById(R.id.payment_tv);

            }
        }
    }
}