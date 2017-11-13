package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class ClientDashboardActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);


        /* APPLICATION OBJECT */
        workyApplication app = (workyApplication)getApplication();


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

    }
}