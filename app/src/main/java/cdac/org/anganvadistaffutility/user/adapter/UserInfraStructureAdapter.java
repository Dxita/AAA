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

public class UserInfraStructureAdapter  extends RecyclerView.Adapter<UserInfraStructureAdapter.ViewHolder> {
    private Context mContext;
    private List<AaganwadiInfraStructure.Data.InfrastructureDatum> infrastructureData;
    private List<Integer> infraImageList;
    protected UserInfraStructureAdapter.ItemClickListener mListener;

    public UserInfraStructureAdapter(Context context, List<AaganwadiInfraStructure.Data.InfrastructureDatum> infrastructureData, List<Integer> infraStructureImageList, ItemClickListener itemClickListener) {
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
        holder.setData(infrastructureData.get(position));

    }

    @Override
    public int getItemCount() {
        return infrastructureData.size();
    }


    public interface ItemClickListener {
        void onItemClick(AaganwadiInfraStructure.Data.InfrastructureDatum item);
    }

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

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(infrastructureDatum);
            }
        }

        public void setData(AaganwadiInfraStructure.Data.InfrastructureDatum infrastructureDatum) {
            this.infrastructureDatum = infrastructureDatum;
            String name = "";
            String icon = "";
            if (LocaleManager.getLocale(mContext.getResources()).getLanguage().equalsIgnoreCase(LocaleManager.ENGLISH)) {
                name = infrastructureDatum.getTimInfraNamee();

            } else {
                name = infrastructureDatum.getTimInfraNameh();
            }
            icon=infrastructureDatum.getTimInfraIcon();

            txt_infra_name.setText(name);
            Glide.with(mContext)
                    .load(icon)
                    .into(img_infra_name);

        }
    }
}


         /*  String fnm = infrastructureDatum.getTimInfraIcon(); //  this is image file name
            String PACKAGE_NAME = mContext.getPackageName();
            int imgId = mContext.getResources().getIdentifier(PACKAGE_NAME+":drawable/"+fnm , null, null);
            System.out.println("IMG ID :: "+imgId);
            System.out.println("PACKAGE_NAME :: "+PACKAGE_NAME);
//    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imgId);

           // img_infra_name.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),imgId));
        }*/




