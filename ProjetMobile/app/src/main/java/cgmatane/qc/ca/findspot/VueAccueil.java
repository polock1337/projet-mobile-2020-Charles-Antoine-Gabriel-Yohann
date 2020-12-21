package cgmatane.qc.ca.findspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cgmatane.qc.ca.findspot.Donnee.ObjectifDAO;
import cgmatane.qc.ca.findspot.Modele.Objectif;
import cgmatane.qc.ca.findspot.Modele.Utilisateur;

public class VueAccueil extends AppCompatActivity
{
    ObjectifDAO objectifDAO;
    protected ListView vueAccueilListeObjectif;
    protected List<HashMap<String, String>> listeObjectif;

    protected Intent intentionNaviguerTableauScores;
    protected Intent intentionNaviguerObjectif;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String nomID;
    private String scoreID;

    Utilisateur profilUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Utilisateurs");
        nomID = user.getUid();

        objectifDAO = ObjectifDAO.getInstance(nomID);
        super.onCreate(savedInstanceState);
        listeObjectif = new ArrayList<HashMap<String, String>>();
        setContentView(R.layout.activity_vue_accueil);
        vueAccueilListeObjectif = (ListView)findViewById(R.id.vueAccueilListeObjectif);
        System.out.println("vueAccueilListeObjectif !!!! : " + vueAccueilListeObjectif);
        preparerListeObjectif();



        final TextView nomTextView = (TextView) findViewById(R.id.nomUtilisateur);

        final TextView scoreTextView = (TextView) findViewById(R.id.scoreUtilisateur);

        reference.child(nomID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profilUtilisateur = snapshot.getValue(Utilisateur.class);

                if(profilUtilisateur != null){
                    String nom = profilUtilisateur.nom;
                    String score = profilUtilisateur.score;

                    nomTextView.setText(nom);
                    scoreTextView.setText("Score : " + score);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VueAccueil.this,"Un problème est survenu!", Toast.LENGTH_LONG).show();
            }
        });

        Button vueTableauDesScore = (Button)findViewById(R.id.vueTableauDesScores);

        intentionNaviguerTableauScores = new Intent(this, VueTableauDesScores.class);

        vueTableauDesScore.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        startActivity(intentionNaviguerTableauScores);
                    }
                }
        );

        intentionNaviguerObjectif = new Intent(this, VueObjectif.class);

        vueAccueilListeObjectif.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View vue,
                                            int positionDansAdapteur, long positionItem) {


                        ListView vueListeObjectif = (ListView)vue.getParent();

                        @SuppressWarnings("unchecked")
                        HashMap<String,String> objectif =
                                (HashMap<String, String>)
                                        vueListeObjectif.getItemAtPosition((int)positionItem);
                        Bundle b = new Bundle();
                        b.putString("id",objectif.get("id"));
                        intentionNaviguerObjectif.putExtras(b);
                        startActivity(intentionNaviguerObjectif);

                    }}
        );
    }

    public void preparerListeObjectif()
    {
        Task<QuerySnapshot> db = FirebaseFirestore.getInstance().collection("objectif")
                .get();
        db.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                listerOjectifNonFait();
            }
        });

    }
    private void listerOjectifNonFait()
    {
        Task<QuerySnapshot> db = FirebaseFirestore.getInstance().collection("objectifFait")
                .get();
        db.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (Objectif objectif : objectifDAO.listeObjectif) {
                        if(ObjectifDAO.getInstance(nomID).objectifEstNonFait(objectif))
                            listeObjectif.add(objectif.obtenirObjectifPourAfficher());
                    }
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    afficherObjectif();
                }
            }
        });
    }
    private void afficherObjectif()
    {
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listeObjectif,
                android.R.layout.two_line_list_item,
                new String[] {"nom", ""},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        vueAccueilListeObjectif.setAdapter(adapter);
    }
}