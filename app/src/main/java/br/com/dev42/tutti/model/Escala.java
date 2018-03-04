package br.com.dev42.tutti.model;

import java.io.Serializable;

/**
 * Created by sossai on 03/06/17.
 */

public class Escala implements Serializable {
    private Integer id;
    private String nome;
    private Boolean selecionado;
    private String tipo; //

    public Escala(Integer id, String nome, Boolean selecionado, String tipo) {
        this.id = id;
        this.nome = nome;
        this.selecionado = selecionado;
        this.tipo = tipo;
    }

    public Boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
