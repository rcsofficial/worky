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

public class ClientDashboardActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter = null;

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);


        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();


        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String cUsername = user.get(workySessionMgt.KEY_USERNAME);


        /* GET USER DATA BASED FROM USERNAME IN SESSION */
        String firstname = app.getClientAcctByUsername(cUsername).getFirstname();
        String company = app.getClientAcctByUsername(cUsername).getCompany();
        String field = app.getClientAcctByUsername(cUsername).getField();


        /* SET DASHBOARD DATA */
        TextView txvCfirstname = (TextView) findViewById(R.id.txv_c_firstname);
        TextView txvCcompany = (TextView) findViewById(R.id.txv_c_company);
        TextView txvCfield = (TextView) findViewById(R.id.txv_c_field);
        txvCfirstname.setText(firstname);
        txvCcompany.setText(company);
        txvCfield.setText(field);

        // TODO: View Profile As Seen by Freelancer, Redirect to own Handshaking Page

        /* LAUNCH FIND SERVICES OFFERED */
        ImageView imvbtnFind = (ImageView) findViewById(R.id.imvbtn_c_find);
        imvbtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchClientFindActivity = new Intent(ClientDashboardActivity.this,
                        ClientFindActivity.class);
                startActivity(launchClientFindActivity);
            }
        });


        /* LAUNCH POST JOB OFFERS */
        ImageView imvbtnPost = (ImageView) findViewById(R.id.imvbtn_c_post);
        imvbtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchClientPostActivity = new Intent(ClientDashboardActivity.this,
                        ClientPostActivity.class);
                startActivity(launchClientPostActivity);
            }
        });


        /* LAUNCH CLIENT EDIT/DELETE POSTS ACTIVITY */
        ImageView imvbtnEditPost = (ImageView) findViewById(R.id.imvbtn_c_editpost);
        imvbtnEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchClientEditDeletePostActivity = new Intent(ClientDashboardActivity.this,
                        ClientEditDeletePostActivity.class);
                startActivity(launchClientEditDeletePostActivity);
            }
        });


        /* LAUNCH LOGIN ACTIVITY AFTER LOGOUT */
        ImageView imvbtnLogout = (ImageView) findViewById(R.id.imvbtn_c_logout);
        imvbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchLoginActivity = new Intent(ClientDashboardActivity.this,
                        LoginActivity.class);

                /* LOGOUT SESSION */
                session.logoutUser();
                startActivity(launchLoginActivity);
            }
        });


        /* LAUNCH CLIENT EDIT PROFILE ACTIVITY */
        ImageView imvbtnEditProfile = (ImageView) findViewById(R.id.imvbtn_c_editprofile);
        imvbtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchClientEditProfileActivity = new Intent(ClientDashboardActivity.this,
                        ClientEditProfileActivity.class);
                startActivity(launchClientEditProfileActivity);
            }
        });

        // TODO: Display ListView of Previous Transactions

        ListView listJobs = (ListView) findViewById(R.id.lsv_c_joborders);
        ArrayList<workyLinkJob> linkJobs = app.getLinkedJobsByTypeClient(user.get(workySessionMgt.KEY_USERNAME));
        ArrayList<String> stringOutput = new ArrayList<>();
        for (int i = 0; i < linkJobs.size(); i++) {
            stringOutput.add("Your Job: " + linkJobs.get(i).getJob().getJobtitle() + "\n"
                                + "Freelancer Applied: " + linkJobs.get(i).getFreelancer().getUsername() + "\n"
                                + "Email: " + linkJobs.get(i).getFreelancer().getEmail() + "\n"
                                + "Contact: " + linkJobs.get(i).getFreelancer().getMobile());
        }
        mAdapter = new ArrayAdapter<String>(ClientDashboardActivity.this, android.R.layout.simple_list_item_1, stringOutput);
        listJobs.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        Button btnViewProfile = (Button) findViewById(R.id.btn_c_viewprofile);
        btnViewProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent launchClientViewAsFreelanceActivity = new Intent(ClientDashboardActivity.this,
                                ClientViewAsFreelancerActivity.class);
                        launchClientViewAsFreelanceActivity.putExtra("ORIGIN", "DASHBOARD");
                        startActivity(launchClientViewAsFreelanceActivity);
                    }
                }
        );

    }
}