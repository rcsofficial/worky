package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The activity class where a client can edit his details.
 */
public class ClientEditProfileActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;
    int cIconCode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit_profile);

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(ClientEditProfileActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblEditProfile = (TextView) findViewById(R.id.lbl_c_e_editprofile);
        lblEditProfile.setTypeface(font);
        
        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String cUsername = user.get(workySessionMgt.KEY_USERNAME);

        /* SET USERNAME */
        TextView txvCusername = (TextView) findViewById(R.id.txv_c_e_username);
        txvCusername.setText(cUsername);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();
        workyClient clientAcct = app.getClientAcctByUsername(cUsername);
        String initialGender = clientAcct.getGender();
        String initialField = clientAcct.getField();


        /* DATA FIELDS */
        final EditText edtCpassword = (EditText) findViewById(R.id.edt_c_e_password);
        final EditText edtCfirstname = (EditText) findViewById(R.id.edt_c_e_firstname);
        final EditText edtCmidname = (EditText) findViewById(R.id.edt_c_e_midname);
        final EditText edtClastname = (EditText) findViewById(R.id.edt_c_e_lastname);
        final EditText edtCage = (EditText) findViewById(R.id.edt_c_e_age);
        final Spinner spnCgender = (Spinner) findViewById(R.id.spn_c_e_gender);
        final EditText edtCemail = (EditText) findViewById(R.id.edt_c_e_email);
        final EditText edtCmobilenum = (EditText) findViewById(R.id.edt_c_e_mobilenum);
        final EditText edtCprofile = (EditText) findViewById(R.id.edt_c_e_profile);
        final EditText edtCcompany = (EditText) findViewById(R.id.edt_c_e_company);
        final Spinner spnCfield = (Spinner) findViewById(R.id.spn_c_e_field);
        final EditText edtCspecialization = (EditText) findViewById(R.id.edt_c_e_specialization);
        final EditText edtClocation = (EditText) findViewById(R.id.edt_c_e_location);

            /* CONVERT GENDER AND FIELD TO INT */
            int initialGenderPos = 0;
            int initialFieldPos = 0;
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
                if (initialField.equals(LOOKUP_FIELD[i]))
                    initialFieldPos = i;
            }

        edtCpassword.setText(clientAcct.getPassword());
        edtCfirstname.setText(clientAcct.getFirstname());
        edtCmidname.setText(clientAcct.getMiddlename());
        edtClastname.setText(clientAcct.getLastname());
        edtCage.setText(String.valueOf(clientAcct.getAge()));
        spnCgender.setSelection(initialGenderPos);
        edtCemail.setText(clientAcct.getEmail());
        edtCmobilenum.setText(String.valueOf(clientAcct.getMobile()));
        edtCprofile.setText(clientAcct.getProfile());
        edtCcompany.setText(clientAcct.getCompany());
        spnCfield.setSelection(initialFieldPos);
        edtCspecialization.setText(clientAcct.getSpecialization());
        edtClocation.setText(clientAcct.getLocation());

        /* Set Client Avatar */
        final int icons[] = {R.drawable.profpic1, R.drawable.profpic2, R.drawable.profpic3, R.drawable.profpic4};
        ImageView imvProfPic = (ImageView) findViewById(R.id.imv_c_e_icon);
        imvProfPic.setImageResource(icons[clientAcct.getIconCode()]);

        final ImageView imvIcon = (ImageView) findViewById(R.id.imv_c_e_icon);
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


        ImageView btnFinish = (ImageView) findViewById(R.id.btn_c_e_editprofile);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginActivity = new Intent(ClientEditProfileActivity.this,
                        LoginActivity.class);


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
                    long cMobilenum = Long.parseLong(edtCmobilenum.getText().toString());
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
                        /* LOGOUT SESSION */
                        session.logoutUser();
                        Toast.makeText(ClientEditProfileActivity.this,
                                "SUCCESS. Please log back in to apply changes.",
                                Toast.LENGTH_SHORT).show();


                        /* EDIT CLIENT ACCOUNT */
                        app.editClient(cUsername, cPassword, cFirstname, cMidname, cLastname,
                                cAge, cGender, cEmail, cMobilenum, cProfile, cCompany,
                                cField, cSpecialization, cLocation, cIconCode);
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
