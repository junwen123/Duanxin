package com.example.administrator.duanxin;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class MainActivity extends Activity {
    EventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MobSDK.init(this, "20930c3f8b814", "1028103bacc542289fcd1d2128747576");
        SMSSDK.setAskPermisionOnReadContact(true);
        RegisterPage r = new RegisterPage();

        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Log.d("SSSSSSSSSSSSS", "-----------AA---------------------------");
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    String msg = throwable.getMessage();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        HashMap<String,Object> h = (HashMap)data;
                        String cc = (String)h.get("country");
                        String pp = (String)h.get("phone");
                        // 处理你自己的逻辑
                        SMSSDK.submitUserInfo("ss", "vv", null, cc, pp);
                        Log.d("BBB", "===" + result + "==" + data);
                    }

                    if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){

                        if(result == SMSSDK.RESULT_COMPLETE){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"ttttttttt",Toast.LENGTH_SHORT);
                                }
                            });
                            Log.d("BBB", "==ggggggggg=" + result + "==" + data);
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"ffffffffffffff",Toast.LENGTH_SHORT);
                                }
                            });
                        }
                    }

                    if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        Log.d("BBB", "===" + result + "==" + data);
                    }
                }
            }
        };
        r.setRegisterCallback(eventHandler);
        r.show(this);

    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        SMSSDK.unregisterEventHandler(eventHandler);
        super.onDestroy();
    }


}
