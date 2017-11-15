package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class ClientViewAsFreelancerActivity extends AppCompatActivity {
    workySessionMgt session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view_as_freelancer);

        final workyApplication app = (workyApplication)getApplication();

        session = new workySessionMgt(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        String cUsername;

        final Intent recvdIntent = getIntent();


        TextView txvUserName = (TextView) findViewById(R.id.txv_c_v_username);
        TextView txvFullName = (TextView) findViewById(R.id.txv_c_v_fullname);
        TextView txvCompany = (TextView) findViewById(R.id.txv_c_v_company);
        TextView txvField = (TextView) findViewById(R.id.txv_c_v_field);
        TextView txvSpecialization = (TextView) findViewById(R.id.txv_c_v_specialization);
        TextView txvProfile = (TextView) findViewById(R.id.txv_c_v_profile);
        TextView txvAge = (TextView) findViewById(R.id.txv_c_v_age);
        TextView txvGender = (TextView) findViewById(R.id.txv_c_v_gender);
        TextView txvLocation = (TextView) findViewById(R.id.txv_c_v_location);
        TextView txvMobile = (TextView) findViewById(R.id.txv_c_v_mobile);
        TextView txvEmail = (TextView) findViewById(R.id.txv_c_v_email);


        if (recvdIntent.getStringExtra("ORIGIN").equals("DASHBOARD")){
            cUsername = user.get(workySessionMgt.KEY_USERNAME);
        }
        else {
            String accountType = recvdIntent.getStringExtra("C_ACCOUNTTYPE");
            cUsername = recvdIntent.getStringExtra("C_USERNAME");
            String title = recvdIntent.getStringExtra("C_TITLE");
            final workyJobs job = app.getJobByTypeUsernameTitle(accountType, cUsername, title);

            TextView txvJobField = (TextView) findViewById(R.id.txv_c_v_job_field);
            TextView txvTitle = (TextView) findViewById(R.id.txv_c_v_title);
            TextView txvSalary = (TextView) findViewById(R.id.txv_c_v_salary);
            TextView txvJobLocation = (TextView) findViewById(R.id.txv_c_v_job_location);
            TextView txvDescription = (TextView) findViewById(R.id.txv_c_v_description);

            txvJobField.setText(job.getJobfield());
            txvTitle.setText(job.getJobtitle());
            txvSalary.setText(String.valueOf(job.getSalary()));
            txvJobLocation.setText(job.getLocation());
            txvDescription.setText(job.getDescription());

            Button btnSubmit = (Button) findViewById(R.id.btn_c_v_submit);
            btnSubmit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent launchFreelanceDashboardActivity =
                                    new Intent(ClientViewAsFreelancerActivity.this,
                                            FreelanceDashboardActivity.class);

                            app.addLinkJob("Client", recvdIntent.getStringExtra("C_USERNAME"),
                                    user.get(workySessionMgt.KEY_USERNAME), job);

                            startActivity(launchFreelanceDashboardActivity);
                        }
                    }
            );
        }

        txvUserName.setText(cUsername);
        String fullname = app.getClientAcctByUsername(cUsername).getFirstname() + " "
                + app.getClientAcctByUsername(cUsername).getMiddlename() + " "
                + app.getClientAcctByUsername(cUsername).getLastname();
        txvFullName.setText(fullname);
        txvCompany.setText(app.getClientAcctByUsername(cUsername).getCompany());
        txvField.setText(app.getClientAcctByUsername(cUsername).getField());
        txvSpecialization.setText(app.getClientAcctByUsername(cUsername).getSpecialization());
        txvProfile.setText(app.getClientAcctByUsername(cUsername).getProfile());
        txvAge.setText(Long.toString(app.getClientAcctByUsername(cUsername).getAge()));
        txvGender.setText(app.getClientAcctByUsername(cUsername).getGender());
        txvLocation.setText(app.getClientAcctByUsername(cUsername).getLocation());
        txvMobile.setText(Long.toString(app.getClientAcctByUsername(cUsername).getMobile()));
        txvEmail.setText(app.getClientAcctByUsername(cUsername).getEmail());

    }
}