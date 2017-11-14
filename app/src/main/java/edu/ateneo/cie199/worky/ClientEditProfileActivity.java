package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientEditProfileActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit_profile);

        
        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String cUsername = user.get(workySessionMgt.KEY_USERNAME);


        /* SET USERNAME */
        TextView txvCusername = (TextView) findViewById(R.id.txv_c_e_username);
        txvCusername.setText(cUsername + " (Usernames are Permanent)");


        Button btnFinish = (Button) findViewById(R.id.btn_c_e_editprofile);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginActivity = new Intent(ClientEditProfileActivity.this,
                        LoginActivity.class);

                /* APPLICATION OBJECT */
                final workyApplication app = (workyApplication) getApplication();


                /* GET EDIT TEXT AND SPIN FIELDS CONTENT */
                EditText edtCpassword = (EditText) findViewById(R.id.edt_c_e_password);
                EditText edtCfirstname = (EditText) findViewById(R.id.edt_c_e_firstname);
                EditText edtCmidname = (EditText) findViewById(R.id.edt_c_e_midname);
                EditText edtClastname = (EditText) findViewById(R.id.edt_c_e_lastname);
                EditText edtCage = (EditText) findViewById(R.id.edt_c_e_age);
                Spinner spnCgender = (Spinner) findViewById(R.id.spn_c_e_gender);
                EditText edtCemail = (EditText) findViewById(R.id.edt_c_e_email);
                EditText edtCmobilenum = (EditText) findViewById(R.id.edt_c_e_mobilenum);
                EditText edtCprofile = (EditText) findViewById(R.id.edt_c_e_profile);
                EditText edtCcompany = (EditText) findViewById(R.id.edt_c_e_company);
                Spinner spnCfield = (Spinner) findViewById(R.id.spn_c_e_field);
                EditText edtCspecialization = (EditText) findViewById(R.id.edt_c_e_specialization);
                EditText edtClocation = (EditText) findViewById(R.id.edt_c_e_location);


                /* LOOKUP SPINNER TRANSLATION TABLE */
                String[] LOOKUP_GENDER = { "Male", "Female", "Lesbian", "Gay", "Bisexual",
                        "Transsexual", "Queer", "Intersex", "Asexual"};
                String[] LOOKUP_FIELD = { "Agriculture", "Arts", "Clerical", "Education",
                        "Engineering", "Finance", "Health", "Hospitality",
                        "IT", "Legal", "Manufacturing", "Transport", "Others"};


                /* CHECK IF INT FIELDS BLANK TO PREVENT PARSE ERROR */
                if (areIntFieldsBlank(edtCage, edtCmobilenum))
                    return;
                else {
                    /* EXTRACT DATA FROM USER INPUT */
                    String cPassword = edtCpassword.getText().toString();
                    String cFirstname = edtCfirstname.getText().toString();
                    String cMidname = edtCmidname.getText().toString();
                    String cLastname = edtClastname.getText().toString();
                    int cAge = Integer.parseInt(edtCage.getText().toString());
                    String cGender = LOOKUP_GENDER[spnCgender.getSelectedItemPosition()];
                    String cEmail = edtCemail.getText().toString();
                    int cMobilenum = Integer.parseInt(edtCmobilenum.getText().toString());
                    String cProfile = edtCprofile.getText().toString();
                    String cCompany = edtCcompany.getText().toString();
                    String cField = LOOKUP_FIELD[spnCfield.getSelectedItemPosition()];
                    String cSpecialization = edtCspecialization.getText().toString();
                    String cLocation = edtClocation.getText().toString();

                    if (areStrFieldsBlank(cPassword, cFirstname, cMidname, cLastname,
                            cEmail, cProfile, cCompany, cSpecialization, cLocation)) {
                        return;
                    }
                    else {
                        /* EDIT CLIENT ACCOUNT */
                        app.editClient(cUsername, cPassword, cFirstname, cMidname, cLastname,
                                cAge, cGender, cEmail, cMobilenum, cProfile, cCompany,
                                cField, cSpecialization, cLocation);


                        /* LOGOUT SESSION */
                        session.logoutUser();
                        Toast.makeText(ClientEditProfileActivity.this,
                                "SUCCESS. Please log back in to apply changes.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                /* LAUNCH LOGIN ACTIVITY */
                startActivity(launchLoginActivity);
                finish();
            }
        });
    }

    protected boolean areIntFieldsBlank(EditText age, EditText mobilenum) {
        if (age.getText().toString().isEmpty() || mobilenum.getText().toString().isEmpty()) {
            Toast.makeText(ClientEditProfileActivity.this,
                    "ERROR: You may not leave any of the fields empty.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    protected boolean areStrFieldsBlank(String password, String firstname,
                                        String midname, String lastname, String email,
                                        String profile, String company, String specialization,
                                        String location) {
        if (password.isEmpty() || firstname.isEmpty() || midname.isEmpty()
                || lastname.isEmpty() || email.isEmpty() || profile.isEmpty() ||
                company.isEmpty() || specialization.isEmpty() || location.isEmpty()) {
            Toast.makeText(ClientEditProfileActivity.this,
                    "ERROR: You may not leave any of the fields empty.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }
}
