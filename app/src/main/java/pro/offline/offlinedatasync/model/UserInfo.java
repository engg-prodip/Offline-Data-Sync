package pro.offline.offlinedatasync.model;

public class UserInfo {
    private String name, phone, email, password, companyName, designation;
    private int userID, offlineStatus;

    public UserInfo(String name, String phone, String email, String password, String companyName,
                    String designation, int userID, int offlineStatus) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.companyName = companyName;
        this.designation = designation;
        this.userID = userID;
        this.offlineStatus = offlineStatus;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getOfflineStatus() {
        return offlineStatus;
    }

    public void setOfflineStatus(int offlineStatus) {
        this.offlineStatus = offlineStatus;
    }
}
