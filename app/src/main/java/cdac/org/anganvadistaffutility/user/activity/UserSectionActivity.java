package cdac.org.anganvadistaffutility.user.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ag.floatingactionmenu.OptionsFabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.AutoFitGridLayoutManager;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.activity.infrastructure.AvailableInfraDetailsActivity;
import cdac.org.anganvadistaffutility.user.pctsMapping.activity.MappingModuleActivity;

public class UserSectionActivity extends BaseActivity {
    ArrayList personNames;
    RecyclerView recyclerView;
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.profile, R.drawable.building, R.drawable.mapping));

     RelativeLayout relativeLayout;
    private OptionsFabLayout fabWithOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_section);

        initFabActionMenu();
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);


        //relativeLayout = findViewById(R.id.relativeLayout);
        personNames = new ArrayList<>(Arrays.asList(getResources().getString(R.string.profile_details), getResources().getString(R.string.infrastructure_data),
                getResources().getString(R.string.mapping)));

        AutoFitGridLayoutManager manager = new AutoFitGridLayoutManager(context, 500);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        UserSectionAdapter userSectionAdapter = new UserSectionAdapter(context, personNames, personImages);
        recyclerView.setAdapter(userSectionAdapter); // set the Adapter to RecyclerView

    }

    private void initFabActionMenu() {
        fabWithOptions = findViewById(R.id.fab_menu);
        fabWithOptions.setMiniFabsColors(R.color.sky, R.color.sky, R.color.sky);

        fabWithOptions.setMainFabOnClickListener(view -> {
            if (fabWithOptions.isOptionsMenuOpened()) {
                fabWithOptions.closeOptionsMenu();
            }
        });

        fabWithOptions.setMiniFabSelectedListener(fabItem -> {
            if (fabItem.getItemId() == R.id.fab_change_password) {
                startActivity(new Intent(context, ChangePasswordActivity.class));
            } else if (fabItem.getItemId() == R.id.fab_change_language) {
                if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                    changeAppLocale((AppCompatActivity) context, LocaleManager.ENGLISH);
                } else {
                    changeAppLocale((AppCompatActivity) context, LocaleManager.HINDI);
                }
            } else if (fabItem.getItemId() == R.id.fab_logout) {
                logout();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserSectionActivity.this);
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

    private class UserSectionAdapter extends RecyclerView.Adapter<MyViewHolded> {
        ArrayList personNames, personImages;
        Context context;

        public UserSectionAdapter(Context context, ArrayList personNames, ArrayList personImages) {
            this.context = context;
            this.personNames = personNames;
            this.personImages = personImages;
        }

        @NonNull
        @Override
        public MyViewHolded onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_rv_items, parent, false);
            return new MyViewHolded(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolded holder, int position) {
            holder.text_category.setText((CharSequence) personNames.get(position));
            holder.img_category.setImageResource((Integer) personImages.get(position));
            //holder.img_category.setImageResource((Integer) personImages.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position == 0) {
                        startActivity(new Intent(context, HomeActivity.class));
                    }
                    if (position == 1) {
                        startActivity(new Intent(context, AvailableInfraDetailsActivity.class));
                    }
                    if (position == 2) {
                        startActivity(new Intent(context, MappingModuleActivity.class));
                    }

                }
            });
        }


        @Override
        public int getItemCount() {
            return personNames.size();
        }
    }

    private static class MyViewHolded extends RecyclerView.ViewHolder {
        CardView cardView_category;
        TextView text_category;
        ImageView img_category;

        public MyViewHolded(@NonNull View itemView) {
            super(itemView);
            cardView_category = itemView.findViewById(R.id.cardv);
            text_category = itemView.findViewById(R.id.txt_infra_name);
            img_category = itemView.findViewById(R.id.img_infra_name);
        }
    }
}