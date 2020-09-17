package cdac.org.anganvadistaffutility.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentModel {

    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("employee_name_english")
    @Expose
    private String employeeNameEnglish;
    @SerializedName("employee_name_hindi")
    @Expose
    private Object employeeNameHindi;
    @SerializedName("subbillid")
    @Expose
    private String subbillid;
    @SerializedName("subbillname")
    @Expose
    private String subbillname;
    @SerializedName("billnameid")
    @Expose
    private String billnameid;
    @SerializedName("billname")
    @Expose
    private String billname;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("absent_days")
    @Expose
    private String absentDays;
    @SerializedName("salary")
    @Expose
    private String salary;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeNameEnglish() {
        return employeeNameEnglish;
    }

    public void setEmployeeNameEnglish(String employeeNameEnglish) {
        this.employeeNameEnglish = employeeNameEnglish;
    }

    public Object getEmployeeNameHindi() {
        return employeeNameHindi;
    }

    public void setEmployeeNameHindi(Object employeeNameHindi) {
        this.employeeNameHindi = employeeNameHindi;
    }

    public String getSubbillid() {
        return subbillid;
    }

    public void setSubbillid(String subbillid) {
        this.subbillid = subbillid;
    }

    public String getSubbillname() {
        return subbillname;
    }

    public void setSubbillname(String subbillname) {
        this.subbillname = subbillname;
    }

    public String getBillnameid() {
        return billnameid;
    }

    public void setBillnameid(String billnameid) {
        this.billnameid = billnameid;
    }

    public String getBillname() {
        return billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(String absentDays) {
        this.absentDays = absentDays;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

}