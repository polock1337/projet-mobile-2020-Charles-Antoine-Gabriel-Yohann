package cgmatane.qc.ca.findspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cgmatane.qc.ca.findspot.Donnee.ObjectifDAO;
import cgmatane.qc.ca.findspot.Modele.Objectif;
import cgmatane.qc.ca.findspot.R;

public class VueAccueil extends AppCompatActivity
{
    ObjectifDAO objectifDAO;
    protected ListView vueAccueilListeObjectif;
    protected List<HashMap<String, String>> listeObjectif;

    protected Intent intentionNaviguerTableauScores;
    protected Intent intentionNaviguerObjectif;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        objectifDAO = ObjectifDAO.getInstance();
        super.onCreate(savedInstanceState);
        listeObjectif = new ArrayList<HashMap<String, String>>();
        setContentView(R.layout.activity_vue_accueil);
        vueAccueilListeObjectif = (ListView)findViewById(R.id.vueAccueilListeObjectif);
        System.out.println("vueAccueilListeObjectif !!!! : " + vueAccueilListeObjectif);
        preparerListeObjectif();

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
        System.out.println("Je suis dans preparerListeObjectif");
        Task<QuerySnapshot> db = FirebaseFirestore.getInstance().collection("objectif")
            .get();
        db.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                    for (Objectif objectif : objectifDAO.listeObjectif) {
                        listeObjectif.add(objectif.obtenirObjectifPourAfficher());
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        afficherObjectif();

                    }

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