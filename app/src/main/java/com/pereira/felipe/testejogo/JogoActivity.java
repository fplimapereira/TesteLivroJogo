package com.pereira.felipe.testejogo;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewSwitcher;

import com.pereira.felipe.banco.BancoCore;
import com.pereira.felipe.banco.JogoPojo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JogoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private Button acao;
    private TextSwitcher contexto;
    private Spinner escolhas;
    private JogoPojo evento;
    private BancoCore banco;
    private boolean init = true;
    private int ponteiro;
    private SharedPreferences personagem;
    private Random r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        acao = (Button) findViewById(R.id.bt_acao);
        contexto = (TextSwitcher) findViewById(R.id.tx_evento);
        escolhas = (Spinner) findViewById(R.id.spinner);
        loadAnimation();
        setFactory();
        setListener();
        banco = new BancoCore(this);

    }



    void loadAnimation() {
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        contexto.setInAnimation(fadeIn);
        contexto.setOutAnimation(fadeOut);
    }

    void setFactory() {
        contexto.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                TextView myText = new TextView(JogoActivity.this);
                myText.setGravity(Gravity.TOP | Gravity.LEFT);
                myText.setTextSize(15);
                myText.setTextColor(Color.BLACK);
                return myText;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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

        if (init) {
            evento = banco.getEvento(1);
            verificaEvento(evento);
        }


    }

    private void verificaEvento(JogoPojo evento) {
        contexto.setText(evento.getContexto());
        if (evento.getTeste() == null) {
            List<String> list = new ArrayList<>();
            list.add(evento.getAcao_um());
            if (evento.getAcao_dois() != null) {list.add(evento.getAcao_dois());}
            if (evento.getAcao_tres() != null) {list.add(evento.getAcao_tres());}
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            escolhas.setAdapter(adapter);
            escolhas.setOnItemSelectedListener(this);
        }else{
            verificaTeste(evento.getTeste());
        }
    }

    private void verificaTeste(String teste) {
        personagem = getSharedPreferences("personagem", Context.MODE_PRIVATE);
        r = new Random();
        switch (teste){
            case "habilidade":
                int habilidade = personagem.getInt("Habilidade", 0);
                if(habilidade <= r.nextInt(13)){
                    ponteiro = evento.getPonteiro_dois();}
                else {ponteiro = evento.getPonteiro_um();}
                break;

            case "sorte":
                int sorte = personagem.getInt("Sorte", 0);
                if(sorte <= r.nextInt(13)){
                    ponteiro = evento.getPonteiro_dois();}
                else {ponteiro = evento.getPonteiro_um();}
                sorte--;
                SharedPreferences.Editor editor = personagem.edit();
                editor.putInt("Sorte", sorte);
                editor.apply();
                break;
        }

    }


    public void setListener(){
        acao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               evento = banco.getEvento(ponteiro);
                verificaEvento(evento);
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                ponteiro = evento.getPonteiro_um();
                break;
            case 1:
                ponteiro = evento.getPonteiro_dois();
                break;
            case 2:
                ponteiro = evento.getPonteiro_tres();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}