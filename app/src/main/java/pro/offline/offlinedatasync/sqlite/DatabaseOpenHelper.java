package pro.offline.offlinedatasync.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/pro.offline.offlinedatasync/databases/";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    String stringdatabase = "unifox.db";

    public DatabaseOpenHelper(Context context) {
        super(context, "unifox.db", null, 1);
        this.myContext = context;
    }


    public void onCreate(SQLiteDatabase database) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {

        } else {
            this.getReadableDatabase();
            this.close();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }


    private boolean checkDataBase() {
        boolean checkDB = false;
        try {
            String myPath = DB_PATH + stringdatabase;
            File file = new File(myPath);
            checkDB = file.exists();

        } catch (SQLiteException e) {

        }

        return checkDB;
    }


    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(stringdatabase);
        String outFileName = DB_PATH + stringdatabase;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + stringdatabase;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
    }


    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }
}
