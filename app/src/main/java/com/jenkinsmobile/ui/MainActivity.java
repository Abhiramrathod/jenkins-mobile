package com.jenkinsmobile.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jenkinsmobile.R;
import com.jenkinsmobile.adapter.BuildAdapter;
import com.jenkinsmobile.adapter.JobAdapter;
import com.jenkinsmobile.adapter.NodeAdapter;
import com.jenkinsmobile.adapter.QueueAdapter;
import com.jenkinsmobile.api.ApiClient;
import com.jenkinsmobile.model.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private SwipeRefreshLayout swipe;
    private MaterialToolbar toolbar;
    private JobAdapter jobAdapter = new JobAdapter();
    private QueueAdapter queueAdapter = new QueueAdapter();
    private NodeAdapter nodeAdapter = new NodeAdapter();
    private int currentTab = 0;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        recycler=findViewById(R.id.recycler);
        swipe=findViewById(R.id.swipe);
        toolbar=findViewById(R.id.toolbar);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(jobAdapter);
        swipe.setOnRefreshListener(this::load);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if(id==R.id.menu_settings){ startActivity(new Intent(this, SettingsActivity.class)); return true; }
            if(id==R.id.menu_queue){ currentTab=1; bottomSelect(); return true; }
            if(id==R.id.menu_nodes){ currentTab=2; bottomSelect(); return true; }
            return false;
        });
        BottomNavigationView nav = findViewById(R.id.bottomNav);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id==R.id.nav_jobs) currentTab=0;
            else if(id==R.id.nav_queue) currentTab=1;
            else if(id==R.id.nav_nodes) currentTab=2;
            load();
            return true;
        });
        load();
    }
    private void bottomSelect(){ load(); }
    @Override protected void onResume(){ super.onResume(); load(); }
    private void load(){
        swipe.setRefreshing(true);
        if(currentTab==0) loadJobs();
        else if(currentTab==1) loadQueue();
        else loadNodes();
    }
    private void loadJobs(){
        recycler.setAdapter(jobAdapter);
        toolbar.setTitle(R.string.jobs);
        ApiClient.get(this).api().getAllView("jobs[name,url,color,buildable]").enqueue(new Callback<View>(){
            @Override public void onResponse(Call<View> c, Response<View> r){
                swipe.setRefreshing(false);
                if(r.isSuccessful() && r.body()!=null) jobAdapter.setJobs(r.body().jobs);
                else toast("Failed: HTTP "+r.code());
            }
            @Override public void onFailure(Call<View> c, Throwable t){ swipe.setRefreshing(false); toast(t.getMessage()); }
        });
    }
    private void loadQueue(){
        recycler.setAdapter(queueAdapter);
        toolbar.setTitle(R.string.queue);
        ApiClient.get(this).api().getQueue("items[id,task[name,url],why,inQueueSince,blocked,stuck]").enqueue(new Callback<Queue>(){
            @Override public void onResponse(Call<Queue> c, Response<Queue> r){
                swipe.setRefreshing(false);
                if(r.isSuccessful() && r.body()!=null) queueAdapter.setItems(r.body().items);
                else toast("Failed: HTTP "+r.code());
            }
            @Override public void onFailure(Call<Queue> c, Throwable t){ swipe.setRefreshing(false); toast(t.getMessage()); }
        });
    }
    private void loadNodes(){
        recycler.setAdapter(nodeAdapter);
        toolbar.setTitle(R.string.nodes);
        ApiClient.get(this).api().getNodes("computer[displayName,offline,numExecutors,idle,offlineCauseReason]").enqueue(new Callback<Nodes>(){
            @Override public void onResponse(Call<Nodes> c, Response<Nodes> r){
                swipe.setRefreshing(false);
                if(r.isSuccessful() && r.body()!=null) nodeAdapter.setNodes(r.body().computer);
                else toast("Failed: HTTP "+r.code());
            }
            @Override public void onFailure(Call<Nodes> c, Throwable t){ swipe.setRefreshing(false); toast(t.getMessage()); }
        });
    }
    private void toast(String m){ Toast.makeText(this, m==null?"Error":m, Toast.LENGTH_SHORT).show(); }
}
