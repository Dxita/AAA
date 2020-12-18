package cdac.org.anganvadistaffutility.admin.adapter;

import android.content.Context;

import java.util.List;

import cdac.org.anganvadistaffutility.admin.data.DistrictWiseEmployeeDetails;

public class DistrictWiseInfrastructuretAdapter {
    private final Context mContext;
    protected List<DistrictWiseEmployeeDetails> districtWiseEmployeeDetailsList;
    protected DistrictWisePieChartAdapter.ItemClickListener mListener;
    protected int fixListSize = 7;
    private int totalListSize = 0;

    public DistrictWiseInfrastructuretAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public class ItemClickListener {
    }
}
