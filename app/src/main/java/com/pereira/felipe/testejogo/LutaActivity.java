package com.pereira.felipe.testejogo;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.pereira.felipe.banco.BancoCore;
import com.pereira.felipe.banco.OponentePojo;
import com.pereira.felipe.banco.Shaker;
import java.io.IOException;
import java.util.List;
import static com.pereira.felipe.banco.Utils.rollDices;

public class LutaActivity extends AppCompatActivity {

    private int indice, hab, uLife, enemLife, habEne, opn, atq, atqEnem, golpe, acerto, dano;
    private BancoCore banco;
    private SharedPreferences personagem;
    private List<OponentePojo> oponentes;
    private Runnable showBaseAtack, showResult, showHit, endCombat;
    private Handler mHandler;
    private ValueAnimator animator;
    private Shaker shakerU, shakerEne;
    private Button golpear;
    private MediaPlayer player;
    private SoundPool pool;

    private TextView ene, eneLife, userLife, tvUBAtk, tvEnBatk, tvUsHit, tvEnHit, tvUserHab, tvEneHab, tvUStk, tvEStk, tvUTotalAtk,
            tvETotalAtk, tvResultado, tvUAtklabel, tvEAtklabel, tvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luta);
        indice = getIntent().getIntExtra("Indice", 0);
        banco = new BancoCore(this);
        initBanco();
        tvUser = findViewById(R.id.tvUser);
        ene = findViewById(R.id.tvEnen);
        eneLife = findViewById(R.id.tvEneLf);
        userLife = findViewById(R.id.tvUserLf);
        tvUserHab = findViewById(R.id.tvUserH);
        tvEneHab = findViewById(R.id.tvEneH);
        tvUBAtk = findViewById(R.id.tvUBAtk);
        tvEnBatk = findViewById(R.id.tvEnBatk);
        tvUsHit = findViewById(R.id.tvUsHit);
        tvEnHit = findViewById(R.id.tvEnHit);
        tvUStk = findViewById(R.id.tvUStk);
        tvEStk = findViewById(R.id.tvEStk);
        tvUTotalAtk = findViewById(R.id.tvUTotalAtk);
        tvETotalAtk = findViewById(R.id.tvETotalAtk);
        tvResultado = findViewById(R.id.tvResultado);
        tvUAtklabel = findViewById(R.id.tvUAtklabel);
        tvEAtklabel = findViewById(R.id.tvEAtklabel);
        golpear = findViewById(R.id.btAtk);
        oponentes = banco.getOponentes(indice);
        personagem = getSharedPreferences("Personagem", Context.MODE_PRIVATE);
        hab = personagem.getInt("Habilidade", 0);
        tvUserHab.setText(String.valueOf(hab));
        tvUBAtk.setText(String.valueOf(hab) + " +");
        userLife.setText(String.valueOf(personagem.getInt("Energia", 0)));
        uLife = personagem.getInt("Energia", 0);
        opn = 0;
        mHandler = new Handler();
        setOponente(opn);
        setAnimacao();
        setRunnables();
        setSound();
    }

    private void setSound() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            pool = new SoundPool.Builder()
                    .setMaxStreams(3)
                    .setAudioAttributes(audioAttributes)
                    .build();

            golpe = pool.load(this, R.raw.golpear, 1);
            acerto = pool.load(this, R.raw.userdano, 1);
            dano = pool.load(this, R.raw.monstrodano, 1);
        }
        else{
            pool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }
    }

    private void setRunnables() {
        showBaseAtack = new Runnable(){
            @Override
            public void run(){
                tvUAtklabel.setVisibility(View.VISIBLE);
                tvEAtklabel.setVisibility(View.VISIBLE);
                tvUBAtk.setVisibility(View.VISIBLE);
                tvEnBatk.setVisibility(View.VISIBLE);
                tvUStk.setText(String.valueOf(atq) + " =");
                tvEStk.setText(String.valueOf(atqEnem) + " =");
                tvUStk.setVisibility(View.VISIBLE);
                tvEStk.setVisibility(View.VISIBLE);
                mHandler.postDelayed(showResult, 1000);
            }
        };

        showResult = new Runnable(){
            @Override
            public void run(){
                atq = hab + atq;
                atqEnem = habEne + atqEnem;
                tvUTotalAtk.setText(String.valueOf(atq));
                tvUTotalAtk.setVisibility(View.VISIBLE);
                tvETotalAtk.setText(String.valueOf(atqEnem));
                tvETotalAtk.setVisibility(View.VISIBLE);
                mHandler.postDelayed(showHit, 1000);
            }
        };

        showHit = new Runnable(){
            @Override
            public void run(){
                resultado();
            }
        };

        endCombat = new Runnable(){
            @Override
            public void run(){
                OponentePojo op = oponentes.get(0);
                finishWithResult(op.getVitoria());
            }
        };
    }

    private void setAnimacao() {
        animator = new ValueAnimator();
        animator.setObjectValues(2, 12);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tvUsHit.setText(String.valueOf(animation.getAnimatedValue()));
                tvEnHit.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animator.setDuration(400);
        animator.setRepeatCount(ValueAnimator.INFINITE);

        shakerEne = new Shaker(ene, -15, 15, android.R.color.transparent, Color.RED);
        shakerU = new Shaker(tvUser, -15, 15, android.R.color.transparent, Color.RED);
    }

    @Override
    protected void onResume(){
        super.onResume();
        animator.start();
    }

    @Override
    protected void onStart(){
        super.onStart();
       if(player == null){
            player = MediaPlayer.create(this, R.raw.battle);
        }
        player.setVolume(0.4f, 0.4f);
        player.start();
        player.setLooping(true);
    }

    public void golpear(View v){
        golpear.setClickable(false);
        pool.play(golpe, 1, 1, 0, 0,1);
        atq = rollDices();
        atqEnem = rollDices();
        animator.end();

        tvUsHit.setText(String.valueOf(atq));
        tvEnHit.setText(String.valueOf(atqEnem));

        mHandler.postDelayed(showBaseAtack, 1500);
    }

    public void setOponente(int fila){
        OponentePojo op = oponentes.get(fila);
        eneLife.setText(String.valueOf(op.getEnergia()));
        tvEneHab.setText(String.valueOf(op.getHabilidade()));
        tvEnBatk.setText(String.valueOf(op.getHabilidade()) + " +");
        enemLife = op.getEnergia();
        ene.setText(op.getNome());
        habEne = op.getHabilidade();
        opn++;
    }



    public void resultado(){

        if(atq < atqEnem){
            pool.play(acerto, 1, 1, 0, 0,1);
            shakerU.shake();
            uLife = uLife - 2;
            userLife.setText(String.valueOf(uLife));
        }
        if(atq > atqEnem){
            pool.play(dano, 1, 1, 0, 0,1);
            shakerEne.shake();
            enemLife = enemLife - 2;
            eneLife.setText(String.valueOf(enemLife));
        }

        if(enemLife <= 0){
            if(oponentes.size() > opn){
                setOponente(opn);
            }
            else{
                tvResultado.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = personagem.edit();
                editor.putInt("Energia", uLife);
                editor.apply();
                mHandler.postDelayed(endCombat, 3000);
            }
        }

        if(uLife <=0){
            OponentePojo op = oponentes.get(opn);
            finishWithResult(op.getDerrota());
        }

        if(enemLife > 0 && uLife > 0){
            liberaAtaque();
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

    private void liberaAtaque() {
        golpear.setClickable(true);
        tvUAtklabel.setVisibility(View.INVISIBLE);
        tvEAtklabel.setVisibility(View.INVISIBLE);
        tvUBAtk.setVisibility(View.INVISIBLE);
        tvEnBatk.setVisibility(View.INVISIBLE);
        tvUStk.setVisibility(View.INVISIBLE);
        tvEStk.setVisibility(View.INVISIBLE);
        tvUTotalAtk.setVisibility(View.INVISIBLE);
        tvETotalAtk.setVisibility(View.INVISIBLE);
        animator.start();
    }

    private void finishWithResult(int result)
    {
        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onStop(){
        super.onStop();
        player.release();
        player = null;
        pool.release();
        pool = null;
    }

}
