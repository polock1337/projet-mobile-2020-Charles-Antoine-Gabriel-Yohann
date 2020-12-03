package cgmatane.qc.ca.preuve_concept_projet_mobil.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cgmatane.qc.ca.preuve_concept_projet_mobil.R;

public class VueObjectif extends AppCompatActivity
{
    protected Intent intentionNaviguerVueAccueil;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_objectif);

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

    public void naviguerRetourListeObjectif()
    {
        this.finish();
    }

}