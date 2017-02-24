package com.szxb.wxx.library.h510;

import com.cloudpos.apidemo.jniinterface.PrinterInterface;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * 作者：Tangren_ on 2017/2/23 0023.
 * 邮箱：wu_tangren@163.com
 * TODO:H510打印
 */


public class H510Print {


    public static  void Print(String title, Map<Object, Object> params) {
        int resultP = PrinterInterface.PrinterOpen();
        if (resultP < 0) {
            return;
        }
        resultP = PrinterInterface.PrinterBegin();
        byte[] line;
        line = PrinterCommand.getCmdEscDN(3);
        PrinterInterface.PrinterWrite(line, line.length);
        PrinterInterface p = new PrinterInterface();
        PrinterCommand ps = new PrinterCommand();
        p.PrinterOpen();

        byte[] arryData;
        byte[] arryDatas;
        arryData = HexString2Bytes(sb(title));
        p.PrinterWrite(ps.getCmdEsc3N(36), ps.getCmdEsc3N(36).length);
        p.PrinterWrite(ps.getCmdGs_N(1), ps.getCmdGs_N(1).length);
        p.PrinterWrite(ps.getCmdEscAN(1), ps.getCmdEscAN(1).length);
        p.PrinterWrite(ps.getCmdEscEN(1), ps.getCmdEscEN(1).length);
        p.PrinterWrite(arryData, arryData.length);
        p.PrinterWrite(ps.getCmdLf(), ps.getCmdLf().length);
        p.PrinterWrite(ps.getCmdLf(), ps.getCmdLf().length);
        p.PrinterWrite(ps.getCmdEscAN(0), ps.getCmdEscAN(0).length);
        p.PrinterWrite(ps.getCmdEscEN(0), ps.getCmdEscEN(0).length);
        p.PrinterWrite(ps.getCmdGs_N(0), ps.getCmdGs_N(0).length);

        Set<Object> key = params.keySet();
        for (Object value : key) {
            arryDatas = HexString2Bytes(sb(value + ":" + params.get(value)));
            p.PrinterWrite(ps.getCmdLf(), ps.getCmdLf().length);
            p.PrinterWrite(arryDatas, arryDatas.length);
            p.PrinterWrite(ps.getCmdLf(), ps.getCmdLf().length);
        }
        p.PrinterWrite(ps.getCmdLf(), ps.getCmdLf().length);
        p.PrinterWrite(ps.getCmdLf(), ps.getCmdLf().length);
        PrinterInterface.PrinterEnd();
        PrinterInterface.PrinterClose();

    }


    private static String sb(String content) {
        String str = content;

        String hexString = "0123456789ABCDEF";
        byte[] bytes;
        try {
            bytes = str.getBytes("GBK");// 如果此处不加编码转化，得到的结果就不是理想的结果，中文转码
            StringBuilder sb = new StringBuilder(bytes.length * 2);

            for (int i = 0; i < bytes.length; i++) {
                sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
                sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
                // sb.append("");
            }
            str = sb.toString();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;

    }

    private static byte[] HexString2Bytes(String src) {
        if (null == src || 0 == src.length()) {
            return null;
        }
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < (tmp.length / 2); i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }


}
