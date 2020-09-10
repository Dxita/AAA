package cdac.org.anganvadistaffutility.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeDetails {

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

    public static class Job implements Parcelable {

        @SerializedName("employee status")
        @Expose
        private String employeeStatus;
        @SerializedName("date_of_joining")
        @Expose
        private String dateOfJoining;
        @SerializedName("designation name english")
        @Expose
        private String designationNameEnglish;
        @SerializedName("designation name hindi")
        @Expose
        private Object designationNameHindi;
        @SerializedName("payslab amount")
        @Expose
        private String payslabAmount;
        @SerializedName("educational qualification")
        @Expose
        private String educationalQualification;

        protected Job(Parcel in) {
            employeeStatus = in.readString();
            dateOfJoining = in.readString();
            designationNameEnglish = in.readString();
            payslabAmount = in.readString();
            educationalQualification = in.readString();
        }

        public static final Creator<Job> CREATOR = new Creator<Job>() {
            @Override
            public Job createFromParcel(Parcel in) {
                return new Job(in);
            }

            @Override
            public Job[] newArray(int size) {
                return new Job[size];
            }
        };

        public String getEmployeeStatus() {
            return employeeStatus;
        }

        public void setEmployeeStatus(String employeeStatus) {
            this.employeeStatus = employeeStatus;
        }

        public String getDateOfJoining() {
            return dateOfJoining;
        }

        public void setDateOfJoining(String dateOfJoining) {
            this.dateOfJoining = dateOfJoining;
        }

        public String getDesignationNameEnglish() {
            return designationNameEnglish;
        }

        public void setDesignationNameEnglish(String designationNameEnglish) {
            this.designationNameEnglish = designationNameEnglish;
        }

        public Object getDesignationNameHindi() {
            return designationNameHindi;
        }

        public void setDesignationNameHindi(Object designationNameHindi) {
            this.designationNameHindi = designationNameHindi;
        }

        public String getPayslabAmount() {
            return payslabAmount;
        }

        public void setPayslabAmount(String payslabAmount) {
            this.payslabAmount = payslabAmount;
        }

        public String getEducationalQualification() {
            return educationalQualification;
        }

        public void setEducationalQualification(String educationalQualification) {
            this.educationalQualification = educationalQualification;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

            parcel.writeString(employeeStatus);
            parcel.writeString(dateOfJoining);
            parcel.writeString(designationNameEnglish);
            parcel.writeString(payslabAmount);
            parcel.writeString(educationalQualification);

        }
    }

    public static class Profile implements Parcelable {

        @SerializedName("employee name english")
        @Expose
        private String employeeNameEnglish;
        @SerializedName("employee name hindi")
        @Expose
        private Object employeeNameHindi;
        @SerializedName("date of birth")
        @Expose
        private String dateOfBirth;
        @SerializedName("awcid")
        @Expose
        private String awcid;
        @SerializedName("moblie number")
        @Expose
        private String moblieNumber;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("husband father name")
        @Expose
        private String husbandFatherName;

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

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getAwcid() {
            return awcid;
        }

        public void setAwcid(String awcid) {
            this.awcid = awcid;
        }

        public String getMoblieNumber() {
            return moblieNumber;
        }

        public void setMoblieNumber(String moblieNumber) {
            this.moblieNumber = moblieNumber;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getHusbandFatherName() {
            return husbandFatherName;
        }

        public void setHusbandFatherName(String husbandFatherName) {
            this.husbandFatherName = husbandFatherName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeString(employeeNameEnglish);
            dest.writeString((String) employeeNameHindi);
            dest.writeString(dateOfBirth);
            dest.writeString(awcid);
            dest.writeString(moblieNumber);
            dest.writeString(category);
            dest.writeString(husbandFatherName);
        }

        private Profile(Parcel in) {
            this.employeeNameEnglish = in.readString();
            this.employeeNameHindi = in.readString();
            this.dateOfBirth = in.readString();
            this.awcid = in.readString();
            this.moblieNumber = in.readString();
            this.category = in.readString();
            this.husbandFatherName = in.readString();
        }

        public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
            @Override
            public Profile createFromParcel(Parcel source) {
                return new Profile(source);
            }

            @Override
            public Profile[] newArray(int size) {
                return new Profile[size];
            }
        };
    }

    public static class Data {

        @SerializedName("profile")
        @Expose
        private Profile profile;
        @SerializedName("card")
        @Expose
        private Card card;
        @SerializedName("job")
        @Expose
        private Job job;
        @SerializedName("bank")
        @Expose
        private Bank bank ;

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public Card getCard() {
            return card;
        }

        public void setCard(Card card) {
            this.card = card;
        }

        public Job getJob() {
            return job;
        }

        public void setJob(Job job) {
            this.job = job;
        }

        public Bank getBank() {
            return bank;
        }

        public void setBank(Bank bank) {
            this.bank = bank;
        }
    }

    public static class Card implements Parcelable{

        @SerializedName("pan number")
        @Expose
        private String panNumber;
        @SerializedName("bhamashah number")
        @Expose
        private String bhamashahNumber;
        @SerializedName("aadhar_number")
        @Expose
        private String aadharNumber;
        @SerializedName("ashaid")
        @Expose
        private String ashaid;
        @SerializedName("janaadhar number")
        @Expose
        private Object janaadharNumber;
        @SerializedName("janaadhar self number")
        @Expose
        private Object janaadharSelfNumber;

        protected Card(Parcel in) {
            panNumber = in.readString();
            bhamashahNumber = in.readString();
            aadharNumber = in.readString();
            ashaid = in.readString();
        }

        public static final Creator<Card> CREATOR = new Creator<Card>() {
            @Override
            public Card createFromParcel(Parcel in) {
                return new Card(in);
            }

            @Override
            public Card[] newArray(int size) {
                return new Card[size];
            }
        };

        public String getPanNumber() {
            return panNumber;
        }

        public void setPanNumber(String panNumber) {
            this.panNumber = panNumber;
        }

        public String getBhamashahNumber() {
            return bhamashahNumber;
        }

        public void setBhamashahNumber(String bhamashahNumber) {
            this.bhamashahNumber = bhamashahNumber;
        }

        public String getAadharNumber() {
            return aadharNumber;
        }

        public void setAadharNumber(String aadharNumber) {
            this.aadharNumber = aadharNumber;
        }

        public String getAshaid() {
            return ashaid;
        }

        public void setAshaid(String ashaid) {
            this.ashaid = ashaid;
        }

        public Object getJanaadharNumber() {
            return janaadharNumber;
        }

        public void setJanaadharNumber(Object janaadharNumber) {
            this.janaadharNumber = janaadharNumber;
        }

        public Object getJanaadharSelfNumber() {
            return janaadharSelfNumber;
        }

        public void setJanaadharSelfNumber(Object janaadharSelfNumber) {
            this.janaadharSelfNumber = janaadharSelfNumber;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(panNumber);
            parcel.writeString(bhamashahNumber);
            parcel.writeString(aadharNumber);
            parcel.writeString(ashaid);



        }
    }

    public static class Bank implements Parcelable {

        @SerializedName("branch code")
        @Expose
        private String branchCode;
        @SerializedName("bank name")
        @Expose
        private String bankName;
        @SerializedName("ifsc code")
        @Expose
        private String ifscCode;
        @SerializedName("branch address")
        @Expose
        private String branchAddress;
        @SerializedName("bank account_number")
        @Expose
        private String bankAccountNumber;
        @SerializedName("aadhar attached_bank_account_number")
        @Expose
        private Object aadharAttachedBankAccountNumber;

        protected Bank(Parcel in) {
            branchCode = in.readString();
            bankName = in.readString();
            ifscCode = in.readString();
            branchAddress = in.readString();
            bankAccountNumber = in.readString();
        }

        public static final Creator<Bank> CREATOR = new Creator<Bank>() {
            @Override
            public Bank createFromParcel(Parcel in) {
                return new Bank(in);
            }

            @Override
            public Bank[] newArray(int size) {
                return new Bank[size];
            }
        };

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getIfscCode() {
            return ifscCode;
        }

        public void setIfscCode(String ifscCode) {
            this.ifscCode = ifscCode;
        }

        public String getBranchAddress() {
            return branchAddress;
        }

        public void setBranchAddress(String branchAddress) {
            this.branchAddress = branchAddress;
        }

        public String getBankAccountNumber() {
            return bankAccountNumber;
        }

        public void setBankAccountNumber(String bankAccountNumber) {
            this.bankAccountNumber = bankAccountNumber;
        }

        public Object getAadharAttachedBankAccountNumber() {
            return aadharAttachedBankAccountNumber;
        }

        public void setAadharAttachedBankAccountNumber(Object aadharAttachedBankAccountNumber) {
            this.aadharAttachedBankAccountNumber = aadharAttachedBankAccountNumber;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

            parcel.writeString(branchCode);
            parcel.writeString(bankName);
            parcel.writeString(ifscCode);
            parcel.writeString(branchAddress);
            parcel.writeString(bankAccountNumber);
        }
    }
}
