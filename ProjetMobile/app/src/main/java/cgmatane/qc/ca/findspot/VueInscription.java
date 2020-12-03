package cgmatane.qc.ca.findspot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cgmatane.qc.ca.findspot.R;

public class VueInscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_inscription);

        Button validerInscription = (Button)findViewById(R.id.validerInscription);

        //intentionNaviguerVueAccueil = new Intent(this, VueAccueil.class);

        validerInscription.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        // TODO : coder !

                       Toast message = Toast.makeText(

                                getApplicationContext(),
                                "Inscription Valider",
                                Toast.LENGTH_LONG);

                        message.show();
                        //startActivityForResult(intentionNaviguerProfit, ACTIVITE_PROFIT);
                    }
                }


        );
    }
}