package com.liaozp.devicemanagerdemo;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.UserManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;


import static android.app.admin.DevicePolicyManager.PASSWORD_QUALITY_NUMERIC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.liaozp.devicemanagerdemo.manager.DeviceAdminManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final String TAG = "neildemo";

    private AppCompatButton mBtnIsDeviceOwner;
    private AppCompatButton btnLock, mBtnResetPwToken, mBtnCleanPwToken;
    private AppCompatButton btnLockPassword;
    private AppCompatButton btnApiTest;
    private DeviceAdminManager deviceAdminManager;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;

    private String mResetPasswordToken = "12345678901234567890123456789012345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "lzp init dirboot 1433");
        initData();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mBtnIsDeviceOwner.setOnClickListener(this);
        btnLock.setOnClickListener(this);
        mBtnResetPwToken.setOnClickListener(this);
        mBtnCleanPwToken.setOnClickListener(this);
        btnLockPassword.setOnClickListener(this);
        btnApiTest.setOnClickListener(this);
//        switchDisableScreen.setOnCheckedChangeListener(this);
//        switchDisableUsb.setOnCheckedChangeListener(this);
//        switchDisableCall.setOnCheckedChangeListener(this);
//        switchDisableDebug.setOnCheckedChangeListener(this);
//        switchDisableGPS.setOnCheckedChangeListener(this);
//        switchDisableNetworkShare.setOnCheckedChangeListener(this);
//        switchDisableResetFactory.setOnCheckedChangeListener(this);
//        switchDisableSMS.setOnCheckedChangeListener(this);
//        switchDisableTF.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        deviceAdminManager = DeviceAdminManager.get();
        deviceAdminManager.init(this);

        devicePolicyManager = deviceAdminManager.getDevicePolicyManager();
        componentName = deviceAdminManager.getDeviceAdminReceiver();

        mBtnIsDeviceOwner = findViewById(R.id.btn_deviceowner_check);
        mBtnResetPwToken = findViewById(R.id.btn_set_pw_token);
        mBtnCleanPwToken = findViewById(R.id.btn_clean_pw_token);
        btnLock = findViewById(R.id.btn_lock);
        btnLockPassword = findViewById(R.id.btn_lock_password);
        btnApiTest =findViewById(R.id.btn_apitest);
//        switchDisableScreen = findViewById(R.id.switch_disable_screen);
//        switchDisableUsb = findViewById(R.id.switch_disable_usb);
//        switchDisableCall = findViewById(R.id.switch_disable_call);
//        switchDisableDebug = findViewById(R.id.switch_disable_debug);
//        switchDisableGPS = findViewById(R.id.switch_disable_gps);
//        switchDisableNetworkShare = findViewById(R.id.switch_disable_network_share);
//        switchDisableResetFactory = findViewById(R.id.switch_disable_reset_factory);
//        switchDisableSMS = findViewById(R.id.switch_disable_sms);
//        switchDisableTF = findViewById(R.id.switch_disable_tf);
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick");
        int id = view.getId();
        if (id == R.id.btn_lock) {
            lockScreen();
        } else if (id == R.id.btn_set_pw_token) {
            setDefResetPasswordToken();
        } else if (id == R.id.btn_clean_pw_token) {
            clearResetPasswordToken();
        } else if (id == R.id.btn_lock_password) {
            setLockPassword();
        } else if (id == R.id.btn_apitest) {
            apiTestFunc();
        } else if (id == R.id.btn_deviceowner_check) {
            IsDeviceOwner();
        }
}

    private void IsDeviceOwner() {
        boolean isDeviceOwner = devicePolicyManager.isDeviceOwnerApp("com.liaozp.devicemanagerdemo");
        Log.i(TAG, "IsDeviceOwner=" + isDeviceOwner);
        Log.i(TAG, "componentName=" + componentName.getPackageName() + ";");

    }

    /**
     * 锁屏
     */
    private void lockScreen() {
        if (devicePolicyManager.isAdminActive(componentName)) {
            devicePolicyManager.lockNow();
        }
    }

    private void setDefResetPasswordToken(){
        if(devicePolicyManager.isDeviceOwnerApp(componentName.getPackageName())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "setResetPasswordToken");
                boolean ret = devicePolicyManager.setResetPasswordToken(componentName, mResetPasswordToken.getBytes());
                Log.i(TAG, "setResetPasswordToken ret=" +ret);
            }
        }
    }

    private void clearResetPasswordToken(){
        if (devicePolicyManager.isDeviceOwnerApp(componentName.getPackageName())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "clearResetPasswordToken");
                boolean ret = devicePolicyManager.clearResetPasswordToken(componentName);
                Log.i(TAG, "clearResetPasswordToken ret=" +ret);
            }
        }
    }

    /**
     * 设置锁屏密码
     */
    private void setLockPassword() {
        if (devicePolicyManager.isDeviceOwnerApp(componentName.getPackageName())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "resetPasswordWithToken");
                boolean ret = devicePolicyManager.resetPasswordWithToken(componentName, null, mResetPasswordToken.getBytes(), 0);
                Log.i(TAG, "resetPasswordWithToken ret=" +ret);
            }
        }
    }

    private void apiTestFunc(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Log.i(TAG, "getDevicePolicyManagementRoleHolderPackage");
            String tmp = devicePolicyManager.getDevicePolicyManagementRoleHolderPackage();
            Log.i(TAG, "value=" + tmp + ";");
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }
}
