package com.jenkinsmobile.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jenkinsmobile.R;
import com.jenkinsmobile.model.Node;
import java.util.ArrayList;
import java.util.List;
public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.VH> {
    private final List<Node> nodes = new ArrayList<>();
    public void setNodes(List<Node> list){ nodes.clear(); if(list!=null) nodes.addAll(list); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v){ View view = LayoutInflater.from(p.getContext()).inflate(R.layout.item_node, p, false); return new VH(view); }
    @Override public void onBindViewHolder(@NonNull VH h, int pos){ Node n = nodes.get(pos); h.name.setText(n.displayName); h.status.setText(n.offline?"OFFLINE":"ONLINE - "+n.numExecutors+" exec"); }
    @Override public int getItemCount(){ return nodes.size(); }
    static class VH extends RecyclerView.ViewHolder { TextView name,status; VH(View v){ super(v); name=v.findViewById(R.id.txtNodeName); status=v.findViewById(R.id.txtNodeStatus); } }
}
