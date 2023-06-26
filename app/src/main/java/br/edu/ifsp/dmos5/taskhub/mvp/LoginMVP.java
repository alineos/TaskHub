package br.edu.ifsp.dmos5.taskhub.mvp;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;

import com.google.android.material.chip.Chip;

import br.edu.ifsp.dmos5.taskhub.model.Tarefa;
import br.edu.ifsp.dmos5.taskhub.model.Usuario;

public interface LoginMVP {

    interface View {
        Context getContext();
    }

    interface Presenter {
        void detach();

        void updateUI(Intent intent, EditText editTextLogin, EditText editTextSenha, ActionBar toolBar);



        Usuario searchUser(EditText userName, Intent intent);
    }
}
