package com.pereira.felipe.testejogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pereira.felipe.banco.BancoCore;
import com.pereira.felipe.banco.OponentePojo;
import com.pereira.felipe.banco.Utils;

import java.io.IOException;
import java.util.List;

import static com.pereira.felipe.banco.Utils.rollDice;

public class LutaActivity extends AppCompatActivity {

    private int indice, hab;
    private Intent i;
    private BancoCore banco;
    private SharedPreferences personagem;
    private List<OponentePojo> oponentes;

    private TextView ene, eneLife, userLife, atqUser, atqEne, turnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luta);
        i = getIntent();
        indice =  i.getIntExtra("indice", 0);
        banco = new BancoCore(this);
        initBanco();
        ene = findViewById(R.id.tvEnen);
        eneLife = findViewById(R.id.tvEneLf);
        userLife = findViewById(R.id.tvUserLf);
        atqUser = findViewById(R.id.tvAtqUser);
        atqEne = findViewById(R.id.tvAtqEne);
        turnos = findViewById(R.id.tvTurnos);
        oponentes = banco.getOponentes(indice);
        personagem = getSharedPreferences("Personagem", Context.MODE_PRIVATE);
        hab = personagem.getInt("Habilidade", 0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        for (OponentePojo oponente: oponentes) {
            ene.setText(oponente.getNome());
            eneLife.setText(String.valueOf(oponente.getEnergia()));
            userLife.setText(String.valueOf(personagem.getInt("Energia", 0)));
            simulacaoBatalha(oponente.getHabilidade());
        }
    }

    private void simulacaoBatalha(int habEne) {
        int atq = hab + rollDice();
        int atqEne = habEne + rollDice();
    }

    private void initBanco() {
        try{
            banco.createDatabase();
        }catch(IOException e){
            e.printStackTrace();
            throw new Error("Falha ao criar banco");
        }
        try{
            banco.openDataBase();
        }catch(SQLiteException sql){
            sql.printStackTrace();
            throw sql;
        }
    }

}
