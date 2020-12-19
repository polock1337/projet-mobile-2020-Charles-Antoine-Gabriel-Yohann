package cgmatane.qc.ca.findspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cgmatane.qc.ca.findspot.Utilisateur;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class VueInscription extends AppCompatActivity {

    private EditText champNom, champCourriel, champDeMotDePasse;

    private FirebaseAuth authentification;
    private Utilisateur utilisateur = new Utilisateur(null,null);

    private Button validerInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_inscription);

        authentification = FirebaseAuth.getInstance();

        champNom = (EditText) findViewById(R.id.champNomutilisateur);
        champCourriel = (EditText) findViewById(R.id.champEmail);
        champDeMotDePasse = (EditText) findViewById(R.id.champMotDePasse);


        validerInscription = (Button)findViewById(R.id.validerInscription);

        validerInscription.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        validerInscription();
                    }
                }


        );
    }

    private void validerInscription(){
        final String email= champCourriel.getText().toString().trim();
        final String nom= champNom.getText().toString().trim();
        String motDePasse= champDeMotDePasse.getText().toString().trim();

        if(nom.isEmpty())
        {
            champNom.setError("Un nom d'utilisateur est requis!");
            champNom.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            champCourriel.setError("Un Email est requis!");
            champCourriel.requestFocus();
            return;
        }
        if(motDePasse.isEmpty())
        {
            champDeMotDePasse.setError("Un mot de passe est requis!");
            champDeMotDePasse.requestFocus();
            return;
        }
        /*if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            champCourriel.setError("Entree une addresse valide");
            champCourriel.requestFocus();
            return;
        }*/
        if(motDePasse.length() < 6){
            champDeMotDePasse.setError("Au minimum contenir 6 caracteres!");
            champDeMotDePasse.requestFocus();
            return;
        }


        authentification.createUserWithEmailAndPassword(email, motDePasse)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Utilisateur utilisateur = new Utilisateur(nom, email);
                            utilisateur = new Utilisateur(nom, email);

                            FirebaseDatabase.getInstance().getReference("Utilisateurs")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(utilisateur).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(VueInscription.this, "Utilisateur a bien ete creer!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(VueInscription.this, VueConnexion.class));
                                    }else {
                                        Toast.makeText(VueInscription.this, "Inscription echouer! Essayez de nouveau!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(VueInscription.this, "Inscription echouer! Essayez de nouveau!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}