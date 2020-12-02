package cdac.org.anganvadistaffutility.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;

import java.util.List;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.admin.adapter.InfraStructureAdapter;
import cdac.org.anganvadistaffutility.admin.data.AaganwadiInfraStructure;
import cdac.org.anganvadistaffutility.common.utils.LocaleManager;
import cdac.org.anganvadistaffutility.user.data.UserInfrastructureData;

public class UserInfraStructureAdapter extends RecyclerView.Adapter<UserInfraStructureAdapter.ViewHolder> {
    private final Context mContext;
    private final List<UserInfrastructureData.Data.InfrastructureDatum> infrastructureData;
   private final List<Integer> infraImageList;
    protected UserInfraStructureAdapter.ItemClickListener mListener;

    public UserInfraStructureAdapter(Context context, List<UserInfrastructureData.Data.InfrastructureDatum> infrastructureData, List<Integer> infraStructureImageList, ItemClickListener itemClickListener) {
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
        holder.setData(infrastructureData.get(position),infraImageList.get(position));

    }

    @Override
    public int getItemCount() {
        return infrastructureData.size();
    }


    public interface ItemClickListener {
        void onItemClick(UserInfrastructureData.Data.InfrastructureDatum item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView txt_infra_name;
        protected View layout;
        protected AppCompatImageView img_infra_name;
        protected UserInfrastructureData.Data.InfrastructureDatum infrastructureDatum;

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

        public void setData(UserInfrastructureData.Data.InfrastructureDatum infrastructureDatum,int drawable) {
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
    }
}






