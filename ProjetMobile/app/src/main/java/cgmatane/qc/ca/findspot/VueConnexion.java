package cgmatane.qc.ca.findspot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cgmatane.qc.ca.findspot.R;

public class VueConnexion extends AppCompatActivity
{
    protected Intent intentionNaviguerInscription;
    protected Intent intentionNaviguerAccueil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_connexion);

        Button buttonConnexion = (Button)findViewById(R.id.buttonConnexion);

        intentionNaviguerAccueil = new Intent(this, VueAccueil.class);

        buttonConnexion.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        // TODO : coder !

                        /*Toast message = Toast.makeText(

                                getApplicationContext(),
                                "Connexion Valider",
                                Toast.LENGTH_LONG);

                        message.show();*/
                        //startActivityForResult(intentionNaviguerProfit, ACTIVITE_PROFIT);
                        startActivity(intentionNaviguerAccueil);
                    }
                }


        );

        Button buttonInscription = (Button)findViewById(R.id.buttonInscription);

        intentionNaviguerInscription = new Intent(this, VueInscription.class);

        buttonInscription.setOnClickListener(

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

                        startActivity(intentionNaviguerInscription);
                        //startActivityForResult(intentionNaviguerProfit, ACTIVITE_PROFIT);
                    }
                }
        );
    }
}