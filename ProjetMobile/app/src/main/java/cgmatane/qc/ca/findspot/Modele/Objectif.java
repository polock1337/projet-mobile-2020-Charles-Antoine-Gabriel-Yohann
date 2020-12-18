package cgmatane.qc.ca.findspot.Modele;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;

public class Objectif {
    protected String id;
    protected String nom;
    protected GeoPoint localisation;
    protected String reference;

    public Objectif(String id, String nom, GeoPoint localisation, String reference) {
        this.id = id;
        this.nom = nom;
        this.localisation = localisation;
        this.reference = reference;
    }

    public String getNom() {
        return nom;
    }

    public GeoPoint getLocalisation() {
        return localisation;
    }

    public String getReference() {
        return reference;
    }

    public String getId() {
        return id;
    }

    public HashMap<String, String> obtenirObjectifPourAfficher(){
        HashMap<String, String> objectifPourAfficher = new  HashMap<String, String>();
        objectifPourAfficher.put("nom", this.nom);
        objectifPourAfficher.put("id", "" + this.id);

        return  objectifPourAfficher;
    }
}