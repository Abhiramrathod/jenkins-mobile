package com.jenkinsmobile.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.jenkinsmobile.R;
import com.jenkinsmobile.api.ApiClient;
import com.jenkinsmobile.model.Build;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class BuildDetailActivity extends AppCompatActivity {
    private String jobName; private int number;
    private TextView txtResult, txtInfo;
    private ProgressBar progress;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        jobName = getIntent().getStringExtra("job_name");
        number = getIntent().getIntExtra("build_number", -1);
        setContentView(R.layout.activity_build_detail);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(jobName+" #"+number);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(v -> finish());
        txtResult=findViewById(R.id.txtResult);
        txtInfo=findViewById(R.id.txtInfo);
        progress=findViewById(R.id.progress);
        findViewById(R.id.btnLog).setOnClickListener(v -> {
            Intent i = new Intent(this, BuildLogActivity.class);
            i.putExtra("job_name", jobName); i.putExtra("build_number", number);
            startActivity(i);
        });
        findViewById(R.id.btnStop).setOnClickListener(v -> stop());
        load();
    }
    private void load(){
        progress.setVisibility(View.VISIBLE);
        ApiClient.get(this).api().getBuildDepth(jobName, number, 1).enqueue(new Callback<Build>(){
            @Override public void onResponse(Call<Build> c, Response<Build> r){
                progress.setVisibility(View.GONE);
                if(r.isSuccessful() && r.body()!=null){
                    Build bd = r.body();
                    txtResult.setText(bd.building ? "BUILDING" : (bd.result==null?"UNKNOWN":bd.result));
                    StringBuilder sb = new StringBuilder();
                    sb.append("Duration: ").append(bd.duration/1000).append("s\n");
                    sb.append("Built on: ").append(bd.builtOn==null?"-":bd.builtOn).append("\n");
                    sb.append("Timestamp: ").append(bd.timestamp).append("\n");
                    if(bd.actions!=null){
                        for(com.jenkinsmobile.model.Action a : bd.actions){
                            if(a.causes!=null && !a.causes.isEmpty())
                                sb.append("Cause: ").append(a.causes.get(0).shortDescription).append("\n");
                        }
                    }
                    txtInfo.setText(sb.toString());
                } else toast("Failed: HTTP "+r.code());
            }
            @Override public void onFailure(Call<Build> c, Throwable t){ progress.setVisibility(View.GONE); toast(t.getMessage()); }
        });
    }
    private void stop(){
        ApiClient.get(this).api().stopBuild(jobName, number).enqueue(new Callback<Void>(){
            @Override public void onResponse(Call<Void> c, Response<Void> r){ toast(r.isSuccessful()?"Stop requested":"Failed: HTTP "+r.code()); }
            @Override public void onFailure(Call<Void> c, Throwable t){ toast(t.getMessage()); }
        });
    }
    private void toast(String m){ Toast.makeText(this, m==null?"Error":m, Toast.LENGTH_SHORT).show(); }
}
