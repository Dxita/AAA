package cdac.org.anganvadistaffutility.admin.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictWiseInfraData  implements Comparable<DistrictWiseInfraData>{


    private String count;

    private String tidInfraNamee;

    private String timInfraNamee;

    private String tidInfraDetailId;

    private String district;

    private String distid;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTidInfraNamee() {
        return tidInfraNamee;
    }

    public void setTidInfraNamee(String tidInfraNamee) {
        this.tidInfraNamee = tidInfraNamee;
    }

    public String getTimInfraNamee() {
        return timInfraNamee;
    }

    public void setTimInfraNamee(String timInfraNamee) {
        this.timInfraNamee = timInfraNamee;
    }

    public String getTidInfraDetailId() {
        return tidInfraDetailId;
    }

    public void setTidInfraDetailId(String tidInfraDetailId) {
        this.tidInfraDetailId = tidInfraDetailId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistid() {
        return distid;
    }

    public void setDistid(String distid) {
        this.distid = distid;
    }



    @Override
    public int compareTo(DistrictWiseInfraData districtWiseInfraData) {

        return this.getTidInfraNamee().compareTo(districtWiseInfraData.getTidInfraNamee());
    }
}
