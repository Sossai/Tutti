package br.com.dev42.tutti.model;

import java.io.Serializable;

/**
 * Created by sossai on 11/06/17.
 */

public class DataTsEscalas implements Serializable{
    private Integer opcaoid, escalaid, telaid, sequencia, seekstartsomcompleto, seekendsomcompleto;
    private String imagem, som1, som2, som3, som4, somcompleto;

    public DataTsEscalas() {
    }

    public DataTsEscalas(Integer opcaoid, Integer escalaid, Integer telaid, Integer sequencia, String imagem, String som1, String som2, String som3, String som4, String somcompleto, Integer seekstartsomcompleto, Integer seekendsomcompleto) {
        this.opcaoid = opcaoid;
        this.escalaid = escalaid;
        this.telaid = telaid;
        this.sequencia = sequencia;
        this.imagem = imagem;
        this.som1 = som1;
        this.som2 = som2;
        this.som3 = som3;
        this.som4 = som4;
        this.somcompleto = somcompleto;
        this.seekstartsomcompleto = seekstartsomcompleto;
        this.seekendsomcompleto = seekendsomcompleto;
    }

    public Integer getOpcaoid() {
        return opcaoid;
    }

    public Integer getEscalaid() {
        return escalaid;
    }

    public Integer getTelaid() {
        return telaid;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public String getImagem() {
        return imagem;
    }

    public String getSom1() {
        return som1;
    }

    public String getSom2() {
        return som2;
    }

    public String getSom3() {
        return som3;
    }

    public String getSom4() {
        return som4;
    }

    public void setOpcaoid(Integer opcaoid) {
        this.opcaoid = opcaoid;
    }

    public void setEscalaid(Integer escalaid) {
        this.escalaid = escalaid;
    }

    public void setTelaid(Integer telaid) {
        this.telaid = telaid;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setSom1(String som1) {
        this.som1 = som1;
    }

    public void setSom2(String som2) {
        this.som2 = som2;
    }

    public void setSom3(String som3) {
        this.som3 = som3;
    }

    public void setSom4(String som4) {
        this.som4 = som4;
    }

    public String getSomcompleto() {
        return somcompleto;
    }

    public void setSomcompleto(String somcompleto) {
        this.somcompleto = somcompleto;
    }

    public Integer getSeekstartsomcompleto() {
        return seekstartsomcompleto;
    }

    public void setSeekstartsomcompleto(Integer seekstartsomcompleto) {
        this.seekstartsomcompleto = seekstartsomcompleto;
    }

    public Integer getSeekendsomcompleto() {
        return seekendsomcompleto;
    }

    public void setSeekendsomcompleto(Integer seekendsomcompleto) {
        this.seekendsomcompleto = seekendsomcompleto;
    }

    @Override
    public String toString() {
        return "DataTsEscalas{" +
                "opcaoid=" + opcaoid +
                ", escalaid=" + escalaid +
                ", telaid=" + telaid +
                ", sequencia=" + sequencia +
                ", imagem='" + imagem + '\'' +
                ", som1='" + som1 + '\'' +
                ", som2='" + som2 + '\'' +
                ", som3='" + som3 + '\'' +
                ", som4='" + som4 + '\'' +
                '}';
    }
}
