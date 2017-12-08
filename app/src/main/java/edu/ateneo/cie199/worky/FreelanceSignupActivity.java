package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The activity class where the user signups for a new freelancer account. Redirects to
 * <code>FreelanceDashboardActivity</code> in a successful signup.
 *
 * @see ClientDashboardActivity
 */
public class FreelanceSignupActivity extends AppCompatActivity {
    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;
    int fIconCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_signup);

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(FreelanceSignupActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblSignup = (TextView) findViewById(R.id.lbl_f_signuppage);
        lblSignup.setTypeface(font);

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());

        /* SET USERNAME BASED FROM EDIT FIELD IN LOGIN ACTIVITY */
        Intent intentFromLogin = getIntent();
        if (intentFromLogin == null) { return; }
        TextView txvFusername = (TextView) findViewById(R.id.txv_f_username);
        final String fUsername = intentFromLogin.getStringExtra("F_USERNAME");
        final String fPassword = intentFromLogin.getStringExtra("F_PASSWORD");
        txvFusername.setText(fUsername);

        final ImageView imvIcon = (ImageView) findViewById(R.id.imv_f_icon);
        final int icons[] = {R.drawable.profpic1, R.drawable.profpic2, R.drawable.profpic3, R.drawable.profpic4};

        /* ICON ONPRESS LISTENER */
        imvIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fIconCode < icons.length - 1)
                            fIconCode ++;
                        else
                            fIconCode = 0;

                        imvIcon.setImageResource(icons[fIconCode]);
                    }
                }
        );

        ImageView btnFinish = (ImageView) findViewById(R.id.btn_f_submitsignup);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchFreelanceDashboardActivity = new Intent(FreelanceSignupActivity.this,
                        FreelanceDashboardActivity.class);

                /* APPLICATION OBJECT */
                final workyApplication app = (workyApplication) getApplication();

                /* GET EDIT TEXT AND SPIN FIELDS CONTENT */
                EditText edtFfirstname = (EditText) findViewById(R.id.edt_f_firstname);
                EditText edtFmidname = (EditText) findViewById(R.id.edt_f_midname);
                EditText edtFlastname = (EditText) findViewById(R.id.edt_f_lastname);
                EditText edtFage = (EditText) findViewById(R.id.edt_f_age);
                Spinner spnFgender = (Spinner) findViewById(R.id.spn_f_gender);
                EditText edtFemail = (EditText) findViewById(R.id.edt_f_email);
                EditText edtFmobilenum = (EditText) findViewById(R.id.edt_f_mobilenum);
                EditText edtFprofile = (EditText) findViewById(R.id.edt_f_profile);
                EditText edtFeduc = (EditText) findViewById(R.id.edt_f_educattainment);
                Spinner spnFexpertise = (Spinner) findViewById(R.id.spn_f_expertise);
                EditText edtFcourse = (EditText) findViewById(R.id.edt_f_course);
                EditText edtFlocation = (EditText) findViewById(R.id.edt_f_location);

                /* LOOKUP SPINNER TRANSLATION TABLE */
                String[] LOOKUP_GENDER = { "Male", "Female", "Lesbian", "Gay", "Bisexual",
                        "Transsexual", "Queer", "Intersex", "Asexual"};
                String[] LOOKUP_FIELD = { "Agriculture", "Arts", "Clerical", "Education",
                        "Engineering", "Finance", "Health", "Hospitality",
                        "IT", "Legal", "Manufacturing", "Transport", "Others"};

                if (!isInternetAvailable()) {
                    Log.d("HERE", "HERE???");
                    Toast.makeText(FreelanceSignupActivity.this,
                            "ERROR: Please connect to the internet.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                /* CHECK IF INT FIELDS BLANK TO PREVENT PARSE ERROR */
                else if (areIntFieldsBlank(edtFage, edtFmobilenum))
                    return;
                else {
                    /* EXTRACT DATA FROM USER INPUT */
                    String fFirstname = edtFfirstname.getText().toString();
                    String fMidname = edtFmidname.getText().toString();
                    String fLastname = edtFlastname.getText().toString();
                    int fAge;
                    try {
                        fAge = Integer.parseInt(edtFage.getText().toString());
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                        Toast.makeText(FreelanceSignupActivity.this,
                                "ERROR: Invalid age.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String fGender = LOOKUP_GENDER[spnFgender.getSelectedItemPosition()];
                    String fEmail = edtFemail.getText().toString();
                    long fMobilenum;
                    try {
                        fMobilenum = Long.parseLong(edtFmobilenum.getText().toString());
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                        Toast.makeText(FreelanceSignupActivity.this,
                                "ERROR: Invalid mobile number.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String fProfile = edtFprofile.getText().toString();
                    String fEduc = edtFeduc.getText().toString();
                    String fExpertise = LOOKUP_FIELD[spnFexpertise.getSelectedItemPosition()];
                    String fCourse = edtFcourse.getText().toString();
                    String fLocation = edtFlocation.getText().toString();

                    if (areStrFieldsBlank(fFirstname, fMidname, fLastname, fEmail,
                            fProfile, fEduc, fCourse, fLocation)) {
                        return;
                    }
                    else {
                        /* ADD FREELANCER ACCOUNT */
                        app.addFreelancerAccount(new workyFreelancer(fUsername, fPassword, fFirstname,
                                fMidname, fLastname, fAge, fGender, fEmail, fMobilenum, fProfile,
                                fEduc, fExpertise, fCourse, fLocation, fIconCode, null));

                        /* CREATE SESSION */
                        session.createLoginSession(fUsername, fPassword, "Freelancer");
                    }
                }

                /* LAUNCH FREELANCER DASHBOARD ACTIVITY */
                startActivity(launchFreelanceDashboardActivity);
                finish();
            }
        });
    }

    /* CHECKS IF ANY INT FIELDS ARE LEFT BLANK */
    protected boolean areIntFieldsBlank(EditText age, EditText mobilenum) {
        if (age.getText().toString().isEmpty() || mobilenum.getText().toString().isEmpty()) {
            Toast.makeText(FreelanceSignupActivity.this,
                    "ERROR: You may not leave any of the fields empty.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    /* CHECKS IF ANY STRING FIELDS ARE LEFT BLANK */
    protected boolean areStrFieldsBlank(String firstname, String midname, String lastname,
                                        String email, String profile, String educ,
                                        String expertise, String location) {
        if (firstname.isEmpty() || midname.isEmpty() || lastname.isEmpty() || email.isEmpty() ||
                profile.isEmpty() ||  educ.isEmpty() || expertise.isEmpty() ||
                location.isEmpty()) {
            Toast.makeText(FreelanceSignupActivity.this,
                    "ERROR: You may not leave any of the fields empty.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    /* CHECKS IF THERE IS AN INTERNET CONNECTION */
    private Boolean isInternetAvailable() {
        String command = "ping -c 1 google.com";
        try {
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}