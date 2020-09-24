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
import cdac.org.anganvadistaffutility.utils.LocaleManager;

public class InfraStructureAdapter extends RecyclerView.Adapter<InfraStructureAdapter.ViewHolder> {

    private Context mContext;
    private List<AaganwadiInfraStructure.InfrastructureDatum> infrastructureData;
    protected ItemClickListener mListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_infra_name;
        public View layout;
        protected AaganwadiInfraStructure.InfrastructureDatum infrastructureDatum;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txt_infra_name = v.findViewById(R.id.txt_infra_name);
            layout.setOnClickListener(this);
        }

        public void setData(AaganwadiInfraStructure.InfrastructureDatum infrastructureDatum) {
            this.infrastructureDatum = infrastructureDatum;
            String name = "";
            if (LocaleManager.getLocale(mContext.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                name = infrastructureDatum.getTimInfrastructureNamee();
            } else {
                name = infrastructureDatum.getTimInfrastructureNameh();
            }
            txt_infra_name.setText(name);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(infrastructureDatum);
            }
        }
    }

    public InfraStructureAdapter(Context context, List<AaganwadiInfraStructure.InfrastructureDatum> infrastructureData,
                                 ItemClickListener itemClickListener) {
        this.mContext = context;
        this.infrastructureData = infrastructureData;
        this.mListener = itemClickListener;
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
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, final int position) {
        holder.setData(infrastructureData.get(position));
    }

    @Override
    public int getItemCount() {
        return infrastructureData.size();
    }

    public interface ItemClickListener {
        void onItemClick(AaganwadiInfraStructure.InfrastructureDatum item);
    }
}