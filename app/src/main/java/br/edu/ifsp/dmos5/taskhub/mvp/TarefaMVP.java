package br.edu.ifsp.dmos5.taskhub.mvp;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;

import com.google.android.material.chip.Chip;

import br.edu.ifsp.dmos5.taskhub.model.Tarefa;

public interface TarefaMVP {


    interface View{
        Context getContext();

    }

    interface Presenter{
        void detach();

        void updateUI(Intent intent, EditText editTextTitle, EditText editTextObservation, EditText editTextDeadline, Chip chipAlta, Chip chipMedia, Chip chipBaixa, Context context, ActionBar toolBar);

        boolean isNewTask();

        void saveNewTask(Tarefa tarefa);

        void updateTask(Tarefa tarefa);

        void deleteTask();
    }

}
