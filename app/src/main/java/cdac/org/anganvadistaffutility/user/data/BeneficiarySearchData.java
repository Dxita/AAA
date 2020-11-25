package cdac.org.anganvadistaffutility.user.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeneficiarySearchData {

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

        @SerializedName("data_found")
        @Expose
        private List<DataFound> dataFound = null;

        public List<DataFound> getDataFound() {
            return dataFound;
        }

        public void setDataFound(List<DataFound> dataFound) {
            this.dataFound = dataFound;
        }
    }

        public static class DataFound implements Parcelable {

            @SerializedName("tjm_mother_id")
            @Expose
            private String tjmMotherId;
            @SerializedName("tjm_dist_id")
            @Expose
            private Object tjmDistId;
            @SerializedName("tjm_project_id")
            @Expose
            private Object tjmProjectId;
            @SerializedName("tjm_sector_id")
            @Expose
            private Object tjmSectorId;
            @SerializedName("tjm_awc_id")
            @Expose
            private String tjmAwcId;
            @SerializedName("tjm_anm_id")
            @Expose
            private String tjmAnmId;
            @SerializedName("tjm_asha_id")
            @Expose
            private String tjmAshaId;
            @SerializedName("tjm_name")
            @Expose
            private String tjmName;
            @SerializedName("tjm_address")
            @Expose
            private String tjmAddress;
            @SerializedName("tjm_age")
            @Expose
            private String tjmAge;
            @SerializedName("tjm_lmc_date")
            @Expose
            private String tjmLmcDate;
            @SerializedName("tjm_nfsa_eligibility")
            @Expose
            private String tjmNfsaEligibility;
            @SerializedName("tjm_registration_date")
            @Expose
            private String tjmRegistrationDate;
            @SerializedName("tjm_bpl")
            @Expose
            private String tjmBpl;
            @SerializedName("tjm_cast_english")
            @Expose
            private String tjmCastEnglish;
            @SerializedName("tjm_cast_category")
            @Expose
            private String tjmCastCategory;
            @SerializedName("tjm_ghumantu")
            @Expose
            private String tjmGhumantu;
            @SerializedName("tjm_live_child")
            @Expose
            private String tjmLiveChild;
            @SerializedName("tjm_mobileno")
            @Expose
            private String tjmMobileno;
            @SerializedName("tjm_husbname")
            @Expose
            private String tjmHusbname;
            @SerializedName("tjm_accountno")
            @Expose
            private String tjmAccountno;
            @SerializedName("tjm_entry_date")
            @Expose
            private String tjmEntryDate;
            @SerializedName("tjm_ifsc_code")
            @Expose
            private String tjmIfscCode;
            @SerializedName("tjm_rationcard_no")
            @Expose
            private String tjmRationcardNo;
            @SerializedName("tjm_villageauto_id")
            @Expose
            private String tjmVillageautoId;
            @SerializedName("tjm_account_name")
            @Expose
            private String tjmAccountName;
            @SerializedName("tjm_ancreg_id")
            @Expose
            private String tjmAncregId;
            @SerializedName("tjm_is_husband")
            @Expose
            private String tjmIsHusband;
            @SerializedName("tjm_bplcard_no")
            @Expose
            private String tjmBplcardNo;
            @SerializedName("tjm_height")
            @Expose
            private String tjmHeight;
            @SerializedName("tjm_domicile_status")
            @Expose
            private String tjmDomicileStatus;
            @SerializedName("tjm_delivery_date")
            @Expose
            private Object tjmDeliveryDate;
            @SerializedName("tjm_last_modified_date")
            @Expose
            private String tjmLastModifiedDate;
            @SerializedName("tjm_user_id")
            @Expose
            private Object tjmUserId;
            @SerializedName("tjm_ip_address")
            @Expose
            private Object tjmIpAddress;
            @SerializedName("tjm_verifyflag")
            @Expose
            private String tjmVerifyflag;
            @SerializedName("tjm_mother_type")
            @Expose
            private String tjmMotherType;
            @SerializedName("tjm_cast_hindi")
            @Expose
            private String tjmCastHindi;
            @SerializedName("tjm_cron_entry_date")
            @Expose
            private String tjmCronEntryDate;
            @SerializedName("tbm_asha_auto_id")
            @Expose
            private String tbmAshaAutoId;
            @SerializedName("tbm_mother_id")
            @Expose
            private String tbmMotherId;
            @SerializedName("tbm_bhamashah_id")
            @Expose
            private String tbmBhamashahId;
            @SerializedName("tbm_member_id")
            @Expose
            private String tbmMemberId;
            @SerializedName("tbm_aadhar_no")
            @Expose
            private String tbmAadharNo;
            @SerializedName("tbm_validate_flag")
            @Expose
            private String tbmValidateFlag;
            @SerializedName("tbm_bhamashah_ack_id")
            @Expose
            private String tbmBhamashahAckId;
            @SerializedName("tbm_janaadhaar_family")
            @Expose
            private String tbmJanaadhaarFamily;
            @SerializedName("tbm_janaadhar")
            @Expose
            private String tbmJanaadhar;

            protected DataFound(Parcel in) {
                tjmMotherId = in.readString();
                tjmAwcId = in.readString();
                tjmAnmId = in.readString();
                tjmAshaId = in.readString();
                tjmName = in.readString();
                tjmAddress = in.readString();
                tjmAge = in.readString();
                tjmLmcDate = in.readString();
                tjmNfsaEligibility = in.readString();
                tjmRegistrationDate = in.readString();
                tjmBpl = in.readString();
                tjmCastEnglish = in.readString();
                tjmCastCategory = in.readString();
                tjmGhumantu = in.readString();
                tjmLiveChild = in.readString();
                tjmMobileno = in.readString();
                tjmHusbname = in.readString();
                tjmAccountno = in.readString();
                tjmEntryDate = in.readString();
                tjmIfscCode = in.readString();
                tjmRationcardNo = in.readString();
                tjmVillageautoId = in.readString();
                tjmAccountName = in.readString();
                tjmAncregId = in.readString();
                tjmIsHusband = in.readString();
                tjmBplcardNo = in.readString();
                tjmHeight = in.readString();
                tjmDomicileStatus = in.readString();
                tjmLastModifiedDate = in.readString();
                tjmVerifyflag = in.readString();
                tjmMotherType = in.readString();
                tjmCastHindi = in.readString();
                tjmCronEntryDate = in.readString();
                tbmAshaAutoId = in.readString();
                tbmMotherId = in.readString();
                tbmBhamashahId = in.readString();
                tbmMemberId = in.readString();
                tbmAadharNo = in.readString();
                tbmValidateFlag = in.readString();
                tbmBhamashahAckId = in.readString();
                tbmJanaadhaarFamily = in.readString();
                tbmJanaadhar = in.readString();
            }

            public static final Creator<DataFound> CREATOR = new Creator<DataFound>() {
                @Override
                public DataFound createFromParcel(Parcel in) {
                    return new DataFound(in);
                }

                @Override
                public DataFound[] newArray(int size) {
                    return new DataFound[size];
                }
            };

            public String getTjmMotherId() {
                return tjmMotherId;
            }

            public void setTjmMotherId(String tjmMotherId) {
                this.tjmMotherId = tjmMotherId;
            }

            public Object getTjmDistId() {
                return tjmDistId;
            }

            public void setTjmDistId(Object tjmDistId) {
                this.tjmDistId = tjmDistId;
            }

            public Object getTjmProjectId() {
                return tjmProjectId;
            }

            public void setTjmProjectId(Object tjmProjectId) {
                this.tjmProjectId = tjmProjectId;
            }

            public Object getTjmSectorId() {
                return tjmSectorId;
            }

            public void setTjmSectorId(Object tjmSectorId) {
                this.tjmSectorId = tjmSectorId;
            }

            public String getTjmAwcId() {
                return tjmAwcId;
            }

            public void setTjmAwcId(String tjmAwcId) {
                this.tjmAwcId = tjmAwcId;
            }

            public String getTjmAnmId() {
                return tjmAnmId;
            }

            public void setTjmAnmId(String tjmAnmId) {
                this.tjmAnmId = tjmAnmId;
            }

            public String getTjmAshaId() {
                return tjmAshaId;
            }

            public void setTjmAshaId(String tjmAshaId) {
                this.tjmAshaId = tjmAshaId;
            }

            public String getTjmName() {
                return tjmName;
            }

            public void setTjmName(String tjmName) {
                this.tjmName = tjmName;
            }

            public String getTjmAddress() {
                return tjmAddress;
            }

            public void setTjmAddress(String tjmAddress) {
                this.tjmAddress = tjmAddress;
            }

            public String getTjmAge() {
                return tjmAge;
            }

            public void setTjmAge(String tjmAge) {
                this.tjmAge = tjmAge;
            }

            public String getTjmLmcDate() {
                return tjmLmcDate;
            }

            public void setTjmLmcDate(String tjmLmcDate) {
                this.tjmLmcDate = tjmLmcDate;
            }

            public String getTjmNfsaEligibility() {
                return tjmNfsaEligibility;
            }

            public void setTjmNfsaEligibility(String tjmNfsaEligibility) {
                this.tjmNfsaEligibility = tjmNfsaEligibility;
            }

            public String getTjmRegistrationDate() {
                return tjmRegistrationDate;
            }

            public void setTjmRegistrationDate(String tjmRegistrationDate) {
                this.tjmRegistrationDate = tjmRegistrationDate;
            }

            public String getTjmBpl() {
                return tjmBpl;
            }

            public void setTjmBpl(String tjmBpl) {
                this.tjmBpl = tjmBpl;
            }

            public String getTjmCastEnglish() {
                return tjmCastEnglish;
            }

            public void setTjmCastEnglish(String tjmCastEnglish) {
                this.tjmCastEnglish = tjmCastEnglish;
            }

            public String getTjmCastCategory() {
                return tjmCastCategory;
            }

            public void setTjmCastCategory(String tjmCastCategory) {
                this.tjmCastCategory = tjmCastCategory;
            }

            public String getTjmGhumantu() {
                return tjmGhumantu;
            }

            public void setTjmGhumantu(String tjmGhumantu) {
                this.tjmGhumantu = tjmGhumantu;
            }

            public String getTjmLiveChild() {
                return tjmLiveChild;
            }

            public void setTjmLiveChild(String tjmLiveChild) {
                this.tjmLiveChild = tjmLiveChild;
            }

            public String getTjmMobileno() {
                return tjmMobileno;
            }

            public void setTjmMobileno(String tjmMobileno) {
                this.tjmMobileno = tjmMobileno;
            }

            public String getTjmHusbname() {
                return tjmHusbname;
            }

            public void setTjmHusbname(String tjmHusbname) {
                this.tjmHusbname = tjmHusbname;
            }

            public String getTjmAccountno() {
                return tjmAccountno;
            }

            public void setTjmAccountno(String tjmAccountno) {
                this.tjmAccountno = tjmAccountno;
            }

            public String getTjmEntryDate() {
                return tjmEntryDate;
            }

            public void setTjmEntryDate(String tjmEntryDate) {
                this.tjmEntryDate = tjmEntryDate;
            }

            public String getTjmIfscCode() {
                return tjmIfscCode;
            }

            public void setTjmIfscCode(String tjmIfscCode) {
                this.tjmIfscCode = tjmIfscCode;
            }

            public String getTjmRationcardNo() {
                return tjmRationcardNo;
            }

            public void setTjmRationcardNo(String tjmRationcardNo) {
                this.tjmRationcardNo = tjmRationcardNo;
            }

            public String getTjmVillageautoId() {
                return tjmVillageautoId;
            }

            public void setTjmVillageautoId(String tjmVillageautoId) {
                this.tjmVillageautoId = tjmVillageautoId;
            }

            public String getTjmAccountName() {
                return tjmAccountName;
            }

            public void setTjmAccountName(String tjmAccountName) {
                this.tjmAccountName = tjmAccountName;
            }

            public String getTjmAncregId() {
                return tjmAncregId;
            }

            public void setTjmAncregId(String tjmAncregId) {
                this.tjmAncregId = tjmAncregId;
            }

            public String getTjmIsHusband() {
                return tjmIsHusband;
            }

            public void setTjmIsHusband(String tjmIsHusband) {
                this.tjmIsHusband = tjmIsHusband;
            }

            public String getTjmBplcardNo() {
                return tjmBplcardNo;
            }

            public void setTjmBplcardNo(String tjmBplcardNo) {
                this.tjmBplcardNo = tjmBplcardNo;
            }

            public String getTjmHeight() {
                return tjmHeight;
            }

            public void setTjmHeight(String tjmHeight) {
                this.tjmHeight = tjmHeight;
            }

            public String getTjmDomicileStatus() {
                return tjmDomicileStatus;
            }

            public void setTjmDomicileStatus(String tjmDomicileStatus) {
                this.tjmDomicileStatus = tjmDomicileStatus;
            }

            public Object getTjmDeliveryDate() {
                return tjmDeliveryDate;
            }

            public void setTjmDeliveryDate(Object tjmDeliveryDate) {
                this.tjmDeliveryDate = tjmDeliveryDate;
            }

            public String getTjmLastModifiedDate() {
                return tjmLastModifiedDate;
            }

            public void setTjmLastModifiedDate(String tjmLastModifiedDate) {
                this.tjmLastModifiedDate = tjmLastModifiedDate;
            }

            public Object getTjmUserId() {
                return tjmUserId;
            }

            public void setTjmUserId(Object tjmUserId) {
                this.tjmUserId = tjmUserId;
            }

            public Object getTjmIpAddress() {
                return tjmIpAddress;
            }

            public void setTjmIpAddress(Object tjmIpAddress) {
                this.tjmIpAddress = tjmIpAddress;
            }

            public String getTjmVerifyflag() {
                return tjmVerifyflag;
            }

            public void setTjmVerifyflag(String tjmVerifyflag) {
                this.tjmVerifyflag = tjmVerifyflag;
            }

            public String getTjmMotherType() {
                return tjmMotherType;
            }

            public void setTjmMotherType(String tjmMotherType) {
                this.tjmMotherType = tjmMotherType;
            }

            public String getTjmCastHindi() {
                return tjmCastHindi;
            }

            public void setTjmCastHindi(String tjmCastHindi) {
                this.tjmCastHindi = tjmCastHindi;
            }

            public String getTjmCronEntryDate() {
                return tjmCronEntryDate;
            }

            public void setTjmCronEntryDate(String tjmCronEntryDate) {
                this.tjmCronEntryDate = tjmCronEntryDate;
            }

            public String getTbmAshaAutoId() {
                return tbmAshaAutoId;
            }

            public void setTbmAshaAutoId(String tbmAshaAutoId) {
                this.tbmAshaAutoId = tbmAshaAutoId;
            }

            public String getTbmMotherId() {
                return tbmMotherId;
            }

            public void setTbmMotherId(String tbmMotherId) {
                this.tbmMotherId = tbmMotherId;
            }

            public String getTbmBhamashahId() {
                return tbmBhamashahId;
            }

            public void setTbmBhamashahId(String tbmBhamashahId) {
                this.tbmBhamashahId = tbmBhamashahId;
            }

            public String getTbmMemberId() {
                return tbmMemberId;
            }

            public void setTbmMemberId(String tbmMemberId) {
                this.tbmMemberId = tbmMemberId;
            }

            public String getTbmAadharNo() {
                return tbmAadharNo;
            }

            public void setTbmAadharNo(String tbmAadharNo) {
                this.tbmAadharNo = tbmAadharNo;
            }

            public String getTbmValidateFlag() {
                return tbmValidateFlag;
            }

            public void setTbmValidateFlag(String tbmValidateFlag) {
                this.tbmValidateFlag = tbmValidateFlag;
            }

            public String getTbmBhamashahAckId() {
                return tbmBhamashahAckId;
            }

            public void setTbmBhamashahAckId(String tbmBhamashahAckId) {
                this.tbmBhamashahAckId = tbmBhamashahAckId;
            }

            public String getTbmJanaadhaarFamily() {
                return tbmJanaadhaarFamily;
            }

            public void setTbmJanaadhaarFamily(String tbmJanaadhaarFamily) {
                this.tbmJanaadhaarFamily = tbmJanaadhaarFamily;
            }

            public String getTbmJanaadhar() {
                return tbmJanaadhar;
            }

            public void setTbmJanaadhar(String tbmJanaadhar) {
                this.tbmJanaadhar = tbmJanaadhar;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(tjmMotherId);
                dest.writeString(tjmAwcId);
                dest.writeString(tjmAnmId);
                dest.writeString(tjmAshaId);
                dest.writeString(tjmName);
                dest.writeString(tjmAddress);
                dest.writeString(tjmAge);
                dest.writeString(tjmLmcDate);
                dest.writeString(tjmNfsaEligibility);
                dest.writeString(tjmRegistrationDate);
                dest.writeString(tjmBpl);
                dest.writeString(tjmCastEnglish);
                dest.writeString(tjmCastCategory);
                dest.writeString(tjmGhumantu);
                dest.writeString(tjmLiveChild);
                dest.writeString(tjmMobileno);
                dest.writeString(tjmHusbname);
                dest.writeString(tjmAccountno);
                dest.writeString(tjmEntryDate);
                dest.writeString(tjmIfscCode);
                dest.writeString(tjmRationcardNo);
                dest.writeString(tjmVillageautoId);
                dest.writeString(tjmAccountName);
                dest.writeString(tjmAncregId);
                dest.writeString(tjmIsHusband);
                dest.writeString(tjmBplcardNo);
                dest.writeString(tjmHeight);
                dest.writeString(tjmDomicileStatus);
                dest.writeString(tjmLastModifiedDate);
                dest.writeString(tjmVerifyflag);
                dest.writeString(tjmMotherType);
                dest.writeString(tjmCastHindi);
                dest.writeString(tjmCronEntryDate);
                dest.writeString(tbmAshaAutoId);
                dest.writeString(tbmMotherId);
                dest.writeString(tbmBhamashahId);
                dest.writeString(tbmMemberId);
                dest.writeString(tbmAadharNo);
                dest.writeString(tbmValidateFlag);
                dest.writeString(tbmBhamashahAckId);
                dest.writeString(tbmJanaadhaarFamily);
                dest.writeString(tbmJanaadhar);
            }


        }

    }

