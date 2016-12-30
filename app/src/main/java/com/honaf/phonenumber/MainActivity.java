package com.honaf.phonenumber;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String ACTION_SMS_RECEIVER = "android.provider.Telephony.SMS_RECEIVED";
    SMS_Receiver smsReceiver = null;
    private Button btn_send;
    ExecutorService executorService = Executors.newCachedThreadPool();
    private TextView tv_type;
    private TextView tv_phone;
    private DataWatcher watcher = new DataWatcher() {
        @Override
        public void notifyUpdate(final Object data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_phone.setText(data.toString());
                }
            });

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        register();
        tv_type.setText(SMSCore.getSimType(this));
    }

    public void register() {
        smsReceiver = new SMS_Receiver();
        IntentFilter receiverFilter = new IntentFilter(ACTION_SMS_RECEIVER);
        receiverFilter.setPriority(1000);
        registerReceiver(smsReceiver, receiverFilter);

        DataChanger.getInstance().addObserver(watcher);
    }

    public void initView() {
        btn_send = (Button) this.findViewById(R.id.btn_send);
        tv_type = (TextView) this.findViewById(R.id.tv_type);
        tv_phone = (TextView) this.findViewById(R.id.tv_phone);
    }

    public void initListener() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"正在获取本机号码,请稍后......("+SMSCore.SIM_PLAT_NUMBER+"-"+SMSCore.SIM_PLAT_MSG+")",Toast.LENGTH_LONG).show();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("honaf", "开始发送短信");
                        SmsManager.getDefault().sendTextMessage(SMSCore.SIM_PLAT_NUMBER, null, SMSCore.SIM_PLAT_MSG, null, null);
//                        DataChanger.getInstance().postStatus("13794484349");
//                        SMSCore.GetNumberInString("10086哈哈垃圾大幅拉升阶段13794484349阿萨德发射aa点发55");
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
        DataChanger.getInstance().deleteObserver(watcher);
    }


}
