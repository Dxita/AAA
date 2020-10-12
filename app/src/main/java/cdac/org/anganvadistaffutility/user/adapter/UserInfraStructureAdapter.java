package cdac.org.anganvadistaffutility.user.adapter;

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
import cdac.org.anganvadistaffutility.admin.adapter.InfraStructureAdapter;
import cdac.org.anganvadistaffutility.admin.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;

public class UserInfraStructureAdapter  extends RecyclerView.Adapter<UserInfraStructureAdapter.ViewHolder>{
    private Context mContext;
    private List<AaganwadiInfraStructure.InfrastructureDatum> infrastructureData;
    private List<Integer> infraImageList;
    protected UserInfraStructureAdapter.ItemClickListener mListener;

    public UserInfraStructureAdapter(Context context, List<AaganwadiInfraStructure.InfrastructureDatum> infrastructureData, List<Integer> infraStructureImageList, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.infrastructureData = infrastructureData;
        this.infraImageList = infraStructureImageList;
        this.mListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.ifrastructure_category_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(infrastructureData.get(position), infraImageList.get(position));

    }

    @Override
    public int getItemCount() {
        return infrastructureData.size();
    }



    public interface ItemClickListener {
        void onItemClick(AaganwadiInfraStructure.InfrastructureDatum item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        protected TextView txt_infra_name;
        protected View layout;
        protected AppCompatImageView img_infra_name;
        protected AaganwadiInfraStructure.InfrastructureDatum infrastructureDatum;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txt_infra_name = v.findViewById(R.id.txt_infra_name);
            img_infra_name = v.findViewById(R.id.img_infra_name);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(infrastructureDatum);
            }
        }

        public void setData(AaganwadiInfraStructure.InfrastructureDatum infrastructureDatum, Integer drawable) {
            this.infrastructureDatum = infrastructureDatum;
            String name = "";
            if (LocaleManager.getLocale(mContext.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                name = infrastructureDatum.getTimInfrastructureNamee();
            } else {
                name = infrastructureDatum.getTimInfrastructureNameh();
            }
            txt_infra_name.setText(name);
            img_infra_name.setImageResource(drawable);
        }
    }


}
