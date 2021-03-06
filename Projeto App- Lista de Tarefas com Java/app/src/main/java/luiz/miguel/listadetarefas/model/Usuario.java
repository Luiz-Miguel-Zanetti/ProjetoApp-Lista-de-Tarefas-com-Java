package luiz.miguel.listadetarefas.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

public class Usuario {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;


    public Usuario() {
    }

    public void salvarDadosUsuario(){



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usuarioRef = databaseReference.child("usuarios").child(getIdUsuario());
        usuarioRef.setValue(this);





    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
