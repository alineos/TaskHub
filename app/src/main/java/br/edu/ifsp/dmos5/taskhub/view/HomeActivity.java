package br.edu.ifsp.dmos5.taskhub.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifsp.dmos5.taskhub.R;
import br.edu.ifsp.dmos5.taskhub.constant.Constants;
import br.edu.ifsp.dmos5.taskhub.mvp.UsuarioMVP;
import br.edu.ifsp.dmos5.taskhub.presenter.UsuarioPresenter;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, UsuarioMVP.View {

    TextView textview_hiUsername;
    TextView textview_Task;
    Button button_taskAcess;
    ImageView imageView_home;

    UsuarioMVP.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A0BAFF")));
        findById();
        setListener();
        Intent intent = getIntent();
        setUsername(intent.getStringExtra("email"));

        presenter = new UsuarioPresenter(this);
        presenter.updateUI(getIntent(), textview_hiUsername, getSupportActionBar());


    }

    private void setUsername(String username) {

        textview_hiUsername.setText("Olá " + username);

    }

    private void findById() {
        textview_hiUsername = findViewById(R.id.textview_hiUsername);
        textview_Task = findViewById(R.id.textview_Task);
        button_taskAcess = findViewById(R.id.button_taskAcess);
        imageView_home = findViewById(R.id.imageView_home);
    }


    @Override
    public void onClick(View v) {
        String name = textview_hiUsername.getText().toString().substring(4,textview_hiUsername.getText().toString().length()); //fiz isso para eviar o username para a proxima tela
        if (v == button_taskAcess) {
            Intent intent = new Intent(this, ListTaskActivity.class);
            intent.putExtra(Constants.ATTR_USERNAME,name);
            startActivity(intent);
        }

    }


    private void setListener() {
        button_taskAcess.setOnClickListener(this);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
