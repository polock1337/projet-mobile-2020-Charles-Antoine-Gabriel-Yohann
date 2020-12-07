package ca.qc.cgmatane.prototypeswipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View viewRoot = (View)findViewById(R.id.viewRoot);
        viewRoot.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void surSwipeDessus() {
                Toast.makeText(getApplicationContext(), "swipe dessus", Toast.LENGTH_SHORT).show();
            }
            public void surSwipeDroit() {
                Toast.makeText(getApplicationContext(), "swipe droit", Toast.LENGTH_SHORT).show();
            }
            public void surSwipeGauche() {
                Toast.makeText(getApplicationContext(), "swipe gauche", Toast.LENGTH_SHORT).show();
            }
            public void surSwipeDessous() {
                Toast.makeText(getApplicationContext(), "swipe dessous", Toast.LENGTH_SHORT).show();
            }

        });
    }
}