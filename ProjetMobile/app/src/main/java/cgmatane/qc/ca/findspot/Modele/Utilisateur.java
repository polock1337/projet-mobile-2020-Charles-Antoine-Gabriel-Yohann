package cgmatane.qc.ca.findspot.Modele;


import java.util.HashMap;

public class Utilisateur{

    public String nom, email, score;

    public Utilisateur()
    {

    }

    public Utilisateur(String nom, String email, String score)
    {
        this.nom = nom;
        this.email = email;
        this.score = score;
    }

    public HashMap<String, String> obtenirUtilisateurPourAfficher(){
        HashMap<String, String> utilisateurPourAfficher = new  HashMap<String, String>();
        utilisateurPourAfficher.put("nom", this.nom);
        utilisateurPourAfficher.put("score", this.score);

        return  utilisateurPourAfficher;
    }
}
