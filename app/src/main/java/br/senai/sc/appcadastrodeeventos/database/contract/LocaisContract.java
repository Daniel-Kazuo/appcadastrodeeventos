package br.senai.sc.appcadastrodeeventos.database.contract;

import br.senai.sc.appcadastrodeeventos.database.entity.LocaisEntity;

public class LocaisContract {

    private LocaisContract() {}

    public static final String criarTabela() {
        return "CREATE TABLE " + LocaisEntity.TABLE_NAME + " (" +
                LocaisEntity._ID + " INTEGER PRIMARY KEY," +
                LocaisEntity.COLUMN_NAME_NOME_DESCRICAO + " TEXT," +
                LocaisEntity.COLUMN_NAME_BAIRRO + " TEXT," +
                LocaisEntity.COLUMN_NAME_CIDADE + " TEXT," +
                LocaisEntity.COLUMN_NAME_CAPACIDADE + " FLOAT)";
    }

    public static final String removerTabela() {
        return "DROP TABLE IF EXISTS " + LocaisEntity.TABLE_NAME;
    }
}

