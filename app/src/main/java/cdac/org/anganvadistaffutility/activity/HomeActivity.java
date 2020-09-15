package cdac.org.anganvadistaffutility.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.fragment.BankFragment;
import cdac.org.anganvadistaffutility.fragment.CardFragment;
import cdac.org.anganvadistaffutility.fragment.HomeFragment;
import cdac.org.anganvadistaffutility.fragment.JobFragment;
import cdac.org.anganvadistaffutility.fragment.PaymentFragment;

public class HomeActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment = new HomeFragment();
        setMyFragment(homeFragment);
    }

    @Override
    public void onClick(View view) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setMyFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            setMyFragment(homeFragment);
        } else if (id == R.id.nav_account) {

            // startActivity(new Intent(context, ProfileActivity.class).putExtra("profile_details", profileDetails));
            // Handle the camera action
        } else if (id == R.id.nav_job) {
            JobFragment jobFragment = new JobFragment();
            setMyFragment(jobFragment);
        } else if (id == R.id.nav_bank) {
            BankFragment bankFragment = new BankFragment();
            setMyFragment(bankFragment);
        } else if (id == R.id.nav_payments) {
            PaymentFragment paymentFragment = new PaymentFragment();
            setMyFragment(paymentFragment);
        } else if (id == R.id.nav_cards) {
            CardFragment cardsFragment = new CardFragment();
            setMyFragment(cardsFragment);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
