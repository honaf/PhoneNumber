package com.honaf.phonenumber;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSCore {

    public static String PhoneNumber = "";

    public static String SIM_PLAT_NUMBER = "10086";
    public static String SIM_PLAT_MSG = "BJ";

    public static final String MOVE_NUMBER = "10086";
    public static final String UNICON_NUMBER = "10010";
    public static final String TELE_NUMBER = "10001";

    public static final String MOVE_MSG = "BJ";
    public static final String UNICON_MSG = "CXHM";
    public static final String TELE_MSG = "701";

   /* public static String GetPhoneNumberFromSMSText(String sms) {

        List<String> list = GetNumbersInString(sms);
        for (String str : list) {
            if (str.length() == 11)
                return str;
        }
        return "";
    }

    public static List<String> GetNumbersInString(String str) {
        List<String> list = new ArrayList<>();
        String regex = "\\d*";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(str);

        while (m.find()) {
            if (!"".equals(m.group()))
                Log.e("honaf",m.group());
                list.add(m.group());
        }
        return list;
    }*/

    public static String GetNumberInString(String str) {
        String phone = "";
        //"[1]"代表第1位为数字1，"[4578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String regex = "[1][34578]\\d{9}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if(m.find()) {
            phone = m.group();
            Log.e("honaf",phone);
        }
        return phone;
    }

    public static String getSimType(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        StringBuilder simType = new StringBuilder("sim卡类型:");
        if (operator != null) {
            int simNumber = Integer.parseInt(operator);
            switch (simNumber) {
                case 46001:
                    Log.e("honaf","联通");
                    simType.append("联通");
                    SMSCore.SIM_PLAT_NUMBER = SMSCore.UNICON_NUMBER;
                    SMSCore.SIM_PLAT_MSG = SMSCore.UNICON_MSG;
                    break;
                case 46003:
                    Log.e("honaf","电信");
                    simType.append("电信");
                    SMSCore.SIM_PLAT_NUMBER = SMSCore.TELE_NUMBER;
                    SMSCore.SIM_PLAT_MSG = SMSCore.TELE_MSG;
                    break;
                default:
                    simType.append("移动");
                    Log.e("honaf","移动");
                    SMSCore.SIM_PLAT_NUMBER = SMSCore.MOVE_NUMBER;
                    SMSCore.SIM_PLAT_MSG = SMSCore.MOVE_MSG;
                    break;
            }
        } else {
            Log.e("honaf","type == null");
        }
        return simType.toString();

    }

 /*   public void doSendSMSTo(Context context,String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            context.startActivity(intent);
        }
    }*/


}