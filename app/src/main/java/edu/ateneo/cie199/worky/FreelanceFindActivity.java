package edu.ateneo.cie199.worky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FreelanceFindActivity extends AppCompatActivity {

    private ArrayAdapter<workyJobs> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_find);

        Button btnSearch = (Button) findViewById(R.id.btn_f_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* APPLICATION OBJECT */
                final workyApplication app = (workyApplication) getApplication();


                /* DECLARATION OF ARRAY TO BE OUTPUTTED */
                ArrayList<workyJobs> outputArray;


                /* GET SEARCH PARAMETERS FROM USER */
                Spinner spnJobcategory = (Spinner) findViewById(R.id.spn_f_jobcategory);
                Spinner spnSearchfilter = (Spinner) findViewById(R.id.spn_f_searchfilters);
                EditText edtSearch = (EditText) findViewById(R.id.edt_f_search);


                /* LOOKUP TRANSLATION TABLE FOR SPINNER */
                String[] LOOKUP_JOBCATEGORY = { "Agriculture", "Arts", "Clerical", "Education",
                        "Engineering", "Finance", "Health", "Hospitality",
                        "IT", "Legal", "Manufacturing", "Transport", "Others"};


                /* EXTRACT SEARCH PARAMETERS FROM USER */
                String jobCategory = LOOKUP_JOBCATEGORY[spnJobcategory.getSelectedItemPosition()];
                int searchFilter = spnSearchfilter.getSelectedItemPosition();
                String search = edtSearch.getText().toString();


                /* CHECKS THAT KEYWORD IS NOT EMPTY */
                if (search.isEmpty()) {
                    Toast.makeText(FreelanceFindActivity.this,
                            "ERROR: Keyword cannot be left empty.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    /* LISTVIEW BASED FROM THE KEYWORDS */
                    ListView listArticles = (ListView) findViewById(R.id.lsv_f_searchresults);

                    /* ALL SEARCHES NECESSARILY INCLUDE CATEGORY*/

                    /*SEARCH ACCORDING TO JOB TITLE */
                    if (searchFilter == 0)
                        outputArray = app.getJobsByTitle(search, jobCategory, "Client");
                    /* SEARCH ACCORDING TO MINIMUM SALARY */
                    else if (searchFilter == 1) {
                        try {
                            outputArray = app.getJobsByMinSalary(Float.parseFloat(search),
                                    jobCategory, "Client");
                        } catch(NumberFormatException e) {
                            Toast.makeText(FreelanceFindActivity.this,
                                    "ERROR: Keyword Salary only accepts Decimal Numbers",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else if (searchFilter == 2) {
                        try {
                            outputArray = app.getJobsByMaxSalary(Float.parseFloat(search),
                                    jobCategory, "Client");
                        } catch(NumberFormatException e) {
                            Toast.makeText(FreelanceFindActivity.this,
                                    "ERROR: Keyword Salary only accepts Decimal Numbers",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    /* SEARCH ACCORDING TO LOCATION */
                    else if (searchFilter == 3)
                        outputArray = app.getJobsByLocation(search, jobCategory, "Client");
                    else
                        outputArray = app.getAllJobs();


                    mAdapter = new ArrayAdapter<>(FreelanceFindActivity.this,
                            android.R.layout.simple_list_item_1,
                            outputArray);
                    listArticles.setAdapter(mAdapter);


                    /* REDIRECTION TO PROFILE PAGES */
                    listArticles.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                    // TODO: Intent to the Profile Handshaking Page of Freelancer when Clicked
                                    // TODO: (Freelancer must accept before proceeding) and Review Feature
                                    // TODO: After Handshaking: Automatic put job experience / order to both freelance and client profiles
                                    // TODO: After Handshaking: Redirect back to Dashboard
                                    return;
                                }
                            }
                    );
                }
            }
        });
    }
}