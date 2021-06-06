package br.senai.sc.appcadastrodeeventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.senai.sc.appcadastrodeeventos.database.EventoDAO;
import br.senai.sc.appcadastrodeeventos.modelo.Eventos;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEventos;
    private ArrayAdapter<Eventos> adapterEventos;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Cadastro de Eventos");

        listViewEventos = findViewById(R.id.listView_eventos);

        onClickListenerView();
        onLongClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Eventos>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDAO.listar());
        listViewEventos.setAdapter(adapterEventos);

    }

    private void onClickListenerView() {
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Eventos eventoClicado = adapterEventos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
                intent.putExtra("eventoEditado", eventoClicado);
                startActivity(intent);
            }
        });
    }

   private void onLongClick() {
            listViewEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final Eventos eventoClicado = adapterEventos.getItem(position);
                        EventoDAO eventoDAO = new EventoDAO(getBaseContext());

                    if (eventoDAO.excluir(eventoClicado)) {
                        Toast.makeText(getApplicationContext(), "Produto excluido com Sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao excluir", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
        });
    }

    private ArrayList<Eventos> criarListaEventos() {
        ArrayList<Eventos> eventos = new ArrayList<Eventos>();
        return eventos;

        }

        public void onClickNovoEvento(View v) {
        Intent intent = new Intent (MainActivity.this, CadastroEventoActivity.class);
        startActivity(intent);
    }

}
