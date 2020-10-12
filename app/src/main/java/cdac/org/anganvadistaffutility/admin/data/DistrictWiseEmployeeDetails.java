package cdac.org.anganvadistaffutility.admin.data;

public class DistrictWiseEmployeeDetails implements Comparable<DistrictWiseEmployeeDetails> {

    String district_id;

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_name_hindi() {
        return district_name_hindi;
    }

    public void setDistrict_name_hindi(String district_name_hindi) {
        this.district_name_hindi = district_name_hindi;
    }

    public String getDistrict_name_english() {
        return district_name_english;
    }

    public void setDistrict_name_english(String district_name_english) {
        this.district_name_english = district_name_english;
    }

    public String getDistrict_project_code() {
        return district_project_code;
    }

    public void setDistrict_project_code(String district_project_code) {
        this.district_project_code = district_project_code;
    }

    public String getDistrict_registered_employees() {
        return district_registered_employees;
    }

    public void setDistrict_registered_employees(String district_registered_employees) {
        this.district_registered_employees = district_registered_employees;
    }

    public String getDistrict_unregistered_employees() {
        return district_unregistered_employees;
    }

    public void setDistrict_unregistered_employees(String district_unregistered_employees) {
        this.district_unregistered_employees = district_unregistered_employees;
    }

    String district_name_hindi;
    String district_name_english;
    String district_project_code;
    String district_registered_employees;
    String district_unregistered_employees;

    public String getDistrict_employees() {
        return district_employees;
    }

    public void setDistrict_employees(String district_employees) {
        this.district_employees = district_employees;
    }

    String district_employees;

    @Override
    public int compareTo(DistrictWiseEmployeeDetails districtWiseEmployeeDetails) {
        return this.getDistrict_employees().compareTo(districtWiseEmployeeDetails.getDistrict_employees());
    }
}
