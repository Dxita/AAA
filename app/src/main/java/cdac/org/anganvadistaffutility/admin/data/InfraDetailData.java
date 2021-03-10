package cdac.org.anganvadistaffutility.admin.data;

public class InfraDetailData implements Comparable<InfraDetailData>{

    String infraCount;

    public String getInfraCount() {
        return infraCount;
    }

    public void setInfraCount(String infraCount) {
        this.infraCount = infraCount;
    }

    public String getInfraName() {
        return infraName;
    }

    public void setInfraName(String infraName) {
        this.infraName = infraName;
    }

    String infraName;

    public String getInfraDetailID() {
        return infraDetailID;
    }

    public void setInfraDetailID(String infraDetailID) {
        this.infraDetailID = infraDetailID;
    }

    String infraDetailID;


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    String district;


    public String getDistricth() {
        return districth;
    }

    public void setDistricth(String districth) {
        this.districth = districth;
    }

    String districth;


    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    String districtID;


    public String getProjectname() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = district;
    }

    String projectName;


    public String getProjectcode() {
        return projectName;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    String projectCode;

    @Override
    public int compareTo(InfraDetailData infraDetailData) {
        return this.getDistrict().compareTo(infraDetailData.getDistrict());
    }
}
