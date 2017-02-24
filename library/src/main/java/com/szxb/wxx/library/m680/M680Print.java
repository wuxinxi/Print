package com.szxb.wxx.library.m680;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.szxb.smart.pos.jni_interface.StringByte;
import com.szxb.smart.pos.jni_interface.printer;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

//打印String类型的字符串

public class M680Print {
	public static final int BIT_WIDTH = 384;

	private static final int WIDTH = 48;

	private static final int GSV_HEAD = 8;

	@SuppressLint("SimpleDateFormat")
	public  static void printours1() {
		M680Print p = new M680Print();

		p.printString("单位：小兵智能");

		p.printString("职位：");


		p.printString("地址：深圳市科技园");


		p.printString("主营：pos");


		p.printString("终端号：2233");


		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());
		// 获取当前时间
		String str = formatter.format(curDate);
		p.printString("时间：" + str);

	}

	public void printString(String data) {

		data = sb(data);
		final byte[] b = StringByte.HexString2Bytes(data);
		printer.PrinterWrite(b);
		printer.PrinterWrite(printer.getCmdLf());
	}

	// 加粗 i=1加粗 i=0不加粗
	public void bold(int i) {
		printer.PrinterWrite(printer.getCmdEscEN(i));

	}

	// 居中 1中 0右 2左

	public void center(int i) {
		printer.PrinterWrite(printer.getCmdEscAN(i));
	}

	// 加宽
	public void width(int i) {
		switch (i) {
			case 1:
				printer.PrinterWrite(printer.getCmdEscSo());// 加粗
				break;

			case 0:
				printer.PrinterWrite(printer.getCmdEscDc4());// 清除加粗
				break;
		}

	}

	// 倒打 i=1 true, i=0 clear
	@SuppressWarnings("unused")
	private void reverse(int i) {
		printer.PrinterWrite(printer.getCmdEsc__N(i));

	}

	// 反白 i=1 反白 i=0 clear

	public void inverse(int i) {
		printer.PrinterWrite(printer.getCmdGsBN(i));
	}

	// 下划线 i=0~2

	public void underline(int i) {
		printer.PrinterWrite(printer.getCmdEsc___N(i));

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

	public void bitmap(Bitmap b, int left, int top) {
		byte[] result = generateBitmapArrayGSV_MSB(b, left, top);
		printer.PrinterOpen();
		printer.PrinterProbe();
		printer.PrinterStatus();
		int lines = ((result.length - GSV_HEAD) / WIDTH) - 30;
		System.arraycopy(new byte[] { 0x12, 0x2A, (byte) (lines & 0xff),
				(byte) ((lines >> 8) & 0xff) }, 0, result, 0, GSV_HEAD);
		printer.PrinterWrite(result);
		printer.PrinterWrite(printer.getCmdLf());
		printer.PrinterClose();
	}

	private static byte[] generateBitmapArrayGSV_MSB(Bitmap bm,
													 int bitMarginLeft, int bitMarginTop) {
		byte[] result = null;
		int n = bm.getHeight() + bitMarginTop;
		int offset = GSV_HEAD;
		result = new byte[n * WIDTH + offset];
		for (int y = 0; y < bm.getHeight(); y++) {
			for (int x = 0; x < bm.getWidth(); x++) {
				if (x + bitMarginLeft < BIT_WIDTH) {
					int color = bm.getPixel(x, y);

					int red = Color.red(color);

					if (red < 128) {
						int bitX = bitMarginLeft + x;
						int byteX = bitX / 8;
						int byteY = y + bitMarginTop;
						result[offset + byteY * WIDTH + byteX] |= (0x80 >> (bitX - byteX * 8));
					}
				} else {
					// ignore the rest data of this line
					break;
				}
			}
		}
		return result;
	}

}
