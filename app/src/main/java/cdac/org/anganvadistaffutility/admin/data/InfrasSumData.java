package cdac.org.anganvadistaffutility.admin.data;

public class InfrasSumData implements Comparable<InfrasSumData> {

    String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


    String infraSum;

    public String getInfraSum() {
        return infraSum;
    }

    public void setInfraSum(String infraCount) {
        this.infraSum = infraCount;
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

    @Override
    public int compareTo(InfrasSumData infrasSumData) {
        return this.getInfraDetailID().compareTo(infrasSumData.infraDetailID);
    }
}


