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

    public String getProjectnameh() {
        return projectNameh;
    }

    public void setProjectNameh(String projectNameh) {
        this.projectNameh = projectNameh;
    }

    String projectNameh;


    public String getProjectcode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    String projectCode;

    public String getCdpoName() {
        return cdpoName;
    }

    public void setCdpoName(String cdpoName) {
        this.cdpoName = cdpoName;
    }

    String cdpoName;


    public String getCdpoNumber() {
        return cdpoNumber;
    }

    public void setCdpoNumber(String cdpoNumber) {
        this.cdpoNumber = cdpoNumber;
    }

    String cdpoNumber;


    public String getCdpoEmail() {
        return cdpoEmail;
    }

    public void setCdpoEmail(String cdpoEmail) {
        this.cdpoEmail = cdpoEmail;
    }

    String cdpoEmail;


    @Override
    public int compareTo(InfraProjectWiseData infraProjectWiseData) {
        return this.getProjectcode().compareTo(infraProjectWiseData.getProjectcode());
    }
}
