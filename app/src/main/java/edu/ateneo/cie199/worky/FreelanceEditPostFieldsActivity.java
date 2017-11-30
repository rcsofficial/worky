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

import java.util.HashMap;

/**
 * The activity class where a freelancer can edit his jobs.
 */
public class FreelanceEditPostFieldsActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_edit_post_fields);

        /* SET HEADER FONT */
        Typeface font = Typeface.createFromAsset(FreelanceEditPostFieldsActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblEditPost = (TextView) findViewById(R.id.lbl_f_e_editpostpage);
        lblEditPost.setTypeface(font);

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

        /* DATA USER FIELDS */
        final Spinner spnJobcategory = (Spinner) findViewById(R.id.spn_f_e_jobcategory);
        final EditText edtMinpay = (EditText) findViewById(R.id.edt_f_e_minpay);
        final EditText edtJobloc = (EditText) findViewById(R.id.edt_f_e_jobloc);
        final EditText edtJobdesc = (EditText) findViewById(R.id.edt_f_e_jobdesc);

        /* SET INITIAL HINT */
        final workyJobs initialData = app.getJobsByUsername(fUsername, "Freelancer").get(position);
        TextView txvOldJobtitle = (TextView) findViewById(R.id.txv_f_e_oldjobtitle);

            /* CONVERT JOB FIELD TO INT */
            String initialJobField = initialData.getJobfield();

            int initialJobFieldPos = 0;
            String[] LOOKUP_JOBCATEGORY = { "Agriculture", "Arts", "Clerical", "Education",
                    "Engineering", "Finance", "Health", "Hospitality",
                    "IT", "Legal", "Manufacturing", "Transport", "Others"};

            for (int i=0; i<13; i++) {
                if (initialJobField.equals(LOOKUP_JOBCATEGORY[i]))
                    initialJobFieldPos = i;
            }

        txvOldJobtitle.setText(initialData.getJobtitle());
        spnJobcategory.setSelection(initialJobFieldPos);
        edtMinpay.setText(String.valueOf(initialData.getSalary()));
        edtJobloc.setText(initialData.getLocation());
        edtJobdesc.setText(initialData.getDescription());


        ImageView btnEditJob = (ImageView) findViewById(R.id.btn_f_e_editjob);
        btnEditJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchFreelanceDashboardActivity = new Intent(FreelanceEditPostFieldsActivity.this,
                        FreelanceDashboardActivity.class);

                /* LOOKUP TRANSLATION TABLE FOR SPINNER */
                String[] LOOKUP_JOBCATEGORY = { "Agriculture", "Arts", "Clerical", "Education",
                        "Engineering", "Finance", "Health", "Hospitality",
                        "IT", "Legal", "Manufacturing", "Transport", "Others"};

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
