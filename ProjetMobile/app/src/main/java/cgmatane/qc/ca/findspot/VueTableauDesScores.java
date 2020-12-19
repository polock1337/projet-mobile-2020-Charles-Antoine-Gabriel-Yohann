package cgmatane.qc.ca.findspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cgmatane.qc.ca.findspot.R;

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

        listeTableauDesScores = preparerTableauDesScores();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Utilisateurs");
        nomID = user.getUid();

        final TextView nomTextView = (TextView) findViewById(R.id.nomUtilisateur);

        reference.child(nomID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilisateur profilUtilisateur = snapshot.getValue(Utilisateur.class);

                if(profilUtilisateur != null){
                    String nom = profilUtilisateur.nom;

                    nomTextView.setText(nom);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VueTableauDesScores.this,"Un probleme est survenu", Toast.LENGTH_LONG).show();
            }
        });

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listeTableauDesScores,
                android.R.layout.two_line_list_item,
                new String[] {"nomJoueur", "joueurScore"},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        vueListeTableauDesScores.setAdapter(adapter);

        Button retourVueListeObjectif = (Button)findViewById(R.id.retourVueListeObjectif);

        //intentionNaviguerVueAccueil = new Intent(this, VueAccueil.class);

        retourVueListeObjectif.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        // TODO : coder !

                       /* Toast message = Toast.makeText(

                                getApplicationContext(),
                                "vue Ecran Accueil",
                                Toast.LENGTH_LONG);

                        message.show();*/

                        naviguerRetourListeObjectif();
                        //startActivityForResult(intentionNaviguerProfit, ACTIVITE_PROFIT);
                    }
                }


        );


    }

    public List<HashMap<String, String>> preparerTableauDesScores()
    {
        List<HashMap<String, String>> listeTableauDesScores = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> tableauScores;

        tableauScores = new HashMap<String, String>();
        tableauScores.put("nomJoueur", "Paul");
        tableauScores.put("joueurScore", "Score 2598");
        listeTableauDesScores.add(tableauScores);

        return listeTableauDesScores;
    }

    public void naviguerRetourListeObjectif()
    {
        this.finish();
    }
}