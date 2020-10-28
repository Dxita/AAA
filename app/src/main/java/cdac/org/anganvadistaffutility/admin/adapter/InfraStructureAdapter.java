package cdac.org.anganvadistaffutility.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;

public class InfraStructureAdapter extends RecyclerView.Adapter<InfraStructureAdapter.ViewHolder> {

    protected Context mContext;
    protected List<AaganwadiInfraStructure.Data.InfrastructureDatum> infrastructureData;
    protected List<Integer> infraImageList;
    protected ItemClickListener mListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView txt_infra_name;
        protected View layout;
        protected AppCompatImageView img_infra_name;
        protected AaganwadiInfraStructure.Data.InfrastructureDatum infrastructureDatum;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txt_infra_name = v.findViewById(R.id.txt_infra_name);
            img_infra_name = v.findViewById(R.id.img_infra_name);
            layout.setOnClickListener(this);
        }

        public void setData(AaganwadiInfraStructure.Data.InfrastructureDatum infrastructureDatum, int drawable) {
            this.infrastructureDatum = infrastructureDatum;
            String name = "";
            if (LocaleManager.getLocale(mContext.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                name = infrastructureDatum.getTimInfraNamee();
            } else {
                name = infrastructureDatum.getTimInfraNameh();
            }
            txt_infra_name.setText(name);
            img_infra_name.setImageResource(drawable);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(infrastructureDatum);
            }
        }
    }

    public InfraStructureAdapter(Context context, List<AaganwadiInfraStructure.Data.InfrastructureDatum> infrastructureData, List<Integer> imageList,
                                 ItemClickListener itemClickListener) {
        this.mContext = context;
        this.infrastructureData = infrastructureData;
        this.infraImageList = imageList;
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
        holder.setData(infrastructureData.get(position), infraImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return infrastructureData.size();
    }

    public interface ItemClickListener {
        void onItemClick(AaganwadiInfraStructure.Data.InfrastructureDatum item);
    }
}
