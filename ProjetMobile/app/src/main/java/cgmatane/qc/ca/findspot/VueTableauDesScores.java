package cgmatane.qc.ca.findspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
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
import java.util.Map;

import cgmatane.qc.ca.findspot.Modele.Objectif;
import cgmatane.qc.ca.findspot.Modele.Utilisateur;
import io.grpc.okhttp.internal.Util;

public class VueTableauDesScores extends AppCompatActivity
{
    protected ListView vueListeTableauDesScores;
    protected List<HashMap<String, String>> listeTableauDesScores;

    protected Intent intentionNaviguerVueAccueil;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String nomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_tableau_des_scores);

        vueListeTableauDesScores = (ListView)findViewById(R.id.vueListeTableauDesScores);

        listeTableauDesScores = new ArrayList<HashMap<String, String>>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Utilisateurs");
        nomID = user.getUid();

        final TextView nomTextView = (TextView) findViewById(R.id.nomUtilisateur);

        final TextView scoreTextView = (TextView) findViewById(R.id.scoreUtilisateur);

        reference.child(nomID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilisateur profilUtilisateur = snapshot.getValue(Utilisateur.class);

                if(profilUtilisateur != null){
                    String nom = profilUtilisateur.nom;
                    String score = profilUtilisateur.score;

                    nomTextView.setText(nom);
                    scoreTextView.setText("Score : " + score);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VueTableauDesScores.this,"Un probleme est survenu", Toast.LENGTH_LONG).show();
            }
        });

        preparerTableauDesScores();

        Button retourVueListeObjectif = (Button)findViewById(R.id.retourVueListeObjectif);

        //intentionNaviguerVueAccueil = new Intent(this, VueAccueil.class);

        retourVueListeObjectif.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        naviguerRetourListeObjectif();
                    }
                }
        );


    }
/*
    public List<HashMap<String, String>> preparerTableauDesScores()
    {
        List<HashMap<String, String>> listeTableauDesScores = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> tableauScores;

        tableauScores = new HashMap<String, String>();
        tableauScores.put("nomJoueur", "Paul");
        tableauScores.put("joueurScore", "Score 2598");
        listeTableauDesScores.add(tableauScores);

        return listeTableauDesScores;
    }*/

    public void preparerTableauDesScores()
    {
        //System.out.println("Je suis dans preparerTableauDesScores");

        reference = FirebaseDatabase.getInstance().getReference("Utilisateurs");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Log.e("Count " ,"" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String nom = postSnapshot.child("nom").getValue(String.class);
                    String email = postSnapshot.child("email").getValue(String.class);
                    String score = postSnapshot.child("score").getValue(String.class);

                    Utilisateur user = new Utilisateur(nom, email, score);

                    if(user != null) {
                        listeTableauDesScores.add(user.obtenirUtilisateurPourAfficher());
                        afficherScore();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: " , error.getMessage());
            }
        });
    }

    private void afficherScore()
    {
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listeTableauDesScores,
                android.R.layout.two_line_list_item,
                new String[] {"nom", "score"},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        vueListeTableauDesScores.setAdapter(adapter);
    }

    public void naviguerRetourListeObjectif()
    {
        this.finish();
    }
}