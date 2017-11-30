package edu.ateneo.cie199.worky;

import java.util.ArrayList;

/**
 * The client class. Each instance of this class represents one account of type client.
 */
public class workyFreelancer {
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
    private String mEducation;
    private String mExpertise;
    private String mCourse;
    private String mLocation;
    private int mIconCode;
    private ArrayList<String> mExperiences = new ArrayList<>();

    /**
     * Instantiates a new freelancer.
     */
    public workyFreelancer () {}

    /**
     * Instantiates a new freelancer instance taking in all required parameters.
     *
     * @param mUsername    the username of the freelancer
     * @param mPassword    the password of the freelancer
     * @param mFirstname   the firstname of the freelancer
     * @param mMiddlename  the middlename of the freelancer
     * @param mLastname    the lastname of the freelancer
     * @param mAge         the age of the freelancer
     * @param mGender      the gender of the freelancer
     * @param mEmail       the email of the freelancer
     * @param mMobile      the mobile of the freelancer
     * @param mProfile     the profile of the freelancer
     * @param mEducation   the education of the freelancer
     * @param mExpertise   the expertise of the freelancer
     * @param mCourse      the course of the freelancer
     * @param mLocation    the location of the freelancer
     * @param mExperiences the experiences of the freelancer
     */
    public workyFreelancer(String mUsername, String mPassword, String mFirstname,
                           String mMiddlename, String mLastname, int mAge, String mGender,
                           String mEmail, long mMobile, String mProfile, String mEducation,
                           String mExpertise, String mCourse, String mLocation, int mIconCode,
                           ArrayList<String> mExperiences) {
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
        this.mEducation = mEducation;
        this.mExpertise = mExpertise;
        this.mCourse = mCourse;
        this.mLocation = mLocation;
        this.mIconCode = mIconCode;
        this.mExperiences = mExperiences;
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

    public String getEducation() {
        return mEducation;
    }

    public void setEducation(String mEducation) {
        this.mEducation = mEducation;
    }

    public String getExpertise() {
        return mExpertise;
    }

    public void setExpertise(String mExpertise) {
        this.mExpertise = mExpertise;
    }

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String mCourse) {
        this.mCourse = mCourse;
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

    public ArrayList<String> getExperiences() {
        return mExperiences;
    }

    public void setExperiences(ArrayList<String> mExperiences) {
        this.mExperiences = mExperiences;
    }
    
    /* ADD EXPERIENCES */
    public void addExperiences(String experience) {
        mExperiences.add(experience);
        return;
    }

    /**
     * Overrides the <code>toString</code> so that the desired <code>String</code> output
     * is intended by the user.
     */
    public String toString() {
        return getFirstname() + " " + getLastname() + "\n" +
                "Education: " + getEducation() + "\n" +
                "Expertise: " + getExpertise() + "\n" +
                "Course: " + getCourse();
    }
}
