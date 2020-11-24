package cdac.org.anganvadistaffutility.user.activity.beneficiary;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Arrays;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.utils.AutoFitGridLayoutManager;
import cdac.org.anganvadistaffutility.user.adapter.MotherMappingAdapter;

public class MotherMappingActivity extends BaseActivity {
    MotherMappingAdapter motherMappingAdapter;
    ArrayList personNames;
    RecyclerView recyclerView;
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.mother_icon, R.drawable.ic_baseline_category_24, R.drawable.cards, R.drawable.bank, R.drawable.ic_creche_house));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mother_mapping);

        recyclerView = findViewById(R.id.recycler_view);
        //relativeLayout = findViewById(R.id.relativeLayout);
        personNames = new ArrayList<>(Arrays.asList(getResources().getString(R.string.mother_details), getResources().getString(R.string.category_details),
                getResources().getString(R.string.id_card_details), getResources().getString(R.string.bank_details), getResources().getString(R.string.infant_details)));
        AutoFitGridLayoutManager manager = new AutoFitGridLayoutManager(context, 500);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        MotherMappingAdapter motherMappingAdapter = new MotherMappingAdapter(context, personNames, personImages);
        recyclerView.setAdapter(motherMappingAdapter); // set the Adapter to RecyclerView


    }
}