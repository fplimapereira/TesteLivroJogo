package com.pereira.felipe.banco;

public class OponentePojo {

    private String nome;
    private int energia;
    private int habilidade;
    private int vitoria;
    private String derrota;

    public OponentePojo(String nome, int energia, int habilidade, int vitoria, String derrota) {
        this.nome = nome;
        this.energia = energia;
        this.habilidade = habilidade;
        this.vitoria = vitoria;
        this.derrota = derrota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(int habilidade) {
        this.habilidade = habilidade;
    }

    public int getVitoria() {
        return vitoria;
    }

    public void setVitoria(int vitoria) {
        this.vitoria = vitoria;
    }

    public String getDerrota() {
        return derrota;
    }

    public void setDerrota(String derrota) {
        this.derrota = derrota;
    }
}
