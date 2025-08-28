package com.kye.utils;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.kye.pda.biz.base.utils.R;
import com.kye.pda.utils.time.DateUtil;
import com.kye.utils.sign.encoder.BASE64Decoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Routing
 * Desc 图片工具类
 * Source
 * Created by Chase on 2019/4/12 21:20
 * Modify by Chase on 2019/4/12 21:20
 * Version 1.0
 */
public class ImageUtil {

    /**
     * 设置背景图片
     *
     * @param view
     * @param res
     * @param inSampleSize
     * @param inPreferredConfig
     */
    public static void setBitmapBackground(View view, @DrawableRes int res,
                                           int inSampleSize, Bitmap.Config inPreferredConfig) {
        if (view == null) {
            return;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = inPreferredConfig;
            if (inSampleSize > 1) {
                options.inSampleSize = inSampleSize;
            }
            Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), res, options);
            view.setBackground(new BitmapDrawable(view.getResources(), bitmap));
        } catch (Exception e) {
            e.printStackTrace();
            view.setBackgroundResource(res);
        }
    }

    /**
     * 图片转base64
     * 注：copy自老代码UploadUtil类的同名方法
     *
     * @param filePath image path
     * @return
     */
    public static String image2Base64(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        String uploadFilePath = compressBitmap(filePath);
        String imageData = "";
        // 将图片转换为base64编码
        FileInputStream in = null;
        File file = null;
        try {
            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
                if (file.exists()) {
                    in = new FileInputStream(file);
                    StringBuffer base64Buf = new StringBuffer();
                    // base64字符为3个有效byte转换
                    // 详细见资料https://blog.csdn.net/u014248939/article/details/53205030
                    byte[] buffer = new byte[1024 * 3];
                    int validCount = 0;
                    while ((validCount = in.read(buffer)) != -1) {
                        // 判断是否已经读取到文件尾
                        if (validCount == buffer.length) {
                            base64Buf.append(Base64.encodeToString(buffer, 0, validCount, Base64.DEFAULT));
                        } else {
                            byte[] copy = Arrays.copyOf(buffer, validCount);
                            base64Buf.append(Base64.encodeToString(copy, 0, validCount, Base64.DEFAULT));
                        }
                    }
                    imageData = base64Buf.toString();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                in = null;
            }
        }
        return imageData.replaceAll("\n", "");
    }

    public static String image2Base64V2(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data;
        String result = "";
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result.replaceAll("\n", "");
    }

    /**
     * 压缩图片
     *
     * @param filePath
     * @return
     */
    private static String compressBitmap(String filePath) {
        return compressBitmap(filePath, 2048, 1152);
    }

    /**
     * 压缩图片
     *
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static String compressBitmap(String filePath, float reqWidth, float reqHeight) {
        File file = new File(filePath);
        FileOutputStream fout = null;
        String path = "temp_" + file.getName();
        String retPath = file.getParent() + "/" + path;
        Bitmap bitmap = null;
        try {
            fout = new FileOutputStream(new File(retPath));
            bitmap = decodeSampledBitmapFromFile(file, reqWidth, reqHeight);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fout = null;
            }

        }
        return retPath;
    }

    /**
     * 拍照图片加水印
     * 右下角
     *
     * @param src       原图
     * @param watermark 水印
     * @return 加水印的原图
     */
    public static Bitmap waterMask(Bitmap src, Bitmap watermark, String address, Activity context) {
        int w = src.getWidth();
        int h = src.getHeight();
        int h2 = watermark.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(result);
        cv.drawBitmap(src, 0, 0, null);

        int paddingLeft = CommonUtil.getDimenInt(context, R.dimen.dp_3);
        int paddingBottom = CommonUtil.getDimenInt(context, R.dimen.dp_3);
        int paddingTop = CommonUtil.getDimenInt(context, R.dimen.dp_7);
        int textSize = CommonUtil.getDimenInt(context, R.dimen.dp_10);
        int textDateSize = CommonUtil.getDimenInt(context, R.dimen.dp_10);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAlpha(115);

        cv.drawBitmap(watermark, paddingLeft, h - h2 - paddingBottom, paint);
        cv.save();
        cv.restore();

        int lineHeight = CommonUtil.getDimenInt(context, R.dimen.dp_10);
        int linePadding = CommonUtil.getDimenInt(context, R.dimen.dp_7);
        int linePaddingBottom = CommonUtil.getDimenInt(context, R.dimen.dp_14);
        int lineX = paddingLeft + h2 + linePadding;
        int lineY = h - lineHeight - linePaddingBottom;
        int lineSY = h - linePaddingBottom;

        cv.drawLine(lineX, lineY + paddingTop, lineX, lineSY + paddingTop, paint);
        cv.save();
        cv.restore();

        int locationLeft = lineX + linePadding;
        int textPaddingB = CommonUtil.getDimenInt(context, R.dimen.dp_39);
        int datePaddingB = CommonUtil.getDimenInt(context, R.dimen.dp_38);
        int textHeight = CommonUtil.getDimenInt(context, R.dimen.dp_30);

        Paint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setAlpha(115);
        textPaint.setTextSize(textSize);
        Rect textRect = new Rect();
        textPaint.getTextBounds(address, 0, 2, textRect);

        cv.drawText(address, locationLeft, h - textPaddingB + textHeight, textPaint);
        cv.save();
        cv.restore();

        textPaint.setTextSize(textDateSize);
        String date = DateUtil.formatComplemetDate1(new Date());
        textPaint.getTextBounds(date, 0, 2, textRect);

        int dateLocationLeft = w - CommonUtil.getDimenInt(context, R.dimen.dp_100);
        cv.drawText(date, dateLocationLeft, h - datePaddingB + textHeight, textPaint);
        cv.save();
        cv.restore();

        if (!src.isRecycled()) {
            src.recycle();
        }
        if (!watermark.isRecycled()) {
            watermark.recycle();
        }
        return result;
    }

    /**
     * base64转换成图片
     *
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 拍照图片加水印
     * 右下角
     *
     * @param src       原图
     * @param watermark 水印
     * @return 加水印的原图
     */
    public static Bitmap waterMaskV2(Bitmap src, Bitmap watermark, String address, Activity context) {
        int w = src.getWidth();
        int h = src.getHeight();
        int h2 = watermark.getHeight();

        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(result);
        cv.drawBitmap(src, 0, 0, null);

        int paddingLeft = CommonUtil.getDimenInt(context, R.dimen.dp_3);
        int paddingBottom = CommonUtil.getDimenInt(context, R.dimen.dp_3);
        int paddingTop = CommonUtil.getDimenInt(context, R.dimen.dp_7);
        int textSize = CommonUtil.getDimenInt(context, R.dimen.dp_20);
        int textDateSize = CommonUtil.getDimenInt(context, R.dimen.dp_20);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAlpha(115);

        cv.drawBitmap(watermark, paddingLeft, h - h2 - paddingBottom, paint);
        cv.save();
        cv.restore();

        int lineHeight = CommonUtil.getDimenInt(context, R.dimen.dp_20);
        int linePadding = CommonUtil.getDimenInt(context, R.dimen.dp_7);
        int linePaddingBottom = CommonUtil.getDimenInt(context, R.dimen.dp_14);
        int lineX = paddingLeft + h2 + linePadding;
        int lineY = h - lineHeight - linePaddingBottom;
        int lineSY = h - linePaddingBottom;

        cv.drawLine(lineX, lineY + paddingTop, lineX, lineSY + paddingTop, paint);
        cv.save();
        cv.restore();

        int locationLeft = lineX + linePadding;
        int textPaddingB = CommonUtil.getDimenInt(context, R.dimen.dp_39);
        int datePaddingB = CommonUtil.getDimenInt(context, R.dimen.dp_38);
        int textHeight = CommonUtil.getDimenInt(context, R.dimen.dp_30);

        Paint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setAlpha(115);
        textPaint.setTextSize(textSize);
        Rect textRect = new Rect();
        textPaint.getTextBounds(address, 0, 2, textRect);
        String date = DateUtil.formatComplemetDate1(new Date());
        cv.drawText(address + " " + date, locationLeft, h - textPaddingB + textHeight, textPaint);
        cv.save();
        cv.restore();

//        textPaint.setTextSize(textDateSize);
//        String date = DateUtil.formatComplemetDate1(new Date());
//        textPaint.getTextBounds(date, 0, 2, textRect);
//
//        int dateLocationLeft = w - CommonUtil.getDimenInt(context, R.dimen.dp_100);
//        cv.drawText(date, dateLocationLeft,h-datePaddingB+textHeight, textPaint);
//        cv.save();
//        cv.restore();

        if (!src.isRecycled()) {
            src.recycle();
        }
        if (!watermark.isRecycled()) {
            watermark.recycle();
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Bitmap getBitmapSvg(int scaleW, Drawable
            vectorDrawable) {

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();

        // 计算宽高缩放率
        float scaleWidth = (float) (scaleW) / (float) width;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleWidth);
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Bitmap getBitmap(Context context, int scaleW, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        return getBitmapSvg(scaleW, drawable);

//        if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
//            return getBitmapSvg(context,scaleW,drawable);
//        } else {
//            throw new IllegalArgumentException("unsupported drawable type");
//        }
    }

    public static Bitmap decodeSampledBitmapFromFile(File imageFile, float reqWidth, float reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions

        Bitmap scaledBitmap = null, bmp = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = reqWidth / reqHeight;

        if (actualHeight > reqHeight || actualWidth > reqWidth) {
            //If Height is greater
            if (imgRatio < maxRatio) {
                imgRatio = reqHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) reqHeight;

            }  //If Width is greater
            else if (imgRatio > maxRatio) {
                imgRatio = reqWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) reqWidth;
            } else {
                actualHeight = (int) reqHeight;
                actualWidth = (int) reqWidth;
            }
        }

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        if (scaledBitmap == null) {
            return null;
        }

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2,
                middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        bmp.recycle();
        ExifInterface exif;
        try {
            exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),
                    scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scaledBitmap;


    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            inSampleSize *= 2;
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static String generateImage(String imgStr) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return "";
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String fileName = "evluateQrcode";
            File fileDir = new File(FileUtils.getPDARoot());
            File file = new File(fileDir, fileName);
            if (file.exists()) {
                file.delete();
            }
            OutputStream out = new FileOutputStream(file.getAbsolutePath());
            out.write(b);
            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static void saveImage(File file, Bitmap bitmap) {
        saveImage(file, bitmap, 100);
    }

    public static void saveImage(File file, Bitmap bitmap, int quality) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveImageWithExif(File originalFile, File destFile, Bitmap bitmap, int quality) throws Exception {
        try {
            // 文件
            if (!destFile.exists() && destFile.isFile()) {
                destFile.createNewFile();
            }
            // 保存 Bitmap 到文件
            FileOutputStream out = new FileOutputStream(destFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();

            // 使用ExifInterface将原始Exif信息写入新文件
            ExifInterface oldExif = new ExifInterface(originalFile.getAbsolutePath());
            ExifInterface newExif = new ExifInterface(destFile.getAbsolutePath());

            // 遍历原始Exif信息，并复制到新文件
            saveExif(oldExif, newExif);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 保存图片并保存源文件的exif信息
     * @param sourceExif 源文件的exif信息
     * @param destFile 目标文件
     * @param bitmap bitmap
     * @param quality 压缩质量
     */
    public static void saveImageWithExif(ExifInterface sourceExif, File destFile, Bitmap bitmap, int quality){
        try {
            // 文件
            if (!destFile.exists() && destFile.isFile()) {
                destFile.createNewFile();
            }
            // 保存 Bitmap 到文件
            FileOutputStream out = new FileOutputStream(destFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
            if (sourceExif==null){
                return;
            }
            // 使用ExifInterface将原始Exif信息写入新文件
            ExifInterface newExif = new ExifInterface(destFile.getAbsolutePath());
            // 遍历原始Exif信息，并复制到新文件
            saveExif(sourceExif, newExif);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取指定颜色的图标
     */
    public static Drawable getIconToColor(Context context, int iconId, int colorId) {
        try {
            VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(context.getResources(), iconId, context.getTheme());
            if (vectorDrawableCompat != null) {
                //你需要改变的颜色
                vectorDrawableCompat.setTint(context.getResources().getColor(colorId));
                return vectorDrawableCompat;
            } else {
                return ContextCompat.getDrawable(context, iconId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存位图为文件
     *
     * @param destfile  目标文件
     * @param bitmap    位图
     * @param quality   压缩质量系数
     * @param maxLength 保存文件压缩后不能超过的最大值，单位 bit
     */
    public static void saveImage(File destfile, Bitmap bitmap, int quality, int maxLength) {
        if (destfile == null || bitmap == null || quality == 0 || maxLength == 0) {
            return;
        }
        long currentSize;
        FileOutputStream fileOutputStream = null;
        try {
            do {
                fileOutputStream = new FileOutputStream(destfile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
                fileOutputStream.flush();

                currentSize = destfile.length();
                // 如果文件大小超过了上限，继续减小 quality
                if (currentSize > maxLength) {
                    quality -= 5; // 可以根据需要调整减小的步长
                    fileOutputStream.close();
                }
            } while (currentSize > maxLength && quality > 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存位图为文件
     *
     * @param destfile      目标文件
     * @param bitmap    位图
     * @param quality   压缩质量系数
     * @param maxLength 保存文件压缩后不能超过的最大值，单位 bit
     */
    public static void saveImage(File destfile, File originalFile, Bitmap bitmap, int quality, int maxLength) {
        if (destfile == null || bitmap == null || quality == 0 || maxLength == 0) {
            return;
        }
        long currentSize;
        FileOutputStream fileOutputStream = null;
        try {
            // 读取原始图像的EXIF数据
            ExifInterface oldExif = new ExifInterface(originalFile.getAbsolutePath());
            do {
                fileOutputStream = new FileOutputStream(destfile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
                fileOutputStream.flush();

                currentSize = destfile.length();
                // 如果文件大小超过了上限，继续减小 quality
                if (currentSize > maxLength) {
                    quality -= 5; // 可以根据需要调整减小的步长
                    fileOutputStream.close();
                }
            } while (currentSize > maxLength && quality > 0);
            ExifInterface newExif = new ExifInterface(destfile.getAbsolutePath());
            saveExif(oldExif, newExif);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 保存位图为文件
     *
     * @param originalFile      源文件
     * @param bitmap    位图
     * @param quality   压缩质量系数
     * @param maxLength 保存文件压缩后不能超过的最大值，单位 bit
     */
    public static void saveImageWithExif(File originalFile, Bitmap bitmap, int quality, int maxLength) {
        if (originalFile == null || bitmap == null || quality == 0 || maxLength == 0) {
            return;
        }
        long currentSize;
        FileOutputStream fileOutputStream = null;
        try {
            // 读取原始图像的EXIF数据
            ExifInterface oldExif = new ExifInterface(originalFile.getAbsolutePath());
            do {
                fileOutputStream = new FileOutputStream(originalFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
                fileOutputStream.flush();

                currentSize = originalFile.length();
                // 如果文件大小超过了上限，继续减小 quality
                if (currentSize > maxLength) {
                    quality -= 5; // 可以根据需要调整减小的步长
                    fileOutputStream.close();
                }
            } while (currentSize > maxLength && quality > 0);
            ExifInterface newExif = new ExifInterface(originalFile.getAbsolutePath());
            saveExif(oldExif, newExif);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }



    /**
     * 从字节数组创建Bitmap，并保留原始Exif信息。
     * @param imageData         字节数组
     * @param originalImageFile 原始图片文件
     * @param destFile          目标文件
     * @return Bitmap对象
     * @throws IOException 如果读取或写入文件时出现错误
     */
    public static Bitmap byteToBitmapWithExif(byte[] imageData, File originalImageFile, File destFile) throws Exception {

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

        // 保存Bitmap到文件
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); // 100%质量压缩
        byte[] bytes = stream.toByteArray();

        // 创建目标文件
        FileOutputStream fos = new FileOutputStream(destFile);
        fos.write(bytes);
        fos.close();

        // 使用ExifInterface将原始Exif信息写入新文件
        ExifInterface oldExif = new ExifInterface(originalImageFile.getAbsolutePath());
        ExifInterface newExif = new ExifInterface(destFile.getAbsolutePath());

        // 遍历原始Exif信息，并复制到新文件
        saveExif(oldExif, newExif);

        // 删除文件
        if (originalImageFile.exists()) {
            originalImageFile.delete();
        }

        // 返回Bitmap
        return bitmap;
    }


    public static void saveExif(ExifInterface oldExif, ExifInterface newExif)  {
        if (oldExif==null){
            return;
        }
        try {
            Class<ExifInterface> cls = ExifInterface.class;
            Field[] fields = cls.getFields();
            for (int i = 0; i < fields.length; i++) {
                String fieldName = fields[i].getName();
                if (!TextUtils.isEmpty(fieldName) && fieldName.startsWith("TAG")) {
                    String fieldValue = fields[i].get(cls).toString();
                    String attribute = oldExif.getAttribute(fieldValue);
                    if (attribute != null) {
                        newExif.setAttribute(fieldValue, attribute);
                    }
                }
            }
            newExif.saveAttributes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
