package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class FreelanceViewAsClientActivity extends AppCompatActivity {
    workySessionMgt session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_view_as_client);
        final workyApplication app = (workyApplication)getApplication();

        session = new workySessionMgt(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        String cUsername;

        final Intent recvdIntent = getIntent();

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

        if (recvdIntent.getStringExtra("ORIGIN").equals("DASHBOARD")){
            cUsername = user.get(workySessionMgt.KEY_USERNAME);
        }
        else {
            String accountType = recvdIntent.getStringExtra("F_ACCOUNTTYPE");
            cUsername = recvdIntent.getStringExtra("F_USERNAME");
            String title = recvdIntent.getStringExtra("F_TITLE");
            final workyJobs job = app.getJobByTypeUsernameTitle(accountType, cUsername, title);

            TextView txvField = (TextView) findViewById(R.id.txv_f_v_field);
            TextView txvTitle = (TextView) findViewById(R.id.txv_f_v_title);
            TextView txvSalary = (TextView) findViewById(R.id.txv_f_v_salary);
            TextView txvJobLocation = (TextView) findViewById(R.id.txv_f_v_job_location);
            TextView txvDescription = (TextView) findViewById(R.id.txv_f_v_description);

            txvField.setText(job.getJobfield());
            txvTitle.setText(job.getJobtitle());
            txvSalary.setText(String.valueOf(job.getSalary()));
            txvJobLocation.setText(job.getLocation());
            txvDescription.setText(job.getDescription());

            Button btnSubmit = (Button) findViewById(R.id.btn_f_v_submit);
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
                        }
                    }
            );
        }

        txvUserName.setText(cUsername);
        String fullname = app.getFreelancerAcctByUsername(cUsername).getFirstname() + " "
                + app.getFreelancerAcctByUsername(cUsername).getMiddlename() + " "
                + app.getFreelancerAcctByUsername(cUsername).getLastname();
        txvFullName.setText(fullname);
        txvExpertise.setText(app.getFreelancerAcctByUsername(cUsername).getExpertise());
        txvCourse.setText(app.getFreelancerAcctByUsername(cUsername).getCourse());
        txvProfile.setText(app.getFreelancerAcctByUsername(cUsername).getProfile());
        txvAge.setText(Long.toString(app.getFreelancerAcctByUsername(cUsername).getAge()));
        txvGender.setText(app.getFreelancerAcctByUsername(cUsername).getGender());
        txvLocation.setText(app.getFreelancerAcctByUsername(cUsername).getLocation());
        txvMobile.setText(Long.toString(app.getFreelancerAcctByUsername(cUsername).getMobile()));
        txvEmail.setText(app.getFreelancerAcctByUsername(cUsername).getEmail());

    }

}
