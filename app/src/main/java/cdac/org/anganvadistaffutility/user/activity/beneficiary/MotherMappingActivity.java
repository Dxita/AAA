package cdac.org.anganvadistaffutility.user.activity.beneficiary;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.activity.BaseActivity;
import cdac.org.anganvadistaffutility.common.activity.LogoutListener;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.AutoFitGridLayoutManager;
import cdac.org.anganvadistaffutility.user.activity.personal_details.BankActivity;
import cdac.org.anganvadistaffutility.user.activity.personal_details.CardActivity;
import cdac.org.anganvadistaffutility.user.activity.personal_details.JobActivity;
import cdac.org.anganvadistaffutility.user.activity.personal_details.PaymentActivity;
import cdac.org.anganvadistaffutility.user.activity.personal_details.ProfileActivity;
import cdac.org.anganvadistaffutility.user.data.BeneficiarySearchData;
import retrofit2.Call;

public class MotherMappingActivity extends BaseActivity {
    ArrayList personNames;
    RecyclerView recyclerView;
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.ic_user_admin, R.drawable.mother_icon, R.drawable.ic_baseline_category_24, R.drawable.cards, R.drawable.bank, R.drawable.ic_creche_house));
    private RelativeLayout relativeLayout;
    BeneficiarySearchData.Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mother_mapping);
        relativeLayout = findViewById(R.id.relativeLayout);
        recyclerView = findViewById(R.id.recycler_view);
        //relativeLayout = findViewById(R.id.relativeLayout);
        personNames = new ArrayList<>(Arrays.asList((getResources().getString(R.string.basic_info)), getResources().getString(R.string.mother_details), getResources().getString(R.string.category_details),
                getResources().getString(R.string.id_card_details), getResources().getString(R.string.bank_details), getResources().getString(R.string.infant_details)));
        AutoFitGridLayoutManager manager = new AutoFitGridLayoutManager(context, 500);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        MotherMappingAdapter motherMappingAdapter = new MotherMappingAdapter(context, personNames, personImages);
        recyclerView.setAdapter(motherMappingAdapter); // set the Adapter to RecyclerView

        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getBeneficiarySearchData();
        } else {
            AppUtils.showToast(context, getResources().getString(R.string.no_internet_connection));
        }

    }


    private void getBeneficiarySearchData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.BASE_URL);
        Call<BeneficiarySearchData> call = apiInterface.getBeneficiarSearchData("1", appPreferences.getAadharNo(), appPreferences.getMobileNumber(), appPreferences.getJanaadharno(), appPreferences.getBhamashano());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<BeneficiarySearchData>() {
            @Override
            public void onSuccess(BeneficiarySearchData body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                //    AppUtils.showToast(context, body.getMessage());

                    data = body.getData();
                    Log.d("check", String.valueOf(data));

                    //startActivity(new Intent(context, BeneficiarySearchResultActivity.class).putExtra("benefeciary_data", data));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, context.getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }

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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open another activity on item click
                    if (position == 0) {
                        startActivity(new Intent(context, BasicInformationActivity.class).putExtra("basic_info", data));
                    }
                    if (position == 1) {
                        startActivity(new Intent(context, MotherDetailsActivity.class).putExtra("mother_details", data));
                    }
                    if (position == 2) {
                        startActivity(new Intent(context, CategoryActivity.class).putExtra("category", data));
                    }

                    if (position == 3) {
                        startActivity(new Intent(context, IdCardDetailsActivity.class).putExtra("id_cards_details", data));
                    }

                    if (position == 4) {
                        startActivity(new Intent(context, BenefeciaryBankDetailsActivity.class).putExtra("bank_details", data));
                    }

                    if (position == 5) {
                        startActivity(new Intent(context, InfantDetailsActivity.class).putExtra("infant_details", data));
                    } else {

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return personNames.size();
        }
    }

    public static class MyViewHold extends RecyclerView.ViewHolder {
        CardView cardView_category;
        TextView text_category;
        ImageView img_category;

        public MyViewHold(View itemView) {
            super(itemView);
            cardView_category = itemView.findViewById(R.id.cardv);
            text_category = itemView.findViewById(R.id.txt_infra_name);
            img_category = itemView.findViewById(R.id.img_infra_name);

        }
    }

}