package br.com.buscacepretrofit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.buscacepretrofit.R;
import br.com.buscacepretrofit.data.network.CEPService;
import br.com.buscacepretrofit.interfaces.SimpleCallback;
import br.com.buscacepretrofit.model.CEP;

public class MainActivity extends AppCompatActivity {

    //Widgets
    private EditText etCEP;
    private EditText etRua;
    private EditText etBairro;
    private EditText etCidade;
    private EditText etEstado;
    private Spinner spUFs;
    private Button btnBuscarPorCEP;
    private Button btnBuscarPorRuaCidadeEstado;
    private ProgressBar progressBar;

    private ArrayList<CEP> arrayCEPs;

    //Tag para o LOG
    private static final String TAG = "logCEP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Refs.
        etCEP = findViewById(R.id.et_cep);
        btnBuscarPorCEP = findViewById(R.id.btn_buscar_por_cep);
        progressBar = findViewById(R.id.progress_bar);
        etRua = findViewById(R.id.et_rua);
        etBairro = findViewById(R.id.et_bairro);
        etCidade = findViewById(R.id.et_cidade);
        etEstado = findViewById(R.id.et_estado);
        spUFs = findViewById(R.id.sp_ufs);
        btnBuscarPorRuaCidadeEstado = findViewById(R.id.btn_buscar_por_rua_cidade_estado);

        progressBar.setVisibility(View.INVISIBLE);

        btnBuscarPorCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etCEP.getText().toString().isEmpty()) {

                    progressBar.setVisibility(View.VISIBLE);
                    CEPService service = new CEPService(MainActivity.this);

                    service.getCEP(etCEP.getText().toString(), new SimpleCallback<CEP>() {

                        @Override
                        public void onResponse(CEP response) {
                            CEP cep = response;

                            etRua.setText(cep.getLogradouro());
                            etBairro.setText(cep.getBairro());
                            etCidade.setText(cep.getLocalidade());
                            etEstado.setText(cep.getUf());

                            //Retorno na Toast
                            Toast.makeText(
                                    getApplicationContext(),
                                    getResources().getString(R.string.toast_aviso_retorno) + cep.toString(),
                                    Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(String error) {
                            toast("erro onError: " + error.toString());
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    toast("CEP vazio!");
                }
            }
        });

        /* Busca por Rua Cidade e Estado(UF) */
        btnBuscarPorRuaCidadeEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etCidade.getText().toString().isEmpty() &&
                        !etRua.getText().toString().isEmpty() &&
                        spUFs.getSelectedItemPosition() != 0) {

                    progressBar.setVisibility(View.VISIBLE);

                    CEPService service = new CEPService(MainActivity.this);

                    service.getCEPUFCidadeRua(spUFs.getSelectedItem().toString(),
                            etCidade.getText().toString(),
                            etRua.getText().toString(), new SimpleCallback<List<CEP>>() {
                                @Override
                                public void onResponse(List<CEP> response) {

                                    List<CEP> CEPAux = response;
                                    arrayCEPs = new ArrayList<>();

                                    for (CEP cep : CEPAux) {
                                        arrayCEPs.add(cep);

                                        //retorna o cep e o bairro se o endereço informado estiver
                                        //completo
                                        etCEP.setText(cep.getCep());
                                        etBairro.setText(cep.getBairro());
                                        etEstado.setText(cep.getUf());
                                    }

                                    //chama a tela o recyclerview de endereços para escolher e retornar
                                    //para a tela de cadastro
                                    Intent intent = new Intent(MainActivity.this,RetornaCepsActivity.class);
                                    intent.putExtra("CEP", arrayCEPs);
                                    startActivity(intent);

                                    toast(getResources().getString(R.string.toast_aviso_retorno) + arrayCEPs.toString());
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onError(String error) {
                                    toast(getResources().getString(R.string.toast_erro_generico) + error);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                }
            }
        });
    }//oncreate

    private void toast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }
}//classe