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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The activity class where a client can add a job. Redirects to
 * <code>ClientDashboardActivity</code> when a job is successfully added.
 *
 * @see ClientDashboardActivity
 */
public class ClientPostActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_post);

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(ClientPostActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblPost = (TextView) findViewById(R.id.lbl_c_postpage);
        TextView lblTips = (TextView) findViewById(R.id.lbl_c_tips);
        lblPost.setTypeface(font);
        lblTips.setTypeface(font);

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String cUsername = user.get(workySessionMgt.KEY_USERNAME);
        final String cUsertype = user.get(workySessionMgt.KEY_USERTYPE);

        /* EDIT TEXTVIEW FOR POSTING TIPS WITH RANDOM TIPS */
        TextView txvCtips = (TextView) findViewById(R.id.txv_c_tips);
        txvCtips.setText(randomTipsGen());

        ImageView btnPostJob = (ImageView) findViewById(R.id.btn_c_postjob);
        btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchClientDashboardActivity = new Intent(ClientPostActivity.this,
                        ClientDashboardActivity.class);

                /* APPLICATION OBJECT */
                final workyApplication app = (workyApplication) getApplication();

                /* GET DATA FROM USER FIELDS */
                Spinner spnJobcategory = (Spinner) findViewById(R.id.spn_c_jobcategory);
                EditText edtJobtitle = (EditText) findViewById(R.id.edt_c_jobtitle);
                EditText edtMaxpay = (EditText) findViewById(R.id.edt_c_maxpay);
                EditText edtJobloc = (EditText) findViewById(R.id.edt_c_jobloc);
                EditText edtJobdesc = (EditText) findViewById(R.id.edt_c_jobdesc);

                /* LOOKUP TRANSLATION TABLE FOR SPINNER */
                String[] LOOKUP_JOBCATEGORY = { "Agriculture", "Arts", "Clerical", "Education",
                        "Engineering", "Finance", "Health", "Hospitality",
                        "IT", "Legal", "Manufacturing", "Transport", "Others"};

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
                        Toast.makeText(ClientPostActivity.this,
                                "ERROR. You cannot leave fields empty.",
                                Toast.LENGTH_SHORT).show();
                    }

                    else {
                    /* CREATE JOB IF NO FIELD IS EMPTY*/
                        app.addJob(new workyJobs(jobcategory, jobtitle, maxpay, jobloc,
                                jobdesc, cUsername, cUsertype));

                    /* ALERT THAT MAXIMUM SALARY SET TO FREE IF BLANK */
                        if (maxpay == 0.0f){
                            Toast.makeText(ClientPostActivity.this,
                                    "NOTICE. Maximum salary assumed free. Job added to list.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    /* GENERAL ALERT WHEN JOB IS ADDED */
                        else {
                            Toast.makeText(ClientPostActivity.this,
                                    "SUCCESS. Job added to list.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    /* REDIRECT TO CLIENT DASHBOARD ACTIVITY */
                        startActivity(launchClientDashboardActivity);
                        finish();
                    }
                } catch(NumberFormatException e) {
                    Toast.makeText(ClientPostActivity.this,
                            "ERROR. You cannot leave fields empty.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    public String randomTipsGen() {
        /* ARRAY LIST OF RANDOM TIPS */
        ArrayList<String> randomTips = new ArrayList<>();
        randomTips.add("REMEMBER TO BE HUMAN. Job postings whose job descriptions" +
                " appeal to emotion generally attract more applicants.");
        randomTips.add("TELL YOUR STORY. Giving a reason for the job hiring that is more" +
                " personal interest applicants and will be more attracted to apply.");
        randomTips.add("SELL THE POSITION. Provide information about work hours, coworkers," +
                " learning opportunities, and more beyond the usual requirements.");
        randomTips.add("APPLICANTS DESERVE AN EXPLANATION. Give them a reason why they should" +
                " apply for you instead of thousands of other similar jobs with competitive" +
                " price offers.");
        randomTips.add("TITLES DO MATTER. While title is not everything, it is the first thing" +
                " that you hear when someone asks for the job you do. Be creative in using" +
                " killer job titles!");
        randomTips.add("BE STRAIGHTFORWARD. Using too much unnecessary flowery words may" +
                " appear scamming. Provide only the essential information.");
        randomTips.add("KNOW YOUR COMPETITION. There are thousand other clients competing to" +
                " get the best freelancers. Make your offer competitive.");
        randomTips.add("BE RESPONSIVE. When a freelancer calls to inquire, don't slack off!" +
                " Not responding immediately translates to your lack of concern.");

        /* ALGORITHM FOR DRAWING RANDOM TIPS */
        Random rand = new Random();
        int indexTip = rand.nextInt(randomTips.size());
        return randomTips.get(indexTip);
    }
}