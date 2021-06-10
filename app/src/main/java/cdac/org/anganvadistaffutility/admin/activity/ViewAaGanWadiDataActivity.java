package cdac.org.anganvadistaffutility.admin.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.igmpy.IgmpyStatusActivity;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.AutoFitGridLayoutManager;


public class ViewAaGanWadiDataActivity extends BaseActivity implements View.OnClickListener {
    ArrayList personNames;
    RecyclerView recyclerView;
    double latitude;
    double longitude;
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_aaganwadi_data);
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);
        //relativeLayout = findViewById(R.id.relativeLayout);
        personNames = new ArrayList<>(Arrays.asList((getResources()
                        .getString(R.string.view_aaganwadi_data)), getResources().getString(R.string.payment_details),getResources().getString(R.string.igmpy)));
        AutoFitGridLayoutManager manager = new AutoFitGridLayoutManager(context, 500);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        AdminSectionAdapter adminSectionAdapter = new AdminSectionAdapter(context, personNames);
        recyclerView.setAdapter(adminSectionAdapter); // set the Adapter to RecyclerView
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.app_logo);
        builder.setMessage(getString(R.string.exit_text))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> logout())
                .setNegativeButton(getString(R.string.no),
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void logout() {
        AppUtils.showToast(context, getResources().getString(R.string.logout_success));

        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
        }

    }

    private class AdminSectionAdapter extends RecyclerView.Adapter<AdminVieHolder> {
        ArrayList personNames;
        Context context;
        public AdminSectionAdapter(Context context, ArrayList personNames) {
            this.context = context;
            this.personNames = personNames;
        }

        @NonNull
        @Override
        public AdminVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admib_dashboard_rv_items, parent, false);
            return new AdminVieHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull AdminVieHolder holder, int position) {
            holder.text_category.setText((CharSequence) personNames.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position == 0) {
                        startActivity(new Intent(context, ShowKPIActivity.class));
                    }
                    if (position == 1) {
                        startActivity(new Intent(context, EmployeePaymentDetailActivity.class));
                    }
                    if (position == 2) {
                        startActivity(new Intent(context, IgmpyStatusActivity.class));
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return personNames.size();
        }
    }

    private static class AdminVieHolder extends RecyclerView.ViewHolder {
        CardView cardView_category;
        TextView text_category;
        public AdminVieHolder(@NonNull View itemView) {
            super(itemView);
            cardView_category = itemView.findViewById(R.id.cardv);
            text_category = itemView.findViewById(R.id.txt_infra_name);
        }
    }
}
