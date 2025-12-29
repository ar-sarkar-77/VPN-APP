package com.anondo.vpnapp;

import android.content.Intent;
import android.net.VpnService;
import android.os.ParcelFileDescriptor;

import java.io.FileInputStream;

import de.blinkt.openvpn.VpnProfile;


public class MyOpenVPNService extends VpnService {

    private ParcelFileDescriptor vpnInterface = null;

    // Start VPN programmatically
    public void startVPN(int profileResId) {
        VpnProfile profile = new VpnProfile();
        try {
            // Load .ovpn file from res/raw
            FileInputStream fis = new FileInputStream(getResources().openRawResource(profileResId));
            profile.importConfig(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Intent intent = prepare(this);
        if (intent != null) {
            // Ask user for permission
            startActivity(intent);
        } else {
            // Already has permission, start VPN
            Intent vpnIntent = OpenVpnApi.startVpnIntent(this, profile);
            startService(vpnIntent);
        }
    }

    // Stop VPN
    public void stopVPN() {
        try {
            if (vpnInterface != null) {
                vpnInterface.close();
                vpnInterface = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopVPN();
    }
}
