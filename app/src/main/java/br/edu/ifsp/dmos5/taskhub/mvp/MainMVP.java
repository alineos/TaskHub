package br.edu.ifsp.dmos5.taskhub.mvp;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public interface MainMVP {

    interface View {
        Context getContext();
    }

    interface Presenter {
        void detach();

        void populate(RecyclerView recyclerView);

        void populate(String newText, RecyclerView recyclerView);

        void populate(RecyclerView recyclerView,String newText);

        void startListener();

        void stopListener();
    }
}
