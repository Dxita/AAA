package cdac.org.anganvadistaffutility.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.AanganwadiBuildingData;

public class ToiletAdapter extends RecyclerView.Adapter<ToiletAdapter.MyViewHolders> {
    Context context;
    List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;

    public ToiletAdapter(Context context, List<AanganwadiBuildingData.Data.InfrastructureDatum> infrastructureData) {
        this.context = context;
        this.infrastructureData = infrastructureData;
    }

    @NonNull
    @Override
    public MyViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.toilet_rv_items, null);
        return new MyViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolders holder, int position) {

        infrastructureData.get(position);
        holder.checkBox.setTag(position);

        if (infrastructureData.get(position).getStatus().equalsIgnoreCase("yes")) {
            holder.checkBox.setChecked(true);
            Toast.makeText(context, infrastructureData.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                int clickedPos = ((Integer) cb.getTag()).intValue();
                if (cb.isChecked()) {
                    Toast.makeText(context, infrastructureData.get(position).getTidInfraNamee() + "", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.edtx_qty.requestFocus();
                holder.edtx_qty.setCursorVisible(true);
                holder.edtx_qty.setFocusable(true);
                holder.edtx_qty.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                holder.edtx_qty.setClickable(true); // user navigat
            }
        });
        holder.setData(context, infrastructureData.get(position));
    }

    @Override
    public int getItemCount() {
        return infrastructureData.size();
    }


    class MyViewHolders extends RecyclerView.ViewHolder {
        AppCompatTextView item_name;
        private AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData;
        CheckBox checkBox;
        AppCompatEditText edtx_qty;
        AppCompatImageView edit_icon;

        public MyViewHolders(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_tv);
            checkBox = itemView.findViewById(R.id.checkbox);
            edtx_qty = itemView.findViewById(R.id.qty_edtx);
            edit_icon = itemView.findViewById(R.id.edit_icon);

            edtx_qty.setFocusable(false);
            edtx_qty.setCursorVisible(false);
            edtx_qty.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            edtx_qty.setClickable(false); // user navigat
        }

        public void setData(Context context, AanganwadiBuildingData.Data.InfrastructureDatum infrastructureData) {
            this.infrastructureData = infrastructureData;
            String name = "";
            String qty = "";

            if (LocaleManager.getLocale(context.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                name = infrastructureData.getTidInfraNamee();
                qty = infrastructureData.getTjaidQty();

            } else {
                name = infrastructureData.getTidInfraNameh();
                qty = infrastructureData.getTjaidQty();
            }
            //  name = infrastructureData.getTidInfraNamee();
            item_name.setText(name);
            edtx_qty.setText(qty);


        }


    }



}