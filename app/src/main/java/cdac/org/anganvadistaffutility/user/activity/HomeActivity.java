package cdac.org.anganvadistaffutility.user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.UserTypeActivity;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;
import cdac.org.anganvadistaffutility.user.fragment.HomeFragment;
import retrofit2.Call;

public class HomeActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private EmployeeDetails.Profile profileDetails;
    private EmployeeDetails.Job jobDetails;
    private EmployeeDetails.Card cardDetails;
    private EmployeeDetails.Bank bankDetails;
    private RelativeLayout relativeLayout;
    AppCompatTextView logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        logout = drawer.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment = new HomeFragment();
        setMyFragment(homeFragment);

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getEmployeeData();
        } else {
            AppUtils.showToast(context, context.getResources().getString(R.string.no_internet_connection));
        }
    }

    private void getEmployeeData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<EmployeeDetails> call = apiInterface.employeeDetails(appPreferences.getEmployeeId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<EmployeeDetails>() {
            @Override
            public void onSuccess(EmployeeDetails body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                    AppUtils.showToast(context, body.getMessage());

                    EmployeeDetails.Data data = body.getData();
                    profileDetails = data.getProfile();
                    cardDetails = data.getCard();
                    jobDetails = data.getJob();
                    bankDetails = data.getBank();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, context.getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout) {
            SharedPreferences.Editor editor = appPreferences.getAppPreference().edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(context,
                    UserTypeActivity.class);
            startActivity(intent);
            finishAffinity();

            AppUtils.showToast(context, getResources().getString(R.string.logout_success));
        }
    }

    private void setLoginState(boolean b) {
        SharedPreferences sp = getSharedPreferences("LoginState",
                MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("setLoggingOut", b);
        ed.apply();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    private void setMyFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            setMyFragment(homeFragment);
        } else if (id == R.id.nav_account) {
            startActivity(new Intent(context, ProfileActivity.class).putExtra("profile_details", profileDetails));
            // Handle the camera action
        } else if (id == R.id.nav_job) {
            startActivity(new Intent(context, JobActivity.class).putExtra("job_details", jobDetails));
        } else if (id == R.id.nav_bank) {
            startActivity(new Intent(context, BankActivity.class).putExtra("bank_details", bankDetails));
        } else if (id == R.id.nav_payments) {
            startActivity(new Intent(context, PaymentActivity.class));
        } else if (id == R.id.nav_cards) {
            startActivity(new Intent(context, CardActivity.class).putExtra("card_details", cardDetails));
        } else if (id == R.id.change_language) {
            if (LocaleManager.getLanguagePref(context).equalsIgnoreCase(LocaleManager.HINDI)) {
                setAppLocale((AppCompatActivity) context, LocaleManager.ENGLISH);
            } else {
                setAppLocale((AppCompatActivity) context, LocaleManager.HINDI);
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
