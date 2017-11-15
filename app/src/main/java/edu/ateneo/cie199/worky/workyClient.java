package edu.ateneo.cie199.worky;

import java.util.ArrayList;

public class workyClient {
    private String mUsername;
    private String mPassword;
    private String mFirstname;
    private String mMiddlename;
    private String mLastname;
    private int mAge;
    private String mGender;
    private String mEmail;
    private long mMobile;
    private String mProfile;
    private String mCompany;
    private String mField;
    private String mSpecialization;
    private String mLocation;
    private ArrayList<String> mJobOrders = new ArrayList<>();

    /* CONSTRUCTOR */
    public workyClient() {}

    public workyClient(String mUsername, String mPassword, String mFirstname, String mMiddlename,
                       String mLastname, int mAge, String mGender, String mEmail, long mMobile,
                       String mProfile, String mCompany, String mField, String mSpecialization,
                       String mLocation, ArrayList<String> mJobOrders) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mFirstname = mFirstname;
        this.mMiddlename = mMiddlename;
        this.mLastname = mLastname;
        this.mAge = mAge;
        this.mGender = mGender;
        this.mEmail = mEmail;
        this.mMobile = mMobile;
        this.mProfile = mProfile;
        this.mCompany = mCompany;
        this.mField = mField;
        this.mSpecialization = mSpecialization;
        this.mLocation = mLocation;
        this.mJobOrders = mJobOrders;
    }

    /* GETTERS AND SETTERS */
    
    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getFirstname() {
        return mFirstname;
    }

    public void setFirstname(String mFirstname) {
        this.mFirstname = mFirstname;
    }

    public String getMiddlename() {
        return mMiddlename;
    }

    public void setMiddlename(String mMiddlename) {
        this.mMiddlename = mMiddlename;
    }

    public String getLastname() {
        return mLastname;
    }

    public void setLastname(String mLastname) {
        this.mLastname = mLastname;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int mAge) {
        this.mAge = mAge;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public long getMobile() {
        return mMobile;
    }

    public void setMobile(long mMobile) {
        this.mMobile = mMobile;
    }

    public String getProfile() {
        return mProfile;
    }

    public void setProfile(String mProfile) {
        this.mProfile = mProfile;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public String getField() {
        return mField;
    }

    public void setField(String mField) {
        this.mField = mField;
    }

    public String getSpecialization() {
        return mSpecialization;
    }

    public void setSpecialization(String mSpecialization) {
        this.mSpecialization = mSpecialization;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public ArrayList<String> getJobOrders() {
        return mJobOrders;
    }

    public void setJobOrders(ArrayList<String> mJobOrders) {
        this.mJobOrders = mJobOrders;
    }
    
    /* ADD JOB ORDERS */
    public void addJobOrders(String jo) {
        mJobOrders.add(jo);
        return;
    }
}