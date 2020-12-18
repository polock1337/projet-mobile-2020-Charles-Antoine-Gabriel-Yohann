package cgmatane.qc.ca.findspot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.widget.SimpleAdapter;

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
    protected Intent intentionNaviguerConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        objectifDAO = ObjectifDAO.getInstance();
        super.onCreate(savedInstanceState);
        listeObjectif = new ArrayList<HashMap<String, String>>();
        setContentView(R.layout.activity_vue_accueil);
        vueAccueilListeObjectif = (ListView)findViewById(R.id.vueAccueilListeObjectif);
        System.out.println("vueAccueilListeObjectif !!!! : " + vueAccueilListeObjectif);
        listeObjectif = preparerListeObjectif();
        //System.out.println("la liste objectif : " + listeObjectif);

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listeObjectif,
                android.R.layout.two_line_list_item,
                new String[] {"nom", ""},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        vueAccueilListeObjectif.setAdapter(adapter);

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


    public List<HashMap<String, String>> preparerListeObjectif()
    {
        System.out.println("Je suis dans preparerListeObjectif");

        for (Objectif objectif : objectifDAO.listeObjectif) {
            listeObjectif.add(objectif.obtenirObjectifPourAfficher());
        }

        return listeObjectif;
    }
}