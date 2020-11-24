package cdac.org.anganvadistaffutility.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cdac.org.anganvadistaffutility.R;

public class MotherMappingAdapter extends RecyclerView.Adapter<MyViewHold> {
    ArrayList personNames;
    ArrayList personImages;
    Context context;

    public MotherMappingAdapter(Context context, ArrayList personNames, ArrayList personImages) {
        this.context = context;
        this.personNames = personNames;
        this.personImages = personImages;
    }

    @NonNull
    @Override
    public MyViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_mothermapping_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHold holder, int position) {
        holder.text_category.setText((CharSequence) personNames.get(position));
        holder.img_category.setImageResource((Integer) personImages.get(position));

    }

    @Override
    public int getItemCount() {
        return personNames.size();
    }
}
