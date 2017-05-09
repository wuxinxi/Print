# Print <br/>
## <a href="">English</a>
## 导入方式 <br>
1.导入library库即可 <br>
## M680使用方式
```
M680Print.java类

实例化类
M680Print print=new M680Print();
```
1.打印自测
 ```
 print.printours1();
 ```
2.打印一条数据
```
print.printString(String data);
```
3.是否加粗（i=1是 i=0否）
```
 print.bold(int i);
```
4.位置（1中 0右 2左）
```
print.center(int i) ;
```
5.是否加宽（i=1是 i=0否）
```
print.width(int i);
```
6.下划线（i=1是 i=0否）
```
print.underline(int i);
```
7.反白（i=1是 i=0否）
```
print.inverse(int i);
```
8.倒置（i=1是 i=0否）
```
print.reverse(int i);
```
## M680S使用方式
```
PrinterUtil.java 类
PrinterUtil print = new PrinterUtil();
```
1.常规打印文字
```
print.PrinterWrite(byte arryData[],int len);
```
2.打印自测
```
printer.PrinterWrite(printer.printSelf(), printer.printSelf().length);
```
3.常规打印图片
```
print.printBitmap(Bitmap bm, int bitMarginLeft, int bitMarginTop)
```
4.打印文字图片
```
textViewHide.setDrawingCacheEnabled(true);
textViewHide.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                                , View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
textViewHide.layout(0, 0, textViewHide.getMeasuredWidth(), textViewHide.getMeasuredHeight());
                        Bitmap bitmap = textViewHide.getDrawingCache();
myprinter.printBitmap(bitmap, 0, 0);
```
3.详情请见PrinterUtil类
## H510使用方式
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
 WizarPosPrint.Print("交易记录", map);
```
