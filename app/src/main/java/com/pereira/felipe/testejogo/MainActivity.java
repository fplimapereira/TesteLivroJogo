package com.pereira.felipe.testejogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button novo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        novo = (Button) findViewById(R.id.bt_novo);
        novo.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, PersonagemActivity.class);
        startActivity(i);
    }

}
