package cdac.org.anganvadistaffutility.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.AanganwadiBuildingData;

public class DrinkingWaterAdapter extends RecyclerView.Adapter<DrinkingWaterAdapter.MyViewHolders> {
    Context context;
    List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
    private final CheckBox lastChecked = null;
    private final int lastCheckedPos = 0;

    public DrinkingWaterAdapter(Context context, List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData) {
        this.context = context;
        this.infrastructureData = infrastructureData;
    }

    @NonNull
    @Override
    public MyViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.aw_building_items, null);
        return new MyViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolders holder, int position) {
        infrastructureData.get(position);
        holder.checkBox.setTag(position);

        if (infrastructureData.get(position).getStatus().equalsIgnoreCase("yes")) {
            holder.checkBox.setChecked(true);
            Toast.makeText(context, infrastructureData.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                int clickedPos = (Integer) cb.getTag();
                if (cb.isChecked()) {
                    //Toast.makeText(context, ""+clickedPos, Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, infrastructureData.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.setData(context, infrastructureData.get(position));
    }

    @Override
    public int getItemCount() {
        return infrastructureData.size();
    }


    public class MyViewHolders extends RecyclerView.ViewHolder {
        AppCompatTextView item_name;
        private AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData;
        CheckBox checkBox;

        public MyViewHolders(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_tv);
            checkBox = itemView.findViewById(R.id.checkbox);
            // checkBox.setClickable(false);
        }

        public void setData(Context context, AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData) {
            this.infrastructureData = infrastructureData;
            String name = "";
            String qty = "";
            if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                name = infrastructureData.getTidInfraNamee();
            } else {
                name = infrastructureData.getTidInfraNameh();
            }
            //  name = infrastructureData.getTidInfraNamee();
            item_name.setText(name);
        }

    }

}