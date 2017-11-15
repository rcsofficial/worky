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

import java.util.HashMap;

public class FreelanceEditProfileActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_edit_profile);

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String fUsername = user.get(workySessionMgt.KEY_USERNAME);


        /* SET USERNAME */
        TextView txvCusername = (TextView) findViewById(R.id.txv_f_e_username);
        txvCusername.setText(fUsername + " (Usernames are Permanent)");


        Button btnFinish = (Button) findViewById(R.id.btn_f_e_editprofile);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginActivity = new Intent(FreelanceEditProfileActivity.this,
                        LoginActivity.class);

                /* APPLICATION OBJECT */
                final workyApplication app = (workyApplication) getApplication();


                /* GET EDIT TEXT AND SPIN FIELDS CONTENT */
                EditText edtFpassword = (EditText) findViewById(R.id.edt_f_e_password);
                EditText edtFfirstname = (EditText) findViewById(R.id.edt_f_e_firstname);
                EditText edtFmidname = (EditText) findViewById(R.id.edt_f_e_midname);
                EditText edtFlastname = (EditText) findViewById(R.id.edt_f_e_lastname);
                EditText edtFage = (EditText) findViewById(R.id.edt_f_e_age);
                Spinner spnFgender = (Spinner) findViewById(R.id.spn_f_e_gender);
                EditText edtFemail = (EditText) findViewById(R.id.edt_f_e_email);
                EditText edtFmobilenum = (EditText) findViewById(R.id.edt_f_e_mobilenum);
                EditText edtFprofile = (EditText) findViewById(R.id.edt_f_e_profile);
                EditText edtFeduc = (EditText) findViewById(R.id.edt_f_e_educattainment);
                Spinner spnFexpertise = (Spinner) findViewById(R.id.spn_f_e_expertise);
                EditText edtFcourse = (EditText) findViewById(R.id.edt_f_e_course);
                EditText edtFlocation = (EditText) findViewById(R.id.edt_f_e_location);


                /* LOOKUP SPINNER TRANSLATION TABLE */
                String[] LOOKUP_GENDER = { "Male", "Female", "Lesbian", "Gay", "Bisexual",
                        "Transsexual", "Queer", "Intersex", "Asexual"};
                String[] LOOKUP_FIELD = { "Agriculture", "Arts", "Clerical", "Education",
                        "Engineering", "Finance", "Health", "Hospitality",
                        "IT", "Legal", "Manufacturing", "Transport", "Others"};


                /* CHECK IF INT FIELDS BLANK TO PREVENT PARSE ERROR */
                if (areIntFieldsBlank(edtFage, edtFmobilenum))
                    return;
                else {
                    /* EXTRACT DATA FROM USER INPUT */
                    String fPassword = edtFpassword.getText().toString();
                    String fFirstname = edtFfirstname.getText().toString();
                    String fMidname = edtFmidname.getText().toString();
                    String fLastname = edtFlastname.getText().toString();
                    int fAge = Integer.parseInt(edtFage.getText().toString());
                    String fGender = LOOKUP_GENDER[spnFgender.getSelectedItemPosition()];
                    String fEmail = edtFemail.getText().toString();
                    long fMobilenum = Long.parseLong(edtFmobilenum.getText().toString());
                    String fProfile = edtFprofile.getText().toString();
                    String fEduc = edtFeduc.getText().toString();
                    String fExpertise = LOOKUP_FIELD[spnFexpertise.getSelectedItemPosition()];
                    String fCourse = edtFcourse.getText().toString();
                    String fLocation = edtFlocation.getText().toString();

                    if (areStrFieldsBlank(fPassword, fFirstname, fMidname, fLastname,
                            fEmail, fProfile, fEduc, fCourse, fLocation)) {
                        return;
                    }
                    else {

                        /* LOGOUT SESSION */
                        session.logoutUser();
                        Toast.makeText(FreelanceEditProfileActivity.this,
                                "SUCCESS. Please log back in to apply changes.",
                                Toast.LENGTH_SHORT).show();

                        /* EDIT FREELANCER ACCOUNT */
                        app.editFreelancer(fUsername, fPassword, fFirstname, fMidname, fLastname,
                                fAge, fGender, fEmail, fMobilenum, fProfile, fEduc,
                                fExpertise, fCourse, fLocation);

                        startActivity(launchLoginActivity);

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
            Toast.makeText(FreelanceEditProfileActivity.this,
                    "ERROR: You may not leave any of the fields empty.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    protected boolean areStrFieldsBlank(String password, String firstname,
                                        String midname, String lastname, String email,
                                        String profile, String educ, String expertise,
                                        String location) {
        if (password.isEmpty() || firstname.isEmpty() || midname.isEmpty()
                || lastname.isEmpty() || email.isEmpty() || profile.isEmpty() ||
                educ.isEmpty() || expertise.isEmpty() || location.isEmpty()) {
            Toast.makeText(FreelanceEditProfileActivity.this,
                    "ERROR: You may not leave any of the fields empty.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }
}
