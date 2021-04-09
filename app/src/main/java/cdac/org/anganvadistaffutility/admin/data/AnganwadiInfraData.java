package cdac.org.anganvadistaffutility.admin.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnganwadiInfraData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("infradata")
        @Expose
        private List<Infradatum> infradata = null;

        public List<Infradatum> getInfradata() {
            return infradata;
        }

        public void setInfradata(List<Infradatum> infradata) {
            this.infradata = infradata;
        }


        public static class Infradatum {

            @SerializedName("count")
            @Expose
            private String count;
            @SerializedName("tid_infra_namee")
            @Expose
            private String tidInfraNamee;
            @SerializedName("tid_infra_detail_id")
            @Expose
            private String tidInfraDetailId;
            @SerializedName("secnamee")
            @Expose
            private String secnamee;
            @SerializedName("sectorid")
            @Expose
            private String sectorid;
            @SerializedName("pawcid")
            @Expose
            private String pawcid;
            @SerializedName("awcnamee")
            @Expose
            private String awcnamee;
            @SerializedName("awcnameh")
            @Expose
            private String awcnameh;
            @SerializedName("emp_name")
            @Expose
            private String empName;
            @SerializedName("emp_nameh")
            @Expose
            private String empNameh;
            @SerializedName("emp_mob")
            @Expose
            private String empMob;
            @SerializedName("projectcode")
            @Expose
            private String projectcode;
            @SerializedName("projectname")
            @Expose
            private String projectname;
            @SerializedName("projectnameh")
            @Expose
            private String projectnameh;
            @SerializedName("projectincharge")
            @Expose
            private String projectincharge;
            @SerializedName("emailadd")
            @Expose
            private String emailadd;
            @SerializedName("mobileno")
            @Expose
            private String mobileno;

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

            public String getTidInfraDetailId() {
                return tidInfraDetailId;
            }

            public void setTidInfraDetailId(String tidInfraDetailId) {
                this.tidInfraDetailId = tidInfraDetailId;
            }

            public String getSecnamee() {
                return secnamee;
            }

            public void setSecnamee(String secnamee) {
                this.secnamee = secnamee;
            }

            public String getSectorid() {
                return sectorid;
            }

            public void setSectorid(String sectorid) {
                this.sectorid = sectorid;
            }

            public String getPawcid() {
                return pawcid;
            }

            public void setPawcid(String pawcid) {
                this.pawcid = pawcid;
            }

            public String getAwcnamee() {
                return awcnamee;
            }

            public void setAwcnamee(String awcnamee) {
                this.awcnamee = awcnamee;
            }

            public String getAwcnameh() {
                return awcnameh;
            }

            public void setAwcnameh(String awcnameh) {
                this.awcnameh = awcnameh;
            }

            public String getEmpName() {
                return empName;
            }

            public void setEmpName(String empName) {
                this.empName = empName;
            }

            public String getEmpNameh() {
                return empNameh;
            }

            public void setEmpNameh(String empNameh) {
                this.empNameh = empNameh;
            }

            public String getEmpMob() {
                return empMob;
            }

            public void setEmpMob(String empMob) {
                this.empMob = empMob;
            }

            public String getProjectcode() {
                return projectcode;
            }

            public void setProjectcode(String projectcode) {
                this.projectcode = projectcode;
            }

            public String getProjectname() {
                return projectname;
            }

            public void setProjectname(String projectname) {
                this.projectname = projectname;
            }

            public String getProjectnameh() {
                return projectnameh;
            }

            public void setProjectnameh(String projectnameh) {
                this.projectnameh = projectnameh;
            }

            public String getProjectincharge() {
                return projectincharge;
            }

            public void setProjectincharge(String projectincharge) {
                this.projectincharge = projectincharge;
            }

            public String getEmailadd() {
                return emailadd;
            }

            public void setEmailadd(String emailadd) {
                this.emailadd = emailadd;
            }

            public String getMobileno() {
                return mobileno;
            }

            public void setMobileno(String mobileno) {
                this.mobileno = mobileno;
            }

        }
    }
}
