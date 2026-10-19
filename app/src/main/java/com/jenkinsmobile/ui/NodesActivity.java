package com.jenkinsmobile.ui;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.jenkinsmobile.R;
import com.jenkinsmobile.adapter.NodeAdapter;
import com.jenkinsmobile.api.ApiClient;
import com.jenkinsmobile.model.Nodes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class NodesActivity extends AppCompatActivity {
    private NodeAdapter adapter = new NodeAdapter();
    private SwipeRefreshLayout swipe;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_nodes);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nodes);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(v -> finish());
        RecyclerView recycler = findViewById(R.id.recycler);
        swipe = findViewById(R.id.swipe);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        swipe.setOnRefreshListener(this::load);
        load();
    }
    private void load(){
        swipe.setRefreshing(true);
        ApiClient.get(this).api().getNodes("computer[displayName,offline,numExecutors,idle,offlineCauseReason]").enqueue(new Callback<Nodes>(){
            @Override public void onResponse(Call<Nodes> c, Response<Nodes> r){
                swipe.setRefreshing(false);
                if(r.isSuccessful() && r.body()!=null) adapter.setNodes(r.body().computer);
                else toast("HTTP "+r.code());
            }
            @Override public void onFailure(Call<Nodes> c, Throwable t){ swipe.setRefreshing(false); toast(t.getMessage()); }
        });
    }
    private void toast(String m){ Toast.makeText(this, m==null?"Error":m, Toast.LENGTH_SHORT).show(); }
}
