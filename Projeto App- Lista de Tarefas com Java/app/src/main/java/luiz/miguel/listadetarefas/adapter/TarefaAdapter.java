package luiz.miguel.listadetarefas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import luiz.miguel.listadetarefas.R;
import luiz.miguel.listadetarefas.model.Tarefas;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {

    List<Tarefas> listaTarefas;
    Context context;

    public TarefaAdapter(List<Tarefas> listaTarefas, Context context) {
        this.listaTarefas = listaTarefas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate( R.layout.adapter_tarefas, parent, false );

        return new TarefaAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tarefas tarefas = listaTarefas.get(position);

        holder.campoTarefa.setText(tarefas.getTarefa());
        holder.campoData.setText(tarefas.getData());
        holder.campoHora.setText(tarefas.getHora());

    }

    @Override
    public int getItemCount() {
        return listaTarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        EditText campoTarefa, campoData, campoHora;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            campoTarefa = itemView.findViewById(R.id.tarefaAdapter);
            campoData = itemView.findViewById(R.id.dataTarefaAdapter);
            campoHora = itemView.findViewById(R.id.horaTarefaAdapter);


        }


    }

}
