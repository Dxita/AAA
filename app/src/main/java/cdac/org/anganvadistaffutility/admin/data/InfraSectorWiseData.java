package cdac.org.anganvadistaffutility.admin.data;

public class InfraSectorWiseData implements  Comparable<InfraSectorWiseData> {

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


    public String getSecnamee() {
        return secnamee;
    }

    public void setSecnamee(String secnamee) {
        this.secnamee = secnamee;
    }

    String secnamee;


    public String getSectorid() {
        return sectorid;
    }

    public void setSectorid(String sectorid) {
        this.sectorid = sectorid;
    }

    String sectorid;


    @Override
    public int compareTo(InfraSectorWiseData infrasDetailSectorData) {
        return this.getSectorid().compareTo(infrasDetailSectorData.getSectorid());
    }
}
