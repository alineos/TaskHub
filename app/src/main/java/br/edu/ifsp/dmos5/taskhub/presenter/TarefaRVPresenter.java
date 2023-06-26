package br.edu.ifsp.dmos5.taskhub.presenter;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Collections;

import br.edu.ifsp.dmos5.taskhub.model.Tarefa;
import br.edu.ifsp.dmos5.taskhub.view.ItemClickListener;
import br.edu.ifsp.dmos5.taskhub.view.NewTaskActivity;
import br.edu.ifsp.dmos5.taskhub.constant.Constants;
import br.edu.ifsp.dmos5.taskhub.mvp.MainMVP;
import br.edu.ifsp.dmos5.taskhub.view.adapter.TarefaAdapter;

public class TarefaRVPresenter implements MainMVP.Presenter {
    private MainMVP.View view;
    private FirebaseFirestore database;
    private TarefaAdapter adapter;

    private String newParameter;



    public TarefaRVPresenter(MainMVP.View view,String parameter) {
        this.view = view;
        database = FirebaseFirestore.getInstance();
        newParameter = parameter;
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void populate(RecyclerView recyclerView) {
        Query query = database.collection(Constants.TASK_COLLECTION).orderBy(Constants.ATTR_PRIORITY, Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Tarefa> options = new FirestoreRecyclerOptions.Builder<Tarefa>().setQuery(query, Tarefa.class).build();
        adapter = new TarefaAdapter(options);
        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(String referenceId) {
                abrirDetalhes(referenceId);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void populate(String newText, RecyclerView recyclerView) {
        Query query = database.collection(Constants.TASK_COLLECTION).orderBy(Constants.ATTR_TITLE, Query.Direction.ASCENDING).startAt(newText).endAt(newText + "\uf8ff");
        //query.orderBy(Constants.ATTR_PRIORITY,Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Tarefa> options = new FirestoreRecyclerOptions.Builder<Tarefa>().setQuery(query, Tarefa.class).build();
        adapter = new TarefaAdapter(options);
        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(String referenceId) {
                abrirDetalhes(referenceId);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void populate(RecyclerView recyclerView, String newText) {
        Query query = database.collection(Constants.TASK_COLLECTION).orderBy(Constants.ATTR_PRIORITY,Query.Direction.ASCENDING).whereEqualTo(Constants.ATTR_USERNAME,newText);

        FirestoreRecyclerOptions<Tarefa> options = new FirestoreRecyclerOptions.Builder<Tarefa>().setQuery(query, Tarefa.class).build();
        adapter = new TarefaAdapter(options);
        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(String referenceId) {
                abrirDetalhes(referenceId);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startListener() {
        if (adapter != null) adapter.startListening();
    }

    @Override
    public void stopListener() {
        if (adapter != null) adapter.stopListening();
    }

    private void abrirDetalhes(String referenceId) {
        Intent intent = new Intent(view.getContext(), NewTaskActivity.class);
        intent.putExtra(Constants.FIRESOTRE_DOCUMENT_KEY, referenceId);
        intent.putExtra(Constants.ATTR_USERNAME, newParameter);

        view.getContext().startActivity(intent);
    }
}
