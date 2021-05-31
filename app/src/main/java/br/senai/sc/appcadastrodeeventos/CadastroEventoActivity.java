package br.senai.sc.appcadastrodeeventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.senai.sc.appcadastrodeeventos.modelo.Eventos;

public class CadastroEventoActivity extends AppCompatActivity {

    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int RESULT_CODE_EVENTO_EDITADO = 11;

    private boolean edicao = false;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");
        carregarEvento();
    }

    private void carregarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() !=null &&
                intent.getExtras().get("eventoEdicao") != null) {
            Eventos eventos = (Eventos) intent.getExtras().get("eventoEdicao");
            EditText editTextNome = findViewById(R.id.editText_nome);
            EditText editTextData = findViewById(R.id.editText_data);
            EditText editTextLocal = findViewById(R.id.editText_local);
            editTextNome.setText(eventos.getNome());
            editTextData.setText(eventos.getData());
            editTextLocal.setText(eventos.getLocal());
            edicao = true;
            id = eventos.getId();
        }

    }

    public void onClickVoltar(View v) { finish(); }

    public void onClickSalvar (View v) {
        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextData = findViewById(R.id.editText_data);
        EditText editTextLocal = findViewById(R.id.editText_local);

        String nome = editTextNome.getText().toString();
        String data = editTextData.getText().toString();
        String local = editTextLocal.getText().toString();

        Eventos eventos = new Eventos(id,nome, data, local);
        Intent intent = new Intent();
        if (edicao) {
            intent.putExtra("eventoEditado", eventos);
            setResult(RESULT_CODE_EVENTO_EDITADO, intent);
        }else {
            intent.putExtra("novoEvento", eventos);
            setResult(RESULT_CODE_NOVO_EVENTO, intent);
        }
        finish();
    }
}