package luiz.miguel.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import luiz.miguel.listadetarefas.R;
import luiz.miguel.listadetarefas.model.Usuario;

public class LoginActivity extends AppCompatActivity {


    private TextInputEditText campoEmail, campoSenha;
    private FirebaseAuth auth;
    private FirebaseUser  usuarioAtual;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        inicializarComponentes();
    }

    public void inicializarComponentes(){

        campoEmail = findViewById(R.id.editEmailLogin);
        campoSenha = findViewById(R.id.editSenhaLogin);


        auth = FirebaseAuth.getInstance();



    }

    @Override
    protected void onStart() {
        super.onStart();

        usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if ( usuarioAtual!= null ){

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);


        }


    }

    public void abrirTelaCadastro(View view){

        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);


    }

    public void logarUsuario( View view){

        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);

        if ( usuario.getEmail().isEmpty() || usuario.getSenha().isEmpty() ){

            Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();

        }else {

            logarUsuarioFirebase();

        }

    }


    public void logarUsuarioFirebase(){


        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();



        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);


        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    Toast.makeText(getApplicationContext(), "Sucesso ao logar usuário", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {

                    String erro = "";

                    try {

                        throw task.getException();

                    }catch ( FirebaseAuthInvalidUserException e) {

                        erro = "Usuario não está cadastrado";

                    }catch ( FirebaseAuthInvalidCredentialsException e ){

                    erro = "Email ou senha nao correspondem um usuario cadastrado";

                    }catch (Exception e ){

                        erro = "Erro ao logar usuário";

                        e.printStackTrace();

                    }

                    Toast.makeText(getApplicationContext(), erro, Toast.LENGTH_LONG).show();



                }


            }
        });






    }

}