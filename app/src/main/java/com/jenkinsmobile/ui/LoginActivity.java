package com.jenkinsmobile.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.jenkinsmobile.R;
import com.jenkinsmobile.api.ApiClient;
import com.jenkinsmobile.util.Prefs;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edtUrl, edtUser, edtToken;
    private ProgressBar progress;
    private MaterialButton btnLogin;
    private Prefs prefs;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        prefs = new Prefs(this);
        if(!prefs.getUrl().isEmpty()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        edtUrl=findViewById(R.id.edtUrl);
        edtUser=findViewById(R.id.edtUser);
        edtToken=findViewById(R.id.edtToken);
        progress=findViewById(R.id.progress);
        btnLogin=findViewById(R.id.btnLogin);
        edtUrl.setText("http://");
        btnLogin.setOnClickListener(v -> login());
    }
    private void login(){
        String url=edtUrl.getText().toString().trim();
        String user=edtUser.getText().toString().trim();
        String token=edtToken.getText().toString().trim();
        if(url.isEmpty()){ edtUrl.setError("Required"); return; }
        if(!url.startsWith("http")){ url="http://"+url; }
        prefs.saveUrl(url);
        prefs.saveAuth(user, token);
        ApiClient.updateCredentials(this);
        progress.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);
        ApiClient.get(this).api().testConnection().enqueue(new Callback<ResponseBody>() {
            @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> resp){
                progress.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
                if(resp.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    prefs.clearAuth();
                    Toast.makeText(LoginActivity.this, "Auth failed: HTTP "+resp.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override public void onFailure(Call<ResponseBody> call, Throwable t){
                progress.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, "Connection failed: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
