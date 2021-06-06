package br.senai.sc.appcadastrodeeventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.senai.sc.appcadastrodeeventos.database.LocalDAO;
import br.senai.sc.appcadastrodeeventos.modelo.Local;

public class CadastroLocalActivity extends AppCompatActivity {

    private int id = 0;
    private EditText editTextNome;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextCapacidade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_locais);
        setTitle("Cadastro de Locais");
        editTextNome = findViewById(R.id.et_nome);
        editTextBairro = findViewById(R.id.et_bairro);
        editTextCidade = findViewById(R.id.et_cidade);
        editTextCapacidade = findViewById(R.id.et_capacidade);

        carregarLocais();
    }

    public void carregarLocais() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("locaisEdicao") != null) {
            Local local = (Local) intent.getExtras().get("locaisEdicao");
            editTextNome.setText(local.getNome());
            editTextBairro.setText(local.getBairro());
            editTextCidade.setText(local.getCidade());
            editTextCapacidade.setText(String.valueOf(local.getCapacidade()));
            id = local.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        String nome = editTextNome.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String capacidadeStr = editTextCapacidade.getText().toString();
        int capacidade = 0;

        if (!capacidadeStr.isEmpty()){
            capacidade = Integer.parseInt(capacidadeStr);
        }

        if (nome.length() == 0 || bairro.length() == 0 || capacidade <= 0 || cidade.length() == 0) {
            editTextNome.setError("Nome obrigatório");
            editTextBairro.setError("Bairro obrigatório");
            editTextCidade.setError("Cidade obrigatória");
            editTextCapacidade.setError("Capacidade deve ser maior que 0");
        } else {
            Local local = new Local(id, nome, bairro, cidade, capacidade);
            LocalDAO localDAO = new LocalDAO(getBaseContext());
            boolean salvou = localDAO.salvar(local);

            if (salvou) {
                finish();
            } else {
                Toast.makeText(CadastroLocalActivity.this, "Erro ao salvar",Toast.LENGTH_LONG).show();

            }
        }
    }

}
