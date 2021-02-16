package com.example.btapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;

    TextView mStatusBlueTv;
    ImageView mBlueTv;
    Button mOnBtn, mOffBtn, mJuomaBtn;

    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mBlueTv = findViewById(R.id.bluetoothTv);
        mOnBtn = findViewById(R.id.onBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mJuomaBtn = findViewById(R.id.juomaBtn);

        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBlueAdapter == null){
            mStatusBlueTv.setText("BT not available");
        }else{
            mStatusBlueTv.setText("BT available");
        }

        if(mBlueAdapter.isEnabled()){
            mBlueTv.setImageResource(R.drawable.ic_action_on);
        }else{
            mBlueTv.setImageResource(R.drawable.ic_action_off);
        }

        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mBlueAdapter.isEnabled()){
                    showToast("Turning on bluetooth...");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                }else{
                    showToast("Bluetotth is already on");
                }
            }
        });

        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBlueAdapter.isEnabled()){
                    mBlueAdapter.disable();
                    showToast("Switching bt off");
                    mBlueTv.setImageResource(R.drawable.ic_action_off);
                }else{
                    showToast("Bluetooth already off");
                }
            }
        });

        mJuomaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getJuomasivu = new Intent(this,
                        SecondScreen.class);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_ENABLE_BT){
            if(resultCode == RESULT_OK){
                mBlueTv.setImageResource(R.drawable.ic_action_on);
                showToast("BT is on");
            }else{
                showToast("BT is off");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
