package cn.m4399.loginsdk;

import android.app.Application;
import android.content.pm.ActivityInfo;

import java.util.HashMap;
import java.util.Map;

import cn.m4399.OperateCenter;
import cn.m4399.OperateConfig;
import cn.m4399.SDKResult;


public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 游戏配置信息

        Map<String, String> extra = new HashMap<>();
        //假如这边的value值是https://newsimg.5054399.com/css/other/zhuoqu.css?2，不会出现QQ和微博登陆，不会有4399游戏盒下载
//        extra.put("css_control", "https://newsimg.5054399.com/css/other/zhuoqu.css?2");
        //假如这边的value值是web，则登陆SDK不会调用客户端授权登陆
//        extra.put("entry_control", "web");
        OperateConfig opeConfig = new OperateConfig.Builder()
                // 登陆界面横竖屏配置（ 当使用游戏盒授权时，登陆强制为竖屏 ）（ 必填 ）
                .setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                // app在用户中心分配的 client_id 所对应的 redirect_url（ 必填 ）
                .setRedirectUrl("http://my.4399.com")
                // app在用户中心分配的 client_id （ 必填 ）第一次接入的 APP 可自行向 4399用户中心（厦门）申请
                .setClientID("530a1a19a7e98daf50946b1ee883916a")
                // 是否全屏显示登录界面 （ 选填）如果传入false，则wap登录/注册页面是dialog的样式，传入true是全屏的样式，默认是true
                .setFullScreen(true)
                .setExtra(extra)
                .build();
        // 进行游戏初始化
        OperateCenter.getInstance().init(getApplicationContext(), opeConfig, new OperateCenter.ValidateListener() {
            @Override
            public void onValidateFinished(SDKResult result) {

            }
        });
    }
}
