package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * It the class for the activity where a freelancer can view his profile as a client. A client
 * views this when selecting a job posted by a freelancer where the freelancer details and the job is
 * listed.
 */
public class FreelanceViewAsClientActivity extends AppCompatActivity {
    workySessionMgt session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_view_as_client);

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(FreelanceViewAsClientActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblAbout = (TextView) findViewById(R.id.lbl_f_v_about);
        lblAbout.setTypeface(font);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication)getApplication();

        /* LOGIN SESSION MANAGEMENT */
        session = new workySessionMgt(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        String fUsername;

        final Intent recvdIntent = getIntent();

        /* GET INFO AND SET TEXT VIEWS ACCORDINGLY */
        TextView txvUserName = (TextView) findViewById(R.id.txv_f_v_username);
        TextView txvFullName = (TextView) findViewById(R.id.txv_f_v_fullname);
        TextView txvEducation = (TextView) findViewById(R.id.txv_f_v_education);
        TextView txvExpertise = (TextView) findViewById(R.id.txv_f_v_expertise);
        TextView txvCourse = (TextView) findViewById(R.id.txv_f_v_course);
        TextView txvProfile = (TextView) findViewById(R.id.txv_f_v_profile);
        TextView txvAge = (TextView) findViewById(R.id.txv_f_v_age);
        TextView txvGender = (TextView) findViewById(R.id.txv_f_v_gender);
        TextView txvLocation = (TextView) findViewById(R.id.txv_f_v_location);
        TextView txvMobile = (TextView) findViewById(R.id.txv_f_v_mobile);
        TextView txvEmail = (TextView) findViewById(R.id.txv_f_v_email);
        ImageView imvJobIcon = (ImageView) findViewById(R.id.imv_f_v_jobicon);

        if (recvdIntent.getStringExtra("ORIGIN").equals("DASHBOARD")){
            fUsername = user.get(workySessionMgt.KEY_USERNAME);
        }
        else {
            String accountType = recvdIntent.getStringExtra("F_ACCOUNTTYPE");
            fUsername = recvdIntent.getStringExtra("F_USERNAME");
            String title = recvdIntent.getStringExtra("F_TITLE");
            final workyJobs job = app.getJobByTypeUsernameTitle(accountType, fUsername, title);

            TextView txvTitle = (TextView) findViewById(R.id.txv_f_v_title);
            TextView txvField = (TextView) findViewById(R.id.txv_f_v_job_field);
            TextView txvSalary = (TextView) findViewById(R.id.txv_f_v_salary);
            TextView txvJobLocation = (TextView) findViewById(R.id.txv_f_v_job_location);
            TextView txvDescription = (TextView) findViewById(R.id.txv_f_v_description);
            txvTitle.setTypeface(font);

            txvField.setText(job.getJobfield());
            txvTitle.setText(job.getJobtitle());
            txvSalary.setText(String.valueOf(job.getSalary()));
            txvJobLocation.setText(job.getLocation());
            txvDescription.setText(job.getDescription());

            int icons[] = {
                    R.drawable.job1,
                    R.drawable.job2,
                    R.drawable.job3,
                    R.drawable.job4,
                    R.drawable.job5,
                    R.drawable.job6,
                    R.drawable.job7,
                    R.drawable.job8,
                    R.drawable.job9,
                    R.drawable.job10,
                    R.drawable.job11,
                    R.drawable.job12,
                    R.drawable.job13
            };
            imvJobIcon.setImageResource(icons[job.getJobicon()]);

            /* ADDS JOB LINK WHEN INTERESTED, REDIRECT TO DASHBOARD */
            ImageView btnSubmit = (ImageView) findViewById(R.id.btn_f_v_submit);
            btnSubmit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent launchClientDashboardActivity =
                                    new Intent(FreelanceViewAsClientActivity.this,
                                            ClientDashboardActivity.class);

                            app.addLinkJob("Freelancer", user.get(workySessionMgt.KEY_USERNAME),
                                    recvdIntent.getStringExtra("F_USERNAME"), job);

                            Toast.makeText(FreelanceViewAsClientActivity.this,
                                    "The freelancer has been informed of your interest.",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(launchClientDashboardActivity);
                            finish();
                        }
                    }
            );
        }

        /* SET ABOUT CLIENT DETAILS */
        txvUserName.setText(fUsername);
        String fullname = app.getFreelancerAcctByUsername(fUsername).getFirstname() + " "
                + app.getFreelancerAcctByUsername(fUsername).getMiddlename() + " "
                + app.getFreelancerAcctByUsername(fUsername).getLastname();
        txvFullName.setText(fullname);
        txvEducation.setText(app.getFreelancerAcctByUsername(fUsername).getEducation());
        txvExpertise.setText
                (app.getFreelancerAcctByUsername(fUsername).getExpertise());
        txvCourse.setText
                (app.getFreelancerAcctByUsername(fUsername).getCourse());
        txvProfile.setText
                (app.getFreelancerAcctByUsername(fUsername).getProfile());
        txvAge.setText
                (Long.toString(app.getFreelancerAcctByUsername(fUsername).getAge()));
        txvGender.setText
                (app.getFreelancerAcctByUsername(fUsername).getGender());
        txvLocation.setText
                (app.getFreelancerAcctByUsername(fUsername).getLocation());
        txvMobile.setText
                (Long.toString(app.getFreelancerAcctByUsername(fUsername).getMobile()));
        txvEmail.setText
                (app.getFreelancerAcctByUsername(fUsername).getEmail());
    }

}
