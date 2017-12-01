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
 * The activity that provides freelance profile information and previous matches for clients
 */
public class FreelanceProfileActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    /* LISTVIEW ARRAY ADAPTER */
    private ArrayAdapter<workyLinkJob> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_profile);

        /* SET HEADER FONT */
        Typeface font = Typeface.createFromAsset(FreelanceProfileActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblFreelanceProf = (TextView) findViewById(R.id.lbl_f_vp_freelanceprofile);
        TextView lblJobOrders = (TextView) findViewById(R.id.lbl_f_vp_joborders);
        lblFreelanceProf.setTypeface(font);
        lblJobOrders.setTypeface(font);

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();

        /* GET USER DETAILS FROM INTENT*/
        Intent intentFromClientDashboardActivity = getIntent();
        if (intentFromClientDashboardActivity == null) { return; }
        String cUsername = intentFromClientDashboardActivity.getStringExtra("C_USERNAME");
        int position = intentFromClientDashboardActivity.getIntExtra("F_PROF_POS", 0);

        /* GET TEXTVIEW AND IMAGEVIEW CONTENTS */
        TextView txvFullName = (TextView) findViewById(R.id.txv_f_vp_fullname);
        TextView txvUserName = (TextView) findViewById(R.id.txv_f_vp_username);
        TextView txvEducation = (TextView) findViewById(R.id.txv_f_vp_education);
        TextView txvExpertise = (TextView) findViewById(R.id.txv_f_vp_expertise);
        TextView txvCourse = (TextView) findViewById(R.id.txv_f_vp_course);
        TextView txvProfile = (TextView) findViewById(R.id.txv_f_vp_profile);
        TextView txvAge = (TextView) findViewById(R.id.txv_f_vp_age);
        TextView txvGender = (TextView) findViewById(R.id.txv_f_vp_gender);
        TextView txvLocation = (TextView) findViewById(R.id.txv_f_vp_location);
        TextView txvMobile = (TextView) findViewById(R.id.txv_f_vp_mobile);
        TextView txvEmail = (TextView) findViewById(R.id.txv_f_vp_email);
        ImageView imvProfPic = (ImageView) findViewById(R.id.imv_f_vp_profpic);

        /* SET TEXTVIEW AND IMAGEVIEW CONTENTS */
        final workyFreelancer freelancer =
                app.getLinkedJobsByTypeClient(cUsername).get(position).getFreelancer();

        int icons[] = {
                R.drawable.profpic1,
                R.drawable.profpic2,
                R.drawable.profpic3,
                R.drawable.profpic4
        };

        txvFullName.setText
                (freelancer.getFirstname() + " " +
                freelancer.getMiddlename() + " " +
                freelancer.getLastname());
        txvUserName.setText(freelancer.getUsername());
        txvEducation.setText(freelancer.getEducation());
        txvExpertise.setText(freelancer.getExpertise());
        txvCourse.setText(freelancer.getCourse());
        txvProfile.setText(freelancer.getProfile());
        txvAge.setText(String.valueOf(freelancer.getAge()));
        txvGender.setText(freelancer.getGender());
        txvLocation.setText(freelancer.getLocation());
        txvMobile.setText(String.valueOf(freelancer.getMobile()));
        txvEmail.setText(freelancer.getEmail());
        imvProfPic.setImageResource(icons[freelancer.getIconCode()]);

        /* DISPLAY LISTVIEW OF INTERESTED TRANSACTIONS */
        final ListView listJobs = (ListView) findViewById(R.id.lsv_f_vp_matches);
        final Handler handler = new Handler();
        mAdapter = new workyMatchesLsvAdapter
                (FreelanceProfileActivity.this,
                        app.getLinkedJobsByTypeClient(freelancer.getUsername()));
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
                        app.getLinkedJobsByTypeFreelancer(freelancer.getUsername());
                mAdapter.clear();
                mAdapter.addAll(linkJobs);
                mAdapter.notifyDataSetChanged();
            }
        }

        handler.post(new updateAdapter(handler, mAdapter));
    }
}
