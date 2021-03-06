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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * The activity class where a freelancer can edit his details.
 */
public class FreelanceEditProfileActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;
    int fIconCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_edit_profile);

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(FreelanceEditProfileActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblEditProfile = (TextView) findViewById(R.id.lbl_f_e_editprofile);
        lblEditProfile.setTypeface(font);

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String fUsername = user.get(workySessionMgt.KEY_USERNAME);

        /* SET USERNAME */
        TextView txvCusername = (TextView) findViewById(R.id.txv_f_e_username);
        txvCusername.setText(fUsername);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();
        workyFreelancer freelancerAcct = app.getFreelancerAcctByUsername(fUsername);
        String initialGender = freelancerAcct.getGender();
        String initialExpertise = freelancerAcct.getExpertise();
        
        /* GET EDIT TEXT AND SPIN FIELDS CONTENT */
        final EditText edtFpassword = (EditText) findViewById(R.id.edt_f_e_password);
        final EditText edtFfirstname = (EditText) findViewById(R.id.edt_f_e_firstname);
        final EditText edtFmidname = (EditText) findViewById(R.id.edt_f_e_midname);
        final EditText edtFlastname = (EditText) findViewById(R.id.edt_f_e_lastname);
        final EditText edtFage = (EditText) findViewById(R.id.edt_f_e_age);
        final Spinner spnFgender = (Spinner) findViewById(R.id.spn_f_e_gender);
        final EditText edtFemail = (EditText) findViewById(R.id.edt_f_e_email);
        final EditText edtFmobilenum = (EditText) findViewById(R.id.edt_f_e_mobilenum);
        final EditText edtFprofile = (EditText) findViewById(R.id.edt_f_e_profile);
        final EditText edtFeduc = (EditText) findViewById(R.id.edt_f_e_educattainment);
        final Spinner spnFexpertise = (Spinner) findViewById(R.id.spn_f_e_expertise);
        final EditText edtFcourse = (EditText) findViewById(R.id.edt_f_e_course);
        final EditText edtFlocation = (EditText) findViewById(R.id.edt_f_e_location);
        ImageView imvProfPic = (ImageView) findViewById(R.id.imv_f_e_icon);
        
        /* CONVERT GENDER AND FIELD TO INT */
        int initialGenderPos = 0;
        int initialExpertisePos = 0;
        String[] LOOKUP_GENDER = { "Male", "Female", "Lesbian", "Gay", "Bisexual",
                "Transsexual", "Queer", "Intersex", "Asexual"};
        String[] LOOKUP_FIELD = { "Agriculture", "Arts", "Clerical", "Education",
                "Engineering", "Finance", "Health", "Hospitality",
                "IT", "Legal", "Manufacturing", "Transport", "Others"};
        for (int i=0; i<9; i++) {
            if (initialGender.equals(LOOKUP_GENDER[i]))
                initialGenderPos = i;
        }
        for (int i=0; i<13; i++) {
            if (initialExpertise.equals(LOOKUP_FIELD[i]))
                initialExpertisePos = i;
        }

        edtFpassword.setText(freelancerAcct.getPassword());
        edtFfirstname.setText(freelancerAcct.getFirstname());
        edtFmidname.setText(freelancerAcct.getMiddlename());
        edtFlastname.setText(freelancerAcct.getLastname());
        edtFage.setText(String.valueOf(freelancerAcct.getAge()));
        spnFgender.setSelection(initialGenderPos);
        edtFemail.setText(freelancerAcct.getEmail());
        edtFmobilenum.setText(String.valueOf(freelancerAcct.getMobile()));
        edtFprofile.setText(freelancerAcct.getProfile());
        edtFeduc.setText(freelancerAcct.getEducation());
        spnFexpertise.setSelection(initialExpertisePos);
        edtFcourse.setText(freelancerAcct.getCourse());
        edtFlocation.setText(freelancerAcct.getLocation());

        final int icons[] = {
                R.drawable.profpic1,
                R.drawable.profpic2,
                R.drawable.profpic3,
                R.drawable.profpic4
        };

        imvProfPic.setImageResource(icons[freelancerAcct.getIconCode()]);

        /* ICON ONPRESS LISTENER */
        final ImageView imvIcon = (ImageView) findViewById(R.id.imv_f_e_icon);
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

        ImageView btnFinish = (ImageView) findViewById(R.id.btn_f_e_editprofile);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginActivity = new Intent(FreelanceEditProfileActivity.this,
                        LoginActivity.class);
                                

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
                    int fAge;
                    try {
                        fAge = Integer.parseInt(edtFage.getText().toString());
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                        Toast.makeText(FreelanceEditProfileActivity.this,
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
                        Toast.makeText(FreelanceEditProfileActivity.this,
                                "ERROR: Invalid mobile number.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
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
                                fExpertise, fCourse, fLocation, fIconCode);

                        startActivity(launchLoginActivity);

                    }
                }
                /* LAUNCH LOGIN ACTIVITY */
                startActivity(launchLoginActivity);
                finish();
            }
        });
    }

    /* CHECKS IF ANY INT FIELDS ARE LEFT BLANK */
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

    /* CHECKS IF ANY STRING FIELDS ARE LEFT BLANK */
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