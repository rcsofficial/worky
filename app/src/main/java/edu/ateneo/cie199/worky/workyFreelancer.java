package edu.ateneo.cie199.worky;

import java.util.ArrayList;

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
    private ArrayList<String> mExperiences = new ArrayList<>();

    /* CONSTRUCTOR */
    public workyFreelancer () {}

    public workyFreelancer(String mUsername, String mPassword, String mFirstname,
                           String mMiddlename, String mLastname, int mAge, String mGender,
                           String mEmail, long mMobile, String mProfile, String mEducation,
                           String mExpertise, String mCourse, String mLocation,
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

    /* FOR LISTVIEW OUTPUT */
    public String toString() {
        return getFirstname() + " " + getLastname() + "\n" +
                "Education: " + getEducation() + "\n" +
                "Expertise: " + getExpertise() + "\n" +
                "Course: " + getCourse();
    }
}
