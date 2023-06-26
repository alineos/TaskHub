package br.edu.ifsp.dmos5.taskhub.mvp;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import br.edu.ifsp.dmos5.taskhub.model.Usuario;

public interface UsuarioMVP {

    interface View{
        Context getContext();

    }

    interface Presenter{
        void detach();

        void updateUI(Intent intent, EditText editTextPasswordConfirm, EditText editTextPassword, EditText editTextEmail, EditText editTextName, EditText editTextUsername, ActionBar toolBar);

        void updateUI(Intent intent, TextView textview_hiUsername, ActionBar toolBar);

        boolean isNewUser();

        void saveNewUser(Usuario usuario);

        void updateUser(Usuario usuario);

        void deleteUser();
    }

}
