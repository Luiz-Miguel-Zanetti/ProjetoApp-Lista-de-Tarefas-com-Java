package luiz.miguel.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;

import luiz.miguel.listadetarefas.R;


import luiz.miguel.listadetarefas.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText campoNome, campoEmail, campoSenha;
    private FirebaseAuth auth;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();

        configuraToolbar();


    }

    public void inicializarComponentes() {

        campoNome = findViewById(R.id.editNomeCadastro);
        campoEmail = findViewById(R.id.editEmailCadastro);
        campoSenha = findViewById(R.id.editSenhaCadastro);

        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);


    }

    public void configuraToolbar() {

        toolbar.setTitle("Novo usuário");
        toolbar.setTitleTextColor(getResources().getColor(R.color.titulo_toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


    }

    public void cadastrarUsuario(View view) {

        //Recuperar o que o usuario digitou
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNome(nome);


        if (usuario.getNome().isEmpty() || usuario.getEmail().isEmpty() || usuario.getSenha().isEmpty()) {

            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();

        } else {


            cadastrarUsuarioFirebase();

        }


    }

    public void cadastrarUsuarioFirebase() {

        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();


        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {



                if (task.isSuccessful()) {

                    String idUsario = task.getResult().getUser().getUid();

                    usuario.setIdUsuario(idUsario);
                    usuario.salvarDadosUsuario();

                    Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                    Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                    startActivity(intent);

                    finish();


                } else {

                    String erro = "";

                    try {

                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {

                        erro = "Digite uma senha com pelo menos seis caracteres";


                    } catch (FirebaseAuthInvalidUserException e) {

                        erro = "Esse email já esta cadastrado";


                    } catch (FirebaseAuthInvalidCredentialsException e) {

                        erro = "E-mail inválido";


                    } catch (Exception e) {

                        erro = "Erro ao cadastrar usuário";


                    }

                    Toast.makeText(getApplicationContext(), erro, Toast.LENGTH_LONG).show();

                }

            }
        });


    }

}


