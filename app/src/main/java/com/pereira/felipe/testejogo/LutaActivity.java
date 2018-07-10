package com.pereira.felipe.testejogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pereira.felipe.banco.BancoCore;
import com.pereira.felipe.banco.OponentePojo;
import com.pereira.felipe.banco.Utils;

import java.io.IOException;
import java.util.List;

import static com.pereira.felipe.banco.Utils.rollDice;

public class LutaActivity extends AppCompatActivity {

    private int indice, hab, uLife, enemLife, turno, habEne, opn;
    private BancoCore banco;
    private SharedPreferences personagem;
    private List<OponentePojo> oponentes;
    private Runnable r;

    private TextView ene, eneLife, userLife, atqUser, atqEne, turnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luta);
        indice = getIntent().getIntExtra("Indice", 0);
        banco = new BancoCore(this);
        initBanco();
        ene = findViewById(R.id.tvEnen);
        eneLife = findViewById(R.id.tvEneLf);
        userLife = findViewById(R.id.tvUserLf);
        atqUser = findViewById(R.id.tvAtqUser);
        atqEne = findViewById(R.id.tvAtqEne);
        turnos = findViewById(R.id.tvTurno);
        oponentes = banco.getOponentes(indice);
        personagem = getSharedPreferences("Personagem", Context.MODE_PRIVATE);
        hab = personagem.getInt("Habilidade", 0);
        userLife.setText(String.valueOf(personagem.getInt("Energia", 0)));
        uLife = personagem.getInt("Energia", 0);
        opn = 0;
        turno = 1;
        setaOponente(opn);
    }

    public void setaOponente(int fila){
        OponentePojo op = oponentes.get(fila);
        eneLife.setText(String.valueOf(op.getEnergia()));
        enemLife = op.getEnergia();
        ene.setText(op.getNome());
        habEne = op.getHabilidade();
        opn++;
    }



    public void ataque(View v){

        int atq = hab + rollDice();
        int atqEnem = habEne + rollDice();

        atqUser.setText(String.valueOf(atq));
        atqEne.setText(String.valueOf(atqEnem));


        if(atq < atqEnem){
            uLife = uLife - 2;
            userLife.setText(String.valueOf(uLife));
        }
        else{
            enemLife = enemLife - 2;
            eneLife.setText(String.valueOf(enemLife));
        }

        if(uLife > 0 && enemLife > 0){turno++;}
        else{turno = 1;}
        turnos.setText("Turno " + String.valueOf(turno));

        if(enemLife <= 0){
            if(oponentes.size() > opn){
                setaOponente(opn);
            }
        }

        if(uLife <=0){

        }

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
