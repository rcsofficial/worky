package edu.ateneo.cie199.worky;

/**
 * Created by Richmond Sim on 12/11/2017.
 */

public class workyJobs {
    private String mJobfield;
    private String mJobtitle;
    private float mSalary;
    private String mLocation;
    private String mDescription;
    private String mUsername;
    private String mUsertype;

    public workyJobs(String mJobfield, String mJobtitle, float mSalary, 
                     String mLocation, String mDescription, String mUsername, String mUsertype) {
        this.mJobfield = mJobfield;
        this.mJobtitle = mJobtitle;
        this.mSalary = mSalary;
        this.mLocation = mLocation;
        this.mDescription = mDescription;
        this.mUsername = mUsername;
        this.mUsertype = mUsertype;
    }

    public String getJobfield() {
        return mJobfield;
    }

    public void setJobfield(String mJobfield) {
        this.mJobfield = mJobfield;
    }

    public String getJobtitle() {
        return mJobtitle;
    }

    public void setJobtitle(String mJobtitle) {
        this.mJobtitle = mJobtitle;
    }

    public float getSalary() {
        return mSalary;
    }

    public void setSalary(float mSalary) {
        this.mSalary = mSalary;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getUsertype() {
        return mUsertype;
    }

    public void setUsertype(String mUsertype) {
        this.mUsertype = mUsertype;
    }

    /* FOR LISTVIEW OUTPUT */
    public String toString() {
        return getJobtitle() + " by " + getUsername() + "\n" +
                getLocation() + " - PhP" + getSalary() + " - " + getJobfield();
    }
}
