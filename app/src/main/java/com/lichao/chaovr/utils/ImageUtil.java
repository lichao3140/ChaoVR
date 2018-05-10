package com.lichao.chaovr.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

/**
 * Created by ChaoLi on 2018/5/10 0010 - 10:03
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public class ImageUtil {

    /**
     * 颜色着色器
     * @param imageView  image
     * @param id  color
     */
    @SuppressWarnings("deprecation")
    public static void colorImageViewDrawable(ImageView imageView, @ColorRes int id) {
        if (imageView == null) {
            return;
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, new ColorStateList(new int[][]{new int[]{}}, new int[]{imageView.getContext().getResources().getColor(id)}));
            imageView.setImageDrawable(drawable);
        }
    }
}
