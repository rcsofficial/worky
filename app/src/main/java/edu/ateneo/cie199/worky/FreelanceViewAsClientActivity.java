package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView lblAbout = (TextView) findViewById(R.id.lbl_c_v_about);
        lblAbout.setTypeface(font);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication)getApplication();

        /* LOGIN SESSION MANAGEMENT */
        session = new workySessionMgt(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        String cUsername;

        final Intent recvdIntent = getIntent();

        /* GET INFO AND SET TEXT VIEWS ACCORDINGLY */
        TextView txvUserName = (TextView) findViewById(R.id.txv_f_v_username);
        TextView txvFullName = (TextView) findViewById(R.id.txv_f_v_fullname);
        TextView txvExpertise = (TextView) findViewById(R.id.txv_f_v_expertise);
        TextView txvCourse = (TextView) findViewById(R.id.txv_f_v_course);
        TextView txvProfile = (TextView) findViewById(R.id.txv_f_v_profile);
        TextView txvAge = (TextView) findViewById(R.id.txv_f_v_age);
        TextView txvGender = (TextView) findViewById(R.id.txv_f_v_gender);
        TextView txvLocation = (TextView) findViewById(R.id.txv_f_v_location);
        TextView txvMobile = (TextView) findViewById(R.id.txv_f_v_mobile);
        TextView txvEmail = (TextView) findViewById(R.id.txv_f_v_email);
        ImageView imvProfPic = (ImageView) findViewById(R.id.imv_f_v_profpic);

        if (recvdIntent.getStringExtra("ORIGIN").equals("DASHBOARD")){
            cUsername = user.get(workySessionMgt.KEY_USERNAME);
        }
        else {
            String accountType = recvdIntent.getStringExtra("F_ACCOUNTTYPE");
            cUsername = recvdIntent.getStringExtra("F_USERNAME");
            String title = recvdIntent.getStringExtra("F_TITLE");
            final workyJobs job = app.getJobByTypeUsernameTitle(accountType, cUsername, title);

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
                            startActivity(launchClientDashboardActivity);
                            finish();
                        }
                    }
            );
        }

        txvUserName.setText(cUsername);
        String fullname = app.getFreelancerAcctByUsername(cUsername).getFirstname() + " "
                + app.getFreelancerAcctByUsername(cUsername).getMiddlename() + " "
                + app.getFreelancerAcctByUsername(cUsername).getLastname();
        txvFullName.setText(fullname);
        txvExpertise.setText
                (app.getFreelancerAcctByUsername(cUsername).getExpertise());
        txvCourse.setText
                (app.getFreelancerAcctByUsername(cUsername).getCourse());
        txvProfile.setText
                (app.getFreelancerAcctByUsername(cUsername).getProfile());
        txvAge.setText
                (Long.toString(app.getFreelancerAcctByUsername(cUsername).getAge()));
        txvGender.setText
                (app.getFreelancerAcctByUsername(cUsername).getGender());
        txvLocation.setText
                (app.getFreelancerAcctByUsername(cUsername).getLocation());
        txvMobile.setText
                (Long.toString(app.getFreelancerAcctByUsername(cUsername).getMobile()));
        txvEmail.setText
                (app.getFreelancerAcctByUsername(cUsername).getEmail());

        /* Set Client Avatar */
        int icons[] = {R.drawable.profpic1, R.drawable.profpic2, R.drawable.profpic3, R.drawable.profpic4};
        imvProfPic.setImageResource(icons[app.getFreelancerAcctByUsername(cUsername).getIconCode()]);

    }

}
