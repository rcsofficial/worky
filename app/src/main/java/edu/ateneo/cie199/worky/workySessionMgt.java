package edu.ateneo.cie199.worky;

import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This class manages the login sessions of different users.
 */
public class workySessionMgt {

    SharedPreferences pref;
    Editor editor;
    Context context;
    
    private static final String PREF_NAME = "WorkyPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    /**
     * The constant KEY_USERNAME which will be used to later on call the username.
     */
    public static final String KEY_USERNAME = "username";
    /**
     * The constant KEY_PASSWORD which will be used to later on call the password.
     */
    public static final String KEY_PASSWORD = "password";
    /**
     * The constant KEY_USERTYPE which will be used to later on call the user type.
     */
    public static final String KEY_USERTYPE = "usertype";

    /**
     * Instantiates a new session management.
     */
    public workySessionMgt(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    /**
     * Creates a login session.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param usertype the type of the user
     */
    public void createLoginSession(String username, String password, String usertype){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_USERTYPE, usertype);
        editor.commit();
    }

    /**
     * Gets the current status of session.
     *
     * @return <code>true</code> if there is a user currently logged in; <code>false</code>
     * otherwise.
     */
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Checks login. If there is a user currently logged in, it redirects to the <code>LoginActivity</code>
     * class; otherwise, does nothing.
     *
     * @see LoginActivity
     */
    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    /**
     * Logouts a user and redirects to the <code>LoginActivity</code>.
     *
     * @see LoginActivity
     */
    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * Gets the user who is currently logged in in a HashMap.
     *
     * @return the user details
     */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_USERTYPE, pref.getString(KEY_USERTYPE, null));
        return user;
    }
}
