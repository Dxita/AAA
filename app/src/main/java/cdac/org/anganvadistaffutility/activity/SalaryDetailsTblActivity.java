package cdac.org.anganvadistaffutility.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.EmployeeDetails;
import cdac.org.anganvadistaffutility.data.PaymentDetails;

public class SalaryDetailsTblActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_details_tbl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Download Salary Slip");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        init();


        PaymentDetails.Empdatum empdatum = (PaymentDetails.Empdatum) getIntent().getParcelableExtra("salary_data");
        Log.d("data", String.valueOf(empdatum));


    }

    private void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.displayLinear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(12,12,12,12);
        stk.setGravity(Gravity.CENTER_HORIZONTAL);
        stk.setLayoutParams(params);



        for (int i = 0; i < 2; i++) {



            TableRow tbrow0 = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText(" Employee ID ");
            tv0.setGravity(Gravity.CENTER);
            tv0.setTextColor(Color.BLUE);
            tv0.setPadding(10,10,10,10);

            tbrow0.addView(tv0);
            TextView tv1 = new TextView(this);
            tv1.setText(" Name ");
            tv1.setTextColor(Color.BLUE);
            tv1.setGravity(Gravity.CENTER);
            tv1.setPadding(10,10,10,10);
            tbrow0.addView(tv1);

            TextView tv2 = new TextView(this);
            tv2.setText(" Month ");
            tv2.setTextColor(Color.BLUE);
            tv2.setGravity(Gravity.CENTER);
            tv2.setPadding(10,10,10,10);
            tbrow0.addView(tv2);

            TextView tv3 = new TextView(this);
            tv3.setText(" Payslab Amount ");
            tv3.setTextColor(Color.BLUE);
            tv3.setGravity(Gravity.CENTER);
            tv3.setPadding(10,10,10,10);
            tbrow0.addView(tv3);

            stk.addView(tbrow0);
            for (i = 0; i < 12; i++) {
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText("" + i);
                t1v.setTextColor(Color.BLACK);
                t1v.setGravity(Gravity.CENTER);
                t1v.setPadding(10,10,10,10);
                tbrow.addView(t1v);

                TextView t2v = new TextView(this);
                t2v.setText(" " + i);
                t2v.setTextColor(Color.BLACK);
                t2v.setGravity(Gravity.CENTER);
                t2v.setPadding(10,10,10,10);
                tbrow.addView(t2v);

                TextView t3v = new TextView(this);
                t3v.setText("Rs." + i);
                t3v.setTextColor(Color.BLACK);
                t3v.setGravity(Gravity.CENTER);
                t3v.setPadding(10,10,10,10);
                tbrow.addView(t3v);

                TextView t4v = new TextView(this);
                t4v.setText("" + i * 15 / 32 * 10);
                t4v.setTextColor(Color.BLACK);
                t4v.setGravity(Gravity.CENTER);
                t4v.setPadding(10,10,10,10);
                tbrow.addView(t4v);
                stk.addView(tbrow);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}