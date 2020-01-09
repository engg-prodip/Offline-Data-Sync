package pro.offline.offlinedatasync.sqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import pro.offline.offlinedatasync.model.UserInfo;

public class DatabaseAdapter {
    SQLiteDatabase database;
    DatabaseOpenHelper dbHelper;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
    }

    public void open() {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        database.close();
    }

    public UserInfo getUserInfo(String inputEmail, String inputPassword) {
        UserInfo userInfo = null;
        Cursor cursor = database.rawQuery(
                "SELECT name, phone, email, password, companyName, designation, userID, offlineStatus FROM user_info WHERE email ='"+inputEmail+"' AND password ='"+inputPassword+"'", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String companyName = cursor.getString(cursor.getColumnIndex("companyName"));
                String designation = cursor.getString(cursor.getColumnIndex("designation"));
                int userID = cursor.getInt(cursor.getColumnIndex("userID"));
                int offlineStatus = cursor.getInt(cursor.getColumnIndex("offlineStatus"));
                userInfo = new UserInfo(name, phone, email, password, companyName, designation, userID, offlineStatus);
                cursor.moveToNext();
            }

        }else {
            return null;
        }
        cursor.close();
        return userInfo;
    }


    public long RegistrationDataInsert(String name, String phone, String email, String password, String companyName,
                                       String designation, int offlineStatus) {
        this.open();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("companyName", companyName);
        cv.put("designation", designation);
        cv.put("offlineStatus", offlineStatus);
        long insertedRow = database.insert("user_info", null, cv);
        this.close();
        return insertedRow;
    }


    public ArrayList<UserInfo> getAllOffLineUser() {
        ArrayList<UserInfo> userInfoArrayList = new ArrayList<UserInfo>();
        Cursor cursor = database.rawQuery(
                "SELECT name, phone, email, password, companyName, designation, userID, offlineStatus FROM user_info WHERE offlineStatus=0",
                new String[] {});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String companyName = cursor.getString(cursor.getColumnIndex("companyName"));
                String designation = cursor.getString(cursor.getColumnIndex("designation"));
                int userID = cursor.getInt(cursor.getColumnIndex("userID"));
                int offlineStatus = cursor.getInt(cursor.getColumnIndex("offlineStatus"));
                UserInfo a = new UserInfo(name, phone, email, password, companyName, designation, userID, offlineStatus);
                userInfoArrayList.add(a);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return userInfoArrayList;
    }

    public long UserOfflineStatus(int userID, int offlineStatus) {
        this.open();
        ContentValues cv = new ContentValues();
        cv.put("offlineStatus", offlineStatus);
        long insertedRow = database.update("user_info", cv, "userID" + " = " + userID, null);
        this.close();
        return insertedRow;
    }

}
