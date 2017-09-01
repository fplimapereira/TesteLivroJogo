package com.pereira.felipe.banco;

/**
 * Created by Felipe on 25/08/2017.
 */

public class Item {

    private int icone;
    private String nome;
    private String descricao;

    public Item(int icone, String nome, String descricao) {
        this.icone = icone;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getIcone() {
        return icone;
    }

    public void setIcone(int icone) {
        this.icone = icone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
