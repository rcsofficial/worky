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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ClientEditPostFieldsActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit_post_fields);


        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String cUsername = user.get(workySessionMgt.KEY_USERNAME);
        final String cUsertype = user.get(workySessionMgt.KEY_USERTYPE);


        /* SET USERNAME BASED FROM EDIT FIELD IN LOGIN ACTIVITY */
        Intent intentFromClientEditDeletePostActivity = getIntent();
        if (intentFromClientEditDeletePostActivity == null) { return; }
        final int position = intentFromClientEditDeletePostActivity.getIntExtra("C_POST_POS", 0);


        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();


        /* SET INITIAL HINT */
        workyJobs initialData = app.getJobsByUsername(cUsername, "Client").get(position);
        TextView txvOldCategory = (TextView) findViewById(R.id.txv_c_e_oldcategory);
        TextView txvOldJobtitle = (TextView) findViewById(R.id.txv_c_e_oldjobtitle);
        TextView txvOldMaxpay = (TextView) findViewById(R.id.txv_c_e_oldsalary);
        TextView txvOldLocation = (TextView) findViewById(R.id.txv_c_e_oldloc);
        TextView txvOldDesc = (TextView) findViewById(R.id.txv_c_e_olddesc);

        txvOldCategory.setText("Old Category: " + initialData.getJobfield());
        txvOldJobtitle.setText("Old Title: " + initialData.getJobtitle());
        txvOldMaxpay.setText("Old Max Pay: " + String.valueOf(initialData.getSalary()));
        txvOldLocation.setText("Old Location: " + initialData.getLocation());
        txvOldDesc.setText("Old Description: " + initialData.getDescription());



        Button btnEditJob = (Button) findViewById(R.id.btn_c_e_editjob);
        btnEditJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchClientDashboardActivity = new Intent(ClientEditPostFieldsActivity.this,
                        ClientDashboardActivity.class);


                /* LOOKUP TRANSLATION TABLE FOR SPINNER */
                String[] LOOKUP_JOBCATEGORY = { "Agriculture", "Arts", "Clerical", "Education",
                        "Engineering", "Finance", "Health", "Hospitality",
                        "IT", "Legal", "Manufacturing", "Transport", "Others"};


                /* GET DATA FROM USER FIELDS */
                Spinner spnJobcategory = (Spinner) findViewById(R.id.spn_c_e_jobcategory);
                EditText edtJobtitle = (EditText) findViewById(R.id.edt_c_e_jobtitle);
                EditText edtMaxpay = (EditText) findViewById(R.id.edt_c_e_maxpay);
                EditText edtJobloc = (EditText) findViewById(R.id.edt_c_e_jobloc);
                EditText edtJobdesc = (EditText) findViewById(R.id.edt_c_e_jobdesc);


                /* EXTRACT DATA FROM USER FIELDS */
                String jobcategory = LOOKUP_JOBCATEGORY[spnJobcategory.getSelectedItemPosition()];
                String jobtitle = edtJobtitle.getText().toString();
                float maxpay;
                String jobloc = edtJobloc.getText().toString();
                String jobdesc = edtJobdesc.getText().toString();


                /* ENSURE THAT FLOAT FIELD IS NEVER EMPTY TO AVOID CRASH*/
                try {
                    maxpay = Float.parseFloat(edtMaxpay.getText().toString());


                    /* CHECKS IF ANY FIELDS ARE EMPTY */
                    if (jobcategory.isEmpty() || jobtitle.isEmpty() || jobloc.isEmpty() ||
                            jobdesc.isEmpty()) {
                        Toast.makeText(ClientEditPostFieldsActivity.this,
                                "ERROR. You cannot leave fields empty.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                    /* EDIT JOB IF NO FIELD IS EMPTY*/
                        app.editJob(position, jobcategory, jobtitle, maxpay, jobloc,
                                jobdesc, cUsername, cUsertype);


                    /* ALERT THAT MAXIMUM SALARY SET TO FREE IF BLANK */
                        if (maxpay == 0.0f){
                            Toast.makeText(ClientEditPostFieldsActivity.this,
                                    "NOTICE. Maximum salary assumed free. Job edited.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    /* GENERAL ALERT WHEN JOB IS ADDED */
                        else {
                            Toast.makeText(ClientEditPostFieldsActivity.this,
                                    "SUCCESS. Job details edited.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    /* REDIRECT TO CLIENT DASHBOARD ACTIVITY */
                        startActivity(launchClientDashboardActivity);
                        finish();
                    }
                } catch(NumberFormatException e) {
                    Toast.makeText(ClientEditPostFieldsActivity.this,
                            "ERROR. You cannot leave fields empty.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
