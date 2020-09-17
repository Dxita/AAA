package cdac.org.anganvadistaffutility.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.PaymentDetails;
import cdac.org.anganvadistaffutility.data.PaymentModel;
import cdac.org.anganvadistaffutility.utils.AppUtils;

public class SalaryDetailsTblActivity extends AppCompatActivity {

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

        init();

    }

    private void init() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
       /* LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(12, 12, 12, 12);
        stk.setGravity(Gravity.CENTER_HORIZONTAL);
        stk.setLayoutParams(params);*/

       LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
       params.setMargins(12,12,12,12);
       stk.setGravity(Gravity.CENTER_HORIZONTAL);
       stk.setLayoutParams(params);


        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("ID");
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);
        tv0.setPadding(10,10,10,10);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Name ");
        tv1.setTextColor(Color.BLACK);
        tv1.setGravity(Gravity.CENTER);
        tv1.setPadding(10,10,10,10);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" Month ");
        tv2.setTextColor(Color.BLACK);
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(10,10,10,10);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Salary ");
        tv3.setTextColor(Color.BLACK);
        tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(10,10,10,10);
        tbrow0.addView(tv3);

        stk.addView(tbrow0);

        for (int i = 0; i < empdatumList.size(); i++) {

            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + emp_id);
            t1v.setTextColor(Color.GRAY);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText( " " + emp_name);
            t2v.setTextColor(Color.GRAY);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            t3v.setText(" " + emonth);
            t3v.setTextColor(Color.GRAY);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText("" + payslab_amt);
            t4v.setTextColor(Color.GRAY);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }

    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}