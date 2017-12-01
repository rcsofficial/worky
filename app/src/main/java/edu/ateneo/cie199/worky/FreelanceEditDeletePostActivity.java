package edu.ateneo.cie199.worky;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * The activity class where the freelancer can edit or delete his past jobs.
 */
public class FreelanceEditDeletePostActivity extends AppCompatActivity {

    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    /* LISTVIEW ARRAY ADAPTER */
    private ArrayAdapter<workyJobs> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_edit_delete_post);

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(FreelanceEditDeletePostActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblEditPost = (TextView) findViewById(R.id.lbl_f_editpostpage);
        lblEditPost.setTypeface(font);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();

        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String fUsername = user.get(workySessionMgt.KEY_USERNAME);

        /* SHOW INSTRUCTION */
        Toast.makeText(FreelanceEditDeletePostActivity.this,
                "Short Press to Edit. Long Press to Delete.",
                Toast.LENGTH_LONG).show();

        /* LISTVIEW DISPLAY */
        ListView listPostedJobs = (ListView) findViewById(R.id.lsv_f_postedjobs);
        mAdapter = new workyJobLsvAdapter
                (FreelanceEditDeletePostActivity.this,
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
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder sureDelete =
                                new AlertDialog.Builder(FreelanceEditDeletePostActivity.this);
                        sureDelete.setMessage("Are you sure you want to delete this offer?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    /* DELETE ON ADAPTER AND ON ARRAY LIST */
                                        mAdapter.remove(app.getJobsByUsername(fUsername, "Freelancer").get(position));
                                        app.deleteJob(fUsername, "Freelancer", position);
                                        mAdapter.notifyDataSetChanged();

                                        Toast.makeText(FreelanceEditDeletePostActivity.this,
                                                "SUCCESS. Service offer deleted from list.",
                                                Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert = sureDelete.create();
                        alert.setTitle("Delete Service Offered");
                        alert.show();
                        return true;
                    }
                }
        );
    }
}
