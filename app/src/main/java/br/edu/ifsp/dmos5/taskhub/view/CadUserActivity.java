package br.edu.ifsp.dmos5.taskhub.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifsp.dmos5.taskhub.R;
import br.edu.ifsp.dmos5.taskhub.R.id;
import br.edu.ifsp.dmos5.taskhub.model.Usuario;
import br.edu.ifsp.dmos5.taskhub.mvp.UsuarioMVP;
import br.edu.ifsp.dmos5.taskhub.presenter.UsuarioPresenter;

public class CadUserActivity extends AppCompatActivity implements View.OnClickListener, UsuarioMVP.View {

    Button button_newAccount;
    EditText editTextPasswordConfirm;
    EditText editTextPassword;
    EditText editTextEmail;
    EditText editTextName;
    EditText editTextUsername;

    UsuarioMVP.Presenter presenter;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_user);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A0BAFF")));
        findById();
        setListener();
        auth = FirebaseAuth.getInstance();

        presenter = new UsuarioPresenter(this);
        presenter.updateUI(getIntent(), editTextPasswordConfirm, editTextPassword, editTextEmail, editTextName, editTextUsername, getSupportActionBar());
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }


    private void findById() {
        button_newAccount = findViewById(id.button_newAccount);
        editTextPasswordConfirm = findViewById(id.editTextPasswordConfirm);
        editTextPassword = findViewById(id.editTextPassword);
        editTextEmail = findViewById(id.editTextEmail);
        editTextName = findViewById(id.editTextName);
        editTextUsername = findViewById(id.editTextUsername);
    }

    @Override
    public void onClick(View view) {
        if (view == button_newAccount) {
           validaDados();
        }

    }

    @Override
    public Context getContext() {
        return this;
    }

    private void setListener() {
        button_newAccount.setOnClickListener(this);
    }

    private void saveUser() {
        if (presenter.isNewUser()) {
            presenter.saveNewUser(makeUser());
        } else {
            presenter.saveNewUser(makeUser());
        }
        finish();
    }

    private Usuario makeUser() {

        if (editTextPassword.getText().toString().contains(editTextPasswordConfirm.getText().toString())) {
            return new Usuario(editTextUsername.getText().toString()
                    , editTextName.getText().toString()
                    , editTextEmail.getText().toString()
                    , editTextPassword.getText().toString());

        } else {
            Toast.makeText(this.getContext(), "Senha e confirmação invalida", Toast.LENGTH_LONG).show();
            return null;
        }


    }


    private void validaDados(){
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextPassword.getText().toString().trim();
        String confirmaSenha = editTextPasswordConfirm.getText().toString();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                if(!confirmaSenha.isEmpty()){
                    if(senha.contains(confirmaSenha)){
                        criarContaFirebase(email,senha);
                    }else{
                        Toast.makeText(this, "Senha diferente da confirmação", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Informe sua confirmação senha", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Informe sua senha", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Informe seu email", Toast.LENGTH_SHORT).show();
        }
    }

   void criarContaFirebase(String email,String senha){
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                saveUser();

            }else{
                Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
