/*
 * Copyright (c) $year IO Stark
 */

package com.iostark.x10.bitmap;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.iostark.x10.base.annotation.api.API_Beta;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

import java.io.IOException;

/**
 * Bitmap producing operations using EXIF information.
 *
 * @since V0_5_0
 */
@API_Beta
@KeepEntryPoint
public class BitmapEXIFOperation {

    private Bitmap bitmap;

    @KeepEntryPoint
    public BitmapEXIFOperation(final Bitmap originalBitmap) {
        this.bitmap = originalBitmap;
    }

    /**
     * Rotates the original bitmap to the EXIF Orientation value 1.
     *
     * @param exifImagePath EXIF image path
     * @return Bitmap in the orientation toExitOrientation
     * @throws IOException If imageSourcePath cannot be read
     *                     Details: http://www.exiv2.org/Exif2-2.PDF and
     *                     http://sylvana.net/jpegcrop/exif_orientation.html
     *                     for details about EXIF orientation values
     * @since V0_5_0
     */
    @KeepEntryPoint
    public Bitmap rotate(final String exifImagePath) throws IOException {
        final Bitmap bitmap1 = rotate(exifImagePath, 1);
        setCurrentBitmap(bitmap1, true);
        return bitmap1;
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private Bitmap rotate(final String exifImagePath, final int toExitOrientation) throws IOException {
        if (toExitOrientation != 1) {
            throw new UnsupportedOperationException("Rotating bitmap to requested EXIF orientation is not suppoted for now");
        }

        final ExifInterface exifInterface = new ExifInterface(exifImagePath);
        final int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

        final Matrix matrix = new Matrix();
        switch (orientation) {
            case 1:
                return bitmap;
            case 2:
                matrix.setScale(-1, 1);
                break;
            case 3:
                matrix.setRotate(180);
                break;
            case 4:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case 5:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case 6:
                matrix.setRotate(90);
                break;
            case 7:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case 8:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }

        final Bitmap orientedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return orientedBitmap;
    }

    private void setCurrentBitmap(final Bitmap newBitmap, final boolean recyclePreviousBitmap) {
        if (recyclePreviousBitmap) {
            bitmap.recycle();
        }
        bitmap = newBitmap;
    }
}
