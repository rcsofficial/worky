package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    private ArrayAdapter<workyLinkJob> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_dashboard);

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(FreelanceDashboardActivity.this.getAssets(),
                "nunito.ttf");
        TextView txvSelections = (TextView) findViewById(R.id.lbl_f_selections);
        TextView txvJobOrders = (TextView) findViewById(R.id.lbl_f_joborders);
        txvSelections.setTypeface(font);
        txvJobOrders.setTypeface(font);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();

        app.initializeJobLinks();

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        final String fUsername = user.get(workySessionMgt.KEY_USERNAME);


        /* GET USER DATA BASED FROM USERNAME IN SESSION */

        String firstname = "";
        String education = "";
        String expertise = "";
        try {
            firstname = app.getFreelancerAcctByUsername(fUsername).getFirstname();
            education = app.getFreelancerAcctByUsername(fUsername).getEducation();
            expertise = app.getFreelancerAcctByUsername(fUsername).getExpertise();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            Intent launchLoginActivity = new Intent(FreelanceDashboardActivity.this,
                    LoginActivity.class);
            Toast.makeText(FreelanceDashboardActivity.this,
                    "ERROR: Please login again.",
                    Toast.LENGTH_SHORT).show();
            startActivity(launchLoginActivity);
            finish();
            return;
        }

        /* SET DASHBOARD DATA AND AVATAR*/
        TextView txvFfirstname = (TextView) findViewById(R.id.txv_f_firstname);
        TextView txvFeducation = (TextView) findViewById(R.id.txv_f_education);
        TextView txvFexpertise = (TextView) findViewById(R.id.txv_f_expertise);
        ImageView imvProfPic = (ImageView) findViewById(R.id.imv_f_profpic);

        txvFfirstname.setText(firstname);
        txvFfirstname.setTypeface(font);
        txvFeducation.setText(education);
        txvFexpertise.setText(expertise);

        int icons[] = {
                R.drawable.profpic1,
                R.drawable.profpic2,
                R.drawable.profpic3,
                R.drawable.profpic4
        };

        imvProfPic.setImageResource(icons[app.getFreelancerAcctByUsername(fUsername).getIconCode()]);

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

        /* DISPLAY LISTVIEW OF INTERESTED TRANSACTIONS */
        final ListView listJobs = (ListView) findViewById(R.id.lsv_f_joborders);
        final Handler handler = new Handler();
        mAdapter = new workyMatchesLsvAdapter
                (FreelanceDashboardActivity.this,
                        app.getLinkedJobsByTypeClient(fUsername));
        listJobs.setAdapter(mAdapter);

        /* REDIRECT TO CLIENT PROFILE WHEN ITEM CLICKED */
        listJobs.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent launchClientProfileActivity =
                                new Intent(FreelanceDashboardActivity.this,
                                        ClientProfileActivity.class);
                        launchClientProfileActivity.putExtra("F_USERNAME", fUsername);
                        launchClientProfileActivity.putExtra("C_PROF_POS", position);
                        startActivity(launchClientProfileActivity);
                    }
                }
        );

        class updateAdapter implements Runnable {
            private Handler handler;
            private ArrayAdapter<workyLinkJob> mAdapter;
            public updateAdapter(Handler handler, ArrayAdapter<workyLinkJob> mAdapter) {
                this.handler = handler;
                this.mAdapter = mAdapter;
            }

            @Override
            public void run() {
                this.handler.postDelayed(this, 500);

                ArrayList<workyLinkJob> linkJobs =
                        app.getLinkedJobsByTypeFreelancer(user.get(workySessionMgt.KEY_USERNAME));
                mAdapter.clear();
                mAdapter.addAll(linkJobs);
                mAdapter.notifyDataSetChanged();
            }
        }

        handler.post(new updateAdapter(handler, mAdapter));
    }
}