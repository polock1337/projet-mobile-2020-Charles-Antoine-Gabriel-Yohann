package cgmatane.qc.ca.findspot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cgmatane.qc.ca.findspot.R;

public class VueTableauDesScores extends AppCompatActivity
{
    protected ListView vueListeTableauDesScores;
    protected List<HashMap<String, String>> listeTableauDesScores;

    protected Intent intentionNaviguerVueAccueil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_tableau_des_scores);

        vueListeTableauDesScores = (ListView)findViewById(R.id.vueListeTableauDesScores);

        listeTableauDesScores = preparerTableauDesScores();

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