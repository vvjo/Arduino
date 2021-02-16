package com.example.juomaa;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter;
import android.provider.Settings;
import android.content.Intent;


import android.os.Bundle;
import android.util.Log;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        String deviceName = "HC-06";
        BluetoothDevice result = null;
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        if (devices != null) {
            for (BluetoothDevice device : devices) {
                Log.i("[DEVICE]", device.getName());
                if (deviceName.equals(device.getName())) {
                    result = device;
                    break;
                }
            }
        }

        Log.i("[RESULT]", result.getName());

    }
}
