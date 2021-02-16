package com.example.btapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_ENABLE_BT = 1;
    public final static int RESULT_OK = 0;
    private UUID uID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter ba;
    private BluetoothSocket mmSocket;
    private ConThread btt = null;
    private TextView btinfo;
    private boolean lightflag = false;
    private boolean relayFlag = true;
    private Handler mHandler;
    private boolean klikkaple = false;
    private ImageButton k1, k2, k3, k4, k5, k6;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("[START]", "Starting App");

        ba = BluetoothAdapter.getDefaultAdapter();
        if(ba == null)
        {
            makeToast("Bluetooth Device Not Available");
            finish();
        } else {
            if (ba.isEnabled())
            {
                initiateBluetoothProcess();
            }
            else
            {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent,1);
            }
        }

        btinfo = findViewById(R.id.response);

        if (ba == null){
            makeToast("BT adapter null");
            btinfo.setText("BT not OK");
        } else {
            btinfo.setText("BT OK");
        }

        painettava();

        k1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    makeToast("Ordering");
                    orderDrink("Longisland icetea");

                    return true;
                }
                return false;
            }
        });
        k2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    makeToast("Hei");
                    return true;
                }
                return false;
            }
        });
        k3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    makeToast("Hei");
                    return true;
                }
                return false;
            }
        });
        k4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    makeToast("Hei");
                    return true;
                }
                return false;
            }
        });
        k5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    return true;
                }
                return false;
            }
        });
        k6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    makeToast("Hei");
                    if(!ba.isEnabled()) {
                        Intent i = new Intent(ba.ACTION_REQUEST_ENABLE);
                        startActivityForResult(i, REQUEST_ENABLE_BT);
                    }else {
                        makeToast("Bluetooth is already on");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void painettava(){

        k1 = findViewById(R.id.kuva1);
        k2 = findViewById(R.id.kuva2);
        k3 = findViewById(R.id.kuva3);
        k4 = findViewById(R.id.kuva4);
        k5 = findViewById(R.id.kuva5);
        k6 = findViewById(R.id.kuva6);

        k1.setClickable(!klikkaple);
        k2.setClickable(!klikkaple);
        k3.setClickable(!klikkaple);
        k4.setClickable(!klikkaple);
        k5.setClickable(!klikkaple);
        k6.setClickable(!klikkaple);
        k1.setEnabled(!klikkaple);
        k2.setEnabled(!klikkaple);
        k3.setEnabled(!klikkaple);
        k4.setEnabled(!klikkaple);
        k5.setEnabled(!klikkaple);
        k6.setEnabled(!klikkaple);
    }

    public void drink(View view) {
        switch (view.getId()){
            case R.id.kuva1:
                btinfo.setText("Hei");
            case R.id.kuva2:
                orderDrink("ba");
            case R.id.kuva3:
                orderDrink("ca");
            case R.id.kuva4:
                orderDrink("da");
            case R.id.kuva5:
                orderDrink("ea");
            case R.id.kuva6:
                switchLight();
        }
    }

    public void makeToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void orderDrink(String drink) {
        Log.i("[BLUETOOTH]", "Yritetään tilata " + drink);
        if (mmSocket.isConnected() && btt != null) {
            btt.write(drink.getBytes());
            relayFlag = true;
        } else {
            Log.i("[BLUETOOTH", "Lähetettäessä juomaa ongelmia");
            relayFlag = false;
        }
        painettava();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                painettava();
            }
        }, 5000);
    }

    public void switchLight(){
        Log.i("[BLUETOOTH]", "Light main");
        if (mmSocket.isConnected() && btt != null) { //if we have connection to the bluetoothmodule
            if (!lightflag) {
                String sendtxt = "fa";
                btt.write(sendtxt.getBytes());
                lightflag = true;
            } else {
                String sendtxt = "fb";
                btt.write(sendtxt.getBytes());
                lightflag = false;
            }
        } else {
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("[ACTIVITYRESULT}","ON ACTIVITY RESULT: " + resultCode + " " + requestCode + " " + RESULT_OK + " " + REQUEST_ENABLE_BT);

        if(resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT){
            initiateBluetoothProcess();
        } else {
            Log.i("[ACTIVITYRESULT]", "NO RESULT FOR ACTIVITY");
        }
    }

    public void initiateBluetoothProcess(){

        String deviceName = "HC-06";
        BluetoothDevice resultDevice = null;
        Set<BluetoothDevice> devices = ba.getBondedDevices();
        if (devices != null) {
            for (BluetoothDevice device : devices) {
                Log.i("[DEVICE]", device.getName());
                if (deviceName.equals(device.getName())) {
                    resultDevice = device;
                    break;
                }
            }
        }

        if(ba.isEnabled()){

            //attempt to connect to bluetooth module
            BluetoothSocket tmp = null;

            //create socket
            try {
                tmp = resultDevice.createRfcommSocketToServiceRecord(uID);
                mmSocket = tmp;
                mmSocket.connect();
                Log.i("[BLUETOOTHSOCKET]","Connected to: "+resultDevice.getName());
            }catch(IOException e){
                Log.i("[BLUETOOTHSOCKET]", e.toString());
                try{
                    mmSocket.close();
                }
                catch(IOException c){
                    return;
                }
            }

            Log.i("[BLUETOOTH]", "Creating handler");
            mHandler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    //super.handleMessage(msg);
                    if(msg.what == ConThread.RESPONSE_MESSAGE){
                        String txt = (String)msg.obj;
                        if(btinfo.getText().toString().length() >= 30){
                            btinfo.setText("");
                            btinfo.append(txt);
                        }else{
                            btinfo.append("\n" + txt);
                        }
                    }
                }
            };

            Log.i("[BLUETOOTH]", "Creating and running Thread");
            btt = new ConThread(mmSocket,mHandler);
            btt.start();


        }
    }

    protected void onDestroy(){
        super.onDestroy();
        btt.cancel();
    }
}
