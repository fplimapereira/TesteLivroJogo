package com.pereira.felipe.banco;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 24/08/2017.
 */

public class BancoCore extends SQLiteOpenHelper {

    public static final String NOME_BD = "teste.sqlite";
    private static final int VERSAO_BD = 1;
    public static final String DB_LOCATION = "/data/data/com.pereira.felipe/databases";
    String DB_PATH = null;
    private Context mContext;
    private SQLiteDatabase mDataBase;

    public BancoCore(Context ctx){
        super(ctx, NOME_BD, null, VERSAO_BD);
        this.mContext = ctx;
        this.DB_PATH = "/data/data/" + ctx.getPackageName() + "/" + "databases/";

    }

    public void createDatabase() throws IOException {
        boolean dbExist = verificaBanco();
        if(dbExist){
        }else{
            this.getReadableDatabase();
            try{
                copyDataBase();
            }catch(IOException e){
                throw new Error("Erro ao copiar o banco : ");
            }
        }

    }

    private void copyDataBase() throws IOException {
        InputStream inputStream = mContext.getAssets().open(BancoCore.NOME_BD);
        String outFileName = DB_PATH + NOME_BD;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buff = new byte[10];
        int length = 0;
        while ((length = inputStream.read(buff)) > 0) {
            outputStream.write(buff, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }


    private boolean verificaBanco() {
        SQLiteDatabase checkDb = null;
        try{
            String mPath = DB_PATH + NOME_BD;
            checkDb = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            e.printStackTrace();
        }
        if(checkDb != null){
            checkDb.close();
        }

        return checkDb != null ? true : false;
    }


    @Override
    public void onCreate(SQLiteDatabase bd) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {

    }

    public void openDataBase(){
        String dbPath =  DB_PATH + NOME_BD;
        mDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
    }


    @Override
    public synchronized  void close(){
        if(mDataBase != null){
            mDataBase.close();
        }
        super.close();
    }



    public JogoPojo getEvento(int posicao){
        JogoPojo evento = null;
        Cursor cursor = null;
        String[] args = new String[]{String.valueOf(posicao)};

        try {
            openDataBase();
            cursor = mDataBase.rawQuery("SELECT * FROM EVENTOS WHERE POSICAO = ?", args);
            cursor.moveToFirst();
            evento = new JogoPojo(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7),
                    cursor.getInt(8), cursor.getInt(9), cursor.getInt(10), cursor.getString(11));
            cursor.close();
            close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return evento;
    }

    public List<OponentePojo> getOponentes(int batalha){
        List<OponentePojo> oponentes = new ArrayList<OponentePojo>();
        Cursor cursor = null;
        String[] args = new String[]{String.valueOf(batalha)};

        try {
            openDataBase();
            cursor = mDataBase.rawQuery("SELECT * FROM CRIATURA WHERE POSICAO = ?", args);
            if (cursor.moveToFirst()){
                do {
                    OponentePojo oponente = new OponentePojo(cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(5), cursor.getString(5));
                    oponentes.add(oponente);
                } while (cursor.moveToNext());
            }
            cursor.close();
            close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return oponentes;
    }

}
