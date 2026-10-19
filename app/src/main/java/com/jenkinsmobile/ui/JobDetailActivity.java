package com.jenkinsmobile.ui;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.jenkinsmobile.R;
import com.jenkinsmobile.adapter.BuildAdapter;
import com.jenkinsmobile.api.ApiClient;
import com.jenkinsmobile.model.BuildHistory;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class JobDetailActivity extends AppCompatActivity {
    private String jobName;
    private BuildAdapter adapter = new BuildAdapter();
    private SwipeRefreshLayout swipe;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        jobName = getIntent().getStringExtra("job_name");
        setContentView(R.layout.activity_job_detail);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(jobName);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(v -> finish());
        RecyclerView recycler = findViewById(R.id.recycler);
        swipe = findViewById(R.id.swipe);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter.setJobName(jobName);
        recycler.setAdapter(adapter);
        swipe.setOnRefreshListener(this::load);
        findViewById(R.id.btnBuild).setOnClickListener(v -> triggerBuild());
        findViewById(R.id.btnBuildParams).setOnClickListener(v -> {
            Intent i = new Intent(this, ParametersActivity.class);
            i.putExtra("job_name", jobName);
            startActivity(i);
        });
        load();
    }
    private void load(){
        swipe.setRefreshing(true);
        ApiClient.get(this).api().getBuildHistory(jobName, "builds[number,result,building,timestamp,duration]").enqueue(new Callback<BuildHistory>(){
            @Override public void onResponse(Call<BuildHistory> c, Response<BuildHistory> r){
                swipe.setRefreshing(false);
                if(r.isSuccessful() && r.body()!=null) adapter.setBuilds(r.body().builds);
                else toast("Failed: HTTP "+r.code());
            }
            @Override public void onFailure(Call<BuildHistory> c, Throwable t){ swipe.setRefreshing(false); toast(t.getMessage()); }
        });
    }
    private void triggerBuild(){
        ApiClient.get(this).api().build(jobName).enqueue(new Callback<Void>(){
            @Override public void onResponse(Call<Void> c, Response<Void> r){
                if(r.isSuccessful() || r.code()==201) toast("Build queued");
                else toast("Failed: HTTP "+r.code());
            }
            @Override public void onFailure(Call<Void> c, Throwable t){ toast(t.getMessage()); }
        });
    }
    private void toast(String m){ Toast.makeText(this, m==null?"Error":m, Toast.LENGTH_SHORT).show(); }
}
