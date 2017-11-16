package edu.ateneo.cie199.worky;

/**
 * The job link class. Each instance of this class represents one link.
 */
public class workyLinkJob {
    private String mJobUsertype;
    private workyClient mClient;
    private workyFreelancer mFreelancer;
    private workyJobs mJob;

    /**
     * Instantiates a job link class taking in all required parameters.
     *
     * @param jobUsertype the type of the job's owner
     * @param client      the client of the job
     * @param freelancer  the freelancer of the job
     * @param job         the job
     */
    public workyLinkJob(String jobUsertype, workyClient client, workyFreelancer freelancer, workyJobs job) {
        this.mJobUsertype = jobUsertype;
        this.mClient = client;
        this.mFreelancer = freelancer;
        this.mJob = job;
    }

    public String getJobUsertype() {
        return mJobUsertype;
    }

    public void setJobUsertype(String mJobUsertype) {
        this.mJobUsertype = mJobUsertype;
    }

    public workyClient getClient() {
        return mClient;
    }

    public void setClient(workyClient mClient) {
        this.mClient = mClient;
    }

    public workyFreelancer getFreelancer() {
        return mFreelancer;
    }

    public void setFreelancer(workyFreelancer mFreelancer) {
        this.mFreelancer = mFreelancer;
    }

    public workyJobs getJob() {
        return mJob;
    }

    public void setJob(workyJobs mJobs) {
        this.mJob = mJobs;
    }

}
