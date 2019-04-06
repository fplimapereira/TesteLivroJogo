package com.pereira.felipe.testejogo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {

    private ProgressBar pgVida, pgHab, pgSorte;
    private TextView tvEnergia, tvEneTotal, tvHab, tvHabTotal, tvSorte, tvSorteTotal;
    private SharedPreferences personagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens);
        pgVida = findViewById(R.id.pgVida);
        pgHab = findViewById(R.id.pgHab);
        pgSorte = findViewById(R.id.pgSorte);
        tvEnergia = findViewById(R.id.tvEnergia);
        tvEneTotal = findViewById(R.id.tvEneTotal);
        tvHab = findViewById(R.id.tvHabilidade);
        tvHabTotal = findViewById(R.id.tvHabTotal);
        tvSorte = findViewById(R.id.tvSorte);
        tvSorteTotal = findViewById(R.id.tvSorteTotal);
        personagem = getSharedPreferences("Personagem", Context.MODE_PRIVATE);

        tvEnergia.setText(String.valueOf(personagem.getInt("Energia", 0)));
        tvEneTotal.setText(String.valueOf(personagem.getInt("EneTotal", 0)));
        tvHab.setText(String.valueOf(personagem.getInt("Habilidade", 0)));
        tvHabTotal.setText(String.valueOf(personagem.getInt("HabTotal", 0)));
        tvSorte.setText(String.valueOf(personagem.getInt("Sorte", 0)));
        tvSorteTotal.setText(String.valueOf(personagem.getInt("SorteTotal", 0)));

        pgVida.setMax(personagem.getInt("EneTotal", 0));
        pgHab.setMax(personagem.getInt("HabTotal", 0));
        pgSorte.setMax(personagem.getInt("SorteTotal", 0));
        pgVida.setProgress(personagem.getInt("Energia", 0));
        pgHab.setProgress(personagem.getInt("Habilidade", 0));
        pgSorte.setProgress(personagem.getInt("Sorte", 0));
    }
}
