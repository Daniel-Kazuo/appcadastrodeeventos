package br.senai.sc.appcadastrodeeventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.senai.sc.appcadastrodeeventos.database.EventoDAO;
import br.senai.sc.appcadastrodeeventos.modelo.Eventos;

public class CadastroEventoActivity extends AppCompatActivity {

    private int id = 0;

    private EditText editTextNome;
    private EditText editTextData;
    private EditText editTextLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");

        editTextNome = findViewById(R.id.editText_nome);
        editTextData = findViewById(R.id.editText_data);
        editTextLocal = findViewById(R.id.editText_local);

        carregarEvento();
    }

    public void carregarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() !=null && intent.getExtras().get("eventoEdicao") != null) {
            Eventos eventos = (Eventos) intent.getExtras().get("eventoEdicao");
            editTextNome.setText(eventos.getNome());
            editTextData.setText(eventos.getData());
            editTextLocal.setText(eventos.getLocal());
            id = eventos.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar (View v) {
        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextData = findViewById(R.id.editText_data) ;
        EditText editTextLocal = findViewById(R.id.editText_local);

        String nome = editTextNome.getText().toString();
        String data = editTextData.getText().toString();
        String local = editTextLocal.getText().toString();

        Eventos eventos = new Eventos(id, nome, data, local);
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        boolean salvou = eventoDAO.salvar(eventos);
        if (salvou) {
            finish();
        } else {
            Toast.makeText(CadastroEventoActivity.this, "Erro ao salvar", Toast.LENGTH_LONG).show();
        }
    }
}