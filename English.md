# Print <br/>
## <a href="https://github.com/wuxinxi/Print">中文</a>
## Import mode<br>
1.Import library Library <br>
## M680 Usage method
```
M680Print.java

M680Print print=new M680Print();
```
1.Print self-test
 ```
 print.printours1();
 ```
2.Print a data
```
print.printString(String data);
```
3.bold (i=1 is i=0 no)
```
 print.bold(int i);
```
4.postion（1Center 0Right 2Left）
```
print.center(int i) ;
```
5.Widen(i=1 is i=0 no)
```
print.width(int i);
```
6.Underline(i=1 is i=0 no)
```
print.underline(int i);
```
7.Inverse(i=1 is i=0 no)
```
print.inverse(int i);
```
8.Reverse(i=1 is i=0 no)
```
print.reverse(int i);
```
## M680S Usage method
```
PrinterUtil.java 
PrinterUtil print = new PrinterUtil();
```
1.Conventional print text
```
//b={1,2,3,4,5,6,7}
print.PrinterType(byte b);
print.PrinterWrite(byte arryData[],int len);
```
2.Print self-test
```
printer.PrinterWrite(printer.printSelf(), printer.printSelf().length);
```
3.Conventional print image
```
print.printBitmap(Bitmap bm, int bitMarginLeft, int bitMarginTop)
```
4.Print text in a picture

TextView translate picture：<br>
```

textViewHide.setDrawingCacheEnabled(true);
textViewHide.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                                , View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
textViewHide.layout(0, 0, textViewHide.getMeasuredWidth(), textViewHide.getMeasuredHeight());
                        Bitmap bitmap = textViewHide.getDrawingCache();
myprinter.printBitmap(bitmap, 0, 0);
textViewHide.destroyDrawingCache();
```
View translate picture： <br>
view include:ScrollView、ListView、RecyclerView、GridView、ExpandableListView、TextView etc……
```
public static Bitmap convertViewToBitmap(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache(true);
    }
```
5. print self-test
```
  byte[] b = new byte[256];
  for (int i = 0; i < 256; i++) {
      b[i] = (byte) i;
      }
  printer.PrinterType((byte) 5);
  printer.PrinterWrite(b, b.length);
```
3.See the PrinterUtil class for details
## H510 Usage method
```
 Map<Object, Object> map = new LinkedHashMap<Object, Object>();
 map.put("Odd Numbers", "000001");
 map.put("Time", "2017-03-08");
 map.put("Tomatoes", "18.0$");
 map.put("New Zealand beef", "100.0$");
 map.put("Pasta", "36.0$");
 map.put("biscotti", "0.0$");
 map.put("Bread", "50.0$");
 map.put("Egg", "46.5$");
 map.put("Hum", "16.0$");
 map.put("Beijing Roast Duck", "99.9$");
 map.put("Beef in Brown Sauce", "38.9$");
 Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
 WizarPosPrint.Print("Transaction record", map);
```
