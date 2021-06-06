package br.senai.sc.appcadastrodeeventos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.senai.sc.appcadastrodeeventos.database.entity.LocaisEntity;
import br.senai.sc.appcadastrodeeventos.modelo.Local;

public class LocalDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + LocaisEntity.TABLE_NAME;
    private DBGateway dbGateway;

    public LocalDAO(Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Local local) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocaisEntity.COLUMN_NAME_NOME_DESCRICAO, local.getNome());
        contentValues.put(LocaisEntity.COLUMN_NAME_BAIRRO, local.getBairro());
        contentValues.put(LocaisEntity.COLUMN_NAME_CIDADE, local.getCidade());
        contentValues.put(LocaisEntity.COLUMN_NAME_CAPACIDADE, local.getCapacidade());
        if (local.getId() > 0) {
            return dbGateway.getDatabase().update(LocaisEntity.TABLE_NAME,
                    contentValues,
                    LocaisEntity._ID + "=?",
                    new String[]{String.valueOf(local.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(LocaisEntity.TABLE_NAME,
                null,
                contentValues) > 0;
    }

    public List<Local> listar(){
        List<Local> locais = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS,
                null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(LocaisEntity._ID));
            String descricao = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_NOME_DESCRICAO));
            String bairro = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CAPACIDADE));
            locais.add(new Local(id, descricao, bairro, cidade, capacidade));
        }
        cursor.close();
        return locais;
    }

    public boolean excluir(Local local) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocaisEntity.COLUMN_NAME_NOME_DESCRICAO, local.getNome());
        contentValues.put(LocaisEntity.COLUMN_NAME_BAIRRO, local.getBairro());
        contentValues.put(LocaisEntity.COLUMN_NAME_CIDADE, local.getCidade());
        contentValues.put(LocaisEntity.COLUMN_NAME_CAPACIDADE, local.getCapacidade());
        if (local.getId() > 0) {
            return dbGateway.getDatabase().delete(LocaisEntity.TABLE_NAME,
                    LocaisEntity._ID + "=?",
                    new String[]{String.valueOf(local.getId())}) > 0;
        }
        return true;
    }
}