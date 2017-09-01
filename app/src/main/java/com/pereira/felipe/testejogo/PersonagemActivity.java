package com.pereira.felipe.testejogo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class PersonagemActivity extends AppCompatActivity {

    private TextView energia, habilidade, sorte;
    private Button gerar, adiante;
    Random r;
    Boolean valida = false;
    SharedPreferences personagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personagemctivity);
        energia = (TextView) findViewById(R.id.tv_energia);
        habilidade  = (TextView) findViewById(R.id.tv_habilidade);
        sorte  = (TextView) findViewById(R.id.tv_sorte);
        gerar = (Button) findViewById(R.id.bt_gerar);
        gerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { gerarAtributos(); }
        });
        adiante = (Button) findViewById(R.id.bt_adiante);
        adiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { avancar(); }
        });
        r = new Random();
    }

    private void avancar() {
        if(valida){
            personagem = getSharedPreferences(getString(R.string.personagem), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = personagem.edit();
            editor.putInt("Habilidade", Integer.valueOf(habilidade.getText().toString()));
            editor.putInt("Energia", Integer.valueOf(energia.getText().toString()));
            editor.putInt("Sorte", Integer.valueOf(sorte.getText().toString()));
            editor.apply();
            Intent i = new Intent(PersonagemActivity.this, HistoriaActivity.class);
            startActivity(i);
        }
        else{
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(R.string.erro_gerar);
            alert.setPositiveButton("OK",null);
            alert.show();
        }
    }


    private void gerarAtributos() {
        valida = true;
        habilidade.setText(String.valueOf(r.nextInt(6) + 7));
        sorte.setText(String.valueOf(r.nextInt(6) + 7));
        energia.setText(String.valueOf(r.nextInt(13) + 12));
    }


}
