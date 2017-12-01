package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The activity class where a freelancer can find jobs posted by clients.
 */
public class FreelanceFindActivity extends AppCompatActivity {

    private ArrayAdapter<workyJobs> mAdapter = null;
    private ArrayAdapter<String> autoCompleteAdapter = null;
    private ArrayList<String> searchString = new ArrayList<>();
    private String searchCategory = "Agriculture";
    private String searchFilter = "Title";
    private ArrayList<workyJobs> jobBuffer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelance_find);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();
        jobBuffer = app.getAllJobs("Client");

        /* SET FONT OF HEADER */
        Typeface font = Typeface.createFromAsset(FreelanceFindActivity.this.getAssets(),
                "nunito.ttf");
        TextView lblFind = (TextView) findViewById(R.id.lbl_f_findpage);
        TextView lblResult = (TextView) findViewById(R.id.lbl_f_searchresults);
        lblFind.setTypeface(font);
        lblResult.setTypeface(font);

        /* AUTOCOMPLETE ADAPTER */
        autoCompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        final AutoCompleteTextView autoCompleteSearch = (AutoCompleteTextView) findViewById(R.id.edt_f_search);
        autoCompleteSearch.setThreshold(0);
        autoCompleteSearch.setAdapter(autoCompleteAdapter);

        /* INITIALIZE SEARCH STRING FOR AUTOCOMPLETE */
        updateSearchString();

        /* CHANGE AUTOCOMPLETE BASED ON THE CATEGORY SELECTED */
        Spinner spnJobcategory = (Spinner) findViewById(R.id.spn_f_jobcategory);
        spnJobcategory.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String[] LOOKUP_JOBCATEGORY = { "Agriculture", "Arts", "Clerical", "Education",
                                "Engineering", "Finance", "Health", "Hospitality",
                                "IT", "Legal", "Manufacturing", "Transport", "Others"};
                        searchCategory = LOOKUP_JOBCATEGORY[position];

                        updateSearchString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        /* SPINNER FOR SEARCH FILTERS */
        Spinner spnSearchFilter = (Spinner) findViewById(R.id.spn_f_searchfilters);
        spnSearchFilter.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            searchFilter = "Title";
                            updateSearchString();
                        }
                        else if (position == 3) {
                            searchFilter = "Location";
                            updateSearchString();
                        }
                        else
                            searchString.clear();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        ImageView btnSearch = (ImageView) findViewById(R.id.btn_f_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* APPLICATION OBJECT */
                final workyApplication app = (workyApplication) getApplication();

                /* DECLARATION OF ARRAY TO BE OUTPUTTED */
                final ArrayList<workyJobs> outputArray;

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


                    mAdapter = new workyJobLsvAdapter(FreelanceFindActivity.this, outputArray);
                    listArticles.setAdapter(mAdapter);


                    /* REDIRECTION TO PROFILE PAGES */
                    listArticles.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                                    Intent launchClientViewAsFreelancerActivity =
                                            new Intent(FreelanceFindActivity.this,
                                            ClientViewAsFreelancerActivity.class);
                                    launchClientViewAsFreelancerActivity.putExtra
                                            ("ORIGIN", "FIND");
                                    launchClientViewAsFreelancerActivity.putExtra
                                            ("C_ACCOUNTTYPE", outputArray.get((int) id).getUsertype());
                                    launchClientViewAsFreelancerActivity.putExtra
                                            ("C_USERNAME", outputArray.get((int) id).getUsername());
                                    launchClientViewAsFreelancerActivity.putExtra
                                            ("C_TITLE", outputArray.get((int) id).getJobtitle());
                                    startActivity(launchClientViewAsFreelancerActivity);
                                    return;
                                }
                            }
                    );
                }
            }
        });
    }

    /* UPDATE SEARCH STRING FOR AUTOCOMPLETE */
    private void updateSearchString() {
        searchString.clear();

        for (int i = 0; i < jobBuffer.size(); i++) {
            if (jobBuffer.get(i).getJobfield().equals(searchCategory)) {
                if (searchFilter.equals("Title") &&
                        !stringInSearchString(jobBuffer.get(i).getJobtitle())) {
                    searchString.add(jobBuffer.get(i).getJobtitle());
                }
                else if (searchFilter.equals("Location") &&
                        !stringInSearchString(jobBuffer.get(i).getLocation()))
                    searchString.add(jobBuffer.get(i).getLocation());
            }
        }

        autoCompleteAdapter.clear();
        autoCompleteAdapter.addAll(searchString);
        autoCompleteAdapter.notifyDataSetChanged();
    }

    /* CHECKS IF STRING IS AVAILABLE FOR AUTOCOMPLETE */
    private Boolean stringInSearchString(String bufferString) {
        for (int i = 0; i < searchString.size(); i++) {
            if (searchString.get(i).equals(bufferString))
                return true;
        }
        return false;
    }

}