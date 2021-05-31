package br.senai.sc.appcadastrodeeventos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        ArrayList<Eventos> eventos = this.criarlistaEventos();

        adapterEventos = new ArrayAdapter<Eventos>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventos);
        listViewEventos.setAdapter(adapterEventos);

        definirOnClickListenerListView();

    }

    private void definirOnClickListenerListView() {
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Eventos eventoClicado = adapterEventos.getItem(position);
                Intent intent = new Intent(MainActivity.this,CadastroEventoActivity.class);
                intent.putExtra("eventoEdicao",eventoClicado);
                startActivityForResult(intent,REQUEST_CODE_EDITAR_EVENTO );
            }
        });
    }

    private ArrayList<Eventos> criarlistaEventos() {
        ArrayList<Eventos> eventos = new ArrayList<>();
        eventos.add(new Eventos("Palestra Auto Ajuda", "30/05/2021", "CIC"));
        eventos.add(new Eventos("Palestra Leandro Karnal", "03/06/2021", "Teatro Pedro Ivo"));
        return eventos;
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
        }else if (requestCode == REQUEST_CODE_EDITAR_EVENTO && resultCode == RESULT_CODE_EVENTO_EDITADO){
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
}
