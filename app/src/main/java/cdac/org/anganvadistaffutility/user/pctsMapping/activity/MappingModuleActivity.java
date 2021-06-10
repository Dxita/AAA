package cdac.org.anganvadistaffutility.user.pctsMapping.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;

public class MappingModuleActivity extends BaseActivity {
    ArrayList personNames;
    RecyclerView recyclerView;
    //  ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.profile, R.drawable.building, R.drawable.beneficiary, R.drawable.food, R.drawable.mapping, R.drawable.attendance, R.drawable.ic_user_admin));

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mapping_module);

        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);
        //relativeLayout = findViewById(R.id.relativeLayout);
        personNames = new ArrayList<>(Arrays.asList((getResources()
                        .getString(R.string.mother_mapping)), getResources().getString(R.string.mother_mapping_singlepage), getResources().getString(R.string.pcts_district_mapping),
                getResources().getString(R.string.food_beneficiary_cat), getResources().getString(R.string.food_beneficiary_sub_cat)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        PctsSectionAdapter pctsSectionAdapter = new PctsSectionAdapter(context, personNames);
        recyclerView.setAdapter(pctsSectionAdapter); // set the Adapter to RecyclerView


    }

    private class PctsSectionAdapter extends RecyclerView.Adapter<MappingViewHolder> {
        ArrayList personNames, personImages;
        Context context;

        public PctsSectionAdapter(Context context, ArrayList personNames) {
            this.context = context;
            this.personNames = personNames;
        }

        @NonNull
        @Override
        public MappingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mapping_card_items, parent, false);
            return new MappingViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MappingViewHolder holder, int position) {
            holder.text_category.setText((CharSequence) personNames.get(position));
            //  holder.img_category.setImageResource((Integer) personImages.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position == 0) {

                    }
                    if (position == 1) {
                        startActivity(new Intent(context, MmSinglePageActivity.class));

                    }
                }

            });
        }


        @Override
        public int getItemCount() {
            return personNames.size();
        }
    }

    private static class MappingViewHolder extends RecyclerView.ViewHolder {
        CardView cardView_category;
        TextView text_category;

        public MappingViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView_category = itemView.findViewById(R.id.cardv);
            text_category = itemView.findViewById(R.id.txt_infra_name);
        }
    }
}