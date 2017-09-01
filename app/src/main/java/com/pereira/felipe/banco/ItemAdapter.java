package com.pereira.felipe.banco;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pereira.felipe.testejogo.R;

import java.util.List;

/**
 * Created by Felipe on 25/08/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<Item> Itens;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, descricao;
        public ImageView icone;

        public MyViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.tv_nome);
            descricao = (TextView) view.findViewById(R.id.tv_descricao);
            icone = (ImageView) view.findViewById(R.id.imageView);
        }
    }


    public ItemAdapter(List<Item> moviesList) {
        this.Itens = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = Itens.get(position);
        holder.nome.setText(item.getNome());
        holder.descricao.setText(item.getDescricao());
        holder.icone.setImageResource(item.getIcone());
    }

    @Override
    public int getItemCount() {
        return Itens.size();
    }

}
