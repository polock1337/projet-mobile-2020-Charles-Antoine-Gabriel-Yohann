package cgmatane.qc.ca.findspot.Donnee;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cgmatane.qc.ca.findspot.Modele.Objectif;

public class ObjectifDAO {
    public List<Objectif> listeObjectif;
    public List<String> listeObjectifFait;
    private static ObjectifDAO instance = null;
    private ObjectifDAO(String idUtilisateur){
        listeObjectif = new ArrayList<Objectif>();
        listeObjectifFait = new ArrayList<String>();
        listerObjectifFait(idUtilisateur);
        listerObjectif();
    }

    public static ObjectifDAO getInstance(String idUtilisateur ){
        if(null == instance){
            instance = new ObjectifDAO(idUtilisateur);
        }
        return instance;
    }

    public List<Objectif> getListeObjectif(){
        return listeObjectif;
    }
    public void ajouterObjectifFait(String idObjectif,String idUtilisateur)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> objectifFait = new HashMap<>();
        objectifFait.put("idObjectif", idObjectif);
        objectifFait.put("idUtilisateur", idUtilisateur);
        db.collection("objectifFait").add(objectifFait);

    }
    public boolean objectifEstNonFait(Objectif objectif)
    {
        System.out.println(listeObjectifFait);
        if (listeObjectifFait.contains(objectif.getId()))
            return false;
        else
            return true;

    }
    private void listerObjectif() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("objectif")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()){
                                    listeObjectif.add(new Objectif(document.getId(),document.getData().get("nom").toString(),document.getGeoPoint("localisation"),document.getData().get("image").toString()));
                            }
                        } else {
                            Log.w("Info", "Error getting documents.", task.getException());

                        }

                    }
                });
    }
    private void listerObjectifFait(String idUtilisateur){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("objectifFait").whereEqualTo("idUtilisateur",idUtilisateur)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listeObjectifFait.add(document.getString("idObjectif"));
                                System.out.println(document.getString("idObjectif"));
                            }

                        } else {
                            Log.w("Info", "Error getting documents.", task.getException());
                        }

                    }
                });
    }
}