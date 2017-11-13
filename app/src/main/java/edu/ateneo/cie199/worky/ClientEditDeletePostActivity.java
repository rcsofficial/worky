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

public class ClientEditDeletePostActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    private ArrayAdapter<workyJobs> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit_delete_post);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();


        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String cUsername = user.get(workySessionMgt.KEY_USERNAME);


        /* LISTVIEW DISPLAY */
        ListView listPostedJobs = (ListView) findViewById(R.id.lsv_c_postedjobs);
        mAdapter = new ArrayAdapter<>(ClientEditDeletePostActivity.this,
                android.R.layout.simple_list_item_1,
                app.getJobsByUsername(cUsername, "Client"));
        listPostedJobs.setAdapter(mAdapter);


        /* REDIRECT TO EDIT FIELDS WHEN ITEM CLICKED */
        listPostedJobs.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent launchClientEditPostFieldsActivity = new Intent(ClientEditDeletePostActivity.this,
                            ClientEditPostFieldsActivity.class);
                    launchClientEditPostFieldsActivity.putExtra("C_POST_POS", position);
                    startActivity(launchClientEditPostFieldsActivity);
                }
            }
        );


        /* DELETE ITEM WHEN ITEM LONG PRESSED */
        listPostedJobs.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {
                    /* DELETE ON ADAPTER AND ON ARRAY LIST */
                    mAdapter.remove(app.getJobsByUsername(cUsername, "Client").get(position));
                    app.deleteJob(cUsername, "Client", position);
                    mAdapter.notifyDataSetChanged();

                    Toast.makeText(ClientEditDeletePostActivity.this,
                            "SUCCESS. Job offer deleted from list.",
                            Toast.LENGTH_SHORT).show();

                    return false;
                }
            }
        );
    }
}