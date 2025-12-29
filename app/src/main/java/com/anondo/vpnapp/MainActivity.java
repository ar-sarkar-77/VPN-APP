package com.anondo.vpnapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(v -> {
            // .ovpn file from internal storage
            File ovpnFile = new File(getFilesDir(), "myvpn.ovpn");
            startVPN(this, ovpnFile);
        });
    }

    // Helper method in MainActivity
    private void startVPN(android.content.Context context, File ovpnFile) {
        OpenVpnApi.startVpnIntent(context, ovpnFile);
    }
}