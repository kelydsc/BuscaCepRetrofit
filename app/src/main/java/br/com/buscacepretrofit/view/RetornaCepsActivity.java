package br.com.buscacepretrofit.view;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.buscacepretrofit.R;
import br.com.buscacepretrofit.adapter.RecyclerViewCepAdapter;
import br.com.buscacepretrofit.interfaces.RecyclerViewCepClickListener;
import br.com.buscacepretrofit.model.CEP;

public class RetornaCepsActivity extends AppCompatActivity implements RecyclerViewCepClickListener {

    private RecyclerView recyclerView;
    private RecyclerViewCepAdapter adapter;

    private EditText etCEP;
    private EditText etRua;
    private EditText etCidade;
    private EditText etEstado;

    private List<CEP> cepList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retorna_ceps);

        //Seta a toolbar e o botão voltar(back)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Busca de Ceps");

        etRua = findViewById(R.id.et_rua);
        //etBairro = findViewById(R.id.et_bairro);
        etCidade = findViewById(R.id.et_cidade);
        etEstado = findViewById(R.id.et_estado);

        recyclerView = findViewById(R.id.recyclerview_cep);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewCepAdapter(cepList);

        recyclerView.setAdapter(adapter);

        //Valido se veio algum dado na intent
        if (getIntent() != null && getIntent().getExtras() != null) {
            CEP ceps = getIntent().getParcelableExtra("CEP");

            if (ceps != null) {

                //Retorna os dados do endereço
                retornaDetalheListaCeps(ceps);
            }

        }

    }


    private void retornaDetalheListaCeps(CEP ceps) {





    }

    @Override
    public void onClick(CEP cep) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // one inherited from android.support.v4.app.FragmentActivity

        return false;
    }

}

