package com.example.hugo.njupter.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shine on 2016/3/19.
 */
public class IncomingSms extends BroadcastReceiver {
    public static final String SMS_URI_INBOX = "content://sms/inbox";
    private static final String Tag = "会员验证通知";
    private SmsReceiveCallBack callBack;

    public IncomingSms(Activity context, SmsReceiveCallBack callBack) {
        this.callBack = callBack;
        //预读获取权限
        context.managedQuery(Uri.parse(SMS_URI_INBOX), null, null, null, null);
    }

    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String smsbody = currentMessage.getDisplayMessageBody();
                    if (smsbody.contains(Tag)) {
                        String regEx = "[^0-9]";
                        Pattern p = Pattern.compile(regEx);
                        Matcher m = p.matcher(smsbody);
                        String smsContent = m.replaceAll("").trim().toString();
                        callBack.onComplete(smsContent);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface SmsReceiveCallBack {
        void onComplete(String num);
    }
}