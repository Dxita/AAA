package cdac.org.anganvadistaffutility.public_u.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;

public class BuildingDetailsActivity extends BaseActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    CheckBox chk1, chk2, chk3, chk4, chk5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_building_details);

        chk1 = (CheckBox) findViewById(R.id.chk1);
        chk2 = (CheckBox) findViewById(R.id.chk2);
        chk3 = (CheckBox) findViewById(R.id.chk3);
        chk4 = (CheckBox) findViewById(R.id.chk4);
        chk5 = (CheckBox) findViewById(R.id.chk5);


    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str = "";
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.chk1:
                str = checked ? "" : getResources().getString(R.string.drinking_water);
                break;
            case R.id.chk2:
                str = checked ? "" : getResources().getString(R.string.electricity);
                break;
            case R.id.chk3:
                str = checked ? "" : getResources().getString(R.string.kitchen);
                break;
            case R.id.chk4:
                str = checked ? "" : getResources().getString(R.string.open_area);
                break;
            case R.id.chk5:
                str = checked ? "" : getResources().getString(R.string.toilet);
                break;
        }

    }

    @Override
    public void onClick(View view) {

    }


}