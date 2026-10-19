package com.jenkinsmobile.ui;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jenkinsmobile.R;
import com.jenkinsmobile.api.ApiClient;
import com.jenkinsmobile.model.Job;
import com.jenkinsmobile.model.ParameterDefinition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ParametersActivity extends AppCompatActivity {
    private String jobName;
    private LinearLayout container;
    private MaterialButton btnStart;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        jobName = getIntent().getStringExtra("job_name");
        setContentView(R.layout.activity_parameters);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(jobName+" params");
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(v -> finish());
        container = findViewById(R.id.paramContainer);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> start());
        load();
    }
    private void load(){
        ApiClient.get(this).api().getJobDepth(jobName, 1).enqueue(new Callback<Job>(){
            @Override public void onResponse(Call<Job> c, Response<Job> r){
                if(r.isSuccessful() && r.body()!=null && r.body().property!=null){
                    for(ParameterDefinition p : r.body().property){
                        TextInputLayout til = new TextInputLayout(ParametersActivity.this);
                        til.setHint(p.name);
                        TextInputEditText et = new TextInputEditText(ParametersActivity.this);
                        if(p.defaultParameterValue != null && p.defaultParameterValue.has("value")){
                            et.setText(p.defaultParameterValue.get("value").getAsString());
                        }
                        if(p.type != null && p.type.contains("Text")){
                            et.setSingleLine(false);
                        }
                        til.addView(et);
                        container.addView(til);
                    }
                    if(r.body().property == null || r.body().property.isEmpty()){
                        Toast.makeText(ParametersActivity.this, "No parameters", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ParametersActivity.this, "Failed: HTTP "+r.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<Job> c, Throwable t){ Toast.makeText(ParametersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show(); }
        });
    }
    private void start(){
        Map<String,String> params = new HashMap<>();
        for(int i=0;i<container.getChildCount();i++){
            TextInputLayout til = (TextInputLayout) container.getChildAt(i);
            TextInputEditText et = (TextInputEditText) til.getEditText();
            String key = til.getHint() == null ? "" : til.getHint().toString();
            params.put(key, et.getText()==null?"":et.getText().toString());
        }
        if(params.isEmpty()){
            ApiClient.get(this).api().build(jobName).enqueue(new Callback<Void>(){
                @Override public void onResponse(Call<Void> c, Response<Void> r){ toast(r.isSuccessful()?"Queued":"HTTP "+r.code()); finish(); }
                @Override public void onFailure(Call<Void> c, Throwable t){ toast(t.getMessage()); }
            });
        } else {
            ApiClient.get(this).api().buildWithParameters(jobName, params).enqueue(new Callback<Void>(){
                @Override public void onResponse(Call<Void> c, Response<Void> r){ toast(r.isSuccessful()?"Queued":"HTTP "+r.code()); finish(); }
                @Override public void onFailure(Call<Void> c, Throwable t){ toast(t.getMessage()); }
            });
        }
    }
    private void toast(String m){ Toast.makeText(this, m==null?"Error":m, Toast.LENGTH_SHORT).show(); }
}
