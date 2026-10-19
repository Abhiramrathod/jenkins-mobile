package com.jenkinsmobile.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jenkinsmobile.R;
import com.jenkinsmobile.model.Build;
import com.jenkinsmobile.ui.BuildDetailActivity;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class BuildAdapter extends RecyclerView.Adapter<BuildAdapter.VH> {
    private final List<Build> builds = new ArrayList<>();
    private String jobName;
    public void setJobName(String n){ jobName=n; }
    public void setBuilds(List<Build> list){ builds.clear(); if(list!=null) builds.addAll(list); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v){ View view = LayoutInflater.from(p.getContext()).inflate(R.layout.item_build, p, false); return new VH(view); }
    @Override public void onBindViewHolder(@NonNull VH h, int pos){ Build b = builds.get(pos); h.num.setText("#"+b.number); h.result.setText(b.building?"BUILDING":(b.result==null?"":b.result)); h.time.setText(DateFormat.getDateTimeInstance().format(new Date(b.timestamp))); h.itemView.setOnClickListener(v -> { Context ctx = v.getContext(); Intent i = new Intent(ctx, BuildDetailActivity.class); i.putExtra("job_name", jobName); i.putExtra("build_number", b.number); ctx.startActivity(i); }); }
    @Override public int getItemCount(){ return builds.size(); }
    static class VH extends RecyclerView.ViewHolder { TextView num,result,time; VH(View v){ super(v); num=v.findViewById(R.id.txtBuildNum); result=v.findViewById(R.id.txtBuildResult); time=v.findViewById(R.id.txtBuildTime); } }
}
