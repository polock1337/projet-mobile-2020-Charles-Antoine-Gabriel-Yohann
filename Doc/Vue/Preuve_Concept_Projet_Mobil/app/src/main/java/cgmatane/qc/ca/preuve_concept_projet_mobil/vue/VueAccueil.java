package cgmatane.qc.ca.preuve_concept_projet_mobil.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cgmatane.qc.ca.preuve_concept_projet_mobil.R;

public class VueAccueil extends AppCompatActivity
{
    protected ListView vueAccueilListeObjectif;
    protected List<HashMap<String, String>> listeObjectif;

    protected Intent intentionNaviguerTableauScores;
    protected Intent intentionNaviguerObjectif;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_accueil);
        vueAccueilListeObjectif = (ListView)findViewById(R.id.vueAccueilListeObjectif);
        System.out.println("vueAccueilListeObjectif !!!! : " + vueAccueilListeObjectif);
        listeObjectif = preparerListeObjectif();
        //System.out.println("la liste objectif : " + listeObjectif);

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listeObjectif,
                android.R.layout.two_line_list_item,
                new String[] {"lieu", "objectif"},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        vueAccueilListeObjectif.setAdapter(adapter);

        /*Button vueProfil = (Button)findViewById(R.id.vueProfil);

        vueProfil.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        // TODO : coder !

                        Toast message = Toast.makeText(

                                getApplicationContext(),
                                "vue profile",
                                Toast.LENGTH_LONG);

                        message.show();

                        //startActivity(intentionNaviguerAjouterLivre);
                        //startActivityForResult(intentionNaviguerProfit, ACTIVITE_PROFIT);
                    }
                }
        );*/

        Button vueTableauDesScore = (Button)findViewById(R.id.vueTableauDesScores);

        intentionNaviguerTableauScores = new Intent(this, VueTableauDesScores.class);

        vueTableauDesScore.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        // TODO : coder !

                        /*Toast message = Toast.makeText(

                                getApplicationContext(),
                                "vue tableau des scores",
                                Toast.LENGTH_LONG);

                        message.show();*/

                        startActivity(intentionNaviguerTableauScores);
                        //startActivityForResult(intentionNaviguerProfit, ACTIVITE_PROFIT);
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

                        /*
                        Toast message = Toast.makeText(getApplicationContext(),
                                "Position "+positionItem + " titre " + livre.get("titre"),
                                Toast.LENGTH_SHORT);
                        message.show();
                         */

                        startActivity(intentionNaviguerObjectif);

                    }}
        );
    }


    public List<HashMap<String, String>> preparerListeObjectif()
    {
        listeObjectif = new ArrayList<HashMap<String, String>>();
        System.out.println("Je suis dans preparerListeObjectif");

        HashMap<String, String> objectif;

        objectif = new HashMap<String, String>();
        objectif.put("lieu", "Parc des ile");
        objectif.put("objectif", "Trouve les photos de l'equipe de hockey de matane des annee 19XX");
        //System.out.println("PreparerListeObjectif()");
        //System.out.println("objectif : " + objectif);

        listeObjectif.add(objectif);
        //System.out.println("la liste des objectif retourner : " + listeObjectif);
        return listeObjectif;
    }
}