package cdac.org.anganvadistaffutility.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentDetails {

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


    public static class Data implements Parcelable {

        @SerializedName("empdata")
        @Expose
        private List<Empdatum> empdata = null;

        protected Data(Parcel in) {
            empdata = in.createTypedArrayList(Empdatum.CREATOR);
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public List<Empdatum> getEmpdata() {
            return empdata;
        }

        public void setEmpdata(List<Empdatum> empdata) {
            this.empdata = empdata;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {


            parcel.writeTypedList(empdata);
        }
    }

        public static class Empdatum implements Parcelable {

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

            protected Empdatum(Parcel in) {
                employeeId = in.readString();
                employeeNameEnglish = in.readString();
                subbillid = in.readString();
                subbillname = in.readString();
                billnameid = in.readString();
                billname = in.readString();
                month = in.readString();
                absentDays = in.readString();
                salary = in.readString();
            }

            public static final Creator<Empdatum> CREATOR = new Creator<Empdatum>() {
                @Override
                public Empdatum createFromParcel(Parcel in) {
                    return new Empdatum(in);
                }

                @Override
                public Empdatum[] newArray(int size) {
                    return new Empdatum[size];
                }
            };

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(employeeId);
                parcel.writeString(employeeNameEnglish);
                parcel.writeString(subbillid);
                parcel.writeString(subbillname);
                parcel.writeString(billnameid);
                parcel.writeString(billname);
                parcel.writeString(month);
                parcel.writeString(absentDays);
                parcel.writeString(salary);

            }
        }
    }


