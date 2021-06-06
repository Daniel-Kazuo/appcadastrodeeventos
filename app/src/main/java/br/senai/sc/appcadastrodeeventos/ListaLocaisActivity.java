package br.senai.sc.appcadastrodeeventos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.senai.sc.appcadastrodeeventos.database.LocalDAO;
import br.senai.sc.appcadastrodeeventos.modelo.Local;

public class ListaLocaisActivity extends AppCompatActivity {

    private ListView listViewLocais;
    private ArrayAdapter<Local> adapterLocais;

    private int id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_locais);
        setTitle("Lista de Locais");
        listViewLocais = findViewById(R.id.lv_locais);
        definirOnClickListenerListView();
        definirOnLongClickListenerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        adapterLocais = new ArrayAdapter<Local>(ListaLocaisActivity.this,
                android.R.layout.simple_list_item_1,
                localDAO.listar());
        listViewLocais.setAdapter(adapterLocais);
    }

    private void definirOnClickListenerListView() {
        listViewLocais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Local localClicado = adapterLocais.getItem(position);

                new AlertDialog.Builder(ListaLocaisActivity.this)
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setTitle("Editar")
                        .setMessage("Editar este local?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ListaLocaisActivity.this, CadastroLocalActivity.class);
                                intent.putExtra("locaisEdicao", localClicado);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Não", null).show();
            }
        });
    }

    private void definirOnLongClickListenerView() {
        listViewLocais.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Local localClicados = adapterLocais.getItem(position);

                new AlertDialog.Builder(ListaLocaisActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Excluir local?")
                        .setMessage("Excluir permanentemente este local?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LocalDAO localDAO = new LocalDAO(getBaseContext());
                                boolean excluiu = localDAO.excluir(localClicados);
                                if (excluiu) {
                                    onResume();
                                }
                            }
                        })
                        .setNegativeButton("Não", null).show();
                return true;
            }
        });

    }

    public void onClickNovoLocal(View v) {
        Intent intent = new Intent(ListaLocaisActivity.this, CadastroLocalActivity.class);
        startActivity(intent);
    }

    public void OnClickVoltarEventos(View v) {
        Intent intent = new Intent(ListaLocaisActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
