package br.edu.ifsp.dmos5.taskhub.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;

import br.edu.ifsp.dmos5.taskhub.R;
import br.edu.ifsp.dmos5.taskhub.constant.Constants;
import br.edu.ifsp.dmos5.taskhub.model.Tarefa;
import br.edu.ifsp.dmos5.taskhub.mvp.TarefaMVP;

public class TarefaPresenter implements TarefaMVP.Presenter {

    private TarefaMVP.View view;

    private FirebaseFirestore database;

    private String firestoreId = "";

    private Tarefa tarefaL;

    public TarefaPresenter(TarefaMVP.View view) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
    }


    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void updateUI(@NonNull Intent intent, EditText editTextTitle, EditText editTextObservation, EditText editTextDeadline, Chip chipAlta, Chip chipMedia, Chip chipBaixa, Context context, ActionBar toolBar) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        if (intent.hasExtra(Constants.FIRESOTRE_DOCUMENT_KEY)) {
            firestoreId = intent.getStringExtra(Constants.FIRESOTRE_DOCUMENT_KEY);
            database.collection(Constants.TASK_COLLECTION).document(firestoreId).addSnapshotListener(new EventListener<DocumentSnapshot>() {

                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null) {
                        tarefaL = value.toObject(Tarefa.class);
                        if (tarefaL != null) {
                            editTextDeadline.setText(formatter.format(tarefaL.getDeadline()));
                            editTextObservation.setText(tarefaL.getObservacao());
                            editTextTitle.setText(tarefaL.getTitulo());
                            switch (tarefaL.getPrioridade()) {
                                case 1:
                                    chipAlta.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.priority_alta)));
                                    break;
                                case 2:
                                    chipMedia.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.priority_media)));
                                    break;
                                case 3:
                                    chipBaixa.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.priority_baixa)));
                            }
                        }
                    }
                }
            });
        } else {
            toolBar.setTitle("Nova Tarefa");
        }
    }

    @Override
    public boolean isNewTask() {
        return firestoreId.isEmpty();
    }

    @Override
    public void saveNewTask(Tarefa tarefa) {

        CollectionReference collectionReference = database.collection(Constants.TASK_COLLECTION);
        collectionReference.add(tarefa).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(view.getContext(), "Tarefa cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(view.getContext(), "Erro ao cadastrar Tarefa.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void updateTask(Tarefa tarefa) {

        CollectionReference collectionReference = database.collection(Constants.TASK_COLLECTION);
        collectionReference.document(firestoreId).set(tarefa).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
//                Toast.makeText(view.getContext(), "Tarefa atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(view.getContext(), "Erro ao atualizar Tarefa.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void deleteTask() {

        if (!firestoreId.isEmpty()) {
            CollectionReference collectionReference = database.collection(Constants.TASK_COLLECTION);
            collectionReference.document(firestoreId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
//                    Toast.makeText(view.getContext(), "Tarefa removido com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(view.getContext(), "Erro ao deletar Tarefa.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
