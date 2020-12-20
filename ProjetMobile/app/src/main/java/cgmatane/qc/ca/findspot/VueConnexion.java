package cgmatane.qc.ca.findspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VueConnexion extends AppCompatActivity
{
    protected Intent intentionNaviguerInscription;
    protected Intent intentionNaviguerAccueil;

    private EditText champCourriel, champDeMotDePasse;

    private FirebaseAuth authentification;

    private Button buttonConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_connexion);

        buttonConnexion = (Button)findViewById(R.id.buttonConnexion);

        champCourriel = (EditText) findViewById(R.id.champsEmail);
        champDeMotDePasse = (EditText) findViewById(R.id.champMotDePasseUtilisateur);

        authentification = FirebaseAuth.getInstance();

        //intentionNaviguerAccueil = new Intent(this, VueAccueil.class);

        buttonConnexion.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        userLogin();
                        //startActivity(intentionNaviguerAccueil);
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

                        startActivity(intentionNaviguerInscription);
                    }
                }
        );
    }

    private void userLogin()
    {
        String email = champCourriel.getText().toString().trim();
        String motDePasse = champDeMotDePasse.getText().toString().trim();

        if(email.isEmpty())
        {
            champCourriel.setError("Entree un Email!");
            champCourriel.requestFocus();
            return;
        }
        if(motDePasse.isEmpty())
        {
            champDeMotDePasse.setError("Veuillez entrer un mot de passe!");
            champDeMotDePasse.requestFocus();
            return;
        }
        if(motDePasse.length() < 6)
        {
            champDeMotDePasse.setError("Le mot de passe doit comporter au minimum 6 caractères");
            champDeMotDePasse.requestFocus();
            return;
        }

        authentification.signInWithEmailAndPassword(email, motDePasse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        startActivity(new Intent(VueConnexion.this, VueAccueil.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(VueConnexion.this,"Veuillez verifier vos courriel!", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(VueConnexion.this,"Erreurs dans le login! Veuillez ressayer!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}