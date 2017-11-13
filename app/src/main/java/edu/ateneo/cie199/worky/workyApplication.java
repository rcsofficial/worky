package edu.ateneo.cie199.worky;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class workyApplication extends Application {
    private ArrayList<workyFreelancer> mFreelancer = new ArrayList<>();
    private ArrayList<workyClient> mClient = new ArrayList<>();
    private ArrayList<workyAdmin> mAdmin = new ArrayList<>();
    private ArrayList<workyJobs> mJobs = new ArrayList<>();

    // TODO: Save client / freelancer accounts to server

    /* ADD FREELANCER ACCOUNT */
    public void addFreelancerAccount(workyFreelancer fAccount) {
        mFreelancer.add(fAccount);
        return;
    }

    /* ADD CLIENT ACCOUNT */
    public void addClientAccount(workyClient cAccount) {
        mClient.add(cAccount);
        return;
    }

    /* ADD JOB */
    public void addJob(workyJobs job) {
        mJobs.add(job);
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

    /* GET ADMIN ALL ACCOUNTS */
    public ArrayList<workyAdmin> getAdmin() {
        return mAdmin;
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

        for (int i = 0; i<mJobs.size(); i++) {
            if (mJobs.get(i).getJobfield().equals(toDeleteFromMain.getJobfield()) &&
                    mJobs.get(i).getJobtitle().equals(toDeleteFromMain.getJobtitle()) &&
                    mJobs.get(i).getSalary() == toDeleteFromMain.getSalary() &&
                    mJobs.get(i).getLocation().equals(toDeleteFromMain.getLocation()) &&
                    mJobs.get(i).getDescription().equals(toDeleteFromMain.getDescription())) {
                mJobs.remove(i);
            }
        }
        return;
    }

    /* EDIT JOB ENTRIES */
    public void editJob(int index, String jobField, String jobTitle, float salary, String location,
                        String description, String username, String usertype) {
        workyJobs toEditFromMain = getJobsByUsername(username, usertype).get(index);

        for (int i = 0; i<mJobs.size(); i++) {
            if (mJobs.get(i).getJobfield().equals(toEditFromMain.getJobfield()) &&
                    mJobs.get(i).getJobtitle().equals(toEditFromMain.getJobtitle()) &&
                    mJobs.get(i).getSalary() == toEditFromMain.getSalary() &&
                    mJobs.get(i).getLocation().equals(toEditFromMain.getLocation()) &&
                    mJobs.get(i).getDescription().equals(toEditFromMain.getDescription())) {
                mJobs.set(i, new workyJobs(jobField, jobTitle,
                        salary, location, description, username, usertype));
            }
        }
        return;
    }

    /* EDIT CLIENT ENTRIES */
    public  void editClient(String username, String password, String firstName, String middleName,
                            String lastName, int age, String gender, String email, int mobile,
                            String profile, String company, String field, String specialization,
                            String location) {
        workyClient toEditFromMain = getClientAcctByUsername(username);
        toEditFromMain.setPassword(password);
        toEditFromMain.setFirstname(firstName);
        toEditFromMain.setMiddlename(middleName);
        toEditFromMain.setLastname(lastName);
        toEditFromMain.setAge(age);
        toEditFromMain.setGender(gender);
        toEditFromMain.setEmail(email);
        toEditFromMain.setMobile(mobile);
        toEditFromMain.setProfile(profile);
        toEditFromMain.setCompany(company);
        toEditFromMain.setField(field);
        toEditFromMain.setSpecialization(specialization);
        toEditFromMain.setLocation(location);
    }

    /* EDIT FREELANCER ENTRIES */
    public  void editFreelancer(String username, String password, String firstName, String middleName,
                                String lastName, int age, String gender, String email, int mobile,
                                String profile, String educ, String expertise, String course,
                                String location) {
        workyFreelancer toEditFromMain = getFreelancerAcctByUsername(username);
        toEditFromMain.setPassword(password);
        toEditFromMain.setFirstname(firstName);
        toEditFromMain.setMiddlename(middleName);
        toEditFromMain.setLastname(lastName);
        toEditFromMain.setAge(age);
        toEditFromMain.setGender(gender);
        toEditFromMain.setEmail(email);
        toEditFromMain.setMobile(mobile);
        toEditFromMain.setProfile(profile);
        toEditFromMain.setEducation(educ);
        toEditFromMain.setExpertise(expertise);
        toEditFromMain.setCourse(course);
        toEditFromMain.setLocation(location);
    }

    // TODO DB TEST FUNCTIONS BEYOND THIS
    public void testDb() {
        Log.d("HELLO", "HERE1");

        // Access a Cloud Firestore instance from your Activity
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("HELLO", "HERE");

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("HELLO", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("HELLO", "Error adding document", e);
                    }
                });

        // Create a new user with a first, middle, and last name
        Map<String, Object> user1 = new HashMap<>();
        user1.put("first", "Alan");
        user1.put("middle", "Mathison");
        user1.put("last", "Turring");
        user1.put("born", 1912);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user1)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("HELLO", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("HELLO", "Error adding document", e);
                    }
                });

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("HELLO", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("HELLO", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    // TODO DELETE UNTIL HERE
}