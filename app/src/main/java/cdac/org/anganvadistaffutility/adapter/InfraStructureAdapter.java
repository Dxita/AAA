package cdac.org.anganvadistaffutility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.data.AaganwadiInfraStructure;

public class InfraStructureAdapter extends RecyclerView.Adapter<InfraStructureAdapter.ViewHolder> {
    private List<AaganwadiInfraStructure.InfrastructureDatum> values;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_infra_name;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txt_infra_name = v.findViewById(R.id.txt_infra_name);
        }
    }

    public InfraStructureAdapter(Context context, List<AaganwadiInfraStructure.InfrastructureDatum> myDataset) {
        values = myDataset;
    }

    @NonNull
    @Override
    public InfraStructureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycle_item_infrastructure, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = values.get(position).getTimInfrastructureNamee();
        holder.txt_infra_name.setText(name);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}