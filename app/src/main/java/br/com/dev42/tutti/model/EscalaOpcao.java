package br.com.dev42.tutti.model;

/**
 * Created by sossai on 11/06/17.
 */

public class EscalaOpcao {
    private Integer id;
    private String nome;
    private boolean selecionado;

    public EscalaOpcao(Integer id, String nome, boolean selecionado) {
        this.id = id;
        this.nome = nome;
        this.selecionado = selecionado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
}
