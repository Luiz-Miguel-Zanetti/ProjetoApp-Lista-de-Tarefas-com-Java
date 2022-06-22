package luiz.miguel.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import luiz.miguel.listadetarefas.R;
import luiz.miguel.listadetarefas.adapter.TarefaAdapter;
import luiz.miguel.listadetarefas.model.Tarefas;
import luiz.miguel.listadetarefas.model.Usuario;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private FloatingActionButton fabAddAnuncio;
    private RecyclerView recyclerTarefas;
    private List<Tarefas> listaTarefas;
    private TarefaAdapter tarefaAdapter;
    private String idUsuarioAtual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        configuraToolbar();

        configuraFabAddAnuncio();

        listarTarefas();

    }

    public void inicializarComponentes() {

        auth = FirebaseAuth.getInstance();

        fabAddAnuncio = findViewById(R.id.fabAddTarefa);

        toolbar = findViewById(R.id.toolbar);

        recyclerTarefas = findViewById(R.id.recyclerTarefas);
        tarefaAdapter = new TarefaAdapter(listaTarefas, getApplicationContext());

        idUsuarioAtual = FirebaseAuth.getInstance().getCurrentUser().getUid();






    }

    public void configuraToolbar() {


        toolbar.setTitle("Suas Tarefas");
        toolbar.setTitleTextColor(getResources().getColor(R.color.titulo_toolbar));
        setSupportActionBar(toolbar);


    }

    public void configuraRecyclerView(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerTarefas.setHasFixedSize(true);
        recyclerTarefas.setLayoutManager(layoutManager);
        recyclerTarefas.setAdapter(tarefaAdapter);



    }

    public void listarTarefas(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tarefaRef = reference.child( "usuarios" ).child(idUsuarioAtual).child("tarefas");

        tarefaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (  DataSnapshot ds : snapshot.getChildren()  ){

                    Tarefas tarefas = ds.getValue( Tarefas.class);
                    listaTarefas.add(tarefas);


                }

                tarefaAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void configuraFabAddAnuncio() {

        fabAddAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CadastroTarefasActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuSair:
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                auth.signOut();
                startActivity(i);
                finish();
                break;

            case R.id.novoUsuario:
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                auth.signOut();
                startActivity(intent);


        }


        return super.onOptionsItemSelected(item);
    }
}