package cdac.org.anganvadistaffutility.user.activity.infrastructure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;

public class ElectricityActivity extends AppCompatActivity {
    String infra_id;
    private RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    ArrayList personNames = new ArrayList<>(Arrays.asList("Fan", "Bulb", "Cooler", "LED", "AC", "Table fan"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_electricity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                infra_id = null;
            } else {
                infra_id = extras.getString("infra_id");
            }
        } else {
            infra_id = (String) savedInstanceState.getSerializable("infra_id");
        }


        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ElectricityAdapter electricityAdapter = new ElectricityAdapter(ElectricityActivity.this, personNames);
        recyclerView.setAdapter(electricityAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private static class ElectricityAdapter extends RecyclerView.Adapter<MyViewHolder> {
        Context context;
        ArrayList personNames = new ArrayList<>(Arrays.asList("profile", "profile", "profile", "profile", "profile", "profile"));

        public ElectricityAdapter(Context context, ArrayList personNames) {
            this.context = context;
            this.personNames = personNames;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.awc_items_rv, parent, false);
            // set the view's size, margins, paddings and layout parameters
            return new MyViewHolder(v);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           personNames.get(position);
           holder.item_name.setText((CharSequence) personNames.get(position));
           //  holder.item_name.setText((CharSequence)
        }


        @Override
        public int getItemCount() {
            return personNames.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatEditText item_qty;
        AppCompatTextView item_name;

        public MyViewHolder(View v) {
            super(v);
            item_name = v.findViewById(R.id.item_tv);
            item_qty = v.findViewById(R.id.qty_edtx);

        }
    }
}