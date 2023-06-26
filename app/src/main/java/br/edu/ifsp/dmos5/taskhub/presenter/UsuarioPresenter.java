package br.edu.ifsp.dmos5.taskhub.presenter;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import br.edu.ifsp.dmos5.taskhub.model.Usuario;
import br.edu.ifsp.dmos5.taskhub.mvp.UsuarioMVP;
import br.edu.ifsp.dmos5.taskhub.constant.Constants;

public class UsuarioPresenter implements UsuarioMVP.Presenter {
    private UsuarioMVP.View view;

    private FirebaseFirestore database;

    private String firestoreId = "";

    private Usuario usuarioL;

    public UsuarioPresenter(UsuarioMVP.View view) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void updateUI(Intent intent, EditText editTextPasswordConfirm, EditText editTextPassword, EditText editTextEmail, EditText editTextName, EditText editTextUsername, ActionBar toolBar) {
//USERS_COLLECTION

        if (intent.hasExtra(Constants.FIRESOTRE_DOCUMENT_KEY)) {
            firestoreId = intent.getStringExtra(Constants.FIRESOTRE_DOCUMENT_KEY);
            database.collection(Constants.USERS_COLLECTION).document(firestoreId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null) {
                        usuarioL = value.toObject(Usuario.class);
                        if (usuarioL != null) {
                            editTextPasswordConfirm.setText(usuarioL.getSenha());
                            editTextPassword.setText(usuarioL.getSenha());
                            editTextEmail.setText(usuarioL.getEmail());
                            editTextName.setText(usuarioL.getNome());
                            editTextUsername.setText(usuarioL.getUsername());
                        }
                    }
                }
            });

        } else {
            toolBar.setTitle("Novo Usuario");
        }

    }

    @Override
    public void updateUI(Intent intent, TextView textview_hiUsername, ActionBar toolBar) {

        if (intent.hasExtra("email")) {
            firestoreId = intent.getStringExtra("email");
            Query query = database.collection(Constants.USERS_COLLECTION).orderBy("email").whereEqualTo("email", firestoreId);

            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    List<Usuario> usuarios = snapshot.toObjects(Usuario.class);
                    textview_hiUsername.setText("Olá " + usuarios.get(0).getUsername());
                }
            });


        } else {
            toolBar.setTitle("Novo Usuario");
        }


    }

    @Override
    public boolean isNewUser() {
        return firestoreId.isEmpty();
    }

    @Override
    public void saveNewUser(Usuario usuario) {
        CollectionReference collectionReference = database.collection(Constants.USERS_COLLECTION);
        collectionReference.add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(view.getContext(), "Usuario cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(view.getContext(), "Erro ao cadastrar Usuario.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void updateUser(Usuario usuario) {
        CollectionReference collectionReference = database.collection(Constants.USERS_COLLECTION);
        collectionReference.document(firestoreId)
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        Toast.makeText(view.getContext(), "Usuario atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(view.getContext(), "Erro ao atualizar Usuario.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void deleteUser() {

        if (!firestoreId.isEmpty()) {
            CollectionReference collectionReference = database.collection(Constants.USERS_COLLECTION);
            collectionReference.document(firestoreId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
//                            Toast.makeText(view.getContext(), "Usuario removido com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(view.getContext(), "Erro ao deletar Usuario.", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

}
