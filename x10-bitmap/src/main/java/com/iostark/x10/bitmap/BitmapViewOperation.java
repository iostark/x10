/*
 * Copyright (c) $year IO Stark
 */

package com.iostark.x10.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.iostark.x10.base.annotation.api.API_Beta;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

/**
 * Bitmap producing operation using views.
 *
 * @since V0_5_0
 */
@KeepEntryPoint
@API_Beta
public class BitmapViewOperation {

    /**
     * Constructor.
     *
     * @since V0_5_0
     */
    @KeepEntryPoint
    public BitmapViewOperation() {
    }

    /**
     * Create bitmap whose dimensions equals the view's measured dimension.
     *
     * @param view View
     * @return Bitmap of the view
     * @since V0_5_0
     */
    @KeepEntryPoint
    public Bitmap createBitmap(final View view) {
        final Bitmap bitmap = createBitmapFromView(view, view.getMeasuredWidth(), view.getMeasuredHeight());
        return bitmap;
    }

    private static Bitmap createBitmapFromView(final View view, final int bitmapWidth, final int bitmapHeight) {
        final Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }
}
