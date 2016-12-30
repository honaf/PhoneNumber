package com.honaf.phonenumber;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMS_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("honaf", "收到短信广播了");
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

            Object[] pdus = (Object[]) intent.getExtras().get("pdus");

            SmsMessage[] message = new SmsMessage[pdus.length];
            StringBuilder sb = new StringBuilder();
            System.out.println("pdus长度" + pdus.length);
            String address = "";
            for (int i = 0; i < pdus.length; i++) {
                message[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sb.append("接收到短信来自:\n");
                address = message[i].getDisplayOriginatingAddress();
                sb.append(address + "\n");
                sb.append("内容:" + message[i].getDisplayMessageBody());
            }
            Log.e("honaf", sb.toString());
            if (address.equals(SMSCore.SIM_PLAT_NUMBER)) {
                SMSCore.PhoneNumber = SMSCore.GetNumberInString(sb.toString());
                Log.e("honaf", "本机号码:" + SMSCore.PhoneNumber);
                DataChanger.getInstance().postStatus("本机号码:" + SMSCore.PhoneNumber);
            }
        }
    }

}