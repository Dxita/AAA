package cdac.org.anganvadistaffutility.admin.data;

public class InfraProjectWiseData implements  Comparable<InfraProjectWiseData> {

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


    public String getProjectname() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
    public int compareTo(InfraProjectWiseData infraProjectWiseData) {
        return this.getProjectcode().compareTo(infraProjectWiseData.getProjectcode());
    }
}
