package pro.offline.offlinedatasync.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreData {
    private static StoreData store;
    private static String filename = "UniFoxKeys";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String LOGGED_IN_PREF = "logged_in_status";
    public static final String LOGGED_IN_UID = "logged_in_uid";
    public static final String LOGGED_IN_NAME = "logged_in_name";
    public static final String LOGGED_IN_EMAIL = "logged_in_email";
    public static final String LOGGED_IN_PHONE = "logged_in_phone";
    public static final String COMPANYNAME = "company_name";
    public static final String DESIGNATION = "designation";
    public static final String OFFLINESTATUS = "offlinestatus";
    public static final String LOGGED_IN_TEXT_EMAIL = "logged_in_text_email";
    public static final String LOGGED_IN_TEXT_PASSWORD = "logged_in_text_password";
    public static final String PASSWORD = "password";

    private StoreData(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(filename, 0);
    }

    public static StoreData getInstance(Context context) {
        if (store == null) {
            store = new StoreData(context);
        }
        return store;
    }

    public void setLoggedStatus(boolean status) {
        editor = sharedPreferences.edit();
        editor.putBoolean(LOGGED_IN_PREF, status);
        editor.commit();
    }

    public Boolean getLoggedStatus() {
        return sharedPreferences.getBoolean(LOGGED_IN_PREF, false);
    }

    public void setUserData(String u_id, String name, String email, String phone,
                            String company_name, String designation, String password) {
        editor = sharedPreferences.edit();
        editor.putString(LOGGED_IN_UID, u_id);
        editor.putString(LOGGED_IN_NAME, name);
        editor.putString(LOGGED_IN_EMAIL, email);
        editor.putString(LOGGED_IN_PHONE, phone);
        editor.putString(COMPANYNAME, company_name);
        editor.putString(DESIGNATION, designation);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public void setUserTextData(String email, String password) {
        editor = sharedPreferences.edit();
        editor.putString(LOGGED_IN_TEXT_EMAIL, email);
        editor.putString(LOGGED_IN_TEXT_PASSWORD, password);
        editor.commit();
    }

    public String getUserTextEmail() {
        return sharedPreferences.getString(LOGGED_IN_TEXT_EMAIL, "");

    }

    public String getUserTextPassword() {
        return sharedPreferences.getString(LOGGED_IN_TEXT_PASSWORD, "");
    }

    public String getUserID() {
        return sharedPreferences.getString(LOGGED_IN_UID, "");
    }

    public String getUserName() {
        return sharedPreferences.getString(LOGGED_IN_NAME, "");
    }

    public String getUserEmail() {
        return sharedPreferences.getString(LOGGED_IN_EMAIL, "");

    }

    public String getUserPhone() {
        return sharedPreferences.getString(LOGGED_IN_PHONE, "");
    }

    public String getCompanyName() {
        return sharedPreferences.getString(COMPANYNAME, "");
    }

    public String getDesignation() {
        return sharedPreferences.getString(DESIGNATION, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }


    public void setOfflineStatus(int status) {
        editor = sharedPreferences.edit();
        editor.putInt(OFFLINESTATUS, status);
        editor.commit();
    }

    public int getOfflineStatus() {
        return sharedPreferences.getInt(OFFLINESTATUS, 0);

    }

}
