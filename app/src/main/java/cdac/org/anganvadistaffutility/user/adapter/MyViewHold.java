package cdac.org.anganvadistaffutility.user.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import cdac.org.anganvadistaffutility.R;

public class MyViewHold extends RecyclerView.ViewHolder {
    CardView cardView_category;
    TextView text_category;
    ImageView img_category;

    public MyViewHold(View v) {
        super(v);
        cardView_category = v.findViewById(R.id.cardv);
        text_category = v.findViewById(R.id.txt_infra_name);
        img_category = v.findViewById(R.id.img_infra_name);

    }
}
