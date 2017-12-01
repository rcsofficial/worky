package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The activity that provides client profile information and previous matches for freelancers
 */
public class ClientProfileActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    /* LIST VIEW ARRAY ADAPTER */
    private ArrayAdapter<workyLinkJob> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        /* SET HEADER FONT */
        Typeface font = Typeface.createFromAsset(ClientProfileActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblClientProf = (TextView) findViewById(R.id.lbl_c_vp_clientprofile);
        TextView lblJobOrders = (TextView) findViewById(R.id.lbl_c_vp_joborders);
        lblClientProf.setTypeface(font);
        lblJobOrders.setTypeface(font);
        
        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();

        /* GET USER DETAILS FROM INTENT*/
        Intent intentFromFreelanceDashboardActivity = getIntent();
        if (intentFromFreelanceDashboardActivity == null) { return; }
        String fUsername = intentFromFreelanceDashboardActivity.getStringExtra("F_USERNAME");
        int position = intentFromFreelanceDashboardActivity.getIntExtra("C_PROF_POS", 0);
        
         /* GET TEXTVIEW AND IMAGEVIEW CONTENTS */
        TextView txvUserName = (TextView) findViewById(R.id.txv_c_vp_username);
        TextView txvFullName = (TextView) findViewById(R.id.txv_c_vp_fullname);
        TextView txvCompany = (TextView) findViewById(R.id.txv_c_vp_company);
        TextView txvField = (TextView) findViewById(R.id.txv_c_vp_field);
        TextView txvSpecialization = (TextView) findViewById(R.id.txv_c_vp_specialization);
        TextView txvProfile = (TextView) findViewById(R.id.txv_c_vp_profile);
        TextView txvAge = (TextView) findViewById(R.id.txv_c_vp_age);
        TextView txvGender = (TextView) findViewById(R.id.txv_c_vp_gender);
        TextView txvLocation = (TextView) findViewById(R.id.txv_c_vp_location);
        TextView txvMobile = (TextView) findViewById(R.id.txv_c_vp_mobile);
        TextView txvEmail = (TextView) findViewById(R.id.txv_c_vp_email);
        ImageView imvProfPic = (ImageView) findViewById(R.id.imv_c_vp_profpic);

        /* SET TEXTVIEW AND IMAGEVIEW CONTENTS */
        final workyClient client =
                app.getLinkedJobsByTypeFreelancer(fUsername).get(position).getClient();

        int icons[] = {
                R.drawable.profpic1,
                R.drawable.profpic2,
                R.drawable.profpic3,
                R.drawable.profpic4
        };

        txvFullName.setText
                (client.getFirstname() + " " +
                        client.getMiddlename() + " " +
                        client.getLastname());
        txvUserName.setText(client.getUsername());
        txvCompany.setText(client.getCompany());
        txvField.setText(client.getField());
        txvSpecialization.setText(client.getSpecialization());
        txvProfile.setText(client.getProfile());
        txvAge.setText(String.valueOf(client.getAge()));
        txvGender.setText(client.getGender());
        txvLocation.setText(client.getLocation());
        txvMobile.setText(String.valueOf(client.getMobile()));
        txvEmail.setText(client.getEmail());
        imvProfPic.setImageResource(icons[client.getIconCode()]);

        /* DISPLAY LISTVIEW OF INTERESTED TRANSACTIONS */
        final ListView listJobs = (ListView) findViewById(R.id.lsv_c_vp_matches);
        final Handler handler = new Handler();
        mAdapter = new workyMatchesLsvAdapter
                (ClientProfileActivity.this,
                        app.getLinkedJobsByTypeClient(fUsername));

        listJobs.setAdapter(mAdapter);

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
                        app.getLinkedJobsByTypeClient(client.getUsername());
                mAdapter.clear();
                mAdapter.addAll(linkJobs);
                mAdapter.notifyDataSetChanged();
            }
        }

        handler.post(new updateAdapter(handler, mAdapter));
    }
}
