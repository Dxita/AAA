package cdac.org.anganvadistaffutility.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisteredUserKPI {

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
        @SerializedName("empdata")
        @Expose
        private List<Empdatum> empdata = null;

        public List<Empdatum> getEmpdata() {
            return empdata;
        }

        public void setEmpdata(List<Empdatum> empdata) {
            this.empdata = empdata;
        }
    }

    public static class Empdatum {

        @SerializedName("total_employees")
        @Expose
        private String totalEmployees;
        @SerializedName("registerred_employee")
        @Expose
        private String registerredEmployee;
        @SerializedName("a_month")
        @Expose
        private String aMonth;
        @SerializedName("a_year")
        @Expose
        private String aYear;
        @SerializedName("monthyear")
        @Expose
        private String monthyear;

        public String getTotalEmployees() {
            return totalEmployees;
        }

        public void setTotalEmployees(String totalEmployees) {
            this.totalEmployees = totalEmployees;
        }

        public String getRegisterredEmployee() {
            return registerredEmployee;
        }

        public void setRegisterredEmployee(String registerredEmployee) {
            this.registerredEmployee = registerredEmployee;
        }

        public String getAMonth() {
            return aMonth;
        }

        public void setAMonth(String aMonth) {
            this.aMonth = aMonth;
        }

        public String getAYear() {
            return aYear;
        }

        public void setAYear(String aYear) {
            this.aYear = aYear;
        }

        public String getMonthyear() {
            return monthyear;
        }

        public void setMonthyear(String monthyear) {
            this.monthyear = monthyear;
        }
    }
}
