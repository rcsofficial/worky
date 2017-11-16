package edu.ateneo.cie199.worky;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static android.content.ContentValues.TAG;

public class workyApplication extends Application{
    private ArrayList<workyFreelancer> mFreelancer = new ArrayList<>();
    private ArrayList<workyClient> mClient = new ArrayList<>();
    private ArrayList<workyJobs> mJobs = new ArrayList<>();
    private ArrayList<workyLinkJob> mLinkJob = new ArrayList<>();
    private Boolean usersInitialized = false;
    private Boolean jobsInitialized = false;

    public void initializeUsers() {
        if (!usersInitialized) {
            usersInitialized = true;
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
                                        int i = getFreelancerIndexByUsername(freelancer.getUsername());
                                        mFreelancer.set(i, freelancer);
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
                                        int i = getClientIndexByUsername(client.getUsername());
                                        mClient.set(i, client);
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
                                        int i = getJobIndexByTypeUsernameTitle(job.getUsertype(), job.getUsername(), job.getJobtitle());
                                        mJobs.set(i, job);
                                        Log.d(TAG, "Modified job: " + dc.getDocument().getData());
                                        break;
                                    case REMOVED:
                                        mJobs.remove(dc.getDocument().toObject(workyJobs.class));
                                        Log.d(TAG, "Removed job: " + dc.getDocument().getData());
                                        break;
                                }
                            }
                        }
                    });
        }
    }


    public void iniitilizeJobLinks() {
        if (!jobsInitialized) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            jobsInitialized = true;
            /* INITIIALIZE LISTENER FOR JOBLINK COLLECTION */
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
                                            String jobTitle = dc.getDocument().get("job").toString();
                                            String jobUsername;
                                            if (usertype.equals("Freelancer"))
                                                jobUsername = freelancer;
                                            else
                                                jobUsername = client;
                                            workyLinkJob linkJob = new workyLinkJob(usertype,
                                                    getClientAcctByUsername(client),
                                                    getFreelancerAcctByUsername(freelancer),
                                                    getJobByTypeUsernameTitle(usertype, jobUsername, jobTitle));

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
                                        //mLinkJob.remove(dc.getDocument().toObject(workyLinkJob.class));
                                        Log.d(TAG, "Removed Job Link: " + dc.getDocument().getData());
                                        break;
                                }
                            }
                        }
                    });
        }
    }


    /* ADDS A JOB THAT LINKS A CLIENT AND A FREELANCER */
    public void addLinkJob(String jobUserType, String clientUsername, String freelancerUsername,
                           workyJobs job) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> docData = new HashMap<>();
        docData.put("usertype", jobUserType);
        docData.put("client", clientUsername);
        docData.put("freelancer", freelancerUsername);
        docData.put("job", job.getJobtitle());
        db.collection("joblink").add(docData);
        return;
    }


    /* GET LINKED JOBS FOR A SPECIFIC CLIENT */
    public ArrayList<workyLinkJob> getLinkedJobsByTypeClient(String clientUsername) {
        ArrayList<workyLinkJob> linkJobs = new ArrayList<>();
        for (int i = 0; i < mLinkJob.size(); i++) {
            if ( mLinkJob.get(i).getJobUsertype().equals("Client") &&
                   mLinkJob.get(i).getClient().getUsername().equals(clientUsername) )
                linkJobs.add(mLinkJob.get(i));
        }
        return linkJobs;
    }


    /* GET LINKED JOBS FOR A SPECIFIC FREELANCER */
    public ArrayList<workyLinkJob> getLinkedJobsByTypeFreelancer(String freelancerUsername) {
        ArrayList<workyLinkJob> linkJobs = new ArrayList<>();
        for (int i = 0; i < mLinkJob.size(); i++) {
            if ( mLinkJob.get(i).getJobUsertype().equals("Freelancer") &&
                    mLinkJob.get(i).getFreelancer().getUsername().equals(freelancerUsername) )
                linkJobs.add(mLinkJob.get(i));
        }
        return linkJobs;
    }


    /* GET CLIENT INDEX BY USERNAME */
    public int getClientIndexByUsername(String username) {
        for (int i = 0; i < mClient.size(); i++) {
            if (mClient.get(i).getUsername().equals(username))
                return i;
        }
        return -1;
    }


    /* GET SPECIFIC JOB BY TYPE, USERNAME, AND TITLE */
    public workyJobs getJobByTypeUsernameTitle(String type, String username, String title) {
        for (int i = 0; i < mJobs.size(); i++) {
            if ( mJobs.get(i).getUsertype().equals(type) &&
                    mJobs.get(i).getUsername().equals(username) &&
                    mJobs.get(i).getJobtitle().equals(title) )
                return mJobs.get(i);
        }
        return null;
    }


    /* GET JOB INDEX BY TYPE, USERNAME AND TITLE */
    public int getJobIndexByTypeUsernameTitle(String type, String username, String title) {
        for (int i = 0; i < mJobs.size(); i++) {
            if ( mJobs.get(i).getUsertype().equals(type) &&
                 mJobs.get(i).getUsername().equals(username) &&
                 mJobs.get(i).getJobtitle().equals(title) )
                return i;
        }
        return -1;
    }


    /* GET FREELANCER INDEX BY USERNAME */
    public int getFreelancerIndexByUsername(String username) {
        for (int i = 0; i < mFreelancer.size(); i++) {
            if (mFreelancer.get(i).getUsername().equals(username))
                return i;
        }
        return -1;
    }


    /* ADD FREELANCER ACCOUNT */
    public void addFreelancerAccount(workyFreelancer fAccount) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("freelancer").document(fAccount.getUsername()).set(fAccount);
        return;
    }


    /* ADD CLIENT ACCOUNT */
    public void addClientAccount(workyClient cAccount) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("client").document(cAccount.getUsername()).set(cAccount);
        return;
    }


    /* ADD JOB */
    public void addJob(workyJobs job) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("job").document(job.getUsertype() + job.getUsername() + ": " + job.getJobtitle()).set(job);
        return;
    }


    /* ACCOUNT FREELANCE EXISTENCE VERIFICATION */
    public boolean isFreelancerExistent(String username, String password) {
        for (int i=0; i<mFreelancer.size(); i++) {
            if (mFreelancer.get(i).getUsername().equals(username) &&
                    mFreelancer.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }


    /* ACCOUNT CLIENT EXISTENCE VERIFICATION */
    public boolean isClientExistent(String username, String password) {
        for (int i=0; i<mClient.size(); i++) {
            if (mClient.get(i).getUsername().equals(username) &&
                    mClient.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }


    /* USERNAME FREELANCE TAKEN VERIFICATION */
    public boolean isFreelancerUsernameTaken(String username) {
        for (int i = 0; i < mFreelancer.size(); i++) {
            if (mFreelancer.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }


    /* USERNAME CLIENT TAKEN VERIFICATION */
    public boolean isClientUsernameTaken(String username) {
        for (int i = 0; i < mClient.size(); i++) {
            if (mClient.get(i).getUsername().equals(username))
                return true;
        }
        return false;
    }


    /* GET FREELANCER ALL ACCOUNTS */
    public ArrayList<workyFreelancer> getAllFreelancers() {
        return mFreelancer;
    }


    /* GET FREELANCE ACCOUNT BY USERNAME */
    public workyFreelancer getFreelancerAcctByUsername(String username) {
        int index = -1;
        for (int i = 0; i < mFreelancer.size(); i++) {
            if (mFreelancer.get(i).getUsername().equals(username))
                index = i;
        }
        return mFreelancer.get(index);
    }


    /* GET CLIENT ALL ACCOUNTS */
    public ArrayList<workyClient> getAllClients() {
        return mClient;
    }


    /* GET CLIENT ACCOUNT BY USERNAME */
    public workyClient getClientAcctByUsername(String username) {
        int index = -1;
        for (int i = 0; i < mClient.size(); i++) {
            if (mClient.get(i).getUsername().equals(username))
                index = i;
        }
        return mClient.get(index);
    }


    /* GET JOB ALL ENTRIES */
    public ArrayList<workyJobs> getAllJobs(){
        return mJobs;
    }


    /* GET JOB ENTRIES BY USERNAME AND USERTYPE */
    public ArrayList<workyJobs> getJobsByUsername(String username, String usertype) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < mJobs.size(); i++) {
            if (mJobs.get(i).getUsername().equals(username) &&
                    mJobs.get(i).getUsertype().equals(usertype))
                outputEntries.add(mJobs.get(i));
        }
        return outputEntries;
    }


    /* GET JOB ENTRIES BY JOB CATEGORY */
    public ArrayList<workyJobs> getJobsByField(String jobField) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < mJobs.size(); i++) {
            if (mJobs.get(i).getJobfield().equals(jobField))
                outputEntries.add(mJobs.get(i));
        }
        return outputEntries;
    }


    /* GET JOB ENTRIES BY JOB TITLE, CATEGORY, USERTYPE */
    public ArrayList<workyJobs> getJobsByTitle(String jobTitle, String jobField,
                                               String userType) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < getJobsByField(jobField).size(); i++) {
            if (getJobsByField(jobField).get(i).getJobtitle().equals(jobTitle) &&
                    getJobsByField(jobField).get(i).getUsertype().equals(userType))
                outputEntries.add(getJobsByField(jobField).get(i));
        }
        return outputEntries;
    }


    /* GET JOB ENTRIES BY MINIMUM SALARY, CATEGORY, USERTYPE */
    public ArrayList<workyJobs> getJobsByMinSalary(float salary, String jobField,
                                                   String userType) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < getJobsByField(jobField).size(); i++) {
            if (getJobsByField(jobField).get(i).getSalary() >= salary &&
                    getJobsByField(jobField).get(i).getUsertype().equals(userType))
                outputEntries.add(getJobsByField(jobField).get(i));
        }
        return outputEntries;
    }


    /* GET JOB ENTRIES BY MAXIMUM SALARY, CATEGORY, USERTYPE */
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


    /* GET JOB ENTRIES BY LOCATION, CATEGORY, USERTYPE*/
    public ArrayList<workyJobs> getJobsByLocation(String location, String jobField,
                                                  String userType) {
        ArrayList<workyJobs> outputEntries = new ArrayList<>();
        for (int i = 0; i < getJobsByField(jobField).size(); i++) {
            if (getJobsByField(jobField).get(i).getLocation().equals(location) &&
                    getJobsByField(jobField).get(i).getUsertype().equals(userType))
                outputEntries.add(getJobsByField(jobField).get(i));
        }
        return outputEntries;
    }


    /* DELETE JOB ENTRIES */
    public void deleteJob(String username, String usertype, int index) {
        workyJobs toDeleteFromMain = getJobsByUsername(username, usertype).get(index);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("job").document(toDeleteFromMain.getUsertype()+ toDeleteFromMain.getUsername() + ": " + toDeleteFromMain.getJobtitle())
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


    /* EDIT JOB ENTRIES */
    public void editJob(int index, String jobField, String jobTitle, float salary, String location,
                        String description, String username, String usertype) {
        workyJobs toEditFromMain = getJobsByUsername(username, usertype).get(index);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("job").document(toEditFromMain.getUsertype() + toEditFromMain.getUsername() + ": " + toEditFromMain.getJobtitle())
                .set(new workyJobs(jobField, jobTitle,
                        salary, location, description, username, usertype))
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

        return;
    }


    /* EDIT CLIENT ENTRIES */
    public  void editClient(String username, String password, String firstName, String middleName,
                            String lastName, int age, String gender, String email, long mobile,
                            String profile, String company, String field, String specialization,
                            String location) {

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


    /* EDIT FREELANCER ENTRIES */
    public  void editFreelancer(String username, String password, String firstName, String middleName,
                                String lastName, int age, String gender, String email, long mobile,
                                String profile, String educ, String expertise, String course,
                                String location) {
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
