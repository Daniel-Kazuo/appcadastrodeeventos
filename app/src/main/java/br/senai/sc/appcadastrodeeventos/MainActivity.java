package br.senai.sc.appcadastrodeeventos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.senai.sc.appcadastrodeeventos.modelo.Eventos;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_NOVO_EVENTO = 1;
    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int REQUEST_CODE_EDITAR_EVENTO = 2;
    private final int RESULT_CODE_EVENTO_EDITADO = 11;

    private ListView listViewEventos;
    private ArrayAdapter<Eventos> adapterEventos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Cadastro de Eventos");

        listViewEventos = findViewById(R.id.listView_eventos);
        ArrayList<Eventos> evento = cadastroNovoEvento();
        adapterEventos = new ArrayAdapter<Eventos>(MainActivity.this, android.R.layout.simple_list_item_1, evento);
        listViewEventos.setAdapter(adapterEventos);

        onClickListenerView();
        onLongClickListener();
    }

    private void onClickListenerView() {
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Eventos eventoClicado = adapterEventos.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon((android.R.drawable.ic_menu_edit))
                        .setTitle("Editar")
                        .setMessage("Editar este item?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
                                intent.putExtra("eventoEditado", eventoClicado);
                                startActivityForResult(intent, REQUEST_CODE_EDITAR_EVENTO);
                            }
                        })
                .setNegativeButton("Não", null).show();
            }
        });
    }

   private void onLongClickListener() {
            listViewEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final Eventos eventoClicado = adapterEventos.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Excluir item")
                        .setMessage("Excluir permanentemente este item?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterEventos.remove(eventoClicado);
                                adapterEventos.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Item excluido com sucesso", Toast.LENGTH_LONG).show();

                            }
                        } )
                        .setNegativeButton("Não", null).show();
                    return true;
            }
        });
    }

    public void onClickNovoEvento(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NOVO_EVENTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_NOVO_EVENTO && resultCode == RESULT_CODE_NOVO_EVENTO) {
            assert data != null;
            Eventos eventos = (Eventos) data.getExtras().getSerializable("novoEvento");
            eventos.setId(++id);
            this.adapterEventos.add(eventos);
        }else if (requestCode == REQUEST_CODE_EDITAR_EVENTO && resultCode == RESULT_CODE_EVENTO_EDITADO) {
            assert data != null;
            Eventos eventoEditado = (Eventos) data.getExtras().getSerializable("eventoEditado");
            for (int i = 0; i < adapterEventos.getCount(); i++) {
                Eventos eventos = adapterEventos.getItem(i);
                if (eventos.getId() == eventoEditado.getId()) {
                    adapterEventos.remove(eventos);
                    adapterEventos.insert(eventoEditado, i);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private ArrayList<Eventos> cadastroNovoEvento() {
        ArrayList<Eventos> eventos = new ArrayList<Eventos>();
        eventos.add(new Eventos("Palestra Leandro", "05/01/2021", "CIC"));
        return eventos;
    }

}
