package com.kye.utils.imageloader.tranform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.kye.pda.biz.common.utils.BuildConfig;

import java.security.MessageDigest;

/**
 * Routing
 * Desc    glide加载图片时设置圆角， 参考以下github的代码
 * Created by MQH on 2019/11/26 16:04
 *
 * @author MQH
 * Version 1.0
 */
public class CustomRoundedCorners extends BitmapTransformation {
    private static final int VERSION = 1;
    private static final String ID = BuildConfig.LIBRARY_PACKAGE_NAME + "CustomRoundedCorners." + VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private final float topLeft;
    private final float topRight;
    private final float bottomLeft;
    private final float bottomRight;

    /**
     * @param topLeft     左上角
     * @param topRight    右上角
     * @param bottomLeft  左下角
     * @param bottomRight 右下角
     */
    public CustomRoundedCorners(float topLeft, float topRight, float bottomLeft, float bottomRight) {
        super();
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);

        Path path = new Path();

        float[] rids = new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft};
        path.addRoundRect(new RectF(0, 0, width, height), rids, Path.Direction.CW);
        canvas.drawPath(path, paint);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CustomRoundedCorners;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
