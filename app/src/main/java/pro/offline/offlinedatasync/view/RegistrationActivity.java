package pro.offline.offlinedatasync.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import pro.offline.offlinedatasync.MainActivity;
import pro.offline.offlinedatasync.R;
import pro.offline.offlinedatasync.sqlite.DatabaseAdapter;

public class RegistrationActivity extends AppCompatActivity {
    private EditText name, email, phone, pass, repass, edtCompanyName, edtDesignation;
    private TextView regBack;
    private Button submitRegData;
    private Toolbar toolbar;
    private String iName, iEmail, iPhone, iPass, iCompanyName, iDesignation;
    private DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = findViewById(R.id.edtName);
        email = findViewById(R.id.edtEmail);
        phone = findViewById(R.id.edtPhone);
        pass = findViewById(R.id.edtPassword);
        repass = findViewById(R.id.edtRepassword);
        edtCompanyName = findViewById(R.id.edtCompanyName);
        edtDesignation = findViewById(R.id.edtDesignation);
        submitRegData = findViewById(R.id.btnRegDone);
        regBack = findViewById(R.id.btnRegBack);
        regBack.setText(Html.fromHtml("<p>Have an Account?  <u>Login Here</u></p>"));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sign Up");
        databaseAdapter = new DatabaseAdapter(this);

        regBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(regIntent);
            }
        });


        submitRegData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty()|| pass.getText().toString().length() <= 5 || repass.getText().toString().length() <= 5 ||
                        ((pass.getText().toString().length()>5 && repass.getText().toString().length()>5) &&
                                (!pass.getText().toString().equals(repass.getText().toString()))) || phone.getText().toString().length()<=10 || phone.getText().toString().isEmpty() || phone.getText().toString().length()>=12){

                    if (name.getText().toString().isEmpty()) {
                        name.setError("Enter Your Name!");
                    }
                    if (phone.getText().toString().length()<=10 || phone.getText().toString().isEmpty() || phone.getText().toString().length()>=12) {
                        if (phone.getText().toString().isEmpty()) {
                            phone.setError("Enter Mobile No!");
                        }
                        else if (phone.getText().toString().length()<=10) {
                            phone.setError("Invalid Number!");
                        }
                        else if (phone.getText().toString().length()>=12) {
                            phone.setError("Invalid Number!");
                        }
                    }

                    if (email.getText().toString().isEmpty()) {
                        email.setError("Enter Email!");
                    }
                    if (pass.getText().toString().length() <= 5 || repass.getText().toString().length() <= 5) {
                        pass.setError("Password at least 6 characters!");
                    }
                    if((pass.getText().toString().length()>5 && repass.getText().toString().length()>5) &&
                            (!pass.getText().toString().equals(repass.getText().toString()))) {
                        pass.setError("Password Doesn't Match!");
                        repass.setError("Password Doesn't Match!");
                    }
                }
                else{
                    iName = name.getText().toString();
                    iEmail = email.getText().toString();
                    iPhone = phone.getText().toString();
                    iPass = pass.getText().toString();
                    iCompanyName = edtCompanyName.getText().toString();
                    iDesignation = edtDesignation.getText().toString();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    //String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
                    if (iEmail.matches(emailPattern))
                    {

                        long insertedRow = databaseAdapter.RegistrationDataInsert(iName, iPhone, iEmail, iPass, iCompanyName,
                                iDesignation,  0);
                        if(insertedRow>0){
                            Toast.makeText(getApplicationContext(), "Data Save In Offline Mode", Toast.LENGTH_SHORT).show();
                            Intent regIntent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(regIntent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Email Already Exits!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
