package com.jenkinsmobile.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jenkinsmobile.R;
import com.jenkinsmobile.model.QueueItem;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.VH> {
    private final List<QueueItem> items = new ArrayList<>();
    public void setItems(List<QueueItem> list){ items.clear(); if(list!=null) items.addAll(list); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v){ View view = LayoutInflater.from(p.getContext()).inflate(R.layout.item_queue, p, false); return new VH(view); }
    @Override public void onBindViewHolder(@NonNull VH h, int pos){ QueueItem qi = items.get(pos); h.id.setText(String.valueOf(qi.id)); h.why.setText(qi.why==null?"":qi.why); h.time.setText(DateFormat.getDateTimeInstance().format(new Date(qi.inQueueSince))); }
    @Override public int getItemCount(){ return items.size(); }
    static class VH extends RecyclerView.ViewHolder { TextView id,why,time; VH(View v){ super(v); id=v.findViewById(R.id.txtQueueId); why=v.findViewById(R.id.txtQueueWhy); time=v.findViewById(R.id.txtQueueTime); } }
}
