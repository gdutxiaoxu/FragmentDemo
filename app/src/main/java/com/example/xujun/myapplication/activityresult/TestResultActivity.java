package com.example.xujun.myapplication.activityresult;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xujun.myapplication.R;

public class TestResultActivity extends AppCompatActivity {

    public static final double MY_PERMISSIONS_REQUEST_CALL_PHONE =10;
    private Button mBtnSetResult;
    public static final int CODE_RESULT = 100;
    public static final String KEY_RESULT = "KEY_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        initView();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    10);
        }
    }

    private void initView() {
        mBtnSetResult = findViewById(R.id.btn_set_result);
        mBtnSetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(KEY_RESULT, "100");
                setResult(CODE_RESULT, data);
                finish();
            }
        });
    }

}
