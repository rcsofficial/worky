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

public class FreelanceEditPostFieldsActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_edit_post_fields);


        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String fUsername = user.get(workySessionMgt.KEY_USERNAME);
        final String fUsertype = user.get(workySessionMgt.KEY_USERTYPE);


        /* SET USERNAME BASED FROM EDIT FIELD IN LOGIN ACTIVITY */
        Intent intentFromFreelanceEditDeletePostActivity = getIntent();
        if (intentFromFreelanceEditDeletePostActivity == null) { return; }
        final int position = intentFromFreelanceEditDeletePostActivity.getIntExtra("F_POST_POS", 0);


        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();


        /* SET INITIAL HINT */
        final workyJobs initialData = app.getJobsByUsername(fUsername, "Freelancer").get(position);
        TextView txvOldCategory = (TextView) findViewById(R.id.txv_f_e_oldcategory);
        TextView txvOldJobtitle = (TextView) findViewById(R.id.txv_f_e_oldjobtitle);
        TextView txvOldMaxpay = (TextView) findViewById(R.id.txv_f_e_oldsalary);
        TextView txvOldLocation = (TextView) findViewById(R.id.txv_f_e_oldloc);
        TextView txvOldDesc = (TextView) findViewById(R.id.txv_f_e_olddesc);

        txvOldCategory.setText("Old Category: " + initialData.getJobfield());
        txvOldJobtitle.setText(initialData.getJobtitle());
        txvOldMaxpay.setText("Old Max Pay: " + String.valueOf(initialData.getSalary()));
        txvOldLocation.setText("Old Location: " + initialData.getLocation());
        txvOldDesc.setText("Old Description: " + initialData.getDescription());



        Button btnEditJob = (Button) findViewById(R.id.btn_f_e_editjob);
        btnEditJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchFreelanceDashboardActivity = new Intent(FreelanceEditPostFieldsActivity.this,
                        FreelanceDashboardActivity.class);


                /* LOOKUP TRANSLATION TABLE FOR SPINNER */
                String[] LOOKUP_JOBCATEGORY = { "Agriculture", "Arts", "Clerical", "Education",
                        "Engineering", "Finance", "Health", "Hospitality",
                        "IT", "Legal", "Manufacturing", "Transport", "Others"};


                /* GET DATA FROM USER FIELDS */
                Spinner spnJobcategory = (Spinner) findViewById(R.id.spn_f_e_jobcategory);
                EditText edtMinpay = (EditText) findViewById(R.id.edt_f_e_minpay);
                EditText edtJobloc = (EditText) findViewById(R.id.edt_f_e_jobloc);
                EditText edtJobdesc = (EditText) findViewById(R.id.edt_f_e_jobdesc);


                /* EXTRACT DATA FROM USER FIELDS */
                String jobcategory = LOOKUP_JOBCATEGORY[spnJobcategory.getSelectedItemPosition()];
                String jobtitle = initialData.getJobtitle();
                float minpay;
                String jobloc = edtJobloc.getText().toString();
                String jobdesc = edtJobdesc.getText().toString();


                /* ENSURE THAT FLOAT FIELD IS NEVER EMPTY TO AVOID CRASH*/
                try {
                    minpay = Float.parseFloat(edtMinpay.getText().toString());


                    /* CHECKS IF ANY FIELDS ARE EMPTY */
                    if (jobcategory.isEmpty() || jobtitle.isEmpty() || jobloc.isEmpty() ||
                            jobdesc.isEmpty()) {
                        Toast.makeText(FreelanceEditPostFieldsActivity.this,
                                "ERROR. You cannot leave fields empty.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                    /* EDIT JOB IF NO FIELD IS EMPTY*/
                        app.editJob(position, jobcategory, jobtitle, minpay, jobloc,
                                jobdesc, fUsername, fUsertype);


                    /* ALERT THAT MAXIMUM SALARY SET TO FREE IF BLANK */
                        if (minpay == 0.0f){
                            Toast.makeText(FreelanceEditPostFieldsActivity.this,
                                    "NOTICE. Maximum salary assumed free. Job edited.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    /* GENERAL ALERT WHEN JOB IS ADDED */
                        else {
                            Toast.makeText(FreelanceEditPostFieldsActivity.this,
                                    "SUCCESS. Job details edited.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    /* REDIRECT TO FREELANCE DASHBOARD ACTIVITY */
                        startActivity(launchFreelanceDashboardActivity);
                        finish();
                    }
                } catch(NumberFormatException e) {
                    Toast.makeText(FreelanceEditPostFieldsActivity.this,
                            "ERROR. You cannot leave fields empty.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
