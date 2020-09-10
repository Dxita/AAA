package cdac.org.anganvadistaffutility.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cdac.org.anganvadistaffutility.R;
import cdac.org.anganvadistaffutility.activity.HomeActivity;
import cdac.org.anganvadistaffutility.activity.JobActivity;
import cdac.org.anganvadistaffutility.activity.PaymentActivity;
import cdac.org.anganvadistaffutility.activity.ProfileActivity;
import cdac.org.anganvadistaffutility.activity.VerifyOTPActivity;
import cdac.org.anganvadistaffutility.data.EmployeeDetails;
import cdac.org.anganvadistaffutility.retrofit.ApiInterface;
import cdac.org.anganvadistaffutility.retrofit.ApiServiceOperator;
import cdac.org.anganvadistaffutility.retrofit.ApiUtils;
import cdac.org.anganvadistaffutility.utils.AppUtils;
import retrofit2.Call;


public class HomeFragment extends Fragment implements View.OnClickListener {

    CardView cardView,job_card, bank_card, card, payment_card;

    EmployeeDetails.Profile profileDetails;
    EmployeeDetails.Job jobDetails;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);

        cardView=root.findViewById(R.id.profile_card);
        job_card=root.findViewById(R.id.job_card);
        bank_card=root.findViewById(R.id.bank_card);
        payment_card=root.findViewById(R.id.payment_card);
        card=root.findViewById(R.id.card);

        cardView.setOnClickListener(this);
        job_card.setOnClickListener(this);
        payment_card.setOnClickListener(this);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (AppUtils.isNetworkConnected(getActivity())) {
            getEmployeeData();
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
                    EmployeeDetails.Card cardDetails = data.getCard();
                    jobDetails = data.getJob();
                    EmployeeDetails.Bank bankDetails = data.getBank();



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
        switch (view.getId())
        {
            case R.id.profile_card:
                startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("profile_details", profileDetails));

                break;
            case R.id.job_card:
                startActivity(new Intent(getActivity(), JobActivity.class).putExtra("job_details", jobDetails));
                break;
            case R.id.payment_card:
                startActivity(new Intent(getActivity(), PaymentActivity.class));
                break;
        }

    }
}