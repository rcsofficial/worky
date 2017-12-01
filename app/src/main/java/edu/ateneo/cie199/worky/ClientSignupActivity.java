package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The activity class where the user signups for a new client account. Redirects to
 * <code>ClientDashboardActivity</code> in a successful signup.
 *
 * @see ClientDashboardActivity
 */
public class ClientSignupActivity extends AppCompatActivity {
    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;
    int cIconCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_signup);

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(ClientSignupActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblSignup = (TextView) findViewById(R.id.lbl_c_signuppage);
        lblSignup.setTypeface(font);

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());

        /* SET USERNAME BASED FROM EDIT FIELD IN LOGIN ACTIVITY */
        Intent intentFromLogin = getIntent();
        if (intentFromLogin == null) { return; }
        TextView txvCusername = (TextView) findViewById(R.id.txv_c_username);
        final String cUsername = intentFromLogin.getStringExtra("C_USERNAME");
        final String cPassword = intentFromLogin.getStringExtra("C_PASSWORD");
        txvCusername.setText(cUsername);

        final ImageView imvIcon = (ImageView) findViewById(R.id.imv_c_icon);
        final int icons[] = {R.drawable.profpic1, R.drawable.profpic2, R.drawable.profpic3, R.drawable.profpic4};

        /* ICON ONPRESS LISTENER */
        imvIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cIconCode < icons.length - 1)
                            cIconCode ++;
                        else
                            cIconCode = 0;

                        imvIcon.setImageResource(icons[cIconCode]);
                    }
                }
        );

        ImageView btnFinish = (ImageView) findViewById(R.id.btn_c_submitsignup);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchClientDashboardActivity = new Intent(ClientSignupActivity.this,
                        ClientDashboardActivity.class);

                /* APPLICATION OBJECT */
                final workyApplication app = (workyApplication) getApplication();


                /* GET EDIT TEXT AND SPIN FIELDS CONTENT */
                EditText edtCfirstname = (EditText) findViewById(R.id.edt_c_firstname);
                EditText edtCmidname = (EditText) findViewById(R.id.edt_c_midname);
                EditText edtClastname = (EditText) findViewById(R.id.edt_c_lastname);
                EditText edtCage = (EditText) findViewById(R.id.edt_c_age);
                Spinner spnCgender = (Spinner) findViewById(R.id.spn_c_gender);
                EditText edtCemail = (EditText) findViewById(R.id.edt_c_email);
                EditText edtCmobilenum = (EditText) findViewById(R.id.edt_c_mobilenum);
                EditText edtCprofile = (EditText) findViewById(R.id.edt_c_profile);
                EditText edtCcompany = (EditText) findViewById(R.id.edt_c_company);
                Spinner spnCfield = (Spinner) findViewById(R.id.spn_c_field);
                EditText edtCspecialization = (EditText) findViewById(R.id.edt_c_specialization);
                EditText edtClocation = (EditText) findViewById(R.id.edt_c_location);


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
                    String cFirstname = edtCfirstname.getText().toString();
                    String cMidname = edtCmidname.getText().toString();
                    String cLastname = edtClastname.getText().toString();
                    int cAge = Integer.parseInt(edtCage.getText().toString());
                    String cGender = LOOKUP_GENDER[spnCgender.getSelectedItemPosition()];
                    String cEmail = edtCemail.getText().toString();
                    long cMobilenum = Long.parseLong(edtCmobilenum.getText().toString());
                    String cProfile = edtCprofile.getText().toString();
                    String cCompany = edtCcompany.getText().toString();
                    String cField = LOOKUP_FIELD[spnCfield.getSelectedItemPosition()];
                    String cSpecialization = edtCspecialization.getText().toString();
                    String cLocation = edtClocation.getText().toString();

                    if (areStrFieldsBlank(cFirstname, cMidname, cLastname, cEmail,
                            cProfile, cCompany, cSpecialization, cLocation)) {
                        return;
                    }
                    else {
                        /* ADD CLIENT ACCOUNT */
                        app.addClientAccount(new workyClient(cUsername, cPassword, cFirstname,
                                cMidname, cLastname, cAge, cGender, cEmail, cMobilenum, cProfile,
                                cCompany, cField, cSpecialization, cLocation, cIconCode, null));

                        /* CREATE SESSION */
                        session.createLoginSession(cUsername, cPassword, "Client");
                    }
                }

                // TODO: Saved job order remains zero at this point since account is new

                /* LAUNCH CLIENT DASHBOARD ACTIVITY */
                startActivity(launchClientDashboardActivity);
                finish();
            }
        });
    }

    /* CHECKS IF ANY INT FIELDS ARE LEFT BLANK */
    protected boolean areIntFieldsBlank(EditText age, EditText mobilenum) {
        if (age.getText().toString().isEmpty() || mobilenum.getText().toString().isEmpty()) {
            Toast.makeText(ClientSignupActivity.this,
                    "ERROR: You may not leave any of the fields empty.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    /* CHECKS IF ANY STRING FIELDS ARE LEFT BLANK */
    protected boolean areStrFieldsBlank(String firstname, String midname, String lastname,
                                     String email, String profile, String company,
                                     String specialization, String location) {
        if (firstname.isEmpty() || midname.isEmpty() || lastname.isEmpty() || email.isEmpty() ||
                profile.isEmpty() ||  company.isEmpty() || specialization.isEmpty() ||
                location.isEmpty()) {
            Toast.makeText(ClientSignupActivity.this,
                    "ERROR: You may not leave any of the fields empty.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }
}