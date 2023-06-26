package br.edu.ifsp.dmos5.taskhub.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifsp.dmos5.taskhub.R;
import br.edu.ifsp.dmos5.taskhub.model.Usuario;
import br.edu.ifsp.dmos5.taskhub.mvp.LoginMVP;
import br.edu.ifsp.dmos5.taskhub.mvp.UsuarioMVP;


import br.edu.ifsp.dmos5.taskhub.presenter.UsuarioPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginMVP.View {


    private Button button_login;
    private Button button_newAccount;
    private EditText editTextUsername;
    private EditText editTextSenha;


    LoginMVP.Presenter presenter;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A0BAFF")));
        findById();
        setListener();

        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    private void findById() {

        button_login = findViewById(R.id.button_login);
        button_newAccount = findViewById(R.id.button_newAccount);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextUsername = findViewById(R.id.editTextUsername);

    }

    @Override
    public void onClick(View v) {
        if (v == button_newAccount) {
            Intent intent = new Intent(this, CadUserActivity.class);
            startActivity(intent);
        }
        if (v == button_login) {
            validaDados();
        }
    }

    private void setListener() {
        button_newAccount.setOnClickListener(this);
        button_login.setOnClickListener(this);
    }

    @Override
    public Context getContext() {
        return this;
    }


    private void validaDados() {
        String email = editTextUsername.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();


        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                loginContaFirebase(email, senha);
            } else {
                Toast.makeText(this, "Informe sua senha", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu email", Toast.LENGTH_SHORT).show();
        }
    }

    void loginContaFirebase(String email, String senha) {

        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
            }
        });

    }

}