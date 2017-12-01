package edu.ateneo.cie199.worky;

/**
 * The job class. Each instance of this class represents one job.
 */
public class workyJobs {
    private int mJobicon;
    private String mJobfield;
    private String mJobtitle;
    private float mSalary;
    private String mLocation;
    private String mDescription;
    private String mUsername;
    private String mUsertype;

    /**
     * Instantiates a new job.
     */
    public workyJobs() {}

    /**
     * Instantiates a new jobs instance taking all required parameters.
     *
     * @param mJobfield    the field of the job
     * @param mJobtitle    the title of the jobs
     * @param mSalary      the salary of the job
     * @param mLocation    the location of the job
     * @param mDescription the description of the job
     * @param mUsername    the username of the job's owner
     * @param mUsertype    the user type of the job's owner
     */
    public workyJobs(String mJobfield, String mJobtitle, float mSalary,
                     String mLocation, String mDescription, String mUsername, String mUsertype) {
        this.mJobfield = mJobfield;
        this.mJobtitle = mJobtitle;
        this.mSalary = mSalary;
        this.mLocation = mLocation;
        this.mDescription = mDescription;
        this.mUsername = mUsername;
        this.mUsertype = mUsertype;

        if (mJobfield.equals("Agriculture"))
            this.mJobicon = 0;
        else if (mJobfield.equals("Arts"))
            this.mJobicon = 1;
        else if (mJobfield.equals("Clerical"))
            this.mJobicon = 2;
        else if (mJobfield.equals("Education"))
            this.mJobicon = 3;
        else if (mJobfield.equals("Engineering"))
            this.mJobicon = 4;
        else if (mJobfield.equals("Finance"))
            this.mJobicon = 5;
        else if (mJobfield.equals("Health"))
            this.mJobicon = 6;
        else if (mJobfield.equals("Hospitality"))
            this.mJobicon = 7;
        else if (mJobfield.equals("IT"))
            this.mJobicon = 8;
        else if (mJobfield.equals("Legal"))
            this.mJobicon = 9;
        else if (mJobfield.equals("Manufacturing"))
            this.mJobicon = 10;
        else if (mJobfield.equals("Transport"))
            this.mJobicon = 11;
        else if (mJobfield.equals("Others"))
            this.mJobicon = 12;
    }

    public int getJobicon() {
        return mJobicon;
    }

    public void setJobicon(int mJobicon) {
        this.mJobicon = mJobicon;
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

    /**
     * Overrides the <code>toString</code> so that the desired <code>String</code> output
     * is intended by the user.
     */
    /* FOR LISTVIEW OUTPUT */
    public String toString() {
        return getJobtitle() + "\n" +
                "Posted by: " + getUsername() + "\n" +
                "Location: " + getLocation() + "\n" +
                "Salary: PhP" + getSalary() + "\n" +
                "Field: " + getJobfield();
    }
}
