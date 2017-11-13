package edu.ateneo.cie199.worky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    /* LOGIN SESSION MANAGEMENT */
    workySessionMgt session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* APPLICATION OBJECT */
        final workyApplication app = (workyApplication) getApplication();




        // TODO: Remove this part! Only for testing purposes
        app.addClientAccount(new workyClient("c1", "c1", "c1name",
                "c1mid", "c1last", 20, "Male", "c1@w.com", 915, "Hi",
                "W", "Engineering", "CoE", "MLA", null));
        app.addFreelancerAccount(new workyFreelancer("f1", "f1", "f1name",
                "f1mid", "f1last", 20, "Male", "f1@w.com", 915, "Hi",
                "W", "Engineering", "CoE", "MLA", null));
        // TODO: Until this part, remove!

        // TODO: Maintain Signin after App is Closed


        /* LOGIN SESSION MANAGEMENT INITIALIZATION */
        session = new workySessionMgt(getApplicationContext());


        /* DECLARATION OF USER FIELD CONTENTS */
        final EditText edtUsername = (EditText) findViewById(R.id.edt_username);
        final EditText edtPassword = (EditText) findViewById(R.id.edt_password);
        final Spinner spnUsertype = (Spinner) findViewById(R.id.spn_usertype);


        /* REDIRECTION TO SIGN IN DASHBOARDS */
        Button btnSignin = (Button) findViewById(R.id.btn_signin);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* GET INPUT FROM USER FIELDS */
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                int usertype = spnUsertype.getSelectedItemPosition();


                /* CHECK IF FIELDS ARE BLANK */
                if(areFieldsBlank(username, password)) {
                    return;
                }
                else {
                    /* REDIRECT TO FREELANCE DASHBOARD */
                    if (usertype == 0) {
                        if (app.isFreelancerExistent(username, password)) {
                            Intent launchFreelanceDashboardActivity = new Intent(LoginActivity.this,
                                    FreelanceDashboardActivity.class);

                            /* CREATE SESSION */
                            session.createLoginSession(username, password, "Freelancer");

                            launchFreelanceDashboardActivity.putExtra("F_USERNAME", username);
                            launchFreelanceDashboardActivity.putExtra("F_PASSWORD", password);
                            startActivity(launchFreelanceDashboardActivity);
                            finish();
                            return;
                        }
                        else {
                            Toast.makeText(LoginActivity.this,
                                    "ERROR: Please verify freelancer login credentials.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                    /* REDIRECT TO CLIENT DASHBOARD */
                    else if (usertype == 1) {
                        if (app.isClientExistent(username, password)) {
                            Intent launchClientDashboardActivity = new Intent(LoginActivity.this,
                                    ClientDashboardActivity.class);

                            /* CREATE SESSION */
                            session.createLoginSession(username, password, "Client");

                            launchClientDashboardActivity.putExtra("F_USERNAME", username);
                            launchClientDashboardActivity.putExtra("F_PASSWORD", password);
                            startActivity(launchClientDashboardActivity);
                            finish();
                            return;
                        }
                        else {
                            Toast.makeText(LoginActivity.this,
                                    "ERROR: Please verify client login credentials.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    /* REDIRECT TO ADMIN DASHBOARD */
                    else if (usertype == 2) {
                        // TODO: Verify Account
                        Intent launchAdminDashboardActivity = new Intent(LoginActivity.this,
                                AdminDashboardActivity.class);
                        startActivity(launchAdminDashboardActivity);
                        finish();
                        return;
                    }
                }
            }
        });


        // NOTE: You can signup similar usernames as long as usertypes are different.

        /* REDIRECTION TO SIGN UP PAGES */
        Button btnSignup = (Button) findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* GET INPUT FROM USER FIELDS */
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                int usertype = spnUsertype.getSelectedItemPosition();


                /* CHECK IF FIELDS ARE BLANK */
                if (areFieldsBlank(username, password)) {
                    return;
                }
                else {
                    /* REDIRECT TO FREELANCE SIGNUP ACTIVITY */
                    if (usertype == 0) {
                        if (!app.isFreelancerUsernameTaken(username)) {
                            Intent launchFreelanceSignupActivity = new Intent(LoginActivity.this,
                                    FreelanceSignupActivity.class);
                            launchFreelanceSignupActivity.putExtra("F_USERNAME", username);
                            launchFreelanceSignupActivity.putExtra("F_PASSWORD", password);
                            startActivity(launchFreelanceSignupActivity);
                            finish();
                            return;
                        }
                        else {
                            Toast.makeText(LoginActivity.this,
                                    "ERROR: Freelance username already taken.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    /* REDIRECT TO CLIENT SIGNUP ACTIVITY */
                    else if (usertype == 1) {
                        if (!app.isClientUsernameTaken(username)) {
                            Intent launchClientSignupActivity = new Intent(LoginActivity.this,
                                    ClientSignupActivity.class);
                            launchClientSignupActivity.putExtra("C_USERNAME", username);
                            launchClientSignupActivity.putExtra("C_PASSWORD", password);
                            startActivity(launchClientSignupActivity);
                            finish();
                            return;
                        }
                        else {
                            Toast.makeText(LoginActivity.this,
                                    "ERROR: Client username already taken.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    /* ERROR TOAST MESSAGE FOR ADMIN SIGNUPS */
                    else if (usertype == 2) {
                        Toast.makeText(LoginActivity.this,
                                "ERROR: You may not signup as an administrator.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }

    protected boolean areFieldsBlank(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this,
                    "ERROR: You may not leave any of the fields empty.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }
}