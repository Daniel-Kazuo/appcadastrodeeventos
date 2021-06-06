package br.senai.sc.appcadastrodeeventos.modelo;

import java.io.Serializable;

public class Eventos implements Serializable {
    private int id;
    private String nome;
    private String data;
    private Local local;

    public Eventos(int id, String nome, String data, Local local) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.local = local;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "Evento: " + getNome() +
                "\nLocal: " + getLocal().getNome() +
                "\nData: " + getData();
    }
}