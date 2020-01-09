package pro.offline.offlinedatasync;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.MenuItem;
import android.database.SQLException;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import pro.offline.offlinedatasync.model.UserInfo;
import pro.offline.offlinedatasync.sharedpreference.StoreData;
import pro.offline.offlinedatasync.sqlite.DatabaseAdapter;
import pro.offline.offlinedatasync.sqlite.DatabaseOpenHelper;
import pro.offline.offlinedatasync.view.HomeActivity;
import pro.offline.offlinedatasync.view.RegistrationActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private TextView registration;
    private EditText email, pass;
    private String iEmail, iPass;
    private CheckBox checkBoxRem;
    private boolean logged_Status;
    private StoreData store;
    private ImageView passwordShow, passwordHide;
    private DatabaseAdapter dbAdapter;
    private DatabaseOpenHelper dbHelper;
    private UserInfo userInfo=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        store = StoreData.getInstance(getApplicationContext());
        email = findViewById(R.id.edtEmail);
        pass = findViewById(R.id.edtPassword);
        registration = findViewById(R.id.btnReg);
        checkBoxRem = findViewById(R.id.checkBoxRememberMe);
        passwordShow = findViewById(R.id.passwordShow);
        passwordHide = findViewById(R.id.passwordHide);
        final Boolean checkBoxState = checkBoxRem.isChecked();
        registration.setText(Html.fromHtml("<p>Don't Have an Account? <u>Sign Up - Here</u></p>"));
        login = findViewById(R.id.btnLog);

        dbAdapter = new DatabaseAdapter(this);
        dbHelper = new DatabaseOpenHelper(this);

        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            dbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(regIntent);
            }
        });

        passwordShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                pass.setSelection(pass.length());
                passwordShow.setVisibility(View.GONE);
                passwordHide.setVisibility(View.VISIBLE);
            }
        });

        passwordHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                pass.setSelection(pass.length());
                passwordHide.setVisibility(View.GONE);
                passwordShow.setVisibility(View.VISIBLE);
            }
        });

        logged_Status = store.getLoggedStatus();

        if(logged_Status==true) {
            String userID = store.getUserID();
            String name = store.getUserName();
            String email = store.getUserEmail();
            String phone = store.getUserPhone();
            String companyName = store.getCompanyName();
            String designation = store.getDesignation();
            Intent userMainIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(userMainIntent);
            finish();
        }else {
            String stemail = store.getUserTextEmail();
            String stpassword = store.getUserTextPassword();
            email.setText(stemail);
            pass.setText(stpassword);
        }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty() || (pass.getText().toString().length()<=5 )) {

                    if (email.getText().toString().isEmpty()) {
                        email.setError("Enter Email!");
                    }
                    if (pass.getText().toString().length() <= 5) {
                        pass.setError("Password at least 6 characters!");

                    }

                }
                else{
                    iEmail = email.getText().toString();
                    iPass = pass.getText().toString();

                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    //String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
                    if (iEmail.matches(emailPattern))
                    {

                        if(checkBoxRem.isChecked()){
                            store.setUserTextData(iEmail, iPass);
                        }else {
                            store.setUserTextData("", "");
                        }
                        dbAdapter.open();
                        userInfo = dbAdapter.getUserInfo(iEmail, iPass);
                        dbAdapter.close();
                        if (userInfo!=null){
                            store.setLoggedStatus(true);
                            store.setOfflineStatus(userInfo.getOfflineStatus());
                            store.setUserData(String.valueOf(userInfo.getUserID()), userInfo.getName(), userInfo.getEmail(), userInfo.getPhone(),
                                    userInfo.getCompanyName(), userInfo.getDesignation(), userInfo.getPassword());
                            Intent userMainIntent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(userMainIntent);
                            finish();

                        }else {
                            Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        email.setError("Invalid Email Address!");
                    }
                }

            }
        });


    }

}
