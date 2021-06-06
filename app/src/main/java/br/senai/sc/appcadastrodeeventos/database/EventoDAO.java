package br.senai.sc.appcadastrodeeventos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.senai.sc.appcadastrodeeventos.database.entity.EventoEntity;
import br.senai.sc.appcadastrodeeventos.modelo.Eventos;

public class EventoDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + EventoEntity.TABLE_NAME;

    private DBGateway dbGateway;

    public EventoDAO(Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Eventos eventos) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, eventos.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, eventos.getData());
        contentValues.put(EventoEntity.COLUMN_NAME_LOCAL, eventos.getLocal());
        if (eventos.getId() > 0 ) {
            return dbGateway.getDatabase().update(EventoEntity.TABLE_NAME,
                    contentValues,
            EventoEntity._ID + "=?",
            new String[] {String.valueOf(eventos.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(EventoEntity.TABLE_NAME,
                null, contentValues) > 0;
    }

    public boolean excluir (Eventos eventos) {
        return dbGateway.getDatabase().delete(EventoEntity.TABLE_NAME,
                EventoEntity._ID + "=?",
                new String[]{String.valueOf(eventos.getId())}) > 0;
    }

    public List<Eventos> listar() {
        List<Eventos> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            String local = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_LOCAL));
            eventos.add(new Eventos(id, nome, data, local));
        }
        cursor.close();
        return eventos;
    }
}
