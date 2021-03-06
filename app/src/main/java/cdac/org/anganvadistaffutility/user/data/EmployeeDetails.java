package cdac.org.anganvadistaffutility.user.data;

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
        private String designationNameHindi;
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
            designationNameHindi = in.readString();
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

        public String getDesignationNameHindi() {
            return designationNameHindi;
        }

        public void setDesignationNameHindi(String designationNameHindi) {
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
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(employeeStatus);
            dest.writeString(dateOfJoining);
            dest.writeString(designationNameEnglish);
            dest.writeString(designationNameHindi);
            dest.writeString(payslabAmount);
            dest.writeString(educationalQualification);
        }
    }

    public static class Profile implements Parcelable {

        @SerializedName("district english")
        @Expose
        private String districtEnglish;
        @SerializedName("district hindi")
        @Expose
        private String districtHindi;
        @SerializedName("project english")
        @Expose
        private String projectEnglish;
        @SerializedName("project hindi")
        @Expose
        private String projectHindi;
        @SerializedName("sector english")
        @Expose
        private String sectorEnglish;
        @SerializedName("sector hindi")
        @Expose
        private String sectorHindi;
        @SerializedName("employee name english")
        @Expose
        private String employeeNameEnglish;
        @SerializedName("employee name hindi")
        @Expose
        private String employeeNameHindi;
        @SerializedName("date of birth")
        @Expose
        private String dateOfBirth;
        @SerializedName("awcid")
        @Expose
        private String awcid;
        @SerializedName("awc_name_english")
        @Expose
        private String awcNameEnglish;
        @SerializedName("awc_name_hindi")
        @Expose
        private String awcNameHindi;
        @SerializedName("awc_address")
        @Expose
        private String awcAddress;
        @SerializedName("moblie number")
        @Expose
        private String moblieNumber;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("category_hindi")
        @Expose
        private String categoryHindi;
        @SerializedName("husband father name")
        @Expose
        private String husbandFatherName;

        protected Profile(Parcel in) {
            districtEnglish = in.readString();
            districtHindi = in.readString();
            projectEnglish = in.readString();
            projectHindi = in.readString();
            sectorEnglish = in.readString();
            sectorHindi = in.readString();
            employeeNameEnglish = in.readString();
            employeeNameHindi = in.readString();
            dateOfBirth = in.readString();
            awcid = in.readString();
            awcNameEnglish = in.readString();
            awcNameHindi = in.readString();
            awcAddress = in.readString();
            moblieNumber = in.readString();
            category = in.readString();
            categoryHindi = in.readString();
            husbandFatherName = in.readString();
        }

        public static final Creator<Profile> CREATOR = new Creator<Profile>() {
            @Override
            public Profile createFromParcel(Parcel in) {
                return new Profile(in);
            }

            @Override
            public Profile[] newArray(int size) {
                return new Profile[size];
            }
        };

        public String getDistrictEnglish() {
            return districtEnglish;
        }

        public void setDistrictEnglish(String districtEnglish) {
            this.districtEnglish = districtEnglish;
        }

        public String getDistrictHindi() {
            return districtHindi;
        }

        public void setDistrictHindi(String districtHindi) {
            this.districtHindi = districtHindi;
        }

        public String getProjectEnglish() {
            return projectEnglish;
        }

        public void setProjectEnglish(String projectEnglish) {
            this.projectEnglish = projectEnglish;
        }

        public String getProjectHindi() {
            return projectHindi;
        }

        public void setProjectHindi(String projectHindi) {
            this.projectHindi = projectHindi;
        }

        public String getSectorEnglish() {
            return sectorEnglish;
        }

        public void setSectorEnglish(String sectorEnglish) {
            this.sectorEnglish = sectorEnglish;
        }

        public String getSectorHindi() {
            return sectorHindi;
        }

        public void setSectorHindi(String sectorHindi) {
            this.sectorHindi = sectorHindi;
        }

        public String getEmployeeNameEnglish() {
            return employeeNameEnglish;
        }

        public void setEmployeeNameEnglish(String employeeNameEnglish) {
            this.employeeNameEnglish = employeeNameEnglish;
        }

        public String getEmployeeNameHindi() {
            return employeeNameHindi;
        }

        public void setEmployeeNameHindi(String employeeNameHindi) {
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

        public String getAwcNameEnglish() {
            return awcNameEnglish;
        }

        public void setAwcNameEnglish(String awcNameEnglish) {
            this.awcNameEnglish = awcNameEnglish;
        }

        public String getAwcNameHindi() {
            return awcNameHindi;
        }

        public void setAwcNameHindi(String awcNameHindi) {
            this.awcNameHindi = awcNameHindi;
        }

        public String getAwcAddress() {
            return awcAddress;
        }

        public void setAwcAddress(String awcAddress) {
            this.awcAddress = awcAddress;
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

        public String getCategoryHindi() {
            return categoryHindi;
        }

        public void setCategoryHindi(String categoryHindi) {
            this.categoryHindi = categoryHindi;
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
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(districtEnglish);
            dest.writeString(districtHindi);
            dest.writeString(projectEnglish);
            dest.writeString(projectHindi);
            dest.writeString(sectorEnglish);
            dest.writeString(sectorHindi);
            dest.writeString(employeeNameEnglish);
            dest.writeString(employeeNameHindi);
            dest.writeString(dateOfBirth);
            dest.writeString(awcid);
            dest.writeString(awcNameEnglish);
            dest.writeString(awcNameHindi);
            dest.writeString(awcAddress);
            dest.writeString(moblieNumber);
            dest.writeString(category);
            dest.writeString(categoryHindi);
            dest.writeString(husbandFatherName);
        }
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
        private Bank bank;

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
        private String janaadharNumber;
        @SerializedName("janaadhar self number")
        @Expose
        private String janaadharSelfNumber;

        protected Card(Parcel in) {
            panNumber = in.readString();
            bhamashahNumber = in.readString();
            aadharNumber = in.readString();
            ashaid = in.readString();
            janaadharNumber = in.readString();
            janaadharSelfNumber = in.readString();
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

        public String getJanaadharNumber() {
            return janaadharNumber;
        }

        public void setJanaadharNumber(String janaadharNumber) {
            this.janaadharNumber = janaadharNumber;
        }

        public String getJanaadharSelfNumber() {
            return janaadharSelfNumber;
        }

        public void setJanaadharSelfNumber(String janaadharSelfNumber) {
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
            parcel.writeString(janaadharNumber);
            parcel.writeString(janaadharSelfNumber);
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
        private String aadharAttachedBankAccountNumber;

        protected Bank(Parcel in) {
            branchCode = in.readString();
            bankName = in.readString();
            ifscCode = in.readString();
            branchAddress = in.readString();
            bankAccountNumber = in.readString();
            aadharAttachedBankAccountNumber = in.readString();
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

        public String getAadharAttachedBankAccountNumber() {
            return aadharAttachedBankAccountNumber;
        }

        public void setAadharAttachedBankAccountNumber(String aadharAttachedBankAccountNumber) {
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
            parcel.writeString(aadharAttachedBankAccountNumber);
        }
    }
}
