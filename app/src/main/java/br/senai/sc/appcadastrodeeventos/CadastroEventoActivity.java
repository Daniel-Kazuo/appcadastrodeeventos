package br.senai.sc.appcadastrodeeventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.senai.sc.appcadastrodeeventos.database.EventoDAO;
import br.senai.sc.appcadastrodeeventos.database.LocalDAO;
import br.senai.sc.appcadastrodeeventos.modelo.Eventos;
import br.senai.sc.appcadastrodeeventos.modelo.Local;

public class CadastroEventoActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextData;

    private int id = 0;
    private Spinner spinnerLocais;
    private ArrayAdapter<Local> locaisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");

        spinnerLocais = findViewById(R.id.sp_locais);
        editTextNome = findViewById(R.id.ed_edicaoNovoEvento);
        editTextData = findViewById(R.id.ed_edicaoNovaData);

        mostrarLocais();
        mostrarEvento();
    }

    private void mostrarLocais() {
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        locaisAdapter = new ArrayAdapter<Local>(CadastroEventoActivity.this,
                android.R.layout.simple_spinner_item,
                localDAO.listar());
        spinnerLocais.setAdapter(locaisAdapter);
    }

    public void mostrarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("eventoEditado") != null ) {
            Eventos evento = (Eventos) intent.getExtras().get("eventoEditado");
            editTextNome.setText(evento.getNome());
            editTextData.setText(evento.getData());
            int posicaoLocal = posicaoLocais(evento.getLocal());
            spinnerLocais.setSelection(posicaoLocal);
            id = evento.getId();
        }
    }

    private int posicaoLocais(Local local) {
        for (int posicao = 0; posicao < locaisAdapter.getCount(); posicao++) {
            if (locaisAdapter.getItem(posicao).getId() == local.getId()){
                return posicao;
            }
        }
        return 0;
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        String nome = editTextNome.getText().toString();
        String data = editTextData.getText().toString();
        Local local = (Local) spinnerLocais.getSelectedItem();

        if (nome.length() == 0 || data.length() == 0) {
            editTextNome.setError("Nome obrigatório");
            editTextData.setError("Data obrigatória");
        } else {
            Eventos evento = new Eventos(id, nome, data, local);
            EventoDAO eventoDao = new EventoDAO(getBaseContext());
            boolean salvou = eventoDao.salvar(evento);

            if (salvou) {
                finish();
            } else {
                Toast.makeText(CadastroEventoActivity.this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
