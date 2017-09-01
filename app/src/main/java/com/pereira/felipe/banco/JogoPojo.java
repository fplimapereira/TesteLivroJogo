package com.pereira.felipe.banco;

/**
 * Created by Felipe on 29/08/2017.
 */

public class JogoPojo {

    private String contexto;
    private String acao_um;
    private String acao_dois;
    private String acao_tres;
    private int ponteiro_um;
    private int ponteiro_dois;
    private int ponteiro_tres;
    private int item;
    private int batalha;
    private int posicao;
    private String teste;


    public JogoPojo(String contexto, String acao_um, String acao_dois, String acao_tres, int ponteiro_um, int ponteiro_dois, int ponteiro_tres, int item, int batalha, int posicao, String teste) {
        this.contexto = contexto;
        this.acao_um = acao_um;
        this.acao_dois = acao_dois;
        this.acao_tres = acao_tres;
        this.ponteiro_um = ponteiro_um;
        this.ponteiro_dois = ponteiro_dois;
        this.ponteiro_tres = ponteiro_tres;
        this.item = item;
        this.batalha = batalha;
        this.posicao = posicao;
        this.teste = teste;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public String getAcao_um() {
        return acao_um;
    }

    public void setAcao_um(String acao_um) {
        this.acao_um = acao_um;
    }

    public String getAcao_dois() {
        return acao_dois;
    }

    public void setAcao_dois(String acao_dois) {
        this.acao_dois = acao_dois;
    }

    public String getAcao_tres() {
        return acao_tres;
    }

    public void setAcao_tres(String acao_tres) {
        this.acao_tres = acao_tres;
    }

    public int getPonteiro_um() {
        return ponteiro_um;
    }

    public void setPonteiro_um(int ponteiro_um) {
        this.ponteiro_um = ponteiro_um;
    }

    public int getPonteiro_dois() {
        return ponteiro_dois;
    }

    public void setPonteiro_dois(int ponteiro_dois) {
        this.ponteiro_dois = ponteiro_dois;
    }

    public int getPonteiro_tres() {
        return ponteiro_tres;
    }

    public void setPonteiro_tres(int ponteiro_tres) {
        this.ponteiro_tres = ponteiro_tres;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getBatalha() {
        return batalha;
    }

    public void setBatalha(int batalha) {
        this.batalha = batalha;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }
}
