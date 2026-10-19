package com.jenkinsmobile.ui;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.jenkinsmobile.R;
import com.jenkinsmobile.api.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
public class BuildLogActivity extends AppCompatActivity {
    private String jobName; private int number;
    private TextView txtLog; private ScrollView scroll;
    private long lastByte;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        jobName = getIntent().getStringExtra("job_name");
        number = getIntent().getIntExtra("build_number", -1);
        setContentView(R.layout.activity_build_log);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(jobName+" #"+number+" log");
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(v -> finish());
        txtLog = findViewById(R.id.txtLog);
        scroll = findViewById(R.id.scroll);
        lastByte = 0;
        findViewById(R.id.btnRefresh).setOnClickListener(v -> load(true));
        findViewById(R.id.btnCopy).setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText("log", txtLog.getText().toString()));
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
        });
        load(true);
    }
    private void load(boolean append){
        ApiClient.get(this).api().getConsoleProgressive(jobName, number, append?0L:lastByte).enqueue(new Callback<ResponseBody>(){
            @Override public void onResponse(Call<ResponseBody> c, Response<ResponseBody> r){
                if(r.isSuccessful() && r.body()!=null){
                    try {
                        String content = r.body().string();
                        lastByte += content.length();
                        if(append) txtLog.setText(content);
                        else txtLog.append(content);
                        scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN));
                    } catch(IOException ignored){ }
                } else {
                    ApiClient.get(BuildLogActivity.this).api().getConsoleText(jobName, number).enqueue(new Callback<ResponseBody>(){
                        @Override public void onResponse(Call<ResponseBody> c2, Response<ResponseBody> r2){
                            if(r2.isSuccessful() && r2.body()!=null){
                                try { txtLog.setText(r2.body().string()); } catch(IOException ignored){ }
                            }
                        }
                        @Override public void onFailure(Call<ResponseBody> c2, Throwable t){ }
                    });
                }
            }
            @Override public void onFailure(Call<ResponseBody> c, Throwable t){ toast(t.getMessage()); }
        });
    }
    private void toast(String m){ Toast.makeText(this, m==null?"Error":m, Toast.LENGTH_SHORT).show(); }
}
