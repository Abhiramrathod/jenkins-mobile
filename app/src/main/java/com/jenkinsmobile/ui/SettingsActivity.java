package com.jenkinsmobile.ui;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.jenkinsmobile.R;
import com.jenkinsmobile.api.ApiClient;
import com.jenkinsmobile.util.Prefs;
public class SettingsActivity extends AppCompatActivity {
    private TextInputEditText edtUrl, edtUser, edtToken;
    private Prefs prefs;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        prefs = new Prefs(this);
        setContentView(R.layout.activity_settings);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_revert);
        toolbar.setNavigationOnClickListener(v -> finish());
        edtUrl = findViewById(R.id.edtUrl);
        edtUser = findViewById(R.id.edtUser);
        edtToken = findViewById(R.id.edtToken);
        edtUrl.setText(prefs.getUrl());
        edtUser.setText(prefs.getUser());
        edtToken.setText(prefs.getToken());
        findViewById(R.id.btnSave).setOnClickListener(v -> save());
        findViewById(R.id.btnLogout).setOnClickListener(v -> logout());
    }
    private void save(){
        prefs.saveUrl(edtUrl.getText().toString().trim());
        prefs.saveAuth(edtUser.getText().toString().trim(), edtToken.getText().toString().trim());
        ApiClient.get(this).updateCredentials(this);
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void logout(){
        prefs.clearAuth();
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
