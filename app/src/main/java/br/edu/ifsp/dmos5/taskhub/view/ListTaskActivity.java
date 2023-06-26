package br.edu.ifsp.dmos5.taskhub.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.ifsp.dmos5.taskhub.R;
import br.edu.ifsp.dmos5.taskhub.constant.Constants;
import br.edu.ifsp.dmos5.taskhub.mvp.MainMVP;
import br.edu.ifsp.dmos5.taskhub.presenter.TarefaRVPresenter;

public class ListTaskActivity extends AppCompatActivity implements View.OnClickListener, MainMVP.View {

    private MainMVP.Presenter presenter;
    FloatingActionButton mActionButton;
    RecyclerView mRecyclerView;
    SearchView searchView;

    String parameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A0BAFF")));
        findById();
        setListener();
        initList();
        parameter = getIntent().getStringExtra(Constants.ATTR_USERNAME);
        presenter = new TarefaRVPresenter(this, parameter);
    }

    private void filterListByUser(String userFilter) {
        presenter.populate(mRecyclerView, userFilter);
        presenter.startListener();

    }

    private void initList() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
    }

    private void filterList(String query) {
        presenter.populate(query, mRecyclerView);
        presenter.startListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        filterListByUser(parameter);
        presenter.startListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stopListener();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == mActionButton) {
            Intent intent = new Intent(this, NewTaskActivity.class);
            intent.putExtra(Constants.ATTR_USERNAME, parameter);
            startActivity(intent);
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    private void findById() {
        mActionButton = findViewById(R.id.mActionButton);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        searchView = findViewById(R.id.searchView);

    }

    private void setListener() {
        mActionButton.setOnClickListener(this);
    }

}
