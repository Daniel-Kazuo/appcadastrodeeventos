package br.senai.sc.appcadastrodeeventos.modelo;

import java.io.Serializable;

public class Eventos implements Serializable {

    private int id;
    private String nome;
    private String data;
    private String local;

    public Eventos(int id, String nome, String data, String local) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.local = local;
    }

    public Eventos(String nome, String data, String local) {
        this.nome = nome;
        this.data = data;
        this.local = local;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public String getLocal() { return local; }

    public void setLocal(String local) { this.local = local; }

    public String toString() {
        return  "Evento: " + nome +
                "\nData: " + data +
                "\nLocal: " + local;
    }
}
