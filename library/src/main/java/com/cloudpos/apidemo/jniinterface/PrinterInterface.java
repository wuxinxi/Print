package com.cloudpos.apidemo.jniinterface;

import android.graphics.Bitmap;
import android.graphics.Color;

public class PrinterInterface
{
    static {
        System.loadLibrary("jni_cloudpos_printer");
    }
    static public byte[] getCmdLf()
    {
        return new byte[] { (byte) 0x0A };
    }
    /**
     * open the device
     * @return value  >= 0, success in starting the process; value < 0, error code
     * */
    public native static int PrinterOpen();
    /**
     * close the device
     * @return value  >= 0, success in starting the process; value < 0, error code
     * */

    public native static int PrinterClose();
    /**
     * prepare to print
     * @return value  >= 0, success in starting the process; value < 0, error code
     * */

    public native static int PrinterBegin();
    /** end to print
     *  @return value  >= 0, success in starting the process; value < 0, error code
     * */

    public native static int PrinterEnd();
    /**
     * write the data to the device
     * @param arryData : data or control command
     * @param nDataLength : length of data or control command
     * @return value  >= 0, success in starting the process; value < 0, error code
     * */

    public native static int PrinterWrite(byte arryData[], int nDataLength);
    /**
     * query the status of printer
     * return value : < 0 : error code
     *                = 0 : no paper
     *                = 1 : has paper
     *                other value : RFU
     */
    public native static int PrinterQuery();



    public static void printBitmap(Bitmap bm, int bitMarginLeft, int bitMarginTop) {
        printBitmap(bm, bitMarginLeft, bitMarginTop, true);
    }

    public static void printBitmap(Bitmap bm, int bitMarginLeft, int bitMarginTop,
                                   boolean alreadyOpen) {

        printBitmapGSVMSB(bm, bitMarginLeft, bitMarginTop, alreadyOpen);

    }




    public static final int BIT_WIDTH = 384;

    private static final int WIDTH = 48;

    private static final int GSV_HEAD = 8;


    /**
     * print the bitmap by GS v 0 p wL wH hL hH
     *
     * @param bm the android's Bitmap data
     * @param bitMarginLeft the left white space in bits.
     * @param bitMarginTop the top white space in bits.
     * @return
     */
    public static void printBitmapGSVMSB(Bitmap bm, int bitMarginLeft, int bitMarginTop,
                                         boolean alreadyOpen) {
        byte[] result = generateBitmapArrayGSV_MSB(bm, bitMarginLeft, bitMarginTop);


        int lines = (result.length - GSV_HEAD) / WIDTH;
        System.arraycopy(new byte[] {
                0x1D, 0x76, 0x30, 0x00, 0x30, 0x00, (byte) (lines & 0xff),
                (byte) ((lines >> 8) & 0xff)
        }, 0, result, 0, GSV_HEAD);
        PrinterInterface.PrinterWrite(result, result.length);

        // PrinterInterface.PrinterWrite(new byte[]{0x12, 0x54},2);

    }


    /**
     * generate the MSB buffer for bitmap printing GSV command
     *
     * @param bm the android's Bitmap data
     * @param bitMarginLeft the left white space in bits.
     * @param bitMarginTop the top white space in bits.
     * @return buffer with DC2V_HEAD + image length
     */
    private static byte[] generateBitmapArrayGSV_MSB(Bitmap bm, int bitMarginLeft, int bitMarginTop) {
        byte[] result = null;
        int n = bm.getHeight() + bitMarginTop;
        int offset = GSV_HEAD;
        result = new byte[n * WIDTH + offset];
        for (int y = 0; y < bm.getHeight(); y++) {
            for (int x = 0; x < bm.getWidth(); x++) {
                if (x + bitMarginLeft < BIT_WIDTH) {
                    int color = bm.getPixel(x, y);
                    int alpha = Color.alpha(color);
                    int red = Color.red(color);
                    int green = Color.green(color);
                    int blue = Color.blue(color);
                    if (alpha > 128 && (red < 128 || green < 128 || blue < 128)) {
                        // set the color black
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
