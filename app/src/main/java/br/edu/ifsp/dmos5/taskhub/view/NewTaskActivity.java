package br.edu.ifsp.dmos5.taskhub.view;

import static br.edu.ifsp.dmos5.taskhub.R.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ifsp.dmos5.taskhub.R;
import br.edu.ifsp.dmos5.taskhub.constant.Constants;
import br.edu.ifsp.dmos5.taskhub.model.Tarefa;
import br.edu.ifsp.dmos5.taskhub.mvp.TarefaMVP;
import br.edu.ifsp.dmos5.taskhub.presenter.TarefaPresenter;
import br.edu.ifsp.dmos5.taskhub.utils.MaskEditUtil;

public class NewTaskActivity extends AppCompatActivity implements View.OnClickListener, TarefaMVP.View {

    EditText editTextTitle;
    EditText editTextObservation;
    EditText editTextDeadline;
    ChipGroup chipGroup;
    Chip chipAlta;
    Chip chipMedia;
    Chip chipBaixa;
    Button buttonCreateTask;
    Button buttonConcludeTask;

    private int prioridade;

    TarefaMVP.Presenter presenter;

    String parameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_new_task);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#A0BAFF")));
        findById();
        setListener();
        chipTest();

        editTextDeadline.addTextChangedListener(MaskEditUtil.mask(editTextDeadline,MaskEditUtil.FORMAT_DATE_HOUR));
        presenter = new TarefaPresenter(this);

        presenter.updateUI(getIntent(), editTextTitle, editTextObservation, editTextDeadline,chipAlta,chipMedia,chipBaixa,getContext(), getSupportActionBar());

    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }


    private void chipTest() {
        ColorStateList corPadrao = chipAlta.getChipBackgroundColor();

        chipAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prioridade = 1; //"Alta";
                chipAlta.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), color.priority_alta)));
                chipBaixa.setChipBackgroundColor(corPadrao);
                chipMedia.setChipBackgroundColor(corPadrao);

            }
        });
        chipMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prioridade = 2; //"Media";
                chipMedia.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), color.priority_media)));
                chipBaixa.setChipBackgroundColor(corPadrao);
                chipAlta.setChipBackgroundColor(corPadrao);
            }
        });
        chipBaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prioridade = 3;//"Baixa";
                chipBaixa.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), color.priority_baixa)));
                chipMedia.setChipBackgroundColor(corPadrao);
                chipAlta.setChipBackgroundColor(corPadrao);

            }
        });

    }


    private void findById() {

        editTextTitle = findViewById(id.editTextTitle);
        editTextObservation = findViewById(id.editTextObservation);
        editTextDeadline = findViewById(id.editTextDeadline);
        chipGroup = findViewById(id.chipGroup);
        chipAlta = findViewById(id.chipAlta);
        chipMedia = findViewById(id.chipMedia);
        chipBaixa = findViewById(id.chipBaixa);
        buttonCreateTask = findViewById(id.buttonCreateTask);
        buttonConcludeTask = findViewById(id.buttonConcludeTask);
    }


    @Override
    public void onClick(View view) {
        if (view == buttonCreateTask) {
            saveTask(false);
        }
        if(view == buttonConcludeTask){
            saveTask(true);
            buttonCreateTask.setEnabled(false);
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    private void setListener() {
        buttonCreateTask.setOnClickListener(this);
        buttonConcludeTask.setOnClickListener(this);
    }

    private void saveTask(boolean isConclude) {
        if (presenter.isNewTask()) {
            presenter.saveNewTask(makeTask(isConclude));
        } else {
            presenter.updateTask(makeTask(isConclude));
        }
        finish();

    }

    private Tarefa makeTask(boolean isConclude) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = formatter.parse(editTextDeadline.getText().toString());
        }catch (Exception e){
            Toast.makeText(this, "Data Invalida", Toast.LENGTH_SHORT).show();
            return null;
        }
        parameter = getIntent().getStringExtra(Constants.ATTR_USERNAME);
        Tarefa t1 = new Tarefa(editTextObservation.getText().toString(), editTextTitle.getText().toString(), prioridade, date, true,parameter);

        if (isConclude){
            t1.setConcluida(true);
            t1.setPrioridade(3);
        }
        return t1;
    }
}
