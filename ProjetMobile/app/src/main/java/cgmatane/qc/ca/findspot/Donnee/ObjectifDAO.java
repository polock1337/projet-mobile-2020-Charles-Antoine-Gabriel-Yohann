package cgmatane.qc.ca.findspot.Donnee;

import android.net.Credentials;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import cgmatane.qc.ca.findspot.Modele.Objectif;

public class ObjectifDAO {
    public List<Objectif> listeObjectif;
    private static ObjectifDAO instance = null;
    private ObjectifDAO(){
        listeObjectif = new ArrayList<Objectif>();
        listerObjectif();
    }

    public static ObjectifDAO getInstance(){
        if(null == instance){
            instance = new ObjectifDAO();
        }
        return instance;
    }
    public List<Objectif> getListeObjectif(){
        return listeObjectif;
    }
    private void listerObjectif() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("objectif")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId());
                                System.out.println("TEST");
                                listeObjectif.add(new Objectif(document.getId(),document.getData().get("nom").toString(),document.getGeoPoint("localisation"),document.getData().get("image").toString()));
                            }
                        } else {
                            Log.w("Info", "Error getting documents.", task.getException());

                        }

                    }
                });
    }
}
