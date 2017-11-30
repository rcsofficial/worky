package edu.ateneo.cie199.worky;

import java.util.ArrayList;

/**
 * The client class. Each instance of this class represents one account of type client.
 */
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
    private int mIconCode;
    private ArrayList<String> mJobOrders = new ArrayList<>();

    /**
     * Instantiates a new client.
     */
    public workyClient() {}

    /**
     * Instantiates a new client taking in all required parameters.
     *
     * @param mUsername       the username of the client
     * @param mPassword       the password of the client
     * @param mFirstname      the firstname of the client
     * @param mMiddlename     the middlename of the client
     * @param mLastname       the lastname of the client
     * @param mAge            the age of the client
     * @param mGender         the gender of the client
     * @param mEmail          the email of the client
     * @param mMobile         the mobile of the client
     * @param mProfile        the profile of the client
     * @param mCompany        the company of the client
     * @param mField          the field of the client
     * @param mSpecialization the specialization of the client
     * @param mLocation       the location of the client
     * @param mJobOrders      the job orders of the client
     */
    public workyClient(String mUsername, String mPassword, String mFirstname, String mMiddlename,
                       String mLastname, int mAge, String mGender, String mEmail, long mMobile,
                       String mProfile, String mCompany, String mField, String mSpecialization,
                       String mLocation, int mIconCode, ArrayList<String> mJobOrders) {
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
        this.mIconCode = mIconCode;
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

    public int getIconCode() {
        return mIconCode;
    }

    public void setIconCode(int mIconCode) {
        this.mIconCode = mIconCode;
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


    /**
     * Overrides the <code>toString</code> so that the desired <code>String</code> output
     * is intended by the user.
     */
    public String toString() {
        return getFirstname() + " " + getLastname() + "\n" +
                "Company: " + getCompany() + "\n" +
                "Field: " + getField() + "\n" +
                "Specialization: " + getSpecialization();
    }
}