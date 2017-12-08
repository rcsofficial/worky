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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The activity class for the client dash board. <code>LoginActivity</code> redirects here when
 * user signed up as a client. <code>ClientSignupActivity</code> also redirects here when a client
 * is able to successfully sign up. From here the client can redirect to <code>ClientEditDeletePostActivity</code>
 * where the he can edit or delete his jobs, <code>ClientEditProfileActivity</code> where he
 * can edit his profile, <code>ClientFindActivity</code> where he can find jobs,
 * <code>ClientPostActivity</code> where he can post jobs, and <code>ClientViewAsFreelancerActivity</code>
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
public class ClientDashboardActivity extends AppCompatActivity {

    private ArrayAdapter<workyLinkJob> mAdapter = null;

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(ClientDashboardActivity.this.getAssets(),
                "nunito.ttf");
        TextView txvSelections = (TextView) findViewById(R.id.lbl_c_selections);
        TextView txvJobOrders = (TextView) findViewById(R.id.lbl_c_joborders);
        txvSelections.setTypeface(font);
        txvJobOrders.setTypeface(font);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();
        app.initializeJobLinks();

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        final HashMap<String, String> user = session.getUserDetails();
        final String cUsername = user.get(workySessionMgt.KEY_USERNAME);

        /* GET USER DATA BASED FROM USERNAME IN SESSION */

        String firstname = "";
        String company = "";
        String field = "";
        try {
            firstname = app.getClientAcctByUsername(cUsername).getFirstname();
            company = app.getClientAcctByUsername(cUsername).getCompany();
            field = app.getClientAcctByUsername(cUsername).getField();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            Intent launchLoginActivity = new Intent(ClientDashboardActivity.this,
                    LoginActivity.class);
            Toast.makeText(ClientDashboardActivity.this,
                    "ERROR: Please login again.",
                    Toast.LENGTH_SHORT).show();
            startActivity(launchLoginActivity);
            finish();
            return;
        }

        // String firstname = app.getClientAcctByUsername(cUsername).getFirstname();
        // String company = app.getClientAcctByUsername(cUsername).getCompany();
        // String field = app.getClientAcctByUsername(cUsername).getField();

        /* SET DASHBOARD DATA */
        TextView txvCfirstname = (TextView) findViewById(R.id.txv_c_firstname);
        TextView txvCcompany = (TextView) findViewById(R.id.txv_c_company);
        TextView txvCfield = (TextView) findViewById(R.id.txv_c_field);
        txvCfirstname.setText(firstname);
        txvCfirstname.setTypeface(font);
        txvCcompany.setText(company);
        txvCfield.setText(field);

        /* Set Client Avatar */
        int icons[] = {R.drawable.profpic1, R.drawable.profpic2, R.drawable.profpic3, R.drawable.profpic4};
        ImageView imvProfPic = (ImageView) findViewById(R.id.imv_c_profpic);
        imvProfPic.setImageResource(icons[app.getClientAcctByUsername(cUsername).getIconCode()]);

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

        /* DISPLAY LISTVIEW OF INTERESTED TRANSACTIONS */
        final ListView listJobs = (ListView) findViewById(R.id.lsv_c_joborders);
        final Handler handler = new Handler();
        mAdapter = new workyMatchesLsvAdapter
                (ClientDashboardActivity.this,
                        app.getLinkedJobsByTypeClient(cUsername));

        listJobs.setAdapter(mAdapter);

        /* REDIRECT TO FREELANCER PROFILE WHEN ITEM CLICKED */
        listJobs.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent launchFreelanceProfileActivity =
                                new Intent(ClientDashboardActivity.this,
                                        FreelanceProfileActivity.class);
                        launchFreelanceProfileActivity.putExtra("C_USERNAME", cUsername);
                        launchFreelanceProfileActivity.putExtra("F_PROF_POS", position);
                        startActivity(launchFreelanceProfileActivity);
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
                        app.getLinkedJobsByTypeClient(user.get(workySessionMgt.KEY_USERNAME));
                mAdapter.clear();
                mAdapter.addAll(linkJobs);
                mAdapter.notifyDataSetChanged();
            }
        }

        handler.post(new updateAdapter(handler, mAdapter));
    }
}