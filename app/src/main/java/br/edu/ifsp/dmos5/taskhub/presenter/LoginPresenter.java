package br.edu.ifsp.dmos5.taskhub.presenter;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import br.edu.ifsp.dmos5.taskhub.constant.Constants;
import br.edu.ifsp.dmos5.taskhub.model.Usuario;
import br.edu.ifsp.dmos5.taskhub.mvp.LoginMVP;

public class LoginPresenter implements LoginMVP.Presenter {

    private LoginMVP.View view;

    private FirebaseFirestore database;

    private String firestoreId = "";

    private Usuario userL;


    public LoginPresenter(LoginMVP.View view) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void updateUI(Intent intent, EditText editTextLogin, EditText editTextSenha, ActionBar toolBar) {


        if (intent.hasExtra(Constants.FIRESOTRE_DOCUMENT_KEY)) {
            firestoreId = intent.getStringExtra(Constants.FIRESOTRE_DOCUMENT_KEY);
            database.collection(Constants.USERS_COLLECTION).document(firestoreId).addSnapshotListener(new EventListener<DocumentSnapshot>() {

                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null) {
                        userL = value.toObject(Usuario.class);
                        if (userL != null) {
                            editTextLogin.setText(userL.getUsername());
                            editTextSenha.setText("");

                        }
                    }
                }
            });
        } else {
            toolBar.setTitle("Login");
        }

    }


    @Override
    public Usuario searchUser(EditText userName, Intent intent) {
        Usuario user = new Usuario();
        Query query = database.collection(Constants.USERS_COLLECTION).whereEqualTo(Constants.ATTR_USERNAME, userName.getText().toString());

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                List<Usuario> chats = snapshot.toObjects(Usuario.class);
                user.setNome(chats.get(0).getNome());
                user.setEmail(chats.get(0).getEmail());
                user.setSenha(chats.get(0).getSenha());
                user.setUsername(chats.get(0).getUsername());
        }
    });


        return user;

}
}


