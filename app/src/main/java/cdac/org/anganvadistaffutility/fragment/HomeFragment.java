package cdac.org.anganvadistaffutility.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.activity.BankActivity;
import cdac.org.anganvadistaffutility.activity.CardActivity;
import cdac.org.anganvadistaffutility.activity.JobActivity;
import cdac.org.anganvadistaffutility.activity.PaymentActivity;
import cdac.org.anganvadistaffutility.activity.ProfileActivity;
import cdac.org.anganvadistaffutility.data.EmployeeDetails;
import cdac.org.anganvadistaffutility.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.utils.AppUtils;
import retrofit2.Call;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private EmployeeDetails.Profile profileDetails;
    private EmployeeDetails.Job jobDetails;
    private EmployeeDetails.Card cardDetails;
    private EmployeeDetails.Bank bankDetails;

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

        CardView cardView = root.findViewById(R.id.profile_card);
        CardView job_card = root.findViewById(R.id.job_card);
        CardView bank_card = root.findViewById(R.id.bank_card);
        CardView payment_card = root.findViewById(R.id.payment_card);
        CardView card = root.findViewById(R.id.card);

        cardView.setOnClickListener(this);
        job_card.setOnClickListener(this);
        payment_card.setOnClickListener(this);
        bank_card.setOnClickListener(this);
        card.setOnClickListener(this);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (AppUtils.isNetworkConnected(context)) {
            getEmployeeData();
        } else {
            AppUtils.showToast(context, context.getResources().getString(R.string.no_internet_connection));
        }
    }

    private void getEmployeeData() {
        ApiInterface apiInterface = ApiUtils.getApiInterface(ApiUtils.PROFILE_BASE_URL);
        Call<EmployeeDetails> call = apiInterface.employeeDetails(AppUtils.empID);
        call.enqueue(new ApiServiceOperator<>(new ApiServiceOperator.OnResponseListener<EmployeeDetails>() {
            @Override
            public void onSuccess(EmployeeDetails body) {
                if (body.getStatus().equalsIgnoreCase(AppUtils.successStatus)) {
                   AppUtils.showToast(getActivity(), body.getMessage());

                    EmployeeDetails.Data data = body.getData();
                    profileDetails = data.getProfile();
                    cardDetails = data.getCard();
                    jobDetails = data.getJob();
                    bankDetails = data.getBank();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //
            }
        }));
    }

    @Override
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
    }
}