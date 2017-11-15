package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class FreelanceDashboardActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    private ArrayAdapter<String> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_dashboard);


        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();


        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String fUsername = user.get(workySessionMgt.KEY_USERNAME);


        /* GET USER DATA BASED FROM USERNAME IN SESSION */
        String firstname = app.getFreelancerAcctByUsername(fUsername).getFirstname();
        String education = app.getFreelancerAcctByUsername(fUsername).getEducation();
        String expertise = app.getFreelancerAcctByUsername(fUsername).getExpertise();


        /* SET DASHBOARD DATA */
        TextView txvFfirstname = (TextView) findViewById(R.id.txv_f_firstname);
        TextView txvFeducation = (TextView) findViewById(R.id.txv_f_education);
        TextView txvFexpertise = (TextView) findViewById(R.id.txv_f_expertise);
        txvFfirstname.setText(firstname);
        txvFeducation.setText(education);
        txvFexpertise.setText(expertise);


        /* VIEW PROFILE AS SEEN BY CLIENT */
        Button btnViewProfile = (Button) findViewById(R.id.btn_f_viewprofile);
        btnViewProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent launchFreelanceViewAsClientActivity =
                                new Intent(FreelanceDashboardActivity.this,
                                        FreelanceViewAsClientActivity.class);
                        launchFreelanceViewAsClientActivity.putExtra("ORIGIN", "DASHBOARD");
                        startActivity(launchFreelanceViewAsClientActivity);
                    }
                }
        );

        /* LAUNCH FIND FREELANCERS */
        ImageView imvbtnFind = (ImageView) findViewById(R.id.imvbtn_f_find);
        imvbtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchFreelanceFindJobActivity = new Intent(FreelanceDashboardActivity.this,
                        FreelanceFindActivity.class);
                startActivity(launchFreelanceFindJobActivity);
            }
        });


        /* LAUNCH POST JOB OFFERS */
        ImageView imvbtnPost = (ImageView) findViewById(R.id.imvbtn_f_post);
        imvbtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchFreelancePostOfferActivity = new Intent(FreelanceDashboardActivity.this,
                        FreelancePostActivity.class);
                startActivity(launchFreelancePostOfferActivity);
            }
        });


        /* LAUNCH CLIENT EDIT/DELETE POSTS ACTIVITY */
        ImageView imvbtnEditPost = (ImageView) findViewById(R.id.imvbtn_f_editpost);
        imvbtnEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchFreelanceEditDeletePostActivity = new Intent(FreelanceDashboardActivity.this,
                        FreelanceEditDeletePostActivity.class);
                startActivity(launchFreelanceEditDeletePostActivity);
            }
        });


        /* LAUNCH LOGIN ACTIVITY AFTER LOGOUT */
        ImageView imvbtnLogout = (ImageView) findViewById(R.id.imvbtn_f_logout);
        imvbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginActivity = new Intent(FreelanceDashboardActivity.this,
                        LoginActivity.class);

                /* LOGOUT SESSION */
                session.logoutUser();
                startActivity(launchLoginActivity);
            }
        });


        /* LAUNCH FREELANCER EDIT PROFILE ACTIVITY */
        ImageView imvbtnEditProfile = (ImageView) findViewById(R.id.imvbtn_f_editprofile);
        imvbtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchFreelanceEditProfileActivity = new Intent(FreelanceDashboardActivity.this,
                        FreelanceEditProfileActivity.class);
                startActivity(launchFreelanceEditProfileActivity);
            }
        });

        /* Display ListView of Previous Transactions */
        ListView listJobs = (ListView) findViewById(R.id.lsv_f_joborders);
        ArrayList<workyLinkJob> linkJobs =
                app.getLinkedJobsByTypeFreelancer(user.get(workySessionMgt.KEY_USERNAME));
        ArrayList<String> stringOutput = new ArrayList<>();
        for (int i = 0; i < linkJobs.size(); i++) {
            stringOutput.add("Your Job: " + linkJobs.get(i).getJob().getJobtitle() + "\n"
                    + "Client Applied: " + linkJobs.get(i).getClient().getUsername() + "\n"
                    + "Email: " + linkJobs.get(i).getClient().getEmail() + "\n"
                    + "Contact: " + linkJobs.get(i).getClient().getMobile());
        }
        mAdapter = new ArrayAdapter<String>
                (FreelanceDashboardActivity.this, android.R.layout.simple_list_item_1, stringOutput);
        listJobs.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}