package com.example.recruitmenttest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recruitmenttest.R;
import com.example.recruitmenttest.activity.util.SharedPrefManager;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvResultNama)
    TextView tvResultNama;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.btnLihatDosen)
    Button btnLihatDosen;
    @BindView(R.id.btnLihatMatkul)
    Button btnLihatMatkul;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);

        tvResultNama.setText(sharedPrefManager.getSPNama());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        btnLihatDosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DosenActivity.class));
            }
        });

        btnLihatMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MatkulActivity.class));
            }
        });
    }
}