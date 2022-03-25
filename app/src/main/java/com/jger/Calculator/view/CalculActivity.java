package com.jger.Calculator.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jger.groupe5v2.controller.ScoreBaseHelper;
import com.jger.groupe5v2.controller.ScoreDao;
import com.jger.groupe5v2.controller.ScoreService;
import com.jger.groupe5v2.model.Score;
import com.jger.groupe5v2.model.exception.DivideException;
import com.jger.groupe5v2.R;
import com.jger.groupe5v2.model.TypeOperationEnum;

import java.util.Random;

public class CalculActivity extends AppCompatActivity {
    Integer premierElement = 0;
    Integer deuxiemeElement = 0;
    Integer premierElementASK = 0;
    Integer deuxiemeElementASK = 0;
    Integer scorePlus = 0;
    Integer vie = 3;
    Integer type = null;
    TypeOperationEnum typeOperation = null;
    TypeOperationEnum typeOperationASK = null;
    TextView textViewReponse;
    TextView textViewCalcul;
    Integer BORNE_HAUTE = 9999999;
    ScoreService scoreService;
    TextView txtScore;
    TextView txtScoreMax;
    TextView txtvie;
    String txtVieRestant="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);
        scoreService = new ScoreService(new ScoreDao(new ScoreBaseHelper(this)));
        textViewReponse = findViewById(R.id.textViewReponse);
        textViewCalcul = findViewById(R.id.textviewCalcul);
        txtScoreMax = findViewById(R.id.txtScoreMax);
        txtvie = findViewById(R.id.txtVie);
        txtScore = findViewById(R.id.txtScore);

        Button button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(view -> ajouterNombre(1));
        Button button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(view -> ajouterNombre(2));
        Button button3 = findViewById(R.id.button_3);
        button3.setOnClickListener(view -> ajouterNombre(3));
        Button button4 = findViewById(R.id.button_4);
        button4.setOnClickListener(view -> ajouterNombre(4));
        Button button5 = findViewById(R.id.button_5);
        button5.setOnClickListener(view -> ajouterNombre(5));
        Button button6 = findViewById(R.id.button_6);
        button6.setOnClickListener(view -> ajouterNombre(6));
        Button button7 = findViewById(R.id.button_7);
        button7.setOnClickListener(view -> ajouterNombre(7));
        Button button8 = findViewById(R.id.button_8);
        button8.setOnClickListener(view -> ajouterNombre(8));
        Button button9 = findViewById(R.id.button_9);
        button9.setOnClickListener(view -> ajouterNombre(9));
        Button button0 = findViewById(R.id.button_0);
        button0.setOnClickListener(view -> ajouterNombre(0));

        String res = txtScore.getText().toString();
        String resMax = txtScoreMax.getText().toString();

        Button btnValider = findViewById(R.id.btnValider);
        btnValider.setOnClickListener(view -> {
            try {
                validation(res, resMax);
            } catch (DivideException e) {
                e.printStackTrace();
            }
        });

        Button BtnSuppr = findViewById(R.id.BtnSuppr);
        BtnSuppr.setOnClickListener(view -> videTextViewReponse());

        try {
            changementCalcul();
        } catch (DivideException e) {
            e.printStackTrace();
        }

        txtScoreMax.setText(txtScoreMax.getText() +" "+ scoreService.getMax());
        txtScore.setText(res +" "+ scorePlus.toString());
    }

    private void validation(String res, String resMax) throws DivideException {
        if (faisLeCalculASK().equals(premierElement)){
            scorePlus++;
            txtScore.setText(res +" "+ scorePlus.toString());
            if(scoreService.getMax()<scorePlus){
                txtScoreMax.setText(resMax +" "+ scorePlus.toString());
            }
            videTextViewReponse();

        }
        else if(vie==1){
            Score score = new Score();
            score.setScore(scorePlus);
            scoreService.storeInDB(score);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            vie--;
            txtVieRestant="";
            for (int i=0; i<vie; i++){
                txtVieRestant = txtVieRestant+"♥";
            }
            txtvie.setText(txtVieRestant);
        }
        changementCalcul();
    }

    private void changementCalcul() throws DivideException {
        Random r = new Random();
        premierElementASK = r.nextInt(100);
        deuxiemeElementASK = r.nextInt(100);
        type = r.nextInt(4);
        switch (type) {
            case 0:
                typeOperationASK=TypeOperationEnum.ADD;
                break;
            case 1:
                typeOperationASK=TypeOperationEnum.SUBSTRACT;
                break;
            case 2:
                typeOperationASK=TypeOperationEnum.DIVIDE;
                break;
            case 3:
                typeOperationASK=TypeOperationEnum.MULTIPLY;
                break;
        }
        if (typeOperationASK==TypeOperationEnum.DIVIDE && (premierElementASK % deuxiemeElementASK != 0 || deuxiemeElementASK==0)) {
            changementCalcul();
        }
        if (typeOperationASK==TypeOperationEnum.SUBSTRACT && premierElementASK < deuxiemeElementASK) {
            changementCalcul();
        }
        String TextQuestion = premierElementASK.toString() + typeOperationASK.getSymbol() + deuxiemeElementASK.toString();
        textViewCalcul.setText(TextQuestion);
    }

    public void ajouterNombre(Integer valeur) {

        if (10 * premierElement + valeur > BORNE_HAUTE) {
            Toast.makeText(this, getString(R.string.ERROR_VALEUR_TROP_GRANDE), Toast.LENGTH_LONG).show();
        } else {
            premierElement = 10 * premierElement + valeur;
        }

        majText();
    }

    private void majText() {
        String textAAfficher;
        if (typeOperation == null) {
            textAAfficher = premierElement.toString();
        } else {
            textAAfficher = premierElement + " " + typeOperation.getSymbol() + " " + deuxiemeElement;
        }
        textViewReponse.setText(textAAfficher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(false);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        MenuItem boutonVider = menu.findItem(R.id.toolbarNettoyer);
        boutonVider.setOnMenuItemClickListener(menuItem -> videTextViewReponse());
        //boutonCalculer.setOnMenuItemClickListener(menuItem -> ouvrirDernierCalcul());

        return true;
    }

    private boolean videTextViewReponse() {
        TextView textViewReponse = findViewById(R.id.textViewReponse);
        textViewReponse.setText("");
        premierElement = 0;
        deuxiemeElement = 0;
        typeOperation = null;
        return true;
    }

    private Integer faisLeCalculASK() throws DivideException {
        switch (typeOperationASK) {
            case ADD:
                return premierElementASK + deuxiemeElementASK;
            case DIVIDE:
                if (deuxiemeElement != 0) {
                    return premierElementASK / deuxiemeElementASK;
                } else {
                    throw new DivideException();
                }

            case SUBSTRACT:
                return premierElementASK - deuxiemeElementASK;
            case MULTIPLY:
                return premierElementASK * deuxiemeElementASK;
        }
        return 0;
    }
}