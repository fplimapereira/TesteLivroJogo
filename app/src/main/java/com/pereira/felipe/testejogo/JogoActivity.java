package com.pereira.felipe.testejogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.pereira.felipe.banco.BancoCore;
import com.pereira.felipe.banco.JogoPojo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.pereira.felipe.banco.Utils.rollDice;
import static com.pereira.felipe.banco.Utils.rollDices;

public class JogoActivity extends AppCompatActivity {

    private Button acao;
    private TextSwitcher contexto;
    private RadioGroup rg;
    private JogoPojo evento;
    private BancoCore banco;
    private boolean init = true;
    private int ponteiro;
    private SharedPreferences personagem;
    private Random r;
    private TextView resultado;
    private Toolbar tBar;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_jogo);
        acao =  findViewById(R.id.bt_acao);
        contexto =  findViewById(R.id.tx_evento);
        resultado =  findViewById(R.id.tx_resultado);
        contexto.setMeasureAllChildren(false);
        rg =  findViewById(R.id.rg);
        loadAnimation();
        setFactory();
        setListener();
        tBar = findViewById(R.id.toolbar);
        setSupportActionBar(tBar);
        banco = new BancoCore(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.status:
                Intent i = new Intent(JogoActivity.this, StatusActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
            init = false;
        }

        if(player == null){
            player = MediaPlayer.create(this, R.raw.background);
        }
        player.start();
        player.setLooping(true);

    }

    private void verificaEvento(JogoPojo evento) {
        contexto.setText(evento.getContexto());
            List<String> list = new ArrayList<>();
            if(evento.getTeste() == null){
                list.add(evento.getAcao_um());
                if (evento.getAcao_dois() != null) {list.add(evento.getAcao_dois());}
                if (evento.getAcao_tres() != null) {list.add(evento.getAcao_tres());}
                for(int i =0; i < list.size() ;i++){
                    RadioButton rb = new RadioButton(this);
                    rb.setId(i);
                    rb.setText(list.get(i));
                    rg.addView(rb);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((LinearLayout.LayoutParams)rb.getLayoutParams());
                    params.setMargins(0,0,0,48);
                    rb.setLayoutParams(params);
                    rb.requestLayout();
                }
            }

    }

    private void verificaTeste(String teste) {
        personagem = getSharedPreferences("Personagem", Context.MODE_PRIVATE);
        r = new Random();
        switch (teste){
            case "habilidade":
                int habilidade = personagem.getInt("Habilidade", 0);
                if(habilidade < rollDices()){
                    ponteiro = evento.getPonteiro_dois();
                    resultado.setText("Falha no teste de habilidade");
                    resultado.setTextColor(Color.parseColor("#f2000d"));
                    }
                else {
                    ponteiro = evento.getPonteiro_um();
                    resultado.setText("Sucesso no teste de habilidade");
                    resultado.setTextColor(Color.parseColor("#517f47"));
                    }
                break;

            case "sorte":
                int sorte = personagem.getInt("Sorte", 0);
                if(sorte < rollDices()){
                    ponteiro = evento.getPonteiro_dois();
                    resultado.setText("Falha no teste de sorte");
                    resultado.setTextColor(Color.parseColor("#f2000d"));}
                else {
                    ponteiro = evento.getPonteiro_um();
                    resultado.setText("Sucesso no teste de habilidade");
                    resultado.setTextColor(Color.parseColor("#00f20d"));}
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

                if(evento.getTeste() == null){
                    resultado.setVisibility(View.GONE);
                    int p = rg.getCheckedRadioButtonId();

                    switch (p){
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
                    rg.clearCheck();
                    rg.removeAllViews();
                    evento = banco.getEvento(ponteiro);
                    verificaEvento(evento);
                }

                else if(evento.getTeste().equals("luta")){
                    Intent i = new Intent(JogoActivity.this, LutaActivity.class);
                    i.putExtra("Indice",evento.getBatalha());
                    startActivityForResult(i, 2);
                }

                else if(evento.getTeste().equals("aleatorio")){
                    int i = rollDice();

                    switch (i){
                        case 1:
                            ponteiro = evento.getPonteiro_um();
                            break;

                        case 2:
                            ponteiro = evento.getPonteiro_dois();
                            break;

                        default:
                            ponteiro = evento.getPonteiro_tres();
                            break;

                    }

                    rg.clearCheck();
                    rg.removeAllViews();
                    evento = banco.getEvento(ponteiro);
                    verificaEvento(evento);

                }

                else{
                    verificaTeste(evento.getTeste());
                    resultado.setVisibility(View.VISIBLE);
                    evento = banco.getEvento(ponteiro);
                    verificaEvento(evento);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int valor= 0;

        switch(requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    resultado.setVisibility(View.GONE);
                    valor = data.getExtras().getInt("result");
                    evento = banco.getEvento(valor);
                    verificaEvento(evento);
                }
                break;
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        player.release();
        player = null;
    }
}