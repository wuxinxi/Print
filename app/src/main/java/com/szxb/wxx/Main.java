package com.szxb.wxx;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.szxb.wxx.library.m680s.PrinterUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/5/15 0015.
 */

public class Main extends AppCompatActivity {

    private ScrollView scrollView;
    private Button button;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        scrollView = (ScrollView) findViewById(R.id.scrollview);
        imageView = (ImageView) findViewById(R.id.img);

        button = (Button) findViewById(R.id.print);
        final Bitmap bitmap = convertViewToBitmap(scrollView);
        imageView.setImageBitmap(bitmap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PrinterUtil myprinter = new PrinterUtil();

                        if (myprinter.PrinterOpen() < 0) {
                            return;
                        }
                        byte[] b = null;
                        try {
                            b = ("hem, ordet har någon form av magi som får du alltid med henne, kan" +
                                    " jag inte ge något exakt definition endast genom några enkla uttryck " +
                                    "för att beskriva en ritning.").getBytes("UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        myprinter.PrinterType((byte) 5);
//                        myprinter.PrinterWrite(myprinter.printSelf(), myprinter.printSelf().length);
                        myprinter.PrinterWrite(b, b.length);
//                        myprinter.printBitmap(bitmap, 0, 0);
                        myprinter.PrinterClose();
                    }
                }).start();
            }
        });

    }

    public static Bitmap convertViewToBitmap(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache(true);
    }


}
