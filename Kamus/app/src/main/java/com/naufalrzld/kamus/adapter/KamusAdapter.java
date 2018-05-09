package com.naufalrzld.kamus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naufalrzld.kamus.DetailKamusActivity;
import com.naufalrzld.kamus.R;
import com.naufalrzld.kamus.model.KamusModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.ViewHolder> {
    private Context context;
    private ArrayList<KamusModel> kamusList = new ArrayList<>();

    public KamusAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<KamusModel> kamusList) {
        this.kamusList = kamusList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kamus_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final KamusModel kamusModel = kamusList.get(position);

        holder.tvWord.setText(kamusModel.getKata());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailKamusActivity.class);
                i.putExtra("data", kamusModel);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kamusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item)
        LinearLayout item;
        @BindView(R.id.tv_word)
        TextView tvWord;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
