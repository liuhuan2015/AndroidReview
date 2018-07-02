# AndroidReview
android 复习
#### 一 . android中的图像
##### 1 . 位图图片
单色位图：只有黑白颜色两种，只用一个二进制位就可以表示一个像素点<br>
24位位图：用了24个二进制位来表示一种颜色，所以对应的位图的每一个像素点占用的空间大小为3 byte<br>
256色位图：8个二进制位，即1 byte<br>
android下的Bitmap一般都是32位的位图（argb）<br>
例：假如要加载一张2560 \* 1504分辨率的图片到内存中，它在内存中占用的空间将是：<br>
2560 \* 1504 \* 4 byte = 15400960 byte = 15040 kb = 14.6875 M.<br>

android中的图片大部分都是位图图片，位图图片的缺点是：放大会有锯齿形状<br>

##### 2 . 矢量图片
还有一种图片是矢量图片，矢量图片是通过计算机将一串线条和图形转换成一系列指令，在计算机中只存储这些指令，而不是像素。<br>
矢量图片看起来没有位图图片真实，但是矢量图片的存储空间要比位图图片小得多，而且矢量图片放大不会失真<br>

#### 二 . android中的图片处理（加载大图，图片的位移，旋转，缩放等操作）
加载大图：<br>
``` java
 //下面是对图片做一些处理的做法
                try {
                    //1.得到图片的宽高信息，有的时候这些信息可能是获取不到的
                    ExifInterface exif = new ExifInterface("/mnt/sdcard/very_large_photo.jpg");
                    int width = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
                    int height = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
                    Log.e("------------", "width : " + width + " height : " + height);

                    //2.获取屏幕的宽高
                    WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);

                    Point point = new Point();
                    wm.getDefaultDisplay().getSize(point);
                    int screenWidth = point.x;
                    int screenHeight = point.y;
                    Log.e("-----------", "screenWidth : " + screenWidth + "screenHeight : " + screenHeight);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    //设置采样率，宽高分别是原图的1/4，总像素个数就变成了原图的1/16
                    //采样率一般是根据图片的分辨率和屏幕的分辨率进行计算或根据业务需求，进行确定的，这里为了简便就直接写了一个4.
                    options.inSampleSize = 4;
                    Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/very_large_photo.jpg", options);
                    //32位的位图，argb
                    iv.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

```
对图片进行一些处理（位移，缩放，翻转等）<br>
``` java
  //效果：绘制原图的倒影
                //原图
                Bitmap srcBitmap = BitmapFactory.decodeFile("/mnt/sdcard/girl.png");
                imagePre.setImageBitmap(srcBitmap);
                //用代码编辑图片，最好都是处理图片在内存中的拷贝，不去处理原图
                Bitmap copyedBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
                //临摹，创建一个画板
                Canvas canvas = new Canvas(copyedBitmap);
                //创建画笔
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                //作画
                Matrix matrix = new Matrix();//按照一比一的比例作画
                //缩放
                matrix.setScale(1.5f, 1.5f);

                //倒影
                //matrix.setScale(1, -1);
                //matrix.postTranslate(0, srcBitmap.getHeight());
                canvas.drawBitmap(srcBitmap, matrix, paint);
                imageAfter.setImageBitmap(copyedBitmap);

```
#### 三 . 涂鸦的小项目（主要使用的是Canvas、Paint等进行的）,支持涂鸦图片的保存。
```java
imageView.setOnTouchListener(new View.OnTouchListener() {
            int startX, startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("-------", "按下");
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("-------", "移动");
                        int newX = (int) event.getX();
                        int newY = (int) event.getY();
                        canvas.drawLine(startX, startY, newX, newY, mPaint);
                        imageView.setImageBitmap(alterBitmap);
                        startX = newX;
                        startY = newY;
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("-------", "抬起");

                        break;
                }
                return true;
            }
        });
```


