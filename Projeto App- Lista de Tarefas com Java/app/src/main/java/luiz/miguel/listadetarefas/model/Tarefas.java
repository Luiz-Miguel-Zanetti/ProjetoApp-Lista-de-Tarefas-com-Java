package luiz.miguel.listadetarefas.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Tarefas implements Serializable {

    private String idTarefa;
    private String tarefa;
    private String data;
    private String hora;


    public Tarefas() {
    }

    public void salvarTarefa(){

       String idUsuarioAtual  = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tarefaRef = reference.child("usuarios").child(idUsuarioAtual).child("tarefa").push();

        tarefaRef.setValue(this);


    }

    public String getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(String idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
