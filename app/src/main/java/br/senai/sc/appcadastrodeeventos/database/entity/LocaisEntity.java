package br.senai.sc.appcadastrodeeventos.database.entity;

import android.provider.BaseColumns;

public final class LocaisEntity implements BaseColumns {

    private LocaisEntity() {}

    public static final String TABLE_NAME = "local";
    public static final String COLUMN_NAME_NOME_DESCRICAO = "descricao";
    public static final String COLUMN_NAME_BAIRRO = "bairro";
    public static final String COLUMN_NAME_CIDADE = "cidade";
    public static final String COLUMN_NAME_CAPACIDADE = "capacidade";
}
