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
import com.jenkinsmobile.model.Job;
import com.jenkinsmobile.ui.JobDetailActivity;
import java.util.ArrayList;
import java.util.List;
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.VH> {
    private final List<Job> jobs = new ArrayList<>();
    public void setJobs(List<Job> list){ jobs.clear(); if(list!=null) jobs.addAll(list); notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v){ View view = LayoutInflater.from(p.getContext()).inflate(R.layout.item_job, p, false); return new VH(view); }
    @Override public void onBindViewHolder(@NonNull VH h, int pos){ Job j = jobs.get(pos); h.name.setText(j.name); h.status.setText(j.color==null?"":j.color.toUpperCase()); h.itemView.setOnClickListener(v -> { Context ctx = v.getContext(); Intent i = new Intent(ctx, JobDetailActivity.class); i.putExtra("job_name", j.name); ctx.startActivity(i); }); }
    @Override public int getItemCount(){ return jobs.size(); }
    static class VH extends RecyclerView.ViewHolder { TextView name, status; VH(View v){ super(v); name=v.findViewById(R.id.txtJobName); status=v.findViewById(R.id.txtJobStatus); } }
}
