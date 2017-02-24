package com.szxb.wxx.library.wizar;

import com.cloudpos.apidemo.jniinterface.PrinterInterface;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * 作者：Tangren_ on 2017/2/23 0023.
 * 邮箱：wu_tangren@163.com
 * TODO:慧银M680打印
 */


public class WizarPosPrint {
    public static void Print(String title, Map<Object, Object> params) {
        PrinterInterface.PrinterOpen();

        try {

            byte[] center = {(byte) 0x1B, (byte) 0x61, (byte) 0x01};//居中命令
            PrinterInterface.PrinterWrite(center,
                    center.length);

            byte[] bold = {(byte) 0x1B, (byte) 0x21, (byte) 0x08};//加粗命令

            byte[] boldwidth = {(byte) 0x1B, (byte) 0x21, (byte) 0x38};//加粗、加宽命令
            PrinterInterface.PrinterWrite(boldwidth,
                    boldwidth.length);

            PrinterInterface.PrinterWrite(title.getBytes("GB2312"),
                    title.getBytes("GB2312").length);

            byte[] base = {(byte) 0x1B, (byte) 0x21, (byte) 0x00};//取消加粗
            PrinterInterface.PrinterWrite(base,
                    base.length);
            PrinterInterface.PrinterWrite(PrinterInterface.getCmdLf(), 1);

            byte[] left = {(byte) 0x1B, (byte) 0x61, (byte) 0x00};//居左命令
            PrinterInterface.PrinterWrite(left,
                    left.length);

            PrinterInterface.PrinterWrite(PrinterInterface.getCmdLf(), 1);
            PrinterInterface.PrinterWrite(PrinterInterface.getCmdLf(), 1);
            Set<Object> key = params.keySet();

            for (Object value : key) {
                PrinterInterface.PrinterWrite((value + ":" + params.get(value)).getBytes("GB2312"),
                        (value + ":" + params.get(value)).getBytes("GB2312").length);
                PrinterInterface.PrinterWrite(PrinterInterface.getCmdLf(), 1);
                PrinterInterface.PrinterWrite(PrinterInterface.getCmdLf(), 1);
            }

            PrinterInterface.PrinterWrite(PrinterInterface.getCmdLf(), 1);
            PrinterInterface.PrinterWrite(PrinterInterface.getCmdLf(), 1);
            PrinterInterface.PrinterWrite(PrinterInterface.getCmdLf(), 1);

            PrinterInterface.PrinterEnd();
            PrinterInterface.PrinterClose();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
