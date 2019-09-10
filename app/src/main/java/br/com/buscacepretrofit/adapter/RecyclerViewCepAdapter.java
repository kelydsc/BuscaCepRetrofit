package br.com.buscacepretrofit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.buscacepretrofit.R;
import br.com.buscacepretrofit.interfaces.RecyclerViewCepClickListener;
import br.com.buscacepretrofit.model.CEP;

public class RecyclerViewCepAdapter extends RecyclerView.Adapter<RecyclerViewCepAdapter.ViewHolder> {

    //Atributos
    private List<CEP> cepList;
    private RecyclerViewCepClickListener listener;

    //Construtor
    public RecyclerViewCepAdapter(List<CEP> cepList, RecyclerViewCepClickListener listener) {
        this.cepList = cepList;
        this.listener = listener;
    }

    public RecyclerViewCepAdapter(List<CEP> cepList) {

    }

    @NonNull
    @Override
    public RecyclerViewCepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cep_recyclerview_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCepAdapter.ViewHolder viewHolder, int position) {

        final CEP cep = cepList.get(position);

        viewHolder.bind(cep);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(cep);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Atributos
        private EditText etCeps;
        private EditText etRua_cep;
        private EditText etBairro_cep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            etCeps = itemView.findViewById(R.id.et_ceps);
            etRua_cep = itemView.findViewById(R.id.et_rua_cep);
            etBairro_cep = itemView.findViewById(R.id.et_bairro_cep);
        }

        public void bind(CEP cep) {

            etCeps.setText(cep.getCep());
            etRua_cep.setText(cep.getLogradouro());
            etBairro_cep.setText(cep.getBairro());
        }
    }

    public void updateCep(List<CEP> cepList) {
        this.cepList = cepList;

        notifyDataSetChanged();
    }
}
