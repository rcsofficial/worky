package edu.ateneo.cie199.worky;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import static android.content.ContentValues.TAG;

/**
 * workyApplication is where all data needed by the Worky application to run are stored. It is made
 * global across the application by inheriting the Application class. It contains member variables
 * where the database in the cloud are stored to.
 */
public class workyApplication extends Application{
    private ArrayList<workyFreelancer> mFreelancer = new ArrayList<>();
    private ArrayList<workyClient> mClient = new ArrayList<>();
    private ArrayList<workyJobs> mJobs = new ArrayList<>();
    private ArrayList<workyLinkJob> mLinkJob = new ArrayList<>();
    private Boolean usersInitialized = false;
    private Boolean jobsInitialized = false;

    /**
     * Initializes the database listeners for the "freelancer", "client", and "job", collections
     * in the cloud. When a collection change, the member variable changes accordingly.
     */
    public void initializeUsers() {
        if (!usersInitialized) {
            usersInitialized = true;
            /* ACCESS A CLOUD FIRESTORE INSTANCE FROM YOUR ACTIVITY */
            FirebaseApp.initializeApp(this);
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            /* INITIALIZE LISTENERS FOR FREELANCER COLLECTION */
            db.collection("freelancer")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "listen:error", e);
                                return;
                            }

                            for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:
                                        mFreelancer.add(dc.getDocument().toObject(workyFreelancer.class));
                                        Log.d(TAG, "New freelancer: " + dc.getDocument().getData());
                                        break;
                                    case MODIFIED:
                                        workyFreelancer freelancer = dc.getDocument().toObject(workyFreelancer.class);
                                        int freelancerIndex = getFreelancerIndexByUsername(freelancer.getUsername());
                                        mFreelancer.set(freelancerIndex, freelancer);
                                        for (int i = 0; i < mLinkJob.size(); i++) {
                                            if (mLinkJob.get(i).getFreelancer().getUsername().equals(freelancer.getUsername())) {
                                                mLinkJob.get(i).setFreelancer(freelancer);
                                            }
                                        }
                                        Log.d(TAG, "Modified freelancer: " + dc.getDocument().getData());
                                        break;
                                    case REMOVED:
                                        mFreelancer.remove(dc.getDocument().toObject(workyFreelancer.class));
                                        Log.d(TAG, "Removed freelancer: " + dc.getDocument().getData());
                                        break;
                                }
                            }
                        }
                    });

            /* INITIALIZE LISTENER FOR CLIENT COLLECTION */
            db.collection("client")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "listen:error", e);
                                return;
                            }

                            for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:
                                        mClient.add(dc.getDocument().toObject(workyClient.class));
                                        Log.d(TAG, "New client: " + dc.getDocument().getData());
                                        break;
                                    case MODIFIED:
                                        workyClient client = dc.getDocument().toObject(workyClient.class);
                                        int clientIndex = getClientIndexByUsername(client.getUsername());
                                        mClient.set(clientIndex, client);
                                        for (int i = 0; i < mLinkJob.size(); i++) {
                                            if (mLinkJob.get(i).getClient().getUsername().equals(client.getUsername())) {
                                                mLinkJob.get(i).setClient(client);
                                            }
                                        }
                                        Log.d(TAG, "Modified client: " + dc.getDocument().getData());
                                        break;
                                    case REMOVED:
                                        mClient.remove(dc.getDocument().toObject(workyClient.class));
                                        Log.d(TAG, "Removed client: " + dc.getDocument().getData());
                                        break;
                                }
                            }
                        }
                    });

            /* INITIALIZE LISTENER FOR JOB COLLECTION */
            db.collection("job")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "listen:error", e);
                                return;
                            }

                            for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:
                                        mJobs.add(dc.getDocument().toObject(workyJobs.class));
                                        Log.d(TAG, "New job: " + dc.getDocument().getData());
                                        break;
                                    case MODIFIED:
                                        workyJobs job = dc.getDocument().toObject(workyJobs.class);
                                        int i = getJobIndexByAllType(job.getUsertype(), job.getUsername(),
                                                job.getJobfield(), job.getJobtitle(), job.getSalary(), job.getLocation(),
                                                job.getDescription() );
                                        mJobs.set(i, job);
                                        Log.d(TAG, "Modified job: " + dc.getDocument().getData());
                                        break;
                                    case REMOVED:
                                        workyJobs jobs = dc.getDocument().toObject(workyJobs.class);

                                        mJobs.remove(getJobIndexByAllType(jobs.getUsertype(), jobs.getUsername(),
                                                jobs.getJobfield(), jobs.getJobtitle(), jobs.getSalary(), jobs.getLocation(),
                                                jobs.getDescription() ));

                                        //mJobs.remove(dc.getDocument().toObject(workyJobs.class));
                                        Log.d(TAG, "Removed job: " + dc.getDocument().getData());
                                        break;
                                }
                            }
                        }
                    });
        }
    }


    /**
     * Initialize the database listener for the "joblink" collection in the cloud. When the "joblink"
     * collection changes, the member variable is also changed.
     */
    public void initializeJobLinks() {
        if (!jobsInitialized) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            jobsInitialized = true;
            /* INITIALIZE LISTENER FOR JOBLINK COLLECTION */
            db.collection("joblink")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "listen:error", e);
                                return;
                            }

                            for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:
                                        try {
                                            String usertype = dc.getDocument().get("usertype").toString();
                                            String client = dc.getDocument().get("client").toString();
                                            String freelancer = dc.getDocument().get("freelancer").toString();
                                            String jobTitle = dc.getDocument().get("job title").toString();
                                            String jobUsername;
                                            if (usertype.equals("Freelancer"))
                                                jobUsername = freelancer;
                                            else
                                                jobUsername = client;

                                            String jobField = dc.getDocument().get("job field").toString();
                                            Float jobSalary = Float.parseFloat(dc.getDocument().get("job salary").toString());
                                            String jobLocation = dc.getDocument().get("job location").toString();
                                            String jobDescription = dc.getDocument().get("job description").toString();

                                            workyJobs job = new workyJobs(jobField, jobTitle, jobSalary, jobLocation,
                                                    jobDescription, jobUsername, usertype);

                                            workyLinkJob linkJob = new workyLinkJob(usertype,
                                                    getClientAcctByUsername(client),
                                                    getFreelancerAcctByUsername(freelancer),
                                                    /*getJobByTypeUsernameTitle(usertype, jobUsername, jobTitle)*/
                                                    job);

                                            mLinkJob.add(linkJob);
                                        } catch (NullPointerException err) {
                                            Log.e(TAG, err.toString());
                                        }
                                        Log.d(TAG, "New Job Link: " + dc.getDocument().getData());
                                        break;
                                    case MODIFIED:
                                        Log.d(TAG, "Modified Job Link: " + dc.getDocument().getData());
                                        break;
                                    case REMOVED:
                                        deleteLinkJob(dc.getDocument().get("client").toString(),
                                                dc.getDocument().get("freelancer").toString(),
                                                dc.getDocument().get("job title").toString());
                                        Log.d(TAG, "Removed Job Link: " + dc.getDocument().getData());
                                        break;
                                }
                            }
                        }
                    });
        }
    }


    /**
     * Adds a link between a client and user based from a job posted to the database in the cloud.
     * The change will be detected by the database listener which will update the
     * <code>mLinkJob</code> array.
     *
     * @param jobUserType        the user type of the job poster
     * @param clientUsername     the username of the client
     * @param freelancerUsername the username of the freelancer
     * @param job                the job
     */
    /* ADDS A JOB THAT LINKS A CLIENT AND A FREELANCER */
    public void addLinkJob(String jobUserType, String clientUsername, String freelancerUsername,
                           workyJobs job) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> docData = new HashMap<>();
        docData.put("usertype", jobUserType);
        docData.put("client", clientUsername);
        docData.put("freelancer", freelancerUsername);
        docData.put("job title", job.getJobtitle());
        docData.put("job field", job.getJobfield());
        docData.put("job salary", job.getSalary());
        docData.put("job location", job.getLocation());
        docData.put("job description", job.getDescription());
        docData.put("job icon", job.getJobicon());

        db.collection("joblink").document(clientUsername + freelancerUsername + job.getJobtitle())
                .set(docData);
        return;
    }


    /**
     * Gets all jobs of a specific client that are linked to freelancers.
     *
     * @param clientUsername the username of the client
     * @return the array of all jobs linked to the username
     */
    public ArrayList<workyLinkJob> getLinkedJobsByTypeClient(String clientUsername) {
        ArrayList<workyLinkJob> linkJobs = new ArrayList<>();
        for (int i = 0; i < mLinkJob.size(); i++) {
            if ( mLinkJob.get(i).getJobUsertype().equals("Client") &&
                    mLinkJob.get(i).getClient().getUsername().equals(clientUsername) )
                linkJobs.add(mLinkJob.get(i));
        }
        return linkJobs;
    }


    /**
     * Deletes a job link based from the username of the client, the username of the freelancer,
     * and the title of the job.
     *
     * @param clientUsername     the username of the client
     * @param freelancerUsername the username of the freelancer
     * @param jobTitle           the title of the job
     */
    public void deleteLinkJob(String clientUsername, String freelancerUsername, String jobTitle) {

        for (int i = 0; i < mLinkJob.size(); i++) {
            if (mLinkJob.get(i).getClient().getUsername().equals(clientUsername) &&
                    mLinkJob.get(i).getFreelancer().getUsername().equals(freelancerUsername) &&
                    mLinkJob.get(i).getJob().getJobtitle().equals(jobTitle) )
                mLinkJob.remove(i);
        }

        return;
    }


    /**
     * Gets all jobs of a specific freelancer that are linked to clients.
     *
     * @param freelancerUsername the username of the freelancer
     * @return the array of all jobs linked to the freelancer
     */
    public ArrayList<workyLinkJob> getLinkedJobsByTypeFreelancer(String freelancerUsername) {
        ArrayList<workyLinkJob> linkJobs = new ArrayList<>();
        for (int i = 0; i < mLinkJob.size(); i++) {
            if ( mLinkJob.get(i).getJobUsertype().equals("Freelancer") &&
                    mLinkJob.get(i).getFreelancer().getUsername().equals(freelancerUsername) )
                linkJobs.add(mLinkJob.get(i));
        }
        return linkJobs;
    }


    /**
     * Gets the index of the specific client in the <code>mClient</code> array.
     *
     * @param username the username
     * @return the client index by username
     */
    /* GET CLIENT INDEX BY USERNAME */
    public int getClientIndexByUsername(String username) {
        for (int i = 0; i < mClient.size(); i++) {
            if (mClient.get(i).getUsername().equals(username))
                return i;
        }
        return -1;
    }


    /**
     * Gets a job based on the type of the user, the username, and the job title.
     *
     * @param type     the type of the user
     * @param username the username of the user
     * @param title    the title of the job
     * @return <code>null</code> when the job is the not in the array; the specific job otherwise.
     */
    public workyJobs getJobByTypeUsernameTitle(String type, String username, String title) {
        for (int i = 0; i < mJobs.size(); i++) {
            if ( mJobs.get(i).getUsertype().equals(type) &&
                    mJobs.get(i).getUsername().equals(username) &&
                    mJobs.get(i).getJobtitle().equals(title) )
                return mJobs.get(i);
        }
        return null;
    }


    /**
     * Gets the index of the job based on the user, the username, and the job title in the
     * <code>mJobs</code> array.
     *
     * @param type          the type of the user
     * @param username      the username of the user
     * @param field         the field of the job
     * @param title         the title of the job
     * @param salary        the requesting salary of the job owner
     * @param location      the location of the job
     * @param description   the description of the job
     * @return -1 when the job is not found; the index otherwise
     */
    public int getJobIndexByAllType(String type, String username, String field, String title, float salary,
                                    String location, String description) {
        for (int i = 0; i < mJobs.size(); i++) {
            if ( mJobs.get(i).getUsertype().equals(type) &&
                    mJobs.get(i).getUsername().equals(username) &&
                    mJobs.get(i).getJobfield().equals(field) &&
                    mJobs.get(i).getJobtitle().equals(title) &&
                    mJobs.get(i).getSalary() == salary &&
                    mJobs.get(i).getLocation().equals(location) &&
                    mJobs.get(i).getDescription().equals(description)  )
                return i;
        }
        return -1;
    }

    /**
     * Gets the index of the freelancer based on the username in the <code>mFreelancer</code>
     * array.
     *
     * @param username the username of the freelancer
     * @return -1 when the freelancer is not found; the index otherwise
     */
    public int getFreelancerIndexByUsername(String username) {
        for (int i = 0; i < mFreelancer.size(); i++) {
            if (mFreelancer.get(i).getUsername().equals(username))
                return i;
        }
        return -1;
    }


    /**
     * Adds a new freelancer account on the database in the cloud. The change in the database will be detected
     * by the listeners which will change the <code>mFreelancer</code> array.
     *
     * @param fAccount the freelancer object to be added
     */
    public void addFreelancerAccount(workyFreelancer fAccount) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("freelancer").document(fAccount.getUsername()).set(fAccount);
        return;
    }


    /**
     * Adds a new client account on the database in the cloud. The change in the database will be
     * detected by the listeners which will change the <code>mClient</code> array.
     *
     * @param cAccount the account object to be added
     */
    public void addClientAccount(workyClient cAccount) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("client").document(cAccount.getUsername()).set(cAccount);
        return;
    }


    /**
     * Adds a new job to the database in teh cloud. The change in the database will be detected
     * by the listeners which will change the <code>mJob</code> array.
     *
     * @param job the job object to be added
     */
    public void addJob(workyJobs job) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("job").document(job.getUsertype() + job.getUsername() + ":" + job.getJobfield() + job.getJobtitle()
                + job.getSalary() + job.getLocation() + job.getDescription()).set(job);
        return;
    }


    /**
     * Checks if the freelancer account is existing in the <code>mFreelancer</code> array
     * based from its username and password.
     *
     * @param username the username of the freelancer
     * @param password the password of the freelancer
     * @return <code>true</code> when the freelancer is found; <code>false</code> otherwise
     */
    public boolean isFreelancerExistent(String username, String password) {
        for (int i=0; i<mFreelancer.size(); i++) {
            if (mFreelancer.get(i).getUsername().equals(username) &&
                    mFreelancer.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }


    /**
     * Checks if the client account is existing in the <code>mClient</code> array based from its
     * username and password.
     *
     * @param username the username of the client
     * @param password the password of the client
     * @return <code>true</code> when the freelancer is found; <code>false</code> otherwise
     */
    public boolean isClientExistent(String username, String password) {
        for (int i=0; i<mClient.size(); i++) {
            if (mClient.get(i).getUsername().equals(username) &&
                    mClient.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }


    /**
     * Verifies if the username of the freelancer exists in the <code>mFreelancer</code>
     * object array.
     *
     * @param username the username of the freelancer
     * @return <code>true</code> when the username of the freelancer exists, <code>false</code>
     * otherwise
     */
    public boolean isFreelancerUsernameTaken(String username) {
        for (int i = 0; i < mFreelancer.size(); i++) {
            if (mFreelancer.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }


    /**
     * Verifies if the username of the client is exists in the <code>mClient</code> object array.
     *
     * @param username the username of the client
     * @return <code>true</code> when the username of the client exists, <cdoe>false</cdoe> otherwise
     */
    public boolean isClientUsernameTaken(String username) {
        for (int i = 0; i < mClient.size(); i++) {
            if (mClient.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }


    /**
     * Gets all the freelancers stored in the <code>mFreelancer</code> array
     *
     * @return <code>mFreelancer</code>
     */
    public ArrayList<workyFreelancer> getAllFreelancers() {
        return mFreelancer;
    }


    /**
     * Gets the specific freelancer based from its username in the <code>mFreelancer</code> array.
     *
     * @param username the username of the freelancer
     * @return the freelancer object
     */
    public workyFreelancer getFreelancerAcctByUsername(String username) {
        int index = -1;
        for (int i = 0; i < mFreelancer.size(); i++) {
            if (mFreelancer.get(i).getUsername().equals(username))
                index = i;
        }
        return mFreelancer.get(index);
    }


    /**
     * Gets all clients stored in the <code>mClient</code> array.
     *
     * @return <code>mClient</code>
     */
    public ArrayList<workyClient> getAllClients() {
        return mClient;
    }


    /**
     * Gets the specific client based from its username in the <code>mClient</code> array.
     *
     * @param username the username of the client
     * @return the client object
     */
    public workyClient getClientAcctByUsername(String username) {
        int index = -1;
        for (int i = 0; i < mClient.size(); i++) {
            if (mClient.get(i).getUsername().equals(username))
                index = i;
        }
        return mClient.get(index);
    }


    /**
     * Get all jobs stored in the <code>mJobs</code> array
     *
     * @return <code>mJobs</code>
     */
    public ArrayList<workyJobs> getAllJobs(){
        return mJobs;
    }


    /**
     * Gets all jobs.
     *
     * @param type the type of the job owner
     * @return all the jobs owned by a user type
     */
    public ArrayList<workyJobs> getAllJobs(String type) {
        ArrayList<workyJobs> jobs = new ArrayList<>();

        for (int i = 0; i < mJobs.size(); i++) {
            if (mJobs.get(i).getUsertype().equals(type))
                jobs.add(mJobs.get(i));
        }

        return jobs;
    }

    /**
     * Gets jobs based from the username and the type of the user.
     *
     * @param username the username of the user
     * @param usertype the type of the user
     * @return the jobs by username
     */
    public ArrayList<workyJobs> getJobsByUsername(String username, String usertype) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < mJobs.size(); i++) {
            if (mJobs.get(i).getUsername().equals(username) &&
                    mJobs.get(i).getUsertype().equals(usertype))
                outputEntries.add(mJobs.get(i));
        }
        return outputEntries;
    }


    /**
     * Gets all the jobs based from its job field or category.
     *
     * @param jobField the field of the job
     * @return array of jobs
     */
    public ArrayList<workyJobs> getJobsByField(String jobField) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < mJobs.size(); i++) {
            if (mJobs.get(i).getJobfield().equals(jobField))
                outputEntries.add(mJobs.get(i));
        }
        return outputEntries;
    }


    /**
     * Gets jobs based from the job title, category, and user type in the <code>mJobs</code> array.
     *
     * @param jobTitle the title of the job
     * @param jobField the field of the job
     * @param userType the type of the user
     * @return array of jobs
     */
    public ArrayList<workyJobs> getJobsByTitle(String jobTitle, String jobField,
                                               String userType) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < getJobsByField(jobField).size(); i++) {
            if (getJobsByField(jobField).get(i).getJobtitle().toLowerCase().contains(jobTitle.toLowerCase()) &&
                    getJobsByField(jobField).get(i).getUsertype().equals(userType))
                outputEntries.add(getJobsByField(jobField).get(i));
        }
        return outputEntries;
    }


    /**
     * Gets jobs by based from the field, the user type, and where the salary is greater than the
     * given salary in the <code>mJobs</code> array.
     *
     * @param salary   the minimum salary
     * @param jobField the field of the job
     * @param userType the type of the user
     * @return array of jobs
     */
    public ArrayList<workyJobs> getJobsByMinSalary(float salary, String jobField, String userType) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < getJobsByField(jobField).size(); i++) {
            if (getJobsByField(jobField).get(i).getSalary() >= salary &&
                    getJobsByField(jobField).get(i).getUsertype().equals(userType))
                outputEntries.add(getJobsByField(jobField).get(i));
        }
        return outputEntries;
    }


    /**
     * Gets jobs based from the field, the user type, and where the salary is lesser than the given
     * salary in the <code>mJobs</code> salary.
     *
     * @param salary   the maximum salary
     * @param jobField the field of the job
     * @param userType the type of the user
     * @return array of jobs
     */
    public ArrayList<workyJobs> getJobsByMaxSalary(float salary, String jobField,
                                                   String userType) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < getJobsByField(jobField).size(); i++) {
            if (getJobsByField(jobField).get(i).getSalary() <= salary &&
                    getJobsByField(jobField).get(i).getUsertype().equals(userType))
                outputEntries.add(getJobsByField(jobField).get(i));
        }
        return outputEntries;
    }


    /**
     * Gets jobs by the location, field, and the type of the user in the <code>mJobs</code> array.
     *
     * @param location the location of the job
     * @param jobField the field of the job
     * @param userType the type of the user
     * @return array of jobs
     */
    public ArrayList<workyJobs> getJobsByLocation(String location, String jobField,
                                                  String userType) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < getJobsByField(jobField).size(); i++) {
            if (getJobsByField(jobField).get(i).getLocation().toLowerCase().contains(location.toLowerCase()) &&
                    getJobsByField(jobField).get(i).getUsertype().equals(userType))
                outputEntries.add(getJobsByField(jobField).get(i));
        }
        return outputEntries;
    }


    /**
     * Deletes a job in the database in the cloud based from the username of the user, the type of
     * the user, and the index in the array. This will also delete all links between the clients
     * and freelancers. Change will be detected by the database event
     * listeners which will change the <code>mJobs</code> and <code>mLinkJobs</code> array.
     *
     * @param username the username of the user
     * @param usertype the type of the user
     * @param index    the index of the job in <code>mJobs</code> to be deleted
     */
    public void deleteJob(String username, String usertype, int index) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        workyJobs toDeleteFromMain = getJobsByUsername(username, usertype).get(index);

        /*
        for (int i = 0; i < mLinkJob.size(); i++) {
                if (mLinkJob.get(i).getJob() == toDeleteFromMain) {
                    db.collection("joblink").document(mLinkJob.get(i).getClient().getUsername()
                    + mLinkJob.get(i).getFreelancer().getUsername()
                    + mLinkJob.get(i).getJob().getJobtitle())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error deleting document", e);
                                }
                            });
                }
        }
        */

        db.collection("job").document(toDeleteFromMain.getUsertype()+ toDeleteFromMain.getUsername() + ":" +
                toDeleteFromMain.getJobfield() + toDeleteFromMain.getJobtitle() + toDeleteFromMain.getSalary() +
                toDeleteFromMain.getLocation() + toDeleteFromMain.getDescription())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

        return;
    }


    /**
     * Edits a specific job in the database in the cloud based from all of its member variables.
     * The change will be detected by the database listener which will then change the specific job
     * in the <code>mJobs</code> array.
     *
     * @param index       the index of the job in <code>mJobs</code>
     * @param jobField    the field of the job
     * @param jobTitle    the title of the job
     * @param salary      the salary of the job
     * @param location    the location of the job
     * @param description the description of the job
     * @param username    the username of the user
     * @param usertype    the type of the user
     */
    public void editJob(int index, String jobField, String jobTitle, float salary, String location,
                        String description, String username, String usertype) {
        workyJobs toEditFromMain = getJobsByUsername(username, usertype).get(index);

        final String docName = toEditFromMain.getUsertype()+ toEditFromMain.getUsername() + ":" +
                toEditFromMain.getJobfield() + toEditFromMain.getJobtitle() + toEditFromMain.getSalary() +
                toEditFromMain.getLocation() + toEditFromMain.getDescription();

        deleteJob(username, usertype, index);
        addJob(new workyJobs(jobField, jobTitle, salary, location, description, username, usertype));

        /*
        db.collection("job").document(docName)
                .set(new workyJobs(jobField, jobTitle, salary, location, description, username, usertype))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Haha", docName);
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        */
        return;

    }


    /**
     * Edits a specific client in the database in the cloud based from all of its member variables.
     * The change will be detected by the database listener which will then change the specific client
     * in the <code>mClient</code> array.
     *
     * @param username       the username of the client
     * @param password       the password of the client
     * @param firstName      the first name of the client
     * @param middleName     the middle name of the client
     * @param lastName       the last name of the client
     * @param age            the age of the client
     * @param gender         the gender of the client
     * @param email          the email of the client
     * @param mobile         the mobile of the client
     * @param profile        the profile of the client
     * @param company        the company of the client
     * @param field          the field of the client
     * @param specialization the specialization of the client
     * @param location       the location of the client
     */
    public  void editClient(String username, String password, String firstName, String middleName,
                            String lastName, int age, String gender, String email, long mobile,
                            String profile, String company, String field, String specialization,
                            String location, int iconCode) {

        workyClient client = new workyClient();
        client.setUsername(username);
        client.setPassword(password);
        client.setFirstname(firstName);
        client.setMiddlename(middleName);
        client.setLastname(lastName);
        client.setAge(age);
        client.setGender(gender);
        client.setEmail(email);
        client.setMobile(mobile);
        client.setProfile(profile);
        client.setCompany(company);
        client.setField(field);
        client.setSpecialization(specialization);
        client.setLocation(location);
        client.setIconCode(iconCode);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("client").document(username)
                .set(client)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


    /**
     * Edits a specific freelancer in the database in the cloud based from all of its member variables.
     * The change will be detected by the database listener which will then change the specific freelancer
     * in the <code>mFreelancer</code> array.
     *
     * @param username   the username of the freelancer
     * @param password   the password of the freelancer
     * @param firstName  the first name of the freelancer
     * @param middleName the middle name of the freelancer
     * @param lastName   the last name of the freelancer
     * @param age        the age of the freelancer
     * @param gender     the gender of the freelancer
     * @param email      the email of the freelancer
     * @param mobile     the mobile of the freelancer
     * @param profile    the profile of the freelancer
     * @param educ       the educ of the freelancer
     * @param expertise  the expertise of the freelancer
     * @param course     the course of the freelancer
     * @param location   the location of the freelancer
     */
    public  void editFreelancer(String username, String password, String firstName, String middleName,
                                String lastName, int age, String gender, String email, long mobile,
                                String profile, String educ, String expertise, String course,
                                String location, int iconCode) {
        workyFreelancer freelancer = new workyFreelancer();
        freelancer.setUsername(username);
        freelancer.setPassword(password);
        freelancer.setFirstname(firstName);
        freelancer.setMiddlename(middleName);
        freelancer.setLastname(lastName);
        freelancer.setAge(age);
        freelancer.setGender(gender);
        freelancer.setEmail(email);
        freelancer.setMobile(mobile);
        freelancer.setProfile(profile);
        freelancer.setEducation(educ);
        freelancer.setExpertise(expertise);
        freelancer.setCourse(course);
        freelancer.setLocation(location);
        freelancer.setIconCode(iconCode);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("freelancer").document(username)
                .set(freelancer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

}
