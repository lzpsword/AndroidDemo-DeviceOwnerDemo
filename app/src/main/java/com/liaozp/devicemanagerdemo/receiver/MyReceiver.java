package com.liaozp.devicemanagerdemo.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.liaozp.devicemanagerdemo.MainActivity;
import com.liaozp.devicemanagerdemo.manager.DeviceAdminManager;

public class MyReceiver extends BroadcastReceiver {


    private static final String TAG = MainActivity.TAG;

    ComponentName mDap;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        Log.i(TAG, "MyReceiver action=" + action + ";;");
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        if(dpm == null){
            Log.i(TAG, "dpm = null");
            return;
        }

        Log.i(TAG, "isDeviceOwner=" + dpm.isDeviceOwnerApp("com.liaozp.devicemanagerdemo"));
        mDap = new ComponentName(context, CTDeviceAdminReceiver.class);

        boolean ret = false;
        if("com.liaozp.devicemanagerdemo.RESETPWSUCC".equals(action)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "receiver resetPasswordWithToken right");
                ret = dpm.resetPasswordWithToken(mDap, null, "12345678901234567890123456789012345".getBytes(), 0);
            }
        } else if ("com.liaozp.devicemanagerdemo.RESETPWSUCC43".equals(action)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "receiver setResetPasswordToken");
                ret = dpm.resetPasswordWithToken(mDap, "3333", "12345678901234567890123456789012345".getBytes(), 0);
            }
        } else if ("com.liaozp.devicemanagerdemo.RESETPWFAILED".equals(action)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "receiver resetPasswordWithToken wrong");
                ret = dpm.resetPasswordWithToken(mDap, null, "1234567890123456789012fdsafdsafdsdfsa5".getBytes(), 0);
            }
        } else if ("com.liaozp.devicemanagerdemo.SETTOKEN".equals(action)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "receiver setResetPasswordToken");
                ret = dpm.setResetPasswordToken(mDap, "12345678901234567890123456789012345".getBytes());
            }
        }
        Log.i(TAG, "rece ret=" + ret);



    }
}