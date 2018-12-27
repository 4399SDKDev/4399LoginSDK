package cn.m4399.loginsdk;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import cn.m4399.OperateCenter;
import cn.m4399.OperateConfig;
import cn.m4399.SDKResult;

public class MainActivity extends Activity {

    private OperateCenter mOpeCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar(this);
        TextView tv = (TextView) findViewById(R.id.sdk_version);
        tv.setText("version ：" + OperateConfig.getVersion());
        mOpeCenter = OperateCenter.getInstance();
    }

    public void onRegisterClicked(View view) {
        // 注册接口一经调用，无论原先是否已经登录一律清除原有登录信息，重新进行注册并且登录步骤。
        //  注册接口回调与登陆接口相同
        mOpeCenter.register(new OperateCenter.ValidateListener() {
            @Override
            public void onValidateFinished(SDKResult result) {
                //showUserInfo(result);
                int code = result.getResultCode();
                String msg = result.getResultMsg();
                //SdkLog.v(" Register-- " + result.toString() + " -- code : " + code + " , msg : " + msg);
                showToastMsg(" Register-- " + result.toString() + " -- code : " + code + " , msg : " + msg);
            }
        });
    }

    public void onLoginClicked(View view) {
        //登录接口一经调用，无论原先是否已经登录一律清除原有登录信息，重新进行登录步骤。
        mOpeCenter.login(new OperateCenter.ValidateListener() {
            @Override
            public void onValidateFinished(SDKResult result) {
                //showUserInfo(result);
                int code = result.getResultCode();
                String msg = result.getResultMsg();
                showToastMsg(" Login-- " + result.toString() + " -- code : " + code + " , msg : " + msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOpeCenter.removeValidateListener();
        mOpeCenter = null;
    }

    public void onVersionClicked(View view) {
        // 返回当前 SDK 版本名称 (java.lang.String)
        showToastMsg(" Version-- " + OperateConfig.getVersion());
    }

    private void showToastMsg(String msg) {
        String showTxt = "[M4399 Login SDK] " + msg;
        Toast.makeText(getApplicationContext(), showTxt, Toast.LENGTH_LONG).show();
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public void setStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }

}
