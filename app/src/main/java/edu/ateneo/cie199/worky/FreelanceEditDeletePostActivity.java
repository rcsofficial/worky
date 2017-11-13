package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;

public class FreelanceEditDeletePostActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    private ArrayAdapter<workyJobs> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_edit_delete_post);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();


        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String fUsername = user.get(workySessionMgt.KEY_USERNAME);


        /* LISTVIEW DISPLAY */
        ListView listPostedJobs = (ListView) findViewById(R.id.lsv_f_postedjobs);
        mAdapter = new ArrayAdapter<>(FreelanceEditDeletePostActivity.this,
                android.R.layout.simple_list_item_1,
                app.getJobsByUsername(fUsername, "Freelancer"));
        listPostedJobs.setAdapter(mAdapter);


        /* REDIRECT TO EDIT FIELDS WHEN ITEM CLICKED */
        listPostedJobs.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent launchFreelanceEditPostFieldsActivity =
                                new Intent(FreelanceEditDeletePostActivity.this,
                                FreelanceEditPostFieldsActivity.class);
                        launchFreelanceEditPostFieldsActivity.putExtra("F_POST_POS", position);
                        startActivity(launchFreelanceEditPostFieldsActivity);
                    }
                }
        );


        /* DELETE ITEM WHEN ITEM LONG PRESSED */
        listPostedJobs.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        /* DELETE ON ADAPTER AND ON ARRAY LIST */
                        mAdapter.remove(app.getJobsByUsername(fUsername, "Freelancer").get(position));
                        app.deleteJob(fUsername, "Freelancer", position);
                        mAdapter.notifyDataSetChanged();

                        Toast.makeText(FreelanceEditDeletePostActivity.this,
                                "SUCCESS. Job offer deleted from list.",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
        );
    }
}
