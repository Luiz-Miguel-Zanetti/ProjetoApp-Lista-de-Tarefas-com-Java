package luiz.miguel.listadetarefas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.List;

import luiz.miguel.listadetarefas.R;
import luiz.miguel.listadetarefas.adapter.TarefaAdapter;
import luiz.miguel.listadetarefas.model.Tarefas;
import luiz.miguel.listadetarefas.model.Usuario;

public class CadastroTarefasActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText campoTarefa, campoData, campoHora;
    private String idTarefa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefas);

        inicalizarComponentes();

        configuraToolbar();
    }

    public void inicalizarComponentes(){

        toolbar = findViewById(R.id.toolbar);

        campoTarefa = findViewById(R.id.editTarefaCadastro);
        campoData = findViewById(R.id.editDataCadastro);
        campoHora = findViewById(R.id.editHoraCadastro);

        idTarefa = FirebaseAuth.getInstance().getUid();





    }

    public void configuraToolbar(){

        toolbar.setTitle("Nova Tarefa");
        toolbar.setTitleTextColor(getResources().getColor(R.color.titulo_toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void salvarTarefa(View view){

        String tarefa = campoTarefa.getText().toString();
        String data = campoData.getText().toString();
        String hora = campoHora.getText().toString();

        Tarefas tarefas = new Tarefas();
        tarefas.setIdTarefa(idTarefa);
        tarefas.setTarefa(tarefa);
        tarefas.setData(data);
        tarefas.setHora(hora);

        if ( tarefas.getTarefa().isEmpty() ||tarefas.getData().isEmpty()|| tarefas.getHora().isEmpty()){

            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();

        }else{

            Intent intent = new Intent(CadastroTarefasActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Tarefa salva com sucesso!", Toast.LENGTH_LONG).show();

            salvarTarefaFirebase();

        }



    }

    public void salvarTarefaFirebase(){

        

        String tarefa = campoTarefa.getText().toString();
        String data = campoData.getText().toString();
        String hora = campoHora.getText().toString();

        Tarefas tarefas = new Tarefas();
        tarefas.setIdTarefa(idTarefa);
        tarefas.setTarefa(tarefa);
        tarefas.setData(data);
        tarefas.setHora(hora);
        tarefas.salvarTarefa();





    }

}