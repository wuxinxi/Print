package com.szxb.wxx;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szxb.smart.pos.jni_interface.printer;
import com.szxb.wxx.library.m680s.PrinterUtil;

public class MainActivity extends AppCompatActivity {

    private Button print;
    private TextView textViewHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        print = (Button) findViewById(R.id.print);
        textViewHide= (TextView) findViewById(R.id.textViewHide);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        PrinterUtil myprinter = new PrinterUtil();

                        if (myprinter.PrinterOpen() < 0) {
                            return;
                        }
                        textViewHide.setDrawingCacheEnabled(true);
                        textViewHide.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                                , View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                        textViewHide.layout(0, 0, textViewHide.getMeasuredWidth(), textViewHide.getMeasuredHeight());
                        Bitmap bitmap = textViewHide.getDrawingCache();

                        myprinter.printBitmap(bitmap, 0, 0);
                        myprinter.PrinterWrite(printer.printSelf(), printer.printSelf().length);
                        myprinter.PrinterClose();

                    }
                }).start();
            }
        });
    }
}