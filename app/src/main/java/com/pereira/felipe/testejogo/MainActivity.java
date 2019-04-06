package com.pereira.felipe.testejogo;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pereira.felipe.banco.Shaker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button novo;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        novo = findViewById(R.id.bt_novo);
        novo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, PersonagemActivity.class);
        startActivity(i);
    }

    @Override
    public void onStart(){
        super.onStart();
        if(player == null){
            player = MediaPlayer.create(this, R.raw.intro_sound);
        }
        player.start();
        player.setLooping(true);
    }

    @Override
    public void onStop(){
        super.onStop();
        player.release();
        player = null;
    }

}
