package com.dudwo.gyrocounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddWork extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final String TAG = "Bluetooth Service";


    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    FirebaseDatabase db;
    DatabaseReference dbref;
    // Layout
    private EditText mCategory;
    private EditText mType;
    private EditText mMax;
    private EditText mMin;
    private Work work;

    private Button btn_Connect;
    private TextView txt_Result;

    private BluetoothService btService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        findViewById(R.id.add_work_button).setOnClickListener(this);

        // Views

        /** Main Layout **/
        btn_Connect = (Button) findViewById(R.id.btn_connect);

        mCategory = (EditText) findViewById(R.id.category);
        mType = (EditText) findViewById(R.id.type);
        mMax = (EditText) findViewById(R.id.max);
        mMin = (EditText) findViewById(R.id.min);

        btn_Connect.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        // BluetoothService 클래스 생성
        if (btService == null) {
            btService = new BluetoothService(this, mHandler);
        }

    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent("BLUETOOTH");
            intent.putExtra("value", Integer.parseInt(String.valueOf(msg.what)));
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            //Log.d("HANDLER",String.valueOf(msg.what));
        }
    };




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    btService.getDeviceInfo(data);
                }
                break;

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns 
                if (resultCode == Activity.RESULT_OK) {
                    // 확인 눌렀을 때 
                    // Next Step
                    btService.scanDevice();
                } else {
                    // 취소 눌렀을 때 
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
        }
    }


    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.add_work_button) {
            db = FirebaseDatabase.getInstance();
            dbref = db.getReference();
            String id = user.getUid();
            String category = mCategory.getText().toString();
            String type = mType.getText().toString();
            int max = Integer.parseInt(mMax.getText().toString());
            int min = Integer.parseInt(mMin.getText().toString());

            work = new Work(id, category, type, max, min);
            dbref.child("work").push().setValue(work);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (i == R.id.btn_connect) {
            if (btService.getDeviceState()) {
                // 블루투스가 지원 가능한 기기일 때
                btService.enableBluetooth();
                Log.d("AAAA","AAAA");
                btService.write("a".getBytes());
            } else {
                finish();
            }
        }
    }
}

