package cdac.org.anganvadistaffutility.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.common.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.common.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.common.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.common.utils.AppUtils;
import cdac.org.anganvadistaffutility.common.utils.AutoFitGridLayoutManager;
import cdac.org.anganvadistaffutility.user.activity.BankActivity;
import cdac.org.anganvadistaffutility.user.activity.CardActivity;
import cdac.org.anganvadistaffutility.user.activity.HomeActivity;
import cdac.org.anganvadistaffutility.user.activity.JobActivity;
import cdac.org.anganvadistaffutility.user.activity.PaymentActivity;
import cdac.org.anganvadistaffutility.user.activity.ProfileActivity;
import cdac.org.anganvadistaffutility.user.data.EmployeeDetails;
import retrofit2.Call;


public class HomeFragment extends Fragment {

    private Context context;
    private EmployeeDetails.Profile profileDetails;
    private EmployeeDetails.Job jobDetails;
    private EmployeeDetails.Card cardDetails;
    private EmployeeDetails.Bank bankDetails;
    private RelativeLayout relativeLayout;

    RecyclerView recyclerView;
    ArrayList personNames = new ArrayList<>(Arrays.asList("Profile Details", "Job Details", "Bank Details", "Card Details", "Payment Details"));
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.profile, R.drawable.job, R.drawable.bank, R.drawable.cards, R.drawable.payment));


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = root.findViewById(R.id.recycler_view);
        relativeLayout = root.findViewById(R.id.relativeLayout);

        AutoFitGridLayoutManager manager = new AutoFitGridLayoutManager(context, 500);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        CustomAdapter customAdapter = new CustomAdapter(getActivity(), personNames, personImages);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (AppUtils.isNetworkConnected(context)) {
            AppUtils.setProgressBarVisibility(context, relativeLayout, View.VISIBLE);
            getEmployeeData();
        } else {
            AppUtils.showToast(context, context.getResources().getString(R.string.no_internet_connection));
        }
    }

    private void getEmployeeData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<EmployeeDetails> call = apiInterface.employeeDetails(((HomeActivity) context).appPreferences.getEmployeeId());
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<EmployeeDetails>() {
            @Override
            public void onSuccess(EmployeeDetails body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                    AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                    AppUtils.showToast(context, body.getMessage());

                    EmployeeDetails.Data data = body.getData();
                    profileDetails = data.getProfile();
                    cardDetails = data.getCard();
                    jobDetails = data.getJob();
                    bankDetails = data.getBank();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AppUtils.setProgressBarVisibility(context, relativeLayout, View.GONE);
                AppUtils.showToast(context, context.getResources().getString(R.string.error_in_fetch_data));
            }
        }));
    }


   /* @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_card:
                startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("profile_details", profileDetails));
                break;
            case R.id.job_card:
                startActivity(new Intent(getActivity(), JobActivity.class).putExtra("job_details", jobDetails));
                break;
            case R.id.payment_card:
                startActivity(new Intent(getActivity(), PaymentActivity.class));
                break;
            case R.id.bank_card:
                startActivity(new Intent(getActivity(), BankActivity.class).putExtra("bank_details", bankDetails));
                break;
            case R.id.card:
                startActivity(new Intent(getActivity(), CardActivity.class).putExtra("card_details", cardDetails));
                break;
        }
    }*/

    private class CustomAdapter extends RecyclerView.Adapter<ViewHolde> {
        ArrayList personNames;
        ArrayList personImages;
        Context context;

        public CustomAdapter(FragmentActivity activity, ArrayList personNames, ArrayList personImages) {
            this.context = activity;
            this.personNames = personNames;
            this.personImages = personImages;
        }

        @NonNull
        @Override
        public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_items, parent, false);
            // set the view's size, margins, paddings and layout parameters

            return new ViewHolde(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolde holder, final int position) {
            holder.text_category.setText((CharSequence) personNames.get(position));
            holder.img_category.setImageResource((Integer) personImages.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open another activity on item click
                   if (position==0)
                   {
                       startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("profile_details", profileDetails));
                   }
                   if (position==1)
                   {
                       startActivity(new Intent(getActivity(), JobActivity.class).putExtra("job_details", jobDetails));
                   }

                    if (position==2)
                    {
                        startActivity(new Intent(getActivity(), BankActivity.class).putExtra("bank_details", bankDetails));
                    }

                    if (position==3)
                    {
                        startActivity(new Intent(getActivity(), CardActivity.class).putExtra("card_details", cardDetails));
                    }

                    if (position==4)
                    {
                        startActivity(new Intent(getActivity(), PaymentActivity.class));
                    }
                    else{

                    }
                }
            });


        }


        @Override
        public int getItemCount() {
            return personNames.size();
        }
    }


    private static class ViewHolde extends RecyclerView.ViewHolder {
        CardView cardView_category;
        TextView text_category;
        ImageView img_category;

        public ViewHolde(@NonNull View itemView) {

            super(itemView);


            cardView_category = itemView.findViewById(R.id.cardv);
            text_category = itemView.findViewById(R.id.txt_infra_name);
            img_category = itemView.findViewById(R.id.img_infra_name);

        }
    }

}

