package com.pereira.felipe.testejogo;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class HistoriaActivity extends AppCompatActivity {
    Button next,pula;
    TextSwitcher ts;
    int paragrafos, indice;
    String[] intro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
        next = (Button) findViewById(R.id.bt_continue);
        ts = (TextSwitcher) findViewById(R.id.text_intro);
        pula = (Button) findViewById(R.id.bt_skip);

        Resources res = getResources();
        intro = res.getStringArray(R.array.txt_intro);
        paragrafos= intro.length;
        indice = 0;
        setFactory();
        setListener();
        loadAnimation();
        ts.setText(intro[0]);

    }

    void loadAnimation(){
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        ts.setInAnimation(fadeIn);
        ts.setOutAnimation(fadeOut);
    }

    void setFactory(){
        ts.setFactory(new ViewSwitcher.ViewFactory(){

            @Override
            public View makeView() {
                TextView myText = new TextView(HistoriaActivity.this);
                myText.setGravity(Gravity.TOP | Gravity.LEFT);
                myText.setTextSize(15);
                myText.setTextColor(Color.BLACK);
                return myText;
            }
        });
    }


    void setListener(){
        pula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HistoriaActivity.this, JogoActivity.class);
                startActivity(i);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indice++;
                if(indice == paragrafos) {
                    Intent i = new Intent(HistoriaActivity.this, JogoActivity.class);
                    startActivity(i);
                }else{
                    ts.setText(intro[indice]);
                }
            }
        });
    }
}
