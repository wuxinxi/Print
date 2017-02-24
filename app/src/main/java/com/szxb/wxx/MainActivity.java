package com.szxb.wxx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.szxb.wxx.library.m680.M680Print;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        print = (Button) findViewById(R.id.print);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//
//
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Map<Object, Object> map = new LinkedHashMap<Object, Object>();

                        map.put("交易时间", "2016-12-12");
                        map.put("交易地点", "安徽亳州");
                        map.put("交易气候", "晴朗");
                        map.put("交易金额", "20161212");
                        map.put("交易安好", "wq996489865");

//                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//                        WizarPosPrint.Print("交易记录", map);
                        M680Print.printours1();
//                        H510Print.Print("交易记录", map);


                    }
                }).start();
            }
        });
    }
}
