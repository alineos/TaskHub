package br.edu.ifsp.dmos5.taskhub.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;

import br.edu.ifsp.dmos5.taskhub.R;
import br.edu.ifsp.dmos5.taskhub.model.Tarefa;
import br.edu.ifsp.dmos5.taskhub.view.ItemClickListener;

public class TarefaAdapter extends FirestoreRecyclerAdapter<Tarefa, TarefaAdapter.TarefaViewHolder> {

    private ItemClickListener clickListener;

    public TarefaAdapter(@NonNull FirestoreRecyclerOptions<Tarefa> options) {
        super(options);
    }


    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull TarefaViewHolder holder, int position, @NonNull Tarefa model) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        holder.textview_title_listitem.setText(model.getTitulo());
        holder.textview_obs_listitem.setText(formatter.format(model.getDeadline()));

        if(model.isConcluida()){
            holder.pane.setBackgroundColor(Color.parseColor("#A6F3D7"));
         }else{
            holder.pane.setBackgroundColor(Color.parseColor("#F1F5F4"));
        }

        if (model.getPrioridade() == 1) {
            holder.textview_priority_listitem.setTextColor(Color.parseColor("#FF0000"));
            holder.textview_priority_listitem.setText("Alta");
        } else {
            if (model.getPrioridade() == 2) {
                holder.textview_priority_listitem.setTextColor(Color.parseColor("#00FF00"));
                holder.textview_priority_listitem.setText("Media");
            } else {
                holder.textview_priority_listitem.setTextColor(Color.parseColor("#0000FF"));
                holder.textview_priority_listitem.setText("Baixa");
            }
        }


    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
        TarefaViewHolder holder = new TarefaViewHolder(view);
        return holder;
    }

    public class TarefaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textview_title_listitem;
        public TextView textview_obs_listitem;
        public TextView textview_priority_listitem;
        public ConstraintLayout pane;

        public TarefaViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_title_listitem = itemView.findViewById(R.id.textview_title_listitem);
            textview_obs_listitem = itemView.findViewById(R.id.textview_date_listitem);
            textview_priority_listitem = itemView.findViewById(R.id.textview_priority_listitem);
            pane = itemView.findViewById(R.id.pane);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(getSnapshots().getSnapshot(getBindingAdapterPosition()).getId());
        }
    }

}