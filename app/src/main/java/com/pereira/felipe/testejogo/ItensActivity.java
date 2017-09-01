package com.pereira.felipe.testejogo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toolbar;

import com.pereira.felipe.banco.ItemAdapter;

public class ItensActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens);
    }
}
