package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.os.Handler;
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

/**
 * The activity class for the freelancer dash board. <code>LoginActivity</code> redirects here when
 * user signed up as a freelancer. <code>FreelancerSignupActivity</code> also redirects here when a freelancer
 * is able to successfully sign up. From here the client can redirect to <code>FreelanceEditDeletePostActivity</code>
 * where the he can edit or delete his jobs, <code>FreelanceEditProfileActivity</code> where he
 * can edit his profile, <code>FreelanceFindActivity</code> where he can find jobs,
 * <code>FreelancePostActivity</code> where he can post jobs, and <code>FreelanceViewAsClientActivity</code>
 * where he can view his profile as seen by freelancers.
 *
 * @see LoginActivity
 * @see ClientSignupActivity
 * @see ClientEditDeletePostActivity
 * @see ClientFindActivity
 * @see ClientEditProfileActivity
 * @see ClientPostActivity
 * @see ClientViewAsFreelancerActivity
 */
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

        app.initializeJobLinks();

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
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
        final ListView listJobs = (ListView) findViewById(R.id.lsv_f_joborders);
        final Handler handler = new Handler();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listJobs.setAdapter(mAdapter);

        class updateAdapter  implements Runnable {
            private Handler handler;
            private ArrayAdapter<String> mAdapter;
            public updateAdapter(Handler handler, ArrayAdapter<String> mAdapter) {
                this.handler = handler;
                this.mAdapter = mAdapter;
            }

            @Override
            public void run() {
                this.handler.postDelayed(this, 500);

                ArrayList<workyLinkJob> linkJobs =
                        app.getLinkedJobsByTypeFreelancer(user.get(workySessionMgt.KEY_USERNAME));
                ArrayList<String> stringOutput = new ArrayList<>();
                for (int i = 0; i < linkJobs.size(); i++) {
                    stringOutput.add("Your Job: " + linkJobs.get(i).getJob().getJobtitle() + "\n"
                            + "Client Applied: " + linkJobs.get(i).getClient().getUsername() + "\n"
                            + "Email: " + linkJobs.get(i).getClient().getEmail() + "\n"
                            + "Contact: " + linkJobs.get(i).getClient().getMobile());
                }
                mAdapter.clear();
                mAdapter.addAll(stringOutput);
                mAdapter.notifyDataSetChanged();
            }
        }
        handler.post(new updateAdapter(handler, mAdapter));
    }
}